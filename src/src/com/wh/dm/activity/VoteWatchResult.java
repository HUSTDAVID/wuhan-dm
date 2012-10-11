
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class VoteWatchResult extends Activity {
	private ImageButton btnBack;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dm_voteitem1);
        initViews();
    }

    private void initViews() {

        TextView txtStatus = (TextView) findViewById(R.id.vote_ing_3);
        txtStatus.setText("感谢投票！");

        Button btnWatchResult = (Button) findViewById(R.id.btn_vote);
        btnWatchResult.setText("查看结果");

        btnWatchResult.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VoteWatchResult.this, Vote1Activity.class);
                startActivity(intent);
            }
        });
        btnBack =(ImageButton)findViewById(R.id.img_header3_back);
        btnBack.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		finish();
        	}
        });
    }
}
