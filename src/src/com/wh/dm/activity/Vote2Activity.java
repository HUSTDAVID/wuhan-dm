
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.preference.Preferences;
import com.wh.dm.type.VoteResult;
import com.wh.dm.type.VoteResultPercent;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.widget.VoteChoiceAdapter;
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
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Vote2Activity extends Activity {

    private ImageButton btnBack;

    private ListView listView;
    private View lvHeader;
    private TextView txtNum;
    private TextView txtName;
    private TextView txtInfo;
    private View lvFooter;
    private Button btn_close;
    private LinearLayout loadLayout;
    private VoteChoiceAdapter choiceAdapter;
    private VoteResultAdapter resultAdapter;
    private String[] notes;
    private String myChioce;
    private boolean doVoting = false;
    private ArrayList<VoteResultPercent> voteResultList = null;

    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private int aid;
    private String voteName;
    private boolean enable;
    private String voteNum = "0";
    private static final int MSG_GET_VOTE_NUM = 0;
    private static final int MSG_GET_VOTE_RESULT = 1;
    private static final int MSG_POST_VOTE = 2;
    private GetVoteNumTask getVoteNumTask = null;
    private GetVoteResultTask getVoteResultTask = null;
    private PostVoteTask postVoteTask = null;
    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_GET_VOTE_NUM:
                    if (getVoteNumTask != null) {
                        getVoteNumTask.cancel(true);
                        getVoteNumTask = null;
                    }
                    getVoteNumTask = new GetVoteNumTask();
                    getVoteNumTask.execute();
                    break;
                case MSG_GET_VOTE_RESULT:
                    if (getVoteResultTask != null) {
                        getVoteResultTask.cancel(true);
                        getVoteResultTask = null;
                    }
                    getVoteResultTask = new GetVoteResultTask();
                    getVoteResultTask.execute();
                    break;
                case MSG_POST_VOTE:
                    if (postVoteTask != null) {
                        postVoteTask.cancel(true);
                        postVoteTask = null;
                    }
                    postVoteTask = new PostVoteTask();
                    postVoteTask.execute();
                    break;
            }

            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        setContentView(R.layout.activity_vote2);
        LayoutParams params = getWindow().getAttributes();
        params.width = LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

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

        loadLayout = (LinearLayout) findViewById(R.id.vote_load);

        LayoutInflater inflater = getLayoutInflater();
        lvHeader = inflater.inflate(R.layout.vote_info_header, null);
        lvFooter = inflater.inflate(R.layout.vote_info_footer, null);

        enable = getIntent().getBooleanExtra("enable", false);
        aid = getIntent().getIntExtra("aid", 0);
        voteName = getIntent().getStringExtra("name");
        txtName = (TextView) lvHeader.findViewById(R.id.vote_ing_2);
        txtName.setText(voteName);
        txtNum = (TextView) lvHeader.findViewById(R.id.vote_ing_5);
        txtInfo = (TextView) lvHeader.findViewById(R.id.vote_ing_3);
        btn_close = (Button) lvFooter.findViewById(R.id.vote_button_close);
        btn_close.setText("¹Ø±Õ");
        btn_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (doVoting) {
                    Intent intent = new Intent();
                    intent.putExtra("voting", true);
                    Vote2Activity.this.setResult(0, intent);
                }
                Vote2Activity.this.finish();

            }

        });

        wh_dmApp = (WH_DMApp) getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        handler.sendEmptyMessage(MSG_GET_VOTE_NUM);
        listView = (ListView) findViewById(R.id.lv_vote_choice);
        resultAdapter = new VoteResultAdapter(this);
        notes = getIntent().getStringArrayExtra("votenote");
        listView.setDivider(null);
        listView.addHeaderView(lvHeader);
        listView.addFooterView(lvFooter);

        if (enable) {

            choiceAdapter = new VoteChoiceAdapter(this);
            choiceAdapter.setList(notes);
            listView.setAdapter(choiceAdapter);

            listView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {

                    if (position > 0) {
                        view.setSelected(true);
                        myChioce = notes[position - 1];
                        handler.sendEmptyMessage(MSG_POST_VOTE);
                    }
                }
            });

        } else {
            // show result
            loadLayout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            txtInfo.setText(getString(R.string.vote_result));
            handler.sendEmptyMessage(MSG_GET_VOTE_RESULT);
            listView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {

                }
            });
        }

    }

    @Override
    public void onBackPressed() {

        if (doVoting) {
            Intent intent = new Intent();
            intent.putExtra("voting", true);
            Vote2Activity.this.setResult(0, intent);
        }
        super.onBackPressed();
    }

    //
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

    //
    class GetVoteResultTask extends AsyncTask<Void, Void, ArrayList<VoteResultPercent>> {

        @Override
        protected void onPreExecute() {

            loadLayout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            super.onPreExecute();
        }

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
                resultAdapter.setList(result);
                listView.setAdapter(resultAdapter);
                txtInfo.setText(getString(R.string.vote_result));
                listView.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long arg3) {

                    }
                });
                handler.sendEmptyMessage(MSG_GET_VOTE_NUM);
            } else {
                NotificationUtil.showShortToast(getResources().getString(R.string.get_not_result),
                        Vote2Activity.this);
            }
            loadLayout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            super.onPostExecute(result);
        }

    }

    // post vote
    class PostVoteTask extends AsyncTask<Void, Void, VoteResult> {

        Exception reason;

        @Override
        protected VoteResult doInBackground(Void... params) {

            VoteResult result = new VoteResult();
            try {
                result = wh_dmApi.postVote(aid, myChioce,
                        Preferences.getMachineId(Vote2Activity.this));
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(VoteResult result) {

            if (result.isResult()) {
                handler.sendEmptyMessage(MSG_GET_VOTE_RESULT);
                doVoting = true;
            } else {
                Toast.makeText(Vote2Activity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            }

        }

    }

}
