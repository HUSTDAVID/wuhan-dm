
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DM_HeadLineActivity extends Activity {

    private List<ImageView> imageViews;
    private String[] titles;
    private List<View> dots;
    private TextView txt_title;
    private int currentItem = 0;
    private View headerView;
    private LayoutInflater mInfalater;
    private ListView listView;

    private final int SHOW_NEXT = 0011;
    private boolean isRun = true;

    private HorizontalPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_headline);

        init();
        // thread.start();
    }

    private void init() {

        mInfalater = getLayoutInflater();
        headerView = (View) mInfalater.inflate(R.layout.header_headline, null);

        txt_title = (TextView) headerView.findViewById(R.id.txt_headline_title);

        listView = (ListView) findViewById(R.id.lv_headline);

        // image title
        titles = new String[4];
        titles[0] = "这里是标题一呀一呀有木有";
        titles[1] = "这里是标题二呀二呀有木有";
        titles[2] = "这里是标题三呀三呀有木有";
        titles[3] = "这里是标题四呀四呀有木有";
        //
        mPager = (HorizontalPager) headerView.findViewById(R.id.horizontal_pager);
        // mPager.setOnScreenSwitchListener(onScreenSwitchListener);
        mPager.setCurrentScreen(0, true);

        // the four dot
        dots = new ArrayList<View>();
        dots.add(headerView.findViewById(R.id.view_news_dot0));
        dots.add(headerView.findViewById(R.id.view_news_dot1));
        dots.add(headerView.findViewById(R.id.view_news_dot2));
        dots.add(headerView.findViewById(R.id.view_news_dot3));

        // the first title
        txt_title.setText(titles[0]);

        // listView.addHeaderView(headerView);

        HeadlineAdapter adapter = new HeadlineAdapter(this);
        // add data
        String title = getResources().getString(R.string.headline_title);
        String body = getResources().getString(R.string.headline_body);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.temp_news_img);
        for (int i = 0; i < 10; i++) {
            adapter.addItem(title, body, bmp);
        }
        listView.setAdapter(adapter);

        /*
         * ArrayList<View> views = new ArrayList<View>(); for (int i = 0; i <
         * 100; i++) { TextView tv = new TextView(this); tv.setText(i + "ddd");
         * views.add(tv); } listView.setAdapter(new SackOfViewsAdapter(views));
         */
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Intent intent = new Intent(DM_HeadLineActivity.this, DM_NewsDetailsActivity.class);
                startActivity(intent);
            }
        });

    }

    private Handler mflipperHandler = new Handler() {

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

    private Thread thread = new Thread() {
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
