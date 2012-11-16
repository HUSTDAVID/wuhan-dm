
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class VoteWatchResultActivity extends Activity {
    private ImageButton btnBack;

    private int voteId;
    private String used_ips;

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

        voteId = getIntent().getIntExtra("id", 0);
        used_ips = getIntent().getStringExtra("used_ips");

        TextView txtStatus = (TextView) findViewById(R.id.vote_ing_3);
        txtStatus.setText("感谢投票！");

        Button btnWatchResult = (Button) findViewById(R.id.btn_vote);
        btnWatchResult.setText("查看结果");

        btnWatchResult.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VoteWatchResultActivity.this, Vote1Activity.class);
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
}
