
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Vote1Activity extends Activity {
    /** Called when the activity is first created. */

    ProgressBar progressBar1;
    ProgressBar progressBar2;
    ProgressBar progressBar3;
    private ImageButton btnBack;
    private TextView txtNum;
    private Button btnClose;

    private int aid = 0;
    private String voteNum;

    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private int MSG_GET_VOTE_NUM = 0;
    private int MSG_GET_VOTE_RESULT = 1;
    private GetVoteNumTask getVoteNumTask = null;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == MSG_GET_VOTE_NUM) {
                if (getVoteNumTask != null) {
                    getVoteNumTask.cancel(true);
                    getVoteNumTask = null;
                }
                getVoteNumTask = new GetVoteNumTask();
                getVoteNumTask.execute();
            } else if (msg.what == MSG_GET_VOTE_RESULT) {

            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        setContentView(R.layout.activity_vote1);
        init();
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

        aid = getIntent().getIntExtra("aid", 0);

        txtNum = (TextView) findViewById(R.id.vote_ing_5);
        progressBar1 = (ProgressBar) findViewById(R.id.pro_vote_result1);
        progressBar2 = (ProgressBar) findViewById(R.id.pro_vote_result2);
        progressBar3 = (ProgressBar) findViewById(R.id.pro_vote_result3);
        btnBack = (ImageButton) findViewById(R.id.img_header3_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Vote1Activity.this, VotedListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        btnClose = (Button) findViewById(R.id.vote_button_close);
        btnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Vote1Activity.this, VotedListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        wh_dmApp = (WH_DMApp) getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        handler.sendEmptyMessage(MSG_GET_VOTE_NUM);
        setProgress(progressBar1, 43);
        setProgress(progressBar2, 15);
        setProgress(progressBar3, 42);

    }

    private void setProgress(ProgressBar progress, int percent) {

        LayoutParams params = progress.getLayoutParams();
        params.width = (int) (percent * 3.5);
        progress.setLayoutParams(params);
    }

    class GetVoteNumTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            try {
                voteNum = wh_dmApi.getVoteNum(aid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            txtNum.setText(voteNum);
            super.onPostExecute(result);
        }

    }

}
