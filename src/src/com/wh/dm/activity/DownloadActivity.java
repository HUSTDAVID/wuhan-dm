
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.type.LoadInfo;
import com.wh.dm.widget.DownloadAdapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DownloadActivity extends Activity {

    private ListView lvDownload;
    private TextView txtTitle;
    private ImageButton btnBack;
    private ArrayList<LoadInfo> list;

    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        MobclickAgent.onError(this);
        setContentView(R.layout.activity_download);
        init();
    }

    @Override
    public void onResume() {

        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {

        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void init() {

        list = new ArrayList<LoadInfo>();
        txtTitle = (TextView) findViewById(R.id.txt_header_title2);
        txtTitle.setText(getString(R.string.download));
        btnBack = (ImageButton) findViewById(R.id.Btn_back_header2);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        lvDownload = (ListView) findViewById(R.id.lv_download);
        Bitmap bmp = (Bitmap) BitmapFactory.decodeResource(getResources(),
                R.drawable.subscription_manage_logo);
        LoadInfo load1 = new LoadInfo(bmp, "中百仓储", "22496人下载", false, true, false, 100);
        LoadInfo load2 = new LoadInfo(bmp, "Dior", "2224人下载", true, false, false, 20);
        LoadInfo load3 = new LoadInfo(bmp, "柏氏专卖", "22334人下载", true, false, true, 50);
        LoadInfo load4 = new LoadInfo(bmp, "柏氏专卖", "22334人下载", true, true, false, 50);
        list.add(load1);
        list.add(load3);
        list.add(load4);
        list.add(load2);
        DownloadAdapter adapter = new DownloadAdapter(this);
        adapter.setList(list);
        lvDownload.setAdapter(adapter);
    }
}
