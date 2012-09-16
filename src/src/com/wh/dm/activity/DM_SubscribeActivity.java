
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class DM_SubscribeActivity extends ActivityGroup {

    LayoutInflater mInflater;

    // topbar
    private TextView txtTitle;
    private TextView txtSelectedItem;

    private RelativeLayout layoutListTop;
    private RelativeLayout relMain;
    private LayoutParams params = null;

    private TextView txtHot;
    private TextView txtCar;
    private TextView txtGirl;
    private TextView txtPhotograph;
    private TextView txtFun;
    private TextView txtSearch;

    private View vMain;

    private int startX = 0;
    private int itemWidth = 0;

    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_subscribe);

        initViews();
    }

    private void initViews() {

        mInflater = getLayoutInflater();
        // init header
        /*
         * View header = mInflater.inflate(R.layout.header_title3, null);
         * RelativeLayout relHeader = (RelativeLayout)
         * header.findViewById(R.id.rel_header3); TextView txtTitle = (TextView)
         * header.findViewById(R.id.txt_header3_title); TextView txtManager =
         * (TextView) header.findViewById(R.id.txt_total_reply);
         * relHeader.setBackgroundResource(R.drawable.topbar_red_bg);
         * txtTitle.setText(getResources().getString(R.string.subscribe));
         * txtManager.setText(getResources().getString(R.string.sub_manager));
         */

        //

        layoutListTop = (RelativeLayout) findViewById(R.id.sub_list_top);
        // txtTitle = (TextView) findViewById(R.id.txt_title);
        // txtTitle.setText(getResources().getString(R.string.news));

        txtHot = (TextView) findViewById(R.id.txt_listtop_1);
        txtCar = (TextView) findViewById(R.id.txt_listtop_2);
        txtGirl = (TextView) findViewById(R.id.txt_listtop_3);
        txtPhotograph = (TextView) findViewById(R.id.txt_listtop_4);
        txtFun = (TextView) findViewById(R.id.txt_listtop_5);
        txtSearch = (TextView) findViewById(R.id.txt_listtop_6);

        itemWidth = getScreenWidth() / 6;

        txtHot.setOnClickListener(new NewsItemOnClickListener());
        txtCar.setOnClickListener(new NewsItemOnClickListener());
        txtGirl.setOnClickListener(new NewsItemOnClickListener());
        txtPhotograph.setOnClickListener(new NewsItemOnClickListener());
        txtFun.setOnClickListener(new NewsItemOnClickListener());
        txtSearch.setOnClickListener(new NewsItemOnClickListener());

        txtHot.setText(getResources().getString(R.string.hot));
        txtCar.setText(getResources().getString(R.string.car));
        txtGirl.setText(getResources().getString(R.string.girl));
        txtPhotograph.setText(getResources().getString(R.string.photograph));
        txtFun.setText(getResources().getString(R.string.fun));
        txtSearch.setBackgroundResource(R.drawable.btn_subscription_search);
        txtSearch.setText("");

        txtSelectedItem = new TextView(this);
        txtSelectedItem.setText(R.string.hot);
        txtSelectedItem.setTextColor(Color.WHITE);
        txtSelectedItem.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        txtSelectedItem.setGravity(Gravity.CENTER);
        txtSelectedItem.setWidth(getScreenWidth() / 6);
        txtSelectedItem.setBackgroundResource(R.drawable.list_topbar_hover);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(itemWidth,
                LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        layoutListTop.addView(txtSelectedItem);

        // set the activity of hot
        intent = new Intent(DM_SubscribeActivity.this, DM_Sub_HotActivity.class);
        relMain = (RelativeLayout) findViewById(R.id.rel_sub_main);
        vMain = getLocalActivityManager().startActivity("Hot", intent).getDecorView();
        params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        relMain.addView(vMain, params);

    }

    private class NewsItemOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.txt_listtop_1:
                    SetImageSlide(txtSelectedItem, startX, 0, 0, 0);
                    startX = 0;
                    txtSelectedItem.setText(R.string.hot);

                    intent.setClass(DM_SubscribeActivity.this, DM_Sub_HotActivity.class);
                    vMain = getLocalActivityManager().startActivity("hot", intent).getDecorView();
                    Toast.makeText(DM_SubscribeActivity.this, "热点", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.txt_listtop_2:
                    SetImageSlide(txtSelectedItem, startX, itemWidth, 0, 0);
                    startX = itemWidth;
                    txtSelectedItem.setText(R.string.house);

                    Toast.makeText(DM_SubscribeActivity.this, "汽车", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.txt_listtop_3:
                    SetImageSlide(txtSelectedItem, startX, itemWidth * 2, 0, 0);
                    startX = itemWidth * 2;
                    txtSelectedItem.setText(R.string.car);

                    Toast.makeText(DM_SubscribeActivity.this, "美女", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.txt_listtop_4:
                    SetImageSlide(txtSelectedItem, startX, itemWidth * 3, 0, 0);
                    startX = itemWidth * 3;
                    txtSelectedItem.setText(R.string.fashion);

                    Toast.makeText(DM_SubscribeActivity.this, "摄影", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.txt_listtop_5:
                    SetImageSlide(txtSelectedItem, startX, itemWidth * 4, 0, 0);
                    startX = itemWidth * 4;
                    txtSelectedItem.setText(R.string.lift);

                    Toast.makeText(DM_SubscribeActivity.this, "奇趣", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.txt_listtop_6:
                    // SetImageSlide(txtSelectedItem, startX, itemWidth * 5, 0,
                    // 0);
                    // startX = itemWidth * 5;
                    // txtSelectedItem.setText(R.string.traval);

                    Toast.makeText(DM_SubscribeActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                    break;
            }

            // set main content
            relMain.removeAllViews();
            relMain.addView(vMain, params);
        }

    }

    private int getScreenWidth() {

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = display.getWidth();
        return screenWidth;
    }

    public static void SetImageSlide(View v, int startX, int toX, int startY, int toY) {

        TranslateAnimation anim = new TranslateAnimation(startX, toX, startY, toY);
        anim.setDuration(100);
        anim.setFillAfter(true);
        v.startAnimation(anim);
    }
}
