
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.widget.PhotoAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DM_Photos_AllActivity extends Activity {

    RelativeLayout relPhotosHeader;
    TextView txtPhotosHeader;

    GridView gvPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dm_photos_all);

        initViews();
    }

    private void initViews() {

        // init header
        // relPhotosHeader = (RelativeLayout) findViewById(R.id.rel_header3);
        // txtPhotosHeader = (TextView) findViewById(R.id.txt_header3_title);
        // relPhotosHeader.setBackgroundResource(R.drawable.topbar_black_bg);
        // txtPhotosHeader.setText(getResources().getString(R.string.photo));

        gvPhotos = (GridView) findViewById(R.id.gv_photos_all);

        PhotoAdapter adapter = new PhotoAdapter(this);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.temp_photos_img);
        String title = getResources().getString(R.string.photos_title);
        for (int i = 0; i < 10; i++) {
            adapter.addItem(bmp, title, 6, 6);
        }
        gvPhotos.setAdapter(adapter);

        gvPhotos.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent_list = new Intent(DM_Photos_AllActivity.this,
                        DM_PhotosDetailsActivity.class);
                startActivity(intent_list);
            }
        });
    }

}
