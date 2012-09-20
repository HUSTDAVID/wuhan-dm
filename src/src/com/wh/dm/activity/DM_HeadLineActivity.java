
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.widget.HeadlineAdapter;
import com.wh.dm.widget.HorizontalPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class DM_HeadLineActivity extends Activity {

    private String[] titles;
    private int currentItem = 0;
    private View headerView;
    private LayoutInflater mInfalater;
    private ListView listView;

    private final int SHOW_NEXT = 0011;
    private final boolean isRun = true;

    private HorizontalPager mPager;
    private RadioGroup mRadioGroup;
    private TextView txtHorizontalTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_headline);
        init();
        thread.start();
    }

    private void init() {

        listView = (ListView) findViewById(R.id.lv_headline);

        mRadioGroup = (RadioGroup) findViewById(R.id.tabs);
        mRadioGroup.setOnCheckedChangeListener(onCheckedChangedListener);
        mPager = (HorizontalPager) findViewById(R.id.horizontal_pager);
        mPager.setOnScreenSwitchListener(onScreenSwitchListener);
        txtHorizontalTitle = (TextView) findViewById(R.id.txt_horizontal_title);

        mPager.setCurrentScreen(0, true);

        HeadlineAdapter adapter = new HeadlineAdapter(this);
        // add data
        String title = getResources().getString(R.string.headline_title);
        String body = getResources().getString(R.string.headline_body);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.temp_news_img);
        for (int i = 0; i < 10; i++) {
            adapter.addItem(title, body, bmp);
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Intent intent = new Intent(DM_HeadLineActivity.this, DM_NewsDetailsActivity.class);
                startActivity(intent);
            }
        });

    }

    private final RadioGroup.OnCheckedChangeListener onCheckedChangedListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final RadioGroup group, final int checkedId) {

            // Slide to the appropriate screen when the user checks a button.
            switch (checkedId) {
                case R.id.radio_btn_0:
                    mPager.setCurrentScreen(0, true);
                    txtHorizontalTitle.setText("湖北省第四届飞Young杯");
                    break;
                case R.id.radio_btn_1:
                    mPager.setCurrentScreen(1, true);
                    txtHorizontalTitle.setText("再见 那些年 毕业季");
                    break;
                case R.id.radio_btn_2:
                    mPager.setCurrentScreen(2, true);
                    txtHorizontalTitle.setText("飞Young杯 网络歌手大赛");
                    break;
                case R.id.radio_btn_3:
                    mPager.setCurrentScreen(3, true);
                    txtHorizontalTitle.setText("记忆中的橡皮擦");
                default:
                    break;
            }
        }
    };

    private final HorizontalPager.OnScreenSwitchListener onScreenSwitchListener = new HorizontalPager.OnScreenSwitchListener() {
        @Override
        public void onScreenSwitched(final int screen) {

            // Check the appropriate button when the user swipes screens.
            switch (screen) {
                case 0:
                    mRadioGroup.check(R.id.radio_btn_0);
                    break;
                case 1:
                    mRadioGroup.check(R.id.radio_btn_1);
                    break;
                case 2:
                    mRadioGroup.check(R.id.radio_btn_2);
                    break;
                case 3:
                    mRadioGroup.check(R.id.radio_btn_3);
                    break;
                default:
                    break;
            }
        }

    };

    private final Handler mflipperHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            // TODO Auto-generated method stub
            switch (msg.what) {
                case SHOW_NEXT:
                    showNextView();
                    break;

                default:
                    break;
            }
        }

    };

    private final Thread thread = new Thread() {
        @Override
        public void run() {

            while (isRun) {
                try {
                    Thread.sleep(1000 * 4);
                    Message msg = new Message();
                    msg.what = SHOW_NEXT;
                    mflipperHandler.sendMessage(msg);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        }
    };

    private void showNextView() {

        currentItem = mPager.getCurrentScreen();
        if (currentItem < 3) {
            mPager.setCurrentScreen(++currentItem, true);
        } else {
            currentItem = 0;
            mPager.setCurrentScreen(currentItem, true);
        }
    }

}
