
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class SubscribeActivity extends ActivityGroup {

    LinearLayout linearListTop;
    LinearLayout linearSubSearch;
    // topbar
    private TextView txtTitle;
    private TextView txtSelectedItem;

    private RelativeLayout relMain;
    private LayoutParams params = null;

    private TextView txtHot;
    private TextView txtCar;
    private TextView txtGirl;
    private TextView txtPhotograph;
    private TextView txtFun;
    private ImageView imgSearch;
    private ImageButton btnBack;
    private View vMain;

    private final int startX = 0;
    private int itemWidth = 0;

    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_subscribe);

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

        linearListTop = (LinearLayout) findViewById(R.id.linear_sub_list_top);
        linearSubSearch = (LinearLayout) findViewById(R.id.linear_sub_search);

        // init header

        TextView txtTitle = (TextView) findViewById(R.id.txt_header3_title);
        TextView txtManager = (TextView) findViewById(R.id.txt_total_reply);
        txtTitle.setText(getResources().getString(R.string.subscribe));
        txtManager.setText(getResources().getString(R.string.sub_manager));

        txtManager.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SubscribeActivity.this, SubManagerActivity.class);
                startActivity(intent);

            }
        });

        //

        // txtTitle = (TextView) findViewById(R.id.txt_title);
        // txtTitle.setText(getResources().getString(R.string.news));

        txtHot = (TextView) findViewById(R.id.txt_sub_hot);
        txtCar = (TextView) findViewById(R.id.txt_sub_car);
        txtGirl = (TextView) findViewById(R.id.txt_sub_girl);
        txtPhotograph = (TextView) findViewById(R.id.txt_sub_photograph);
        txtFun = (TextView) findViewById(R.id.txt_sub_fun);
        imgSearch = (ImageView) findViewById(R.id.img_sub_search);
        btnBack = (ImageButton) findViewById(R.id.img_header3_back);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });

        itemWidth = txtHot.getWidth();

        txtHot.setOnClickListener(new NewsItemOnClickListener());
        txtCar.setOnClickListener(new NewsItemOnClickListener());
        txtGirl.setOnClickListener(new NewsItemOnClickListener());
        txtPhotograph.setOnClickListener(new NewsItemOnClickListener());
        txtFun.setOnClickListener(new NewsItemOnClickListener());
        imgSearch.setOnClickListener(new NewsItemOnClickListener());

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(itemWidth,
                LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        param.leftMargin = startX;

        // set the activity of hot
        intent = new Intent(SubscribeActivity.this, Sub_HotActivity.class);
        relMain = (RelativeLayout) findViewById(R.id.rel_sub_main);
        vMain = getLocalActivityManager().startActivity("Hot", intent).getDecorView();
        params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        relMain.addView(vMain, params);
        setCurTxt(1);
    }

    private class NewsItemOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            itemWidth = txtHot.getWidth();
            switch (v.getId()) {
                case R.id.txt_sub_hot:
                    setCurTxt(1);
                    intent.setClass(SubscribeActivity.this, Sub_HotActivity.class);
                    vMain = getLocalActivityManager().startActivity("hot", intent).getDecorView();
                    break;

                case R.id.txt_sub_car:
                    setCurTxt(2);
                    break;

                case R.id.txt_sub_girl:
                    setCurTxt(3);
                    break;

                case R.id.txt_sub_photograph:
                    setCurTxt(4);
                    break;

                case R.id.txt_sub_fun:
                    setCurTxt(5);
                    break;

                case R.id.img_sub_search:
                    linearListTop.setVisibility(View.GONE);
                    linearSubSearch.setVisibility(View.VISIBLE);
                    break;
            }

            // set main content
            relMain.removeAllViews();
            relMain.addView(vMain, params);
        }

    }

    private void setCurTxt(int i) {

        switch (i) {
            case 1:
                txtHot.setSelected(true);
                txtHot.setTextColor(Color.WHITE);
                txtCar.setSelected(false);
                txtCar.setTextColor(Color.BLACK);
                txtGirl.setSelected(false);
                txtGirl.setTextColor(Color.BLACK);
                txtPhotograph.setSelected(false);
                txtPhotograph.setTextColor(Color.BLACK);
                txtFun.setSelected(false);
                txtFun.setTextColor(Color.BLACK);
                break;
            case 2:
                txtHot.setSelected(false);
                txtHot.setTextColor(Color.BLACK);
                txtCar.setSelected(true);
                txtCar.setTextColor(Color.WHITE);
                txtGirl.setSelected(false);
                txtGirl.setTextColor(Color.BLACK);
                txtPhotograph.setSelected(false);
                txtPhotograph.setTextColor(Color.BLACK);
                txtFun.setSelected(false);
                txtFun.setTextColor(Color.BLACK);
                break;
            case 3:
                txtHot.setSelected(false);
                txtHot.setTextColor(Color.BLACK);
                txtCar.setSelected(false);
                txtCar.setTextColor(Color.BLACK);
                txtGirl.setSelected(true);
                txtGirl.setTextColor(Color.WHITE);
                txtPhotograph.setSelected(false);
                txtPhotograph.setTextColor(Color.BLACK);
                txtFun.setSelected(false);
                txtFun.setTextColor(Color.BLACK);
                break;
            case 4:
                txtHot.setSelected(false);
                txtHot.setTextColor(Color.BLACK);
                txtCar.setSelected(false);
                txtCar.setTextColor(Color.BLACK);
                txtGirl.setSelected(false);
                txtGirl.setTextColor(Color.BLACK);
                txtPhotograph.setSelected(true);
                txtPhotograph.setTextColor(Color.WHITE);
                txtFun.setSelected(false);
                txtFun.setTextColor(Color.BLACK);
                break;
            case 5:
                txtHot.setSelected(false);
                txtHot.setTextColor(Color.BLACK);
                txtCar.setSelected(false);
                txtCar.setTextColor(Color.BLACK);
                txtGirl.setSelected(false);
                txtGirl.setTextColor(Color.BLACK);
                txtPhotograph.setSelected(false);
                txtPhotograph.setTextColor(Color.BLACK);
                txtFun.setSelected(true);
                txtFun.setTextColor(Color.WHITE);
                break;
        }
    }
}
