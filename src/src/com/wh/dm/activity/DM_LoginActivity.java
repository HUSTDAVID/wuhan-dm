
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DM_LoginActivity extends Activity {

    EditText edtEmail;
    EditText edtPasswd;
    Button btnLogin;
    Button btnForgetPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        initViews();
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
    }

}
