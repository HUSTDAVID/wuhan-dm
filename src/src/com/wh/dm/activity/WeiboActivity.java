
package com.wh.dm.activity;

import com.umeng.api.sns.UMSNSException;
import com.umeng.api.sns.UMSnsService;
import com.umeng.api.sns.UMSnsService.OauthCallbackListener;
import com.umeng.api.sns.UMSnsService.SHARE_TO;
import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class WeiboActivity extends Activity {
    private Button btnSina;
    private Button btnTenc;
    public static int flag = 0;
    private boolean isBindSina;
    private boolean isBingTenc;
    UMSnsService.OauthCallbackListener listener = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_toweibo);
        UMSnsService.UseLocation = true;
        UMSnsService.LocationAuto = true;
        UMSnsService.LOCATION_VALID_TIME = 180000;

        initViews();
    }

    private void initViews() {

        isBindSina = UMSnsService.isAuthorized(this, UMSnsService.SHARE_TO.SINA);
        isBingTenc = UMSnsService.isAuthorized(this, UMSnsService.SHARE_TO.TENC);

        btnSina = (Button) findViewById(R.id.button1);
        btnTenc = (Button) findViewById(R.id.button2);
        if (isBindSina) {
            btnSina.setText(getResources().getString(R.string.bind));
            btnSina.setTextColor(getResources().getColor(R.color.white));
            btnSina.setBackgroundColor(getResources().getColor(R.color.red));
        } else {
            btnSina.setText(getResources().getString(R.string.unbind));
        }
        if (isBingTenc) {
            btnTenc.setText(getResources().getString(R.string.bind));
            btnTenc.setTextColor(getResources().getColor(R.color.white));
            btnTenc.setBackgroundColor(getResources().getColor(R.color.red));
        } else {
            btnTenc.setText(getResources().getString(R.string.unbind));
        }

        listener = new OauthCallbackListener() {

            @Override
            public void onError(UMSNSException arg0, SHARE_TO arg1) {

            }

            @Override
            public void onComplete(Bundle arg0, SHARE_TO arg1) {

                if (arg1 == UMSnsService.SHARE_TO.TENC) {
                    btnTenc.setText(getResources().getString(R.string.bind));
                } else if (arg1 == UMSnsService.SHARE_TO.SINA) {
                    btnSina.setText(getResources().getString(R.string.bind));
                }

            }
        };

        btnSina.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                isBindSina = UMSnsService.isAuthorized(WeiboActivity.this,
                        UMSnsService.SHARE_TO.SINA);
                if (isBindSina) {
                    UMSnsService.writeOffAccount(WeiboActivity.this, UMSnsService.SHARE_TO.SINA);
                    btnSina.setText(getResources().getString(R.string.unbind));
                } else {
                    UMSnsService.oauthSina(WeiboActivity.this, listener);
                }

            }
        });
        btnTenc.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                isBingTenc = UMSnsService.isAuthorized(WeiboActivity.this,
                        UMSnsService.SHARE_TO.TENC);
                if (isBingTenc) {
                    UMSnsService.writeOffAccount(WeiboActivity.this, UMSnsService.SHARE_TO.TENC);
                    btnTenc.setText(getResources().getString(R.string.unbind));
                } else {
                    UMSnsService.oauthTenc(WeiboActivity.this, listener);
                }

            }
        });
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

}
