
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DM_InteractionActivity extends Activity implements OnClickListener {

    RelativeLayout relVote;
    RelativeLayout relCritic;
    RelativeLayout relAward;
    // RelativeLayout relEssence;
    // RelativeLayout relDepth;

    Button btnVote;
    Button btnCritic;
    Button btnAward;
    // Button btnEssence;
    // Button btnDepth;

    ImageView imgLogo;
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dm_interaction);

        initViews();
    }

    private void initViews() {

        relVote = (RelativeLayout) findViewById(R.id.rel_interaction_vote);
        relCritic = (RelativeLayout) findViewById(R.id.rel_interaction_critic);
        relAward = (RelativeLayout) findViewById(R.id.rel_interaction_award);
        // relEssence = (RelativeLayout)
        // findViewById(R.id.rel_interaction_essence);
        // relDepth = (RelativeLayout) findViewById(R.id.rel_interaction_depth);

        btnVote = (Button) findViewById(R.id.btn_interaction_vote);
        btnCritic = (Button) findViewById(R.id.btn_interaction_critic);
        btnAward = (Button) findViewById(R.id.btn_interaction_award);
        // btnEssence = (Button) findViewById(R.id.btn_interaction_essence);
        // btnDepth = (Button) findViewById(R.id.btn_interaction_depth);

        imgLogo = (ImageView) findViewById(R.id.img_logo);
        txtTitle = (TextView) findViewById(R.id.txt_title);

        imgLogo.setVisibility(View.GONE);
        txtTitle.setText(getResources().getString(R.string.interaction));

        relVote.setOnClickListener(this);
        relCritic.setOnClickListener(this);
        relAward.setOnClickListener(this);
        // relEssence.setOnClickListener(this);
        // relDepth.setOnClickListener(this);

        btnVote.setOnClickListener(this);
        btnCritic.setOnClickListener(this);
        btnAward.setOnClickListener(this);
        // btnEssence.setOnClickListener(this);
        // btnDepth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rel_interaction_vote:
            case R.id.btn_interaction_vote:
                Intent intent_vote1 = new Intent(DM_InteractionActivity.this,
                        DM_Vote1Activity.class);
                startActivity(intent_vote1);
                break;
            case R.id.rel_interaction_critic:
            case R.id.btn_interaction_critic:
                Intent intent_vote2 = new Intent(DM_InteractionActivity.this,
                        DM_Vote2Activity.class);
                startActivity(intent_vote2);
                break;
            case R.id.rel_interaction_award:
            case R.id.btn_interaction_award:
                Intent intent_list = new Intent(DM_InteractionActivity.this,
                        DM_VotedListActivity.class);
                startActivity(intent_list);
                break;
        }

    }

}
