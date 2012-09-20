
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.widget.HeadlineAdapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class DM_News_HouseActivity extends Activity {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_house);
        lv = (ListView) findViewById(R.id.news_list_house);
        HeadlineAdapter adapter = new HeadlineAdapter(this);
        // add data
        String title = getResources().getString(R.string.headline_title);
        String body = getResources().getString(R.string.headline_body);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.temp_news_img);
        for (int i = 0; i < 10; i++) {
            adapter.addItem(title, body, bmp);
        }

        lv.setAdapter(adapter);
    }
}
