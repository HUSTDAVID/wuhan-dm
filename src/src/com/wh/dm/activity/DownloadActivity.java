
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.widget.DownloadAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class DownloadActivity extends Activity {

    private ListView lvDownload;
    private TextView txtTitle;
    private ImageButton btnBack;

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
        DownloadAdapter adapter = new DownloadAdapter(this);
        lvDownload.setAdapter(adapter);
    }
}
