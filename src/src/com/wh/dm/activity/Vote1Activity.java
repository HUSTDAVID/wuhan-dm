
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class Vote1Activity extends Activity {
    /** Called when the activity is first created. */

    ProgressBar progressBar1;
    ProgressBar progressBar2;
    ProgressBar progressBar3;
    private ImageButton btnBack;
    private Button btnClose;

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
        setProgress(progressBar1, 43);
        setProgress(progressBar2, 15);
        setProgress(progressBar3, 42);

    }

    private void setProgress(ProgressBar progress, int percent) {

        LayoutParams params = progress.getLayoutParams();
        params.width = (int) (percent * 3.5);
        progress.setLayoutParams(params);
    }
}
