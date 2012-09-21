
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.widget.HeadlineAdapter;
import com.wh.dm.widget.HorizontalPager;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

public class DM_MzineLongActivity extends Activity {

    private List<ImageView> imageViews;
    private View headerView;
    private LayoutInflater mInfalater;
    private ListView listView;
    private boolean isRun = true;

    private HorizontalPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dm_mzine_long);

        init();
        // thread.start();
    }

    private void init() {

        mInfalater = getLayoutInflater();
        headerView = (View) mInfalater.inflate(R.layout.dm_mzine_long_header, null);

        listView = (ListView) findViewById(R.id.lv_mzine_long1);

        listView.addHeaderView(headerView);

        HeadlineAdapter adapter = new HeadlineAdapter(this);

        listView.setAdapter(adapter);

    }

}