
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.preference.Preferences;
import com.wh.dm.service.PushService;
import com.wh.dm.type.PostResult;
import com.wh.dm.util.NotificationUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class RegisterActivity extends Activity {

    private static final int MSG_REGISTER = 0;
    private EditText edtEmail;
    private EditText edtPasswd;
    private EditText edtPasswdAgain;
    private CheckBox cbShowPw;
    private Button btnRigester;
    private PostResult postResult;
    private WH_DMApi wh_dmApi;
    private RegisterTask registerTask = null;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == MSG_REGISTER) {
                if (registerTask != null) {
                    registerTask.cancel(true);
                    registerTask = null;
                }
                registerTask = new RegisterTask();
                registerTask.execute(getEmail(), getPassword(), getMachine());
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        wh_dmApi = ((WH_DMApp) getApplication()).getWH_DMApi();
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

    private void initViews() {

        // init header
        TextView txtHeader = (TextView) findViewById(R.id.txt_header_title2);
        txtHeader.setText(getResources().getString(R.string.register));

        // init view
        edtEmail = (EditText) findViewById(R.id.edt_register_emial);
        edtPasswd = (EditText) findViewById(R.id.edt_register_passwd);
        edtPasswdAgain = (EditText) findViewById(R.id.edt_register_passwd_again);
        cbShowPw = (CheckBox) findViewById(R.id.cb_show_passwd);
        cbShowPw.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    edtPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtPasswdAgain.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                } else {
                    edtPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtPasswdAgain.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                }

            }

        });
        btnRigester = (Button) findViewById(R.id.btn_register_send);
        btnRigester.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (IsValidate()) {
                    handler.sendEmptyMessage(MSG_REGISTER);
                }

            }

        });
        ImageButton btnBack = (ImageButton) findViewById(R.id.Btn_back_header2);
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });

        edtEmail.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (IsValidate()) {
                    handler.sendEmptyMessage(MSG_REGISTER);
                }
                return false;
            }
        });
        edtPasswd.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (IsValidate()) {
                    handler.sendEmptyMessage(MSG_REGISTER);
                }
                return false;
            }
        });

    }

    private String getEmail() {

        return edtEmail.getText().toString();
    }

    private String getPassword() {

        return edtPasswd.getText().toString();
    }

    private String getMachine() {

        return Preferences.getMachineId(this);
    }

    private boolean IsValidate() {

        String email = edtEmail.getText().toString();
        String password = edtPasswd.getText().toString();
        String passwordAgain = edtPasswdAgain.getText().toString();
        if (email.length() == 0 || email == null) {
            NotificationUtil.showShortToast(getString(R.string.mail_toast), RegisterActivity.this);
            return false;
        }
        if (password.length() == 0 || password == null) {
            NotificationUtil.showShortToast(getString(R.string.password_toast),
                    RegisterActivity.this);
            return false;
        }
        if (!password.equals(passwordAgain)) {
            NotificationUtil.showShortToast(getString(R.string.different_password),
                    RegisterActivity.this);
            edtPasswd.setText("");
            edtPasswdAgain.setText("");
            return false;
        }
        if (password.length() < 6) {
            NotificationUtil.showShortToast(getString(R.string.short_password),
                    RegisterActivity.this);
            edtPasswd.setText("");
            edtPasswdAgain.setText("");
        }
        return true;

    }

    private class RegisterTask extends AsyncTask<String, Void, Boolean> {
        Exception reason = null;

        @Override
        protected Boolean doInBackground(String... params) {

            boolean isRegister = false;
            try {
                postResult = wh_dmApi.register(params[0], params[1], params[2]);
                isRegister = postResult.getResult();
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
                return false;
            }
            if (isRegister) {
                return true;
            } else {
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {
                WH_DMApp.isLogin = true;
                WH_DMApp.isLoginById = true;
                NotificationUtil.showShortToast(getString(R.string.register_sucesses),
                        RegisterActivity.this);
                Preferences.saveUser(RegisterActivity.this, getEmail(), getPassword());
                startService(new Intent(RegisterActivity.this, PushService.class));
                Intent intent = new Intent(RegisterActivity.this, SettingActivity.class);
                startActivity(intent);
            } else {
                String msg = "";
                if (postResult != null) {
                    msg = postResult.getMsg();
                }
                NotificationUtil.showShortToast(getString(R.string.register_fails) + ":" + msg,
                        RegisterActivity.this);
            }
            super.onPostExecute(result);
        }

    }

}
