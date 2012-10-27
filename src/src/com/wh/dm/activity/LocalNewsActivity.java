
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class LocalNewsActivity extends ActivityGroup {

    private TextView txtTitle;
    private TextView txtSelectedItem;

    private RelativeLayout relMain;
    private LayoutParams params = null;

    private TextView txtHeadline;
    private TextView txtHouse;
    private TextView txtCar;
    private TextView txtFashion;
    private TextView txtLift;
    private TextView txtTravel;

    private View vMain;
    private int itemWidth = 0;
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dm_news);

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

        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText(getResources().getString(R.string.news));

        txtHeadline = (TextView) findViewById(R.id.txt_listtop_1);
        txtHouse = (TextView) findViewById(R.id.txt_listtop_2);
        txtCar = (TextView) findViewById(R.id.txt_listtop_3);
        txtFashion = (TextView) findViewById(R.id.txt_listtop_4);
        txtLift = (TextView) findViewById(R.id.txt_listtop_5);
        txtTravel = (TextView) findViewById(R.id.txt_listtop_6);

        itemWidth = getScreenWidth() / 6;

        txtHeadline.setOnClickListener(new NewsItemOnClickListener());
        txtHouse.setOnClickListener(new NewsItemOnClickListener());
        txtCar.setOnClickListener(new NewsItemOnClickListener());
        txtFashion.setOnClickListener(new NewsItemOnClickListener());
        txtLift.setOnClickListener(new NewsItemOnClickListener());
        txtTravel.setOnClickListener(new NewsItemOnClickListener());

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(itemWidth,
                LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        // set the activity of headline
        intent = new Intent(LocalNewsActivity.this, HeadNewsActivity.class);
        relMain = (RelativeLayout) findViewById(R.id.rel_news_main);
        vMain = getLocalActivityManager().startActivity("Headline", intent).getDecorView();
        params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        relMain.addView(vMain, params);
        setCurTxt(1);
    }

    private class NewsItemOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.txt_listtop_1:
                    setCurTxt(1);
                    intent.setClass(LocalNewsActivity.this, HeadNewsActivity.class);
                    vMain = getLocalActivityManager().startActivity("Headline", intent)
                            .getDecorView();

                    break;

                case R.id.txt_listtop_2:
                    setCurTxt(2);
                    intent.setClass(LocalNewsActivity.this, HouseNewsActivity.class);
                    vMain = getLocalActivityManager().startActivity("House", intent).getDecorView();
                    break;

                case R.id.txt_listtop_3:
                    setCurTxt(3);
                    intent.setClass(LocalNewsActivity.this, CarNewsActivity.class);
                    vMain = getLocalActivityManager().startActivity("Car", intent).getDecorView();
                    break;

                case R.id.txt_listtop_4:
                    setCurTxt(4);
                    intent.setClass(LocalNewsActivity.this, FashionNewsActivity.class);
                    vMain = getLocalActivityManager().startActivity("Fashion", intent)
                            .getDecorView();
                    break;

                case R.id.txt_listtop_5:
                    setCurTxt(5);
                    intent.setClass(LocalNewsActivity.this, LifeNewsActivity.class);
                    vMain = getLocalActivityManager().startActivity("Life", intent).getDecorView();
                    break;

                case R.id.txt_listtop_6:
                    setCurTxt(6);
                    intent.setClass(LocalNewsActivity.this, TravelNewsActivity.class);
                    vMain = getLocalActivityManager().startActivity("Travel", intent)
                            .getDecorView();
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
                txtHeadline.setSelected(true);
                txtHeadline.setTextColor(Color.WHITE);
                txtHouse.setSelected(false);
                txtHouse.setTextColor(Color.BLACK);
                txtCar.setSelected(false);
                txtCar.setTextColor(Color.BLACK);
                txtFashion.setSelected(false);
                txtFashion.setTextColor(Color.BLACK);
                txtLift.setSelected(false);
                txtLift.setTextColor(Color.BLACK);
                txtTravel.setSelected(false);
                txtTravel.setTextColor(Color.BLACK);
                break;
            case 2:
                txtHeadline.setSelected(false);
                txtHeadline.setTextColor(Color.BLACK);
                txtHouse.setSelected(true);
                txtHouse.setTextColor(Color.WHITE);
                txtCar.setSelected(false);
                txtCar.setTextColor(Color.BLACK);
                txtFashion.setSelected(false);
                txtFashion.setTextColor(Color.BLACK);
                txtLift.setSelected(false);
                txtLift.setTextColor(Color.BLACK);
                txtTravel.setSelected(false);
                txtTravel.setTextColor(Color.BLACK);
                break;
            case 3:
                txtHeadline.setSelected(false);
                txtHeadline.setTextColor(Color.BLACK);
                txtHouse.setSelected(false);
                txtHouse.setTextColor(Color.BLACK);
                txtCar.setSelected(true);
                txtCar.setTextColor(Color.WHITE);
                txtFashion.setSelected(false);
                txtFashion.setTextColor(Color.BLACK);
                txtLift.setSelected(false);
                txtLift.setTextColor(Color.BLACK);
                txtTravel.setSelected(false);
                txtTravel.setTextColor(Color.BLACK);
                break;
            case 4:
                txtHeadline.setSelected(false);
                txtHeadline.setTextColor(Color.BLACK);
                txtHouse.setSelected(false);
                txtHouse.setTextColor(Color.BLACK);
                txtCar.setSelected(false);
                txtCar.setTextColor(Color.BLACK);
                txtFashion.setSelected(true);
                txtFashion.setTextColor(Color.WHITE);
                txtLift.setSelected(false);
                txtLift.setTextColor(Color.BLACK);
                txtTravel.setSelected(false);
                txtTravel.setTextColor(Color.BLACK);
                break;
            case 5:
                txtHeadline.setSelected(false);
                txtHeadline.setTextColor(Color.BLACK);
                txtHouse.setSelected(false);
                txtHouse.setTextColor(Color.BLACK);
                txtCar.setSelected(false);
                txtCar.setTextColor(Color.BLACK);
                txtFashion.setSelected(false);
                txtFashion.setTextColor(Color.BLACK);
                txtLift.setSelected(true);
                txtLift.setTextColor(Color.WHITE);
                txtTravel.setSelected(false);
                txtTravel.setTextColor(Color.BLACK);
                break;
            case 6:
                txtHeadline.setSelected(false);
                txtHeadline.setTextColor(Color.BLACK);
                txtHouse.setSelected(false);
                txtHouse.setTextColor(Color.BLACK);
                txtCar.setSelected(false);
                txtCar.setTextColor(Color.BLACK);
                txtFashion.setSelected(false);
                txtFashion.setTextColor(Color.BLACK);
                txtLift.setSelected(false);
                txtLift.setTextColor(Color.BLACK);
                txtTravel.setSelected(true);
                txtTravel.setTextColor(Color.WHITE);
                break;
        }

    }

}
