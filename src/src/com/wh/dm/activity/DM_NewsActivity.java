
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
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class DM_NewsActivity extends ActivityGroup {

    private TextView txtTitle;
    private TextView txtSelectedItem;

    private RelativeLayout layoutListTop;
    private RelativeLayout relMain;
    private LayoutParams params = null;

    private TextView txtHeadline;
    private TextView txtHouse;
    private TextView txtCar;
    private TextView txtFashion;
    private TextView txtLift;
    private TextView txtTravel;

    private View vMain;

    private int startX = 0;
    private int itemWidth = 0;

    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dm_news);

        initViews();
    }

    private void initViews() {

        layoutListTop = (RelativeLayout) findViewById(R.id.layout_list_top);
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

        txtSelectedItem = new TextView(this);
        txtSelectedItem.setText(R.string.headline);
        txtSelectedItem.setTextColor(Color.WHITE);
        txtSelectedItem.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        txtSelectedItem.setGravity(Gravity.CENTER);
        txtSelectedItem.setWidth(getScreenWidth() / 6);
        txtSelectedItem.setBackgroundResource(R.drawable.list_topbar_hover);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(itemWidth,
                LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        layoutListTop.addView(txtSelectedItem);

        // set the activity of headline
        intent = new Intent(DM_NewsActivity.this, DM_HeadLineActivity.class);
        relMain = (RelativeLayout) findViewById(R.id.rel_news_main);
        vMain = getLocalActivityManager().startActivity("Headline", intent).getDecorView();
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
                    txtSelectedItem.setText(R.string.headline);

                    intent.setClass(DM_NewsActivity.this, DM_HeadLineActivity.class);
                    vMain = getLocalActivityManager().startActivity("Headline", intent)
                            .getDecorView();
                    Toast.makeText(DM_NewsActivity.this, "头条", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.txt_listtop_2:
                    SetImageSlide(txtSelectedItem, startX, itemWidth, 0, 0);
                    startX = itemWidth;
                    txtSelectedItem.setText(R.string.house);

                    Toast.makeText(DM_NewsActivity.this, "房产", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.txt_listtop_3:
                    SetImageSlide(txtSelectedItem, startX, itemWidth * 2, 0, 0);
                    startX = itemWidth * 2;
                    txtSelectedItem.setText(R.string.car);

                    Toast.makeText(DM_NewsActivity.this, "汽车", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.txt_listtop_4:
                    SetImageSlide(txtSelectedItem, startX, itemWidth * 3, 0, 0);
                    startX = itemWidth * 3;
                    txtSelectedItem.setText(R.string.fashion);

                    Toast.makeText(DM_NewsActivity.this, "时尚", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.txt_listtop_5:
                    SetImageSlide(txtSelectedItem, startX, itemWidth * 4, 0, 0);
                    startX = itemWidth * 4;
                    txtSelectedItem.setText(R.string.lift);

                    Toast.makeText(DM_NewsActivity.this, "生活", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.txt_listtop_6:
                    SetImageSlide(txtSelectedItem, startX, itemWidth * 5, 0, 0);
                    startX = itemWidth * 5;
                    txtSelectedItem.setText(R.string.traval);

                    Toast.makeText(DM_NewsActivity.this, "旅游", Toast.LENGTH_SHORT).show();
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
