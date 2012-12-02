
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.type.VoteResult;
import com.wh.dm.util.UrlImageViewHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class VoteWatchResultActivity extends Activity {
    private ImageButton btnBack;

    private int aid;
    private String vtitle;
    private String des;
    private String pic;
    private String voteName;
    private VoteResult result;
    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_DmApi;
    private SingleVoteTask siggleVoteTask;
    private static final int MSG_SINGLE_VOTE = 0;

    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == MSG_SINGLE_VOTE) {
                if (siggleVoteTask != null) {
                    siggleVoteTask.cancel(true);
                    siggleVoteTask = null;
                }
                siggleVoteTask = new SingleVoteTask();
                siggleVoteTask.execute();
            }
            super.handleMessage(msg);
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        setContentView(R.layout.dm_voteitem1);
        initViews();
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

        wh_dmApp = (WH_DMApp) this.getApplication();
        wh_DmApi = wh_dmApp.getWH_DMApi();

        aid = getIntent().getIntExtra("aid", 0);
        vtitle = getIntent().getStringExtra("vtitle");
        pic = getIntent().getStringExtra("pic");
        des = getIntent().getStringExtra("des");
        voteName = getIntent().getStringExtra("name");
        handler.sendEmptyMessage(MSG_SINGLE_VOTE);

        TextView txtStatus = (TextView) findViewById(R.id.vote_ing_3);
        txtStatus.setText(getResources().getString(R.string.thx_voting));
        TextView txtDes = (TextView) findViewById(R.id.btn_vote_des);
        txtDes.setText(des);
        TextView txtTitle = (TextView) findViewById(R.id.vote_list_1);
        txtTitle.setText(voteName);
        ImageView img = (ImageView) findViewById(R.id.img_vote_pic);
        UrlImageViewHelper.setUrlDrawable(img, WH_DMHttpApiV1.URL_DOMAIN + pic,
                R.drawable.vote_banner, null);

        Button btnWatchResult = (Button) findViewById(R.id.btn_vote);
        btnWatchResult.setText(getResources().getString(R.string.watch_result));

        btnWatchResult.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VoteWatchResultActivity.this, Vote1Activity.class);
                intent.putExtra("aid", aid);
                intent.putExtra("name", voteName);
                startActivity(intent);
            }
        });
        btnBack = (ImageButton) findViewById(R.id.img_header3_back);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(VoteWatchResultActivity.this, VotedListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    class SingleVoteTask extends AsyncTask<Void, Void, VoteResult> {

        Exception reason;

        @Override
        protected VoteResult doInBackground(Void... params) {

            try {
                result = wh_DmApi.postVote(aid, vtitle);
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(VoteResult result) {

            Toast.makeText(VoteWatchResultActivity.this, result.getMsg(), Toast.LENGTH_SHORT)
                    .show();
        }

    }
}
