
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.widget.HorizontalPager;
import com.wh.dm.widget.MzineLongAdapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

        MzineLongAdapter adapter = new MzineLongAdapter(this);
        // add data
        Bitmap bmp1_bottom1 = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_2x_1);
        Bitmap bmp2_bottom1 = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_1x_3);
        Bitmap bmp3_bottom1 = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_1x_4);
        Bitmap bmp4_bottom1 = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_2x_2);

        Bitmap bmp1_bottom2 = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_1x_5);
        Bitmap bmp2_bottom2 = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_1x_6);
        Bitmap bmp3_bottom2 = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_1x_7);

        adapter.addItemType1(bmp1_bottom1, bmp2_bottom1, bmp3_bottom1, bmp4_bottom1);
        adapter.addItemType2(bmp1_bottom2, bmp2_bottom2, bmp3_bottom2);

        listView.setAdapter(adapter);

    }

}
