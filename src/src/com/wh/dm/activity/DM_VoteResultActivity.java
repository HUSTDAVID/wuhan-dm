
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class DM_VoteResultActivity extends Activity {

    ProgressBar progressBar1;
    ProgressBar progressBar2;
    ProgressBar progressBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_vote1);

        initViews();
    }

    private void initViews() {

        LinearLayout seleteLayout = (LinearLayout) findViewById(R.id.linear_vote_selete);
        seleteLayout.setVisibility(View.GONE);
        LinearLayout resultLayout = (LinearLayout) findViewById(R.id.linear_vote_resul);
        resultLayout.setVisibility(View.VISIBLE);

        progressBar1 = (ProgressBar) findViewById(R.id.pro_vote_result1);
        progressBar2 = (ProgressBar) findViewById(R.id.pro_vote_result2);
        progressBar3 = (ProgressBar) findViewById(R.id.pro_vote_result3);

    }

}
