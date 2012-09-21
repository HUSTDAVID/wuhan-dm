
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

public class DM_PhotosActivity extends ActivityGroup {

    private TextView txtSelectedItem;

    private RelativeLayout layoutListTop;
    private RelativeLayout relMain;
    private LayoutParams params = null;

    private TextView txtAll;
    private TextView txtHot;
    private TextView txtCar;
    private TextView txtGirl;
    private TextView txtPhotograph;
    private TextView txtFun;

    private View vMain;

    private int startX = 0;
    private int itemWidth = 0;

    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photos);

        initViews();
    }

    private void initViews() {

        // init header
        View header = LayoutInflater.from(this).inflate(R.layout.header_title3, null);
        header.setBackgroundResource(R.drawable.topbar_black_bg);
        RelativeLayout rel = (RelativeLayout) header.findViewById(R.id.rel_header3);
        rel.setBackgroundResource(R.drawable.topbar_black_bg);

        TextView txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText(getResources().getString(R.string.photo));

        // list top
        layoutListTop = (RelativeLayout) findViewById(R.id.layout_list_top);

        txtAll = (TextView) findViewById(R.id.txt_listtop_1);
        txtAll.setText(getResources().getString(R.string.all));
        txtHot = (TextView) findViewById(R.id.txt_listtop_2);
        txtHot.setText(getResources().getString(R.string.hot));
        txtCar = (TextView) findViewById(R.id.txt_listtop_3);
        txtCar.setText(getResources().getString(R.string.car));
        txtGirl = (TextView) findViewById(R.id.txt_listtop_4);
        txtGirl.setText(getResources().getString(R.string.girl));
        txtPhotograph = (TextView) findViewById(R.id.txt_listtop_5);
        txtPhotograph.setText(getResources().getString(R.string.photograph));
        txtFun = (TextView) findViewById(R.id.txt_listtop_6);
        txtFun.setText(getResources().getString(R.string.fun));

        itemWidth = getScreenWidth() / 6;

        txtAll.setOnClickListener(new PhotosItemOnClickListener());
        txtHot.setOnClickListener(new PhotosItemOnClickListener());
        txtCar.setOnClickListener(new PhotosItemOnClickListener());
        txtGirl.setOnClickListener(new PhotosItemOnClickListener());
        txtPhotograph.setOnClickListener(new PhotosItemOnClickListener());
        txtFun.setOnClickListener(new PhotosItemOnClickListener());

        txtSelectedItem = new TextView(this);
        txtSelectedItem.setText(R.string.all);
        txtSelectedItem.setTextColor(Color.WHITE);
        txtSelectedItem.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        txtSelectedItem.setGravity(Gravity.CENTER);
        txtSelectedItem.setWidth(getScreenWidth() / 6);
        txtSelectedItem.setBackgroundResource(R.drawable.list_topbar_hover);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(itemWidth,
                LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        layoutListTop.addView(txtSelectedItem);

        // set the activity of photos all
        intent = new Intent(DM_PhotosActivity.this, DM_Photos_AllActivity.class);
        relMain = (RelativeLayout) findViewById(R.id.rel_photos_main);
        vMain = getLocalActivityManager().startActivity("all", intent).getDecorView();
        params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        relMain.addView(vMain, params);

    }

    private class PhotosItemOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.txt_listtop_1:
                    SetImageSlide(txtSelectedItem, startX, 0, 0, 0);
                    startX = 0;
                    txtSelectedItem.setText(R.string.all);

                    intent.setClass(DM_PhotosActivity.this, DM_Photos_AllActivity.class);
                    vMain = getLocalActivityManager().startActivity("all", intent).getDecorView();
                    break;

                case R.id.txt_listtop_2:
                    SetImageSlide(txtSelectedItem, startX, itemWidth, 0, 0);
                    startX = itemWidth;
                    txtSelectedItem.setText(R.string.hot);

                    break;

                case R.id.txt_listtop_3:
                    SetImageSlide(txtSelectedItem, startX, itemWidth * 2, 0, 0);
                    startX = itemWidth * 2;
                    txtSelectedItem.setText(R.string.car);

                    break;

                case R.id.txt_listtop_4:
                    SetImageSlide(txtSelectedItem, startX, itemWidth * 3, 0, 0);
                    startX = itemWidth * 3;
                    txtSelectedItem.setText(R.string.girl);

                    break;

                case R.id.txt_listtop_5:
                    SetImageSlide(txtSelectedItem, startX, itemWidth * 4, 0, 0);
                    startX = itemWidth * 4;
                    txtSelectedItem.setText(R.string.photograph);

                    break;

                case R.id.txt_listtop_6:
                    SetImageSlide(txtSelectedItem, startX, itemWidth * 5, 0, 0);
                    startX = itemWidth * 5;
                    txtSelectedItem.setText(R.string.fun);

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
