
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.preference.Preferences;
import com.wh.dm.service.PushService;
import com.wh.dm.type.Magazine;
import com.wh.dm.type.PostResult;
import com.wh.dm.util.NotificationUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class LoginActivity extends Activity {

    EditText edtEmail;
    EditText edtPasswd;
    Button btnLogin;
    Button btnForgetPw;
    TextView txtRegister;
    private static final int MSG_LOGIN = 0;
    private static final int MSG_GET_SUBCRIBED = 1;
    private LoginTask loginTask = null;
    private GetSubcribedMagazine getSubcribedTask = null;
    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private DatabaseImpl databaseImpl;
    private LinearLayout loadLayout;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == MSG_LOGIN) {
                if (loginTask != null) {
                    loginTask.cancel(true);
                    loginTask = null;
                }
                loginTask = new LoginTask();
                loginTask.execute(getEmail(), getPassword(), getMachineId());
            } else if (msg.what == MSG_GET_SUBCRIBED) {
                if (getSubcribedTask != null) {
                    getSubcribedTask.cancel(true);
                    getSubcribedTask = null;
                }
                getSubcribedTask = new GetSubcribedMagazine();
                getSubcribedTask.execute();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        init();
        initViews();
    }

    @Override
    public void onResume() {

        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {

        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void init() {

        wh_dmApp = (WH_DMApp) getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        databaseImpl = wh_dmApp.getDatabase();
    }

    private void initViews() {

        loadLayout = (LinearLayout) findViewById(R.id.login_load);
        TextView txtInfo = (TextView) findViewById(R.id.txt_pic_load);
        txtInfo.setText(getString(R.string.logining));
        // init header
        TextView txtHeader = (TextView) findViewById(R.id.txt_header3_title);
        txtHeader.setText(getResources().getString(R.string.login));
        ImageView logo = (ImageView) findViewById(R.id.imageView2);
        logo.setVisibility(View.GONE);
        TextView txtRegister = (TextView) findViewById(R.id.txt_total_reply);
        txtRegister.setText(getResources().getString(R.string.register));

        // init view
        edtEmail = (EditText) findViewById(R.id.edt_login_emial);
        edtPasswd = (EditText) findViewById(R.id.edt_login_passwd);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (IsValidate()) {
                    handler.sendEmptyMessage(MSG_LOGIN);
                }
            }
        });
        btnForgetPw = (Button) findViewById(R.id.btn_login_forget_pw);
        btnForgetPw.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, ForgetPasswdActivity.class);
                startActivity(intent);
            }

        });
        txtRegister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }

        });

        ImageButton btnBack = (ImageButton) findViewById(R.id.img_header3_back);
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });

    }

    private String getEmail() {

        return edtEmail.getText().toString();
    }

    private String getPassword() {

        return edtPasswd.getText().toString();
    }

    private String getMachineId() {

        return Preferences.getMachineId(this);
    }

    private boolean IsValidate() {

        String email = edtEmail.getText().toString();
        String password = edtPasswd.getText().toString();
        if (email.length() == 0 || email == null) {
            NotificationUtil.showShortToast(getString(R.string.mail_toast), LoginActivity.this);
            return false;
        }
        if (password.length() == 0 || password == null) {
            NotificationUtil.showShortToast(getString(R.string.password_toast), LoginActivity.this);
            return false;
        }
        return true;

    }

    private class LoginTask extends AsyncTask<String, Void, PostResult> {
        Exception reason = null;

        @Override
        protected void onPreExecute() {

            loadLayout.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected PostResult doInBackground(String... params) {

            PostResult result;
            try {
                result = ((WH_DMApp) getApplication()).getWH_DMApi().login2(params[0], params[1],
                        params[2]);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                reason = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(PostResult result) {

            loadLayout.setVisibility(View.GONE);
            if (result != null) {
                if (result.getResult()) {
                    NotificationUtil.showShortToast(getString(R.string.login_sucesses),
                            LoginActivity.this);
                    WH_DMApp.isLogin = true;
                    Preferences.saveUser(LoginActivity.this, getEmail(), getPassword());
                    handler.sendEmptyMessage(MSG_GET_SUBCRIBED);
                    finish();
                } else {
                    String test = getString(R.string.login_fails) + "," + result.getMsg();
                    NotificationUtil.showShortToast(test, LoginActivity.this);
                }

            } else {
                if (wh_dmApp.isConnected()) {

                } else {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            LoginActivity.this);
                }
            }
            super.onPostExecute(result);
        }

    }

    private class GetSubcribedMagazine extends AsyncTask<Void, Void, ArrayList<Magazine>> {
        Exception reason = null;

        @Override
        protected ArrayList<Magazine> doInBackground(Void... params) {

            ArrayList<Magazine> magazines = null;
            try {
                magazines = wh_dmApi.getSubcribedMagazines();
                return magazines;
            } catch (Exception e) {
                reason = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Magazine> result) {

            if (result != null) {
                databaseImpl.deletePostMessage();
                Preferences.setPostMessage(LoginActivity.this, 0);
                startService(new Intent(LoginActivity.this, PushService.class));
                databaseImpl.addMagazines(result);
                sendBroadcast(new Intent(WH_DMApp.INTENT_ACTION_SUBCRIBE_CHANGE));
            }
            super.onPostExecute(result);
        }

    }

}
