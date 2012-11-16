
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.type.VoteItem;
import com.wh.dm.widget.VoteChoiceAdapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Vote2Activity extends Activity {
    private Button btn_close;

    private ImageButton btnBack;
    private TextView txtName;
    private ListView listView;
    private VoteChoiceAdapter adapter;
    private GetVoteitemsTask getVoteitemsTask = null;

    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private ArrayList<VoteItem> voteItems;
    private int aid;
    private String voteName;
    private final int MSG_GET_VOTE_ITEM = 0;
    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_GET_VOTE_ITEM:
                    if (getVoteitemsTask != null) {
                        getVoteitemsTask.cancel(true);
                        getVoteitemsTask = null;
                    }
                    getVoteitemsTask = new GetVoteitemsTask();
                    getVoteitemsTask.execute();
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

        wh_dmApp = (WH_DMApp) this.getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        aid = getIntent().getIntExtra("aid", 0);
        voteName = getIntent().getStringExtra("name");
        txtName = (TextView) findViewById(R.id.vote_ing_2);
        txtName.setText(voteName);

        listView = (ListView) findViewById(R.id.lv_vote_choice);
        listView.setDivider(null);
        adapter = new VoteChoiceAdapter(this);
        handler.sendEmptyMessage(MSG_GET_VOTE_ITEM);

        btn_close = (Button) findViewById(R.id.vote_button_close);
        btn_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        btnBack = (ImageButton) findViewById(R.id.img_header3_back);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

    }

    class GetVoteitemsTask extends AsyncTask<Void, Void, ArrayList<VoteItem>> {

        Exception reason;

        @Override
        protected ArrayList<VoteItem> doInBackground(Void... params) {

            try {
                voteItems = wh_dmApi.getVoteItems(aid);
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
            }
            return voteItems;
        }

        @Override
        protected void onPostExecute(ArrayList<VoteItem> result) {

            adapter.setList(result);
            listView.setAdapter(adapter);
            super.onPostExecute(result);
        }

    }
}
