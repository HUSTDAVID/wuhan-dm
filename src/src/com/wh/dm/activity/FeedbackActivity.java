
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApp;
import com.wh.dm.util.NotificationUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class FeedbackActivity extends Activity {

    EditText edtContack;
    EditText edtFeedbackText;
    Button btnSend;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_feedback);

        initViews();

        ImageButton btnBack = (ImageButton) findViewById(R.id.Btn_back_header2);
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });

        btnSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtContack.getText().toString().trim().equals("")
                        || edtFeedbackText.getText().toString().trim().equals("")) {
                    NotificationUtil.showShortToast("请填写完整", FeedbackActivity.this);
                } else {
                    FeedbackTask feedbackTask = new FeedbackTask();
                    feedbackTask.execute(edtContack.getText().toString(), edtFeedbackText.getText()
                            .toString());
                }
            }
        });
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
        txtHeader.setText(getResources().getString(R.string.feedback));

        // init views
        edtContack = (EditText) findViewById(R.id.edt_feedback_contack);
        edtFeedbackText = (EditText) findViewById(R.id.edt_feedback_text);
        btnSend = (Button) findViewById(R.id.btn_feedback_send);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

    private class FeedbackTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {

            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            // TODO Auto-generated method stub
            try {
                return ((WH_DMApp) getApplication()).getWH_DMApi().commitFeedback(params[0],
                        params[1]);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {
                NotificationUtil.showShortToast("提交成功", FeedbackActivity.this);
                finish();
            } else {
                NotificationUtil.showShortToast("提交失败", FeedbackActivity.this);
            }
            progressDialog.dismiss();
            super.onPostExecute(result);
        }
    }
}
