
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.widget.MzineLongAdapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

public class DM_MZineLongActivity extends Activity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dm_mzine_long);

        initViews();
    }

    private void initViews() {

        // init activity header
        ImageView header = (ImageView) findViewById(R.id.img_header);
        header.setImageResource(R.drawable.temp_magazine_title);

        // init image header
        ImageView img1Header = (ImageView) findViewById(R.id.mzine_long_img_header_1);
        ImageView img2Header = (ImageView) findViewById(R.id.mzine_long_img_header_2);
        ImageView img3Header = (ImageView) findViewById(R.id.mzine_long_img_header_3);

        Bitmap bmp1Header = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_4x_1);
        Bitmap bmp2Header = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_1x_2);
        Bitmap bmp3Header = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_1x_1);

        img1Header.setImageBitmap(bmp1Header);
        img2Header.setImageBitmap(bmp2Header);
        img3Header.setImageBitmap(bmp3Header);

        // init list view
        listview = (ListView) findViewById(R.id.lv_mzine_long1);
        MzineLongAdapter adapter = new MzineLongAdapter(this);

        Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_2x_1);
        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_2x_2);
        Bitmap bmp3 = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_1x_3);
        Bitmap bmp4 = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_1x_4);
        Bitmap bmp5 = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_1x_5);
        Bitmap bmp6 = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_1x_6);
        Bitmap bmp7 = BitmapFactory.decodeResource(getResources(), R.drawable.temp_dm_1x_7);

        adapter.addItem(bmp1, bmp2, bmp3, bmp4, bmp5, bmp6, bmp7);
        adapter.addItem(bmp1, bmp2, bmp3, bmp4, bmp5, bmp6, bmp7);

        listview.setAdapter(adapter);

    }

}
