
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

public class SurveyFinishActivity extends Activity implements OnClickListener {

    TextView txtTitle;
    ImageButton btnBack;
    Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_finish);
        initViews();
    }

    public void onResume() {

        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {

        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initViews() {

        txtTitle = (TextView) findViewById(R.id.txt_header_title2);
        txtTitle.setText(getResources().getString(R.string.survey));

        btnBack = (ImageButton) findViewById(R.id.Btn_back_header2);
        btnBack.setOnClickListener(this);
        btnFinish = (Button) findViewById(R.id.btn_survey_finish);
        btnBack.setOnClickListener(this);
        btnFinish.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.Btn_back_header2:
                SurveyFinishActivity.this.finish();
                break;
            case R.id.btn_survey_finish:
                Intent intent = new Intent(SurveyFinishActivity.this, DM_Tab_2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
        }

    }

}
