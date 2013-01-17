
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.type.PostResult;
import com.wh.dm.util.NotificationUtil;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ForgetPasswdActivity extends Activity {

    private Button btnSend;
    private EditText edtText;
    private static final int MSG_FIND_PASSWORD = 1;
    private FindPasswordTask findPasswordTask = null;
    private String email = "";
    private WH_DMApi wh_dmApi = null;
    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_FIND_PASSWORD:
                    if (findPasswordTask != null) {
                        findPasswordTask.cancel(true);
                        findPasswordTask = null;
                    }
                    findPasswordTask = new FindPasswordTask();
                    findPasswordTask.execute();
                    break;
            }
            super.handleMessage(msg);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forget_password);
        WH_DMApp app = (WH_DMApp) getApplication();
        wh_dmApi = app.getWH_DMApi();
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

        ImageButton btnBack = (ImageButton) findViewById(R.id.Btn_back_header2);
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });
        TextView txtHeader = (TextView) findViewById(R.id.txt_header_title2);
        txtHeader.setText(getResources().getString(R.string.forget_passwd));

        btnSend = (Button) findViewById(R.id.btn_forget_pw_send);
        edtText = (EditText) findViewById(R.id.edt_email);
        btnSend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                email = edtText.getText().toString();
                if (email != null && email.length() > 0) {
                    handler.sendEmptyMessage(MSG_FIND_PASSWORD);
                } else {
                    NotificationUtil.showShortToast(getString(R.string.input_email),
                            ForgetPasswdActivity.this);
                }

            }
        });

        edtText.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                email = edtText.getText().toString();
                if (email != null && email.length() > 0) {
                    handler.sendEmptyMessage(MSG_FIND_PASSWORD);
                } else {
                    NotificationUtil.showShortToast(getString(R.string.input_email),
                            ForgetPasswdActivity.this);
                }
                return false;
            }
        });
    }

    class FindPasswordTask extends AsyncTask<Void, Void, PostResult> {

        @Override
        protected PostResult doInBackground(Void... params) {

            PostResult result = null;
            try {
                result = wh_dmApi.findPassword(email);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(PostResult result) {

            if (result != null) {
                if (result.getResult()) {
                    NotificationUtil.showShortToast(getString(R.string.find_password_ok),
                            ForgetPasswdActivity.this);
                } else {
                    NotificationUtil.showShortToast(
                            getString(R.string.find_password_fail) + result.getMsg(),
                            ForgetPasswdActivity.this);
                }
            } else {
                if (!WH_DMApp.isConnected) {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            ForgetPasswdActivity.this);
                }
            }
            super.onPostExecute(result);
        }

    }
}
