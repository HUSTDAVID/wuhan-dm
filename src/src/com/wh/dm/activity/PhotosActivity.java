
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class PhotosActivity extends ActivityGroup {

    private RelativeLayout relMain;
    private LayoutParams params = null;

    private TextView txtAll;
    private TextView txtHot;
    private TextView txtCar;
    private TextView txtGirl;
    private TextView txtPhotograph;
    private TextView txtFun;

    private View vMain;
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

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(itemWidth,
                LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        // set the activity of photos all
        intent = new Intent(PhotosActivity.this, Photos_AllActivity.class);
        relMain = (RelativeLayout) findViewById(R.id.rel_photos_main);
        vMain = getLocalActivityManager().startActivity("all", intent).getDecorView();
        params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        relMain.addView(vMain, params);
        setCurTxt(1);
    }

    private class PhotosItemOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.txt_listtop_1:
                    setCurTxt(1);
                    intent.setClass(PhotosActivity.this, Photos_AllActivity.class);
                    vMain = getLocalActivityManager().startActivity("all", intent).getDecorView();
                    break;

                case R.id.txt_listtop_2:
                    setCurTxt(2);
                    break;

                case R.id.txt_listtop_3:
                    setCurTxt(3);
                    break;

                case R.id.txt_listtop_4:
                    setCurTxt(4);
                    break;

                case R.id.txt_listtop_5:
                    setCurTxt(5);
                    break;

                case R.id.txt_listtop_6:
                    setCurTxt(6);
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

    private void setCurTxt(int i) {

        switch (i) {
            case 1:
                txtAll.setSelected(true);
                txtAll.setTextColor(Color.WHITE);
                txtHot.setSelected(false);
                txtHot.setTextColor(Color.BLACK);
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
                txtAll.setSelected(false);
                txtAll.setTextColor(Color.BLACK);
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
            case 3:
                txtAll.setSelected(false);
                txtAll.setTextColor(Color.BLACK);
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
            case 4:
                txtAll.setSelected(false);
                txtAll.setTextColor(Color.BLACK);
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
            case 5:
                txtAll.setSelected(false);
                txtAll.setTextColor(Color.BLACK);
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
            case 6:
                txtAll.setSelected(false);
                txtAll.setTextColor(Color.BLACK);
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
