
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.widget.VoteChoiceAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class Vote2Activity extends Activity {
    private Button btn_close;

    private ImageButton btnBack;
    private TextView txtName;
    private ListView listView;
    private VoteChoiceAdapter adapter;
    private TextView txtNum;
    private boolean isMore = false;
    private boolean[] choice;
    private String[] notes;
    private Button lastChoice;

    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private int aid;
    private String voteName;
    private String pic;
    private String des;
    private String voteNum = "0";
    private int MSG_GET_VOTE_NUM = 0;
    private GetVoteNumTask getVoteNumTask = null;
    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == MSG_GET_VOTE_NUM) {
                if (getVoteNumTask != null) {
                    getVoteNumTask.cancel(true);
                    getVoteNumTask = null;
                }
                getVoteNumTask = new GetVoteNumTask();
                getVoteNumTask.execute();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        setContentView(R.layout.activity_vote2);

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
        voteName = getIntent().getStringExtra("name");
        pic = getIntent().getStringExtra("pic");
        isMore = getIntent().getBooleanExtra("ismore", false);
        des = getIntent().getStringExtra("des");
        notes = getIntent().getStringArrayExtra("votenote");
        choice = new boolean[notes.length];
        txtName = (TextView) findViewById(R.id.vote_ing_2);
        txtName.setText(voteName);
        txtNum = (TextView) findViewById(R.id.vote_ing_5);

        wh_dmApp = (WH_DMApp) getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        handler.sendEmptyMessage(MSG_GET_VOTE_NUM);

        listView = (ListView) findViewById(R.id.lv_vote_choice);
        listView.setDivider(null);
        adapter = new VoteChoiceAdapter(this);
        adapter.setList(notes);
        listView.setAdapter(adapter);

        btn_close = (Button) findViewById(R.id.vote_button_close);
        btn_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Vote2Activity.this, VoteWatchResultActivity.class);
                if (isMore) {
                    intent.putExtra("ismore", true);
                    String vtitle = "";
                    for (int i = 0; i < choice.length; i++) {
                        if (choice[i]) {
                            if (vtitle.equals("")) {
                                vtitle = notes[i];
                            } else {
                                vtitle = vtitle + "," + notes[i];
                            }
                        }
                    }
                    if (vtitle.equals("")) {
                        NotificationUtil.showShortToast(
                                getResources().getString(R.string.select_choice),
                                Vote2Activity.this);
                    } else {
                        // intent.putExtra("vtitle", vtitle);
                        intent.putExtra("aid", aid);
                        intent.putExtra("des", des);
                        intent.putExtra("pic", pic);
                        intent.putExtra("name", voteName);
                        startActivity(intent);
                    }

                } else {
                    intent.putExtra("ismore", false);
                    String vtitle = "";
                    for (int i = 0; i < choice.length; i++) {
                        if (choice[i]) {
                            vtitle = notes[i];
                            break;
                        }
                    }
                    if (vtitle.equals("")) {
                        NotificationUtil.showShortToast(
                                getResources().getString(R.string.select_choice),
                                Vote2Activity.this);
                    } else {
                        // intent.putExtra("vtitle", vtitle);
                        intent.putExtra("aid", aid);
                        intent.putExtra("des", des);
                        intent.putExtra("name", voteName);
                        intent.putExtra("pic", pic);
                        startActivity(intent);
                    }
                }

            }
        });

        btnBack = (ImageButton) findViewById(R.id.img_header3_back);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {

                if (isMore) {
                    Button btn = (Button) view.findViewById(R.id.btn_vote_item);
                    if (btn.isSelected()) {
                        btn.setSelected(false);
                        choice[position] = false;
                    } else {
                        btn.setSelected(true);
                        choice[position] = true;
                    }
                } else {
                    if (lastChoice != null) {
                        lastChoice.setSelected(false);
                    }
                    Button btn = (Button) view.findViewById(R.id.btn_vote_item);
                    lastChoice = btn;
                    for (int i = 0; i < choice.length; i++) {
                        if (i == position) {
                            choice[i] = true;
                            btn.setSelected(true);
                        } else {
                            choice[i] = false;
                        }
                    }
                }
                if (adapter != null) {
                    adapter.setSelect(choice);
                }
            }
        });

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
