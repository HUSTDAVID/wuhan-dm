
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DM_Vote2Activity extends Activity {
    private Button btn_close;
    private Button votebutton1;
    private Button votebutton2;
    private Button votebutton3;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote2);

        init();

        votebutton1 = (Button) findViewById(R.id.vote_button1);
        votebutton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DM_Vote2Activity.this, DM_VoteWatchResult.class);
                startActivity(intent);

            }
        });
        votebutton2 = (Button) findViewById(R.id.vote_button2);
        votebutton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DM_Vote2Activity.this, DM_VoteWatchResult.class);
                startActivity(intent);

            }
        });
        votebutton3 = (Button) findViewById(R.id.vote_button3);
        votebutton3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DM_Vote2Activity.this, DM_VoteWatchResult.class);
                startActivity(intent);

            }
        });
    }

    public void init() {

        btn_close = (Button) findViewById(R.id.vote_button_close);
        btn_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
}
