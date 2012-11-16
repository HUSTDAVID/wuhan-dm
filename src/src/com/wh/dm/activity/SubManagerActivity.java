
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.widget.SubManagerAdapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class SubManagerActivity extends Activity {

    private ListView lvSubManager;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dm_sub_manager);

        initViews();
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

    private void initViews() {

        // init header
        TextView txtTitle = (TextView) findViewById(R.id.txt_header3_title);
        TextView txtQRCode = (TextView) findViewById(R.id.txt_total_reply);
        btnBack = (ImageButton) findViewById(R.id.img_header3_back);
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });
        txtTitle.setText(getResources().getString(R.string.subscribe));
        txtQRCode.setText("");
        txtQRCode.setBackgroundResource(R.drawable.temp_2d);

        // init listview
        lvSubManager = (ListView) findViewById(R.id.lv_sub_manager);

        SubManagerAdapter adapter = new SubManagerAdapter(this);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                R.drawable.subscription_manage_logo);
        for (int i = 0; i < 5; i++) {
            adapter.addItem(bmp, "中百仓储", "22496人订阅", "新菜天天送，瓶装可乐1元/瓶");
        }
        lvSubManager.setAdapter(adapter);
    }

}
