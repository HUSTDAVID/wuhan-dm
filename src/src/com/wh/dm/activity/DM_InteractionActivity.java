
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
                Toast.makeText(DM_InteractionActivity.this, "vote test", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rel_interaction_critic:
            case R.id.btn_interaction_critic:
                Toast.makeText(DM_InteractionActivity.this, "comment test", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.rel_interaction_award:
            case R.id.btn_interaction_award:
                Toast.makeText(DM_InteractionActivity.this, "survey test", Toast.LENGTH_SHORT)
                        .show();
                break;
        /*
         * case R.id.rel_interaction_essence: case R.id.btn_interaction_essence:
         * Toast.makeText(DM_InteractionActivity.this, "essence test",
         * Toast.LENGTH_SHORT) .show(); break; case R.id.rel_interaction_depth:
         * case R.id.btn_interaction_depth:
         * Toast.makeText(DM_InteractionActivity.this, "depth test",
         * Toast.LENGTH_SHORT) .show(); break;
         */
        }

    }

}
