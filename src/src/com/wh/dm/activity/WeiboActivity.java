
package com.wh.dm.activity;

import com.umeng.api.sns.UMSnsService;
import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class WeiboActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_toweibo);
        initViews();

        initViews();
        UMSnsService.UseLocation = true;
        UMSnsService.LocationAuto = true;
        UMSnsService.LOCATION_VALID_TIME = 180000; // 30MINS

        ImageButton btnOauthTenc = (ImageButton) findViewById(R.id.weibo_imagebtn2);
        ImageButton btnOauthSina = (ImageButton) findViewById(R.id.weibo_imagebtn1);

        btnOauthTenc.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                UMSnsService.oauthTenc(WeiboActivity.this, null);
            }
        });

        btnOauthSina.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                UMSnsService.oauthSina(WeiboActivity.this, null);
            }
        });
        ImageButton btnBack = (ImageButton) findViewById(R.id.Btn_back_header2);
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });
    }

    private void initViews() {

        TextView txtHeader = (TextView) findViewById(R.id.txt_header_title2);
        txtHeader.setText(getResources().getString(R.string.toweibo));

    }
}
