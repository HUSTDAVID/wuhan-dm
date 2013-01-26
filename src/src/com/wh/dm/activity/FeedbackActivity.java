
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApp;
import com.wh.dm.type.PostResult;
import com.wh.dm.util.NotificationUtil;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class FeedbackActivity extends Activity {

    EditText edtContack;
    EditText edtFeedbackText;
    Button btnSend;
    LinearLayout loadLayout;
    private PostResult postResult;

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
        loadLayout = (LinearLayout) findViewById(R.id.feedback_load);
        TextView loadText = (TextView) findViewById(R.id.txt_pic_load);
        loadText.setText(getString(R.string.feedback_load));
        edtContack = (EditText) findViewById(R.id.edt_feedback_contack);
        edtFeedbackText = (EditText) findViewById(R.id.edt_feedback_text);
        btnSend = (Button) findViewById(R.id.btn_feedback_send);

        edtContack.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                edtFeedbackText.requestFocus();
                return false;
            }
        });

    }

    private class FeedbackTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {

            loadLayout.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            // TODO Auto-generated method stub
            try {
                postResult = ((WH_DMApp) getApplication()).getWH_DMApi().commitFeedback(params[0],
                        params[1]);
                return postResult.getResult();
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
                String msg = "提交失败";
                if (postResult != null) {
                    msg = msg + postResult.getMsg();
                }
                NotificationUtil.showShortToast(msg, FeedbackActivity.this);
            }
            loadLayout.setVisibility(View.GONE);
            super.onPostExecute(result);
        }
    }
}
