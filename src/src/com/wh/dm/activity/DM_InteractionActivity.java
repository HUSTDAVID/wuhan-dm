
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApp;
import com.wh.dm.util.NotificationUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
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
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dm_interaction);

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

        relVote = (RelativeLayout) findViewById(R.id.rel_interaction_vote);
        relCritic = (RelativeLayout) findViewById(R.id.rel_interaction_critic);
        relAward = (RelativeLayout) findViewById(R.id.rel_interaction_award);

        btnVote = (Button) findViewById(R.id.btn_interaction_vote);
        btnCritic = (Button) findViewById(R.id.btn_interaction_critic);
        btnAward = (Button) findViewById(R.id.btn_interaction_award);

        imgLogo = (ImageView) findViewById(R.id.img_logo);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        ImageButton imgMessage = (ImageButton) findViewById(R.id.btn_message);
        TextView txtMsgNum = (TextView) findViewById(R.id.txt_message_num);
        imgMessage.setVisibility(View.GONE);
        txtMsgNum.setVisibility(View.GONE);

        imgLogo.setVisibility(View.GONE);
        txtTitle.setText(getResources().getString(R.string.interaction));

        relVote.setOnClickListener(this);
        relCritic.setOnClickListener(this);
        relAward.setOnClickListener(this);

        btnVote.setOnClickListener(this);
        btnCritic.setOnClickListener(this);
        btnAward.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (WH_DMApp.isLogin) {
            switch (v.getId()) {
                case R.id.rel_interaction_vote:
                case R.id.btn_interaction_vote:
                    Intent intent_list = new Intent(DM_InteractionActivity.this,
                            VotedListActivity.class);
                    intent_list.putExtra("sid", DM_Tab_2Activity.sid);
                    startActivity(intent_list);
                    // String text = FileUtil.getCacheSize();
                    // Toast.makeText(DM_InteractionActivity.this, text,
                    // Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rel_interaction_critic:
                case R.id.btn_interaction_critic:
                    NotificationUtil.showShortToast(getString(R.string.no_critic),
                            DM_InteractionActivity.this);
                    break;
                case R.id.rel_interaction_award:
                case R.id.btn_interaction_award:
                    NotificationUtil.showShortToast(getString(R.string.no_award),
                            DM_InteractionActivity.this);
                    // Intent intentSurvey = new
                    // Intent(DM_InteractionActivity.this,
                    // SurveyActivity.class);
                    // startActivity(intentSurvey);
                    break;
            }
        } else {
            NotificationUtil.showShortToast(getString(R.string.please_login),
                    DM_InteractionActivity.this);
            Intent intent = new Intent(DM_InteractionActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

}
