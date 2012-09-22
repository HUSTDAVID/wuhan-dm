
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DM_TempActivity extends Activity implements OnClickListener {

    ImageView img;

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
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm_mzine_pics_txt);
        img = (ImageView) findViewById(R.id.img_header);
        img.setImageResource(R.drawable.temp_magazine_title);
        // initViews();
    }

    private void initViews() {

        linearLayouItem1 = (LinearLayout) findViewById(R.id.layout1_magazine_1);
        linearLayouItem2 = (LinearLayout) findViewById(R.id.layout2_magazine_1);
        linearLayouItem3 = (LinearLayout) findViewById(R.id.layout3_magazine_1);
        linearLayouItem4 = (LinearLayout) findViewById(R.id.layout4_magazine_1);
        linearLayouItem5 = (LinearLayout) findViewById(R.id.layout5_magazine_1);

        txtTile1 = (TextView) findViewById(R.id.txt_magazine_1_title1);
        txtTile2 = (TextView) findViewById(R.id.txt_magazine_1_title2);
        txtTile3 = (TextView) findViewById(R.id.txt_magazine_1_title3);
        txtTile4 = (TextView) findViewById(R.id.txt_magazine_1_title4);
        txtTile5 = (TextView) findViewById(R.id.txt_magazine_1_title5);

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
            case R.id.layout1_magazine_1:
                linearLayouItem1.setBackgroundColor(backColor);
                txtTile1.setTextColor(getResources().getColor(R.color.mzine_txt_clicked));
                break;
            case R.id.layout2_magazine_1:
                linearLayouItem2.setBackgroundColor(backColor);
                txtTile2.setTextColor(getResources().getColor(R.color.mzine_txt_clicked));
                break;
            case R.id.layout3_magazine_1:
                linearLayouItem3.setBackgroundColor(backColor);
                txtTile3.setTextColor(getResources().getColor(R.color.mzine_txt_clicked));
                break;
            case R.id.layout4_magazine_1:
                linearLayouItem4.setBackgroundColor(backColor);
                txtTile4.setTextColor(getResources().getColor(R.color.mzine_txt_clicked));
                break;
            case R.id.layout5_magazine_1:
                linearLayouItem5.setBackgroundColor(backColor);
                txtTile5.setTextColor(getResources().getColor(R.color.mzine_txt_clicked));
                break;
        }

    }
}
