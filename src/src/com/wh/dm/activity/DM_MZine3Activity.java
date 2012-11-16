
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DM_MZine3Activity extends Activity implements OnClickListener {
    LinearLayout linearLayouItem1;
    LinearLayout linearLayouItem2;
    LinearLayout linearLayouItem3;
    LinearLayout linearLayouItem4;
    LinearLayout linearLayouItem5;

    TextView txtTile1;
    TextView txtTile2;
    TextView txtTile3;
    TextView txtTile4;
    TextView txtTile5;

    TextView txtBody1;
    TextView txtBody2;
    TextView txtBody3;
    TextView txtBody4;
    TextView txtBody5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dm_mzine3);

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

        linearLayouItem1 = (LinearLayout) findViewById(R.id.layout1_magazine_3);
        linearLayouItem2 = (LinearLayout) findViewById(R.id.layout2_magazine_3);
        linearLayouItem3 = (LinearLayout) findViewById(R.id.layout3_magazine_3);
        linearLayouItem4 = (LinearLayout) findViewById(R.id.layout4_magazine_3);
        linearLayouItem5 = (LinearLayout) findViewById(R.id.layout5_magazine_3);

        txtTile1 = (TextView) findViewById(R.id.txt_magazine_3_title1);
        txtTile2 = (TextView) findViewById(R.id.txt_magazine_3_title2);
        txtTile3 = (TextView) findViewById(R.id.txt_magazine_3_title3);
        txtTile4 = (TextView) findViewById(R.id.txt_magazine_3_title4);
        txtTile5 = (TextView) findViewById(R.id.txt_magazine_3_title5);

        linearLayouItem1.setOnClickListener(this);
        linearLayouItem2.setOnClickListener(this);
        linearLayouItem3.setOnClickListener(this);
        linearLayouItem4.setOnClickListener(this);
        linearLayouItem5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int backColor = getResources().getColor(R.color.bg_clicked);

        switch (v.getId()) {
            case R.id.layout1_magazine_3:
                linearLayouItem1.setBackgroundColor(backColor);
                txtTile1.setTextColor(getResources().getColor(R.color.mzine_txt_clicked));

                break;
            case R.id.layout2_magazine_3:
                linearLayouItem2.setBackgroundColor(backColor);
                txtTile2.setTextColor(getResources().getColor(R.color.mzine_txt_clicked));

                break;
            case R.id.layout3_magazine_3:
                linearLayouItem3.setBackgroundColor(backColor);
                txtTile3.setTextColor(getResources().getColor(R.color.mzine_txt_clicked));

                break;
            case R.id.layout4_magazine_3:
                linearLayouItem4.setBackgroundColor(backColor);
                txtTile4.setTextColor(getResources().getColor(R.color.mzine_txt_clicked));

                break;
            case R.id.layout5_magazine_3:
                linearLayouItem5.setBackgroundColor(backColor);
                txtTile5.setTextColor(getResources().getColor(R.color.mzine_txt_clicked));

                break;
        }

    }
}
