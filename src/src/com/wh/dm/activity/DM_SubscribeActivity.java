
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class DM_SubscribeActivity extends ActivityGroup {

    LinearLayout linearListTop;
    LinearLayout linearSubSearch;

    RelativeLayout layoutListTop;

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

        linearListTop = (LinearLayout) findViewById(R.id.linear_sub_list_top);
        linearSubSearch = (LinearLayout) findViewById(R.id.linear_sub_search);

        layoutListTop = (RelativeLayout) findViewById(R.id.layout_sub_list);
        // init header

        TextView txtTitle = (TextView) findViewById(R.id.txt_header3_title);
        TextView txtManager = (TextView) findViewById(R.id.txt_total_reply);
        txtTitle.setText(getResources().getString(R.string.subscribe));
        txtManager.setText(getResources().getString(R.string.sub_manager));

        txtManager.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DM_SubscribeActivity.this, DM_SubManagerActivity.class);
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

        itemWidth = txtHot.getWidth();

        txtHot.setOnClickListener(new NewsItemOnClickListener());
        txtCar.setOnClickListener(new NewsItemOnClickListener());
        txtGirl.setOnClickListener(new NewsItemOnClickListener());
        txtPhotograph.setOnClickListener(new NewsItemOnClickListener());
        txtFun.setOnClickListener(new NewsItemOnClickListener());
        imgSearch.setOnClickListener(new NewsItemOnClickListener());

        txtSelectedItem = new TextView(this);
        txtSelectedItem.setText(R.string.hot);
        txtSelectedItem.setTextColor(Color.WHITE);
        txtSelectedItem.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        txtSelectedItem.setGravity(Gravity.CENTER);
        txtSelectedItem.setWidth(itemWidth);
        txtSelectedItem.setBackgroundResource(R.drawable.list_topbar_hover);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(itemWidth,
                LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        param.leftMargin = startX;
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

            itemWidth = txtHot.getWidth();
            switch (v.getId()) {
                case R.id.txt_sub_hot:
                    SetImageSlide(txtSelectedItem, startX, 0, 0, 0);
                    startX = 0;
                    txtSelectedItem.setText(R.string.hot);

                    intent.setClass(DM_SubscribeActivity.this, DM_Sub_HotActivity.class);
                    vMain = getLocalActivityManager().startActivity("hot", intent).getDecorView();
                    Toast.makeText(DM_SubscribeActivity.this, "热点", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.txt_sub_car:
                    SetImageSlide(txtSelectedItem, startX, itemWidth, 0, 0);
                    startX = itemWidth;
                    txtSelectedItem.setText(R.string.car);

                    Toast.makeText(DM_SubscribeActivity.this, "汽车", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.txt_sub_girl:
                    SetImageSlide(txtSelectedItem, startX, itemWidth * 2, 0, 0);
                    startX = itemWidth * 2;
                    txtSelectedItem.setText(R.string.girl);

                    Toast.makeText(DM_SubscribeActivity.this, "美女", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.txt_sub_photograph:
                    SetImageSlide(txtSelectedItem, startX, itemWidth * 3, 0, 0);
                    startX = itemWidth * 3;
                    txtSelectedItem.setText(R.string.photograph);

                    Toast.makeText(DM_SubscribeActivity.this, "摄影", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.txt_sub_fun:
                    SetImageSlide(txtSelectedItem, startX, itemWidth * 4, 0, 0);
                    startX = itemWidth * 4;
                    txtSelectedItem.setText(R.string.fun);

                    Toast.makeText(DM_SubscribeActivity.this, "奇趣", Toast.LENGTH_SHORT).show();
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
