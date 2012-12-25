
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.type.VoteResultPercent;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.widget.VoteResultAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Vote1Activity extends Activity {
    /** Called when the activity is first created. */

    private ListView listview;
    private View lvHeader;
    private TextView txtNum;
    private TextView txtName;
    private View lvFooter;
    private Button btnClose;
    private ImageButton btnBack;
    private VoteResultAdapter adapter;

    private int aid = 0;
    private String voteNum;
    private String voteName;

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

        LayoutInflater inflater = getLayoutInflater();
        lvHeader = inflater.inflate(R.layout.vote_info_header, null);
        lvFooter = inflater.inflate(R.layout.vote_info_footer, null);
        aid = getIntent().getIntExtra("aid", 0);
        voteName = getIntent().getStringExtra("name");

        txtName = (TextView) lvHeader.findViewById(R.id.vote_ing_2);
        txtName.setText(voteName);

        txtNum = (TextView) lvHeader.findViewById(R.id.vote_ing_5);
        btnClose = (Button) lvFooter.findViewById(R.id.vote_button_close);
        btnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Vote1Activity.this, VotedListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        listview = (ListView) findViewById(R.id.lv_vote_result);
        listview.setDivider(null);
        listview.addHeaderView(lvHeader);
        listview.addFooterView(lvFooter);
        adapter = new VoteResultAdapter(this);

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

        wh_dmApp = (WH_DMApp) getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        handler.sendEmptyMessage(MSG_GET_VOTE_NUM);
        handler.sendEmptyMessage(MSG_GET_VOTE_RESULT);

    }

    private void setProgress(ProgressBar progress, int percent) {

        LayoutParams params = progress.getLayoutParams();
        params.width = (int) (percent * 3);
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
                adapter.setList(result);
                listview.setAdapter(adapter);
            } else {
                NotificationUtil.showShortToast(getResources().getString(R.string.get_not_result),
                        Vote1Activity.this);
            }
            super.onPostExecute(result);
        }

    }

}
