
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FeedbackActivity extends Activity {

    EditText edtContack;
    EditText edtFeedbackText;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_feedback);

        initViews();
    }

    private void initViews() {

        // init header
        TextView txtHeader = (TextView) findViewById(R.id.txt_header_title2);
        txtHeader.setText(getResources().getString(R.string.feedback));

        // init views
        edtContack = (EditText) findViewById(R.id.edt_feedback_contack);
        edtFeedbackText = (EditText) findViewById(R.id.edt_feedback_text);
        btnSend = (Button) findViewById(R.id.btn_feedback_send);

    }
}
