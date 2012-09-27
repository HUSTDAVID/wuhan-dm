
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity {

    EditText edtEmail;
    EditText edtPasswd;
    CheckBox cbShowPw;
    Button btnRigester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        initViews();
    }

    private void initViews() {

        // init header
        TextView txtHeader = (TextView) findViewById(R.id.txt_header_title2);
        txtHeader.setText(getResources().getString(R.string.register));

        // init view
        edtEmail = (EditText) findViewById(R.id.edt_register_emial);
        edtPasswd = (EditText) findViewById(R.id.edt_register_passwd);
        cbShowPw = (CheckBox) findViewById(R.id.cb_show_passwd);
        btnRigester = (Button) findViewById(R.id.btn_register_send);
    }

}
