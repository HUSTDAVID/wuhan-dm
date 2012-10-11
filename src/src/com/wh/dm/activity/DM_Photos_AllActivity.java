
package com.wh.dm.activity;

import java.util.zip.Inflater;

import com.wh.dm.R;
import com.wh.dm.widget.PhotoAdapter;
import com.wh.dm.widget.PullToRefreshListView;
import com.wh.dm.widget.PullToRefreshListView.OnRefreshListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DM_Photos_AllActivity extends Activity {

    RelativeLayout relPhotosHeader;
    TextView txtPhotosHeader;

    PullToRefreshListView lvPhotos;
    View footer;
    Button btnFooter;
    LayoutInflater mInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dm_photos_all);
        mInflater = getLayoutInflater();
        initViews();
    }

    private void initViews() {

        // init header
        // relPhotosHeader = (RelativeLayout) findViewById(R.id.rel_header3);
        // txtPhotosHeader = (TextView) findViewById(R.id.txt_header3_title);
        // relPhotosHeader.setBackgroundResource(R.drawable.topbar_black_bg);
        // txtPhotosHeader.setText(getResources().getString(R.string.photo));

        lvPhotos = (PullToRefreshListView) findViewById(R.id.lv_photos_all);
        lvPhotos.setDivider(null);

        PhotoAdapter adapter = new PhotoAdapter(this);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.temp_photos_img);
        String title = getResources().getString(R.string.photos_title);
        for (int i = 0; i < 5; i++) {
            adapter.addItem(bmp, title, 6, 6, bmp, title, 6, 6);
        }

        lvPhotos.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// Your code to refresh the list contents goes here

				// Make sure you call listView.onRefreshComplete()
				// when the loading is done. This can be done from here or any
				// other place, like on a broadcast receive from your loading
				// service or the onPostExecute of your AsyncTask.

				// For the sake of this sample, the code will pause here to
				// force a delay when invoking the refresh
				lvPhotos.postDelayed(new Runnable() {

					@Override
					public void run() {
						lvPhotos.onRefreshComplete();
					}
				}, 2000);
			}
		});

        lvPhotos.setAdapter(adapter);

        lvPhotos.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent_list = new Intent(DM_Photos_AllActivity.this,
                        DM_PhotosDetailsActivity.class);
                startActivity(intent_list);
            }
        });

        footer = mInflater.inflate(R.layout.news_list_footer, null);
        footer.setBackgroundResource(R.drawable.photos_bg);
        btnFooter = (Button) footer.findViewById(R.id.btn_news_footer);
        lvPhotos.addFooterView(footer);
    }

}
