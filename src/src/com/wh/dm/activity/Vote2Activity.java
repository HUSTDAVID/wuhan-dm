
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.type.VoteItem;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Vote2Activity extends Activity {
    private Button btn_close;
    private Button votebutton1;
    private Button votebutton2;
    private Button votebutton3;
    private ImageButton btnBack;
    private TextView txtName;

    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private ArrayList<VoteItem> voteItems;
    private int aid;
    private String voteName;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote2);

        init();

        votebutton1 = (Button) findViewById(R.id.vote_button1);
        votebutton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Vote2Activity.this, VoteWatchResultActivity.class);
                startActivity(intent);

            }
        });
        votebutton2 = (Button) findViewById(R.id.vote_button2);
        votebutton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Vote2Activity.this, VoteWatchResultActivity.class);
                startActivity(intent);

            }
        });
        votebutton3 = (Button) findViewById(R.id.vote_button3);
        votebutton3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Vote2Activity.this, VoteWatchResultActivity.class);
                startActivity(intent);

            }
        });
        btnBack = (ImageButton) findViewById(R.id.img_header3_back);
        btnBack.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {

                finish();
            }
        });
    }

    public void onResume() {

        super.onResume();
        MobclickAgent.onResume(this);
    }

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

        btn_close = (Button) findViewById(R.id.vote_button_close);
        btn_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    class getVoteitemsTask extends AsyncTask<Void, Void, ArrayList<VoteItem>> {

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

            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }

    }
}
