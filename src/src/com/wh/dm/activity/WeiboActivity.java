
package com.wh.dm.activity;

import com.umeng.api.sns.UMSnsService;
import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class WeiboActivity extends Activity {
    private Button button_sina;
    private Button button_tenc;
    public static int flag = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_toweibo);
        UMSnsService.UseLocation = true;
        UMSnsService.LocationAuto = true;
        UMSnsService.LOCATION_VALID_TIME = 180000;

        initViews();
        initButton1();
        initButton2();
    }

    private void initViews() {

        TextView txtHeader = (TextView) findViewById(R.id.txt_header_title2);
        txtHeader.setText(getResources().getString(R.string.toweibo));

        ImageButton btnBack = (ImageButton) findViewById(R.id.Btn_back_header2);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });

    }

    private void initButton1() {

        final Button button_sina = (Button) findViewById(R.id.button1);

        button_sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 0) {

                    button_sina.setText("解除绑定");
                    button_sina.setTextSize(15);
                    UMSnsService.oauthSina(WeiboActivity.this, null);
                    flag = 1;

                } else {

                    button_sina.setText("绑定");
                    UMSnsService.writeOffAccount(WeiboActivity.this, UMSnsService.SHARE_TO.SINA);
                    flag = 0;

                }

            }

        });

    }

    private void initButton2() {

        final Button button_tenc = (Button) findViewById(R.id.button2);

        button_tenc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 0) {

                    UMSnsService.oauthTenc(WeiboActivity.this, null);
                    button_tenc.setText("解除绑定");
                    button_tenc.setTextSize(15);
                    flag = 1;

                } else {

                    UMSnsService.writeOffAccount(WeiboActivity.this, UMSnsService.SHARE_TO.TENC);
                    button_tenc.setText("绑定");
                    flag = 0;

                }

            }

        });

    }

}
