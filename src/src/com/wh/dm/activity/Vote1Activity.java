
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.type.VoteResultPercent;

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

import java.util.ArrayList;

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
    private ArrayList<VoteResultPercent> voteResultList = null;
    private int MSG_GET_VOTE_NUM = 0;
    private int MSG_GET_VOTE_RESULT = 1;
    private GetVoteNumTask getVoteNumTask = null;
    private GetVoteResultTask getVoteResultTask = null;
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

                if (getVoteResultTask != null) {
                    getVoteResultTask.cancel(true);
                    getVoteResultTask = null;
                }
                getVoteResultTask = new GetVoteResultTask();
                getVoteResultTask.execute();
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
        // progressBar3 = (ProgressBar) findViewById(R.id.pro_vote_result3);
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
        handler.sendEmptyMessage(MSG_GET_VOTE_RESULT);

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

    class GetVoteResultTask extends AsyncTask<Void, Void, ArrayList<VoteResultPercent>> {

        @Override
        protected ArrayList<VoteResultPercent> doInBackground(Void... params) {

            try {
                voteResultList = wh_dmApi.getVoteResultPercent(aid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return voteResultList;
        }

        @Override
        protected void onPostExecute(ArrayList<VoteResultPercent> result) {

            if (result != null && result.size() > 0) {
                setProgress(progressBar1, (int) result.get(0).getPercent());
                TextView txtVote1 = (TextView) findViewById(R.id.txt_vote_result1);
                txtVote1.setText("1." + result.get(0).getItem());
                TextView txtPercent1 = (TextView) findViewById(R.id.txt_vote_percent1);
                txtPercent1.setText(String.valueOf(result.get(0).getPercent()) + "%");

                setProgress(progressBar2, (int) result.get(1).getPercent());
                TextView txtVote2 = (TextView) findViewById(R.id.txt_vote_result2);
                txtVote2.setText("2." + result.get(1).getItem());
                TextView txtPercent2 = (TextView) findViewById(R.id.txt_vote_percent2);
                txtPercent2.setText(String.valueOf(result.get(1).getPercent()) + "%");

                // setProgress(progressBar3, (int) result.get(2).getPercent());
                // TextView txtVote3 = (TextView)
                // findViewById(R.id.txt_vote_result3);
                // txtVote3.setText("3." + result.get(2).getItem());
                // TextView txtPercent3 = (TextView)
                // findViewById(R.id.txt_vote_percent3);
                // txtPercent3.setText(String.valueOf(result.get(2).getPercent())
                // + "%");
            } else {
                setProgress(progressBar1, 43);
                setProgress(progressBar2, 15);
                // setProgress(progressBar3, 42);
            }

            super.onPostExecute(result);
        }

    }

}
