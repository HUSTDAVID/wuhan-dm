
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends Activity {

    EditText edtEmail;
    EditText edtPasswd;
    Button btnLogin;
    Button btnForgetPw;
    TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        initViews();
    }

    public void onResume() {

        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {

        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initViews() {

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

}
