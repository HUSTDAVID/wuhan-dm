
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.widget.PullToRefreshListView;
import com.wh.dm.widget.PullToRefreshListView.OnRefreshListener;
import com.wh.dm.widget.SubscribeAdapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Sub_HotActivity extends Activity {

    PullToRefreshListView lvSub;
    View footer;
    Button btnFooter;
    LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sub_item);
        mInflater = getLayoutInflater();
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

        lvSub = (PullToRefreshListView) findViewById(R.id.lv_subscribe);
        lvSub.setDivider(null);

        SubscribeAdapter adapter = new SubscribeAdapter(this);

        Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.tempdm1);
        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.tempdm2);
        Bitmap bmp3 = BitmapFactory.decodeResource(getResources(), R.drawable.dm_temp1);
        Bitmap bmp4 = BitmapFactory.decodeResource(getResources(), R.drawable.dm_temp2);
        Bitmap bmp5 = BitmapFactory.decodeResource(getResources(), R.drawable.dm_temp3);
        Bitmap bmp6 = BitmapFactory.decodeResource(getResources(), R.drawable.fashion_travel);
        adapter.addItem(bmp3, "爱帝", false, bmp4, "君顶会", false);
        adapter.addItem(bmp5, "中国石油石化", false, bmp6, "时尚旅游", false);
        adapter.addItem(bmp1, "光博会", true, bmp2, "今日光谷", true);

        lvSub.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {

                // Your code to refresh the list contents goes here

                // Make sure you call listView.onRefreshComplete()
                // when the loading is done. This can be done from here or any
                // other place, like on a broadcast receive from your loading
                // service or the onPostExecute of your AsyncTask.

                // For the sake of this sample, the code will pause here to
                // force a delay when invoking the refresh
                lvSub.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        lvSub.onRefreshComplete();
                    }
                }, 2000);
            }
        });
        lvSub.setAdapter(adapter);
        footer = mInflater.inflate(R.layout.news_list_footer, null);
        footer.setBackgroundColor(getResources().getColor(R.color.bg_normal));
        btnFooter = (Button) footer.findViewById(R.id.btn_news_footer);
        lvSub.addFooterView(footer);
    }
}
