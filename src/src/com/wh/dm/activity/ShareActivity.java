
package com.wh.dm.activity;

import com.umeng.api.sns.UMSnsService;
import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShareActivity extends Activity implements OnClickListener {

    private String shareStr;
    private RelativeLayout relMessage;
    private RelativeLayout relSina;
    private RelativeLayout relTenc;
    private ImageView imgMessage;
    private ImageView imgSina;
    private ImageView imgTenc;
    private TextView txtMessage;
    private TextView txtSina;
    private TextView txtTenc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        initView();
    }

    private void initView() {

        // init header
        TextView txtHeader = (TextView) findViewById(R.id.txt_header_title2);
        txtHeader.setText(getResources().getString(R.string.meike_share));

        ImageButton btnBack = (ImageButton) findViewById(R.id.Btn_back_header2);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
            }

        });

        // init share
        relMessage = (RelativeLayout) findViewById(R.id.rel_share_message);
        relSina = (RelativeLayout) findViewById(R.id.rel_share_sina);
        relTenc = (RelativeLayout) findViewById(R.id.rel_share_tenc);
        imgMessage = (ImageView) findViewById(R.id.img_share_message);
        imgSina = (ImageView) findViewById(R.id.img_share_sina);
        imgTenc = (ImageView) findViewById(R.id.img_share_tenc);
        txtMessage = (TextView) findViewById(R.id.txt_share_message);
        txtSina = (TextView) findViewById(R.id.txt_share_sina);
        txtTenc = (TextView) findViewById(R.id.txt_share_tenc);

        relMessage.setOnClickListener(this);
        relSina.setOnClickListener(this);
        relTenc.setOnClickListener(this);
        imgMessage.setOnClickListener(this);
        imgSina.setOnClickListener(this);
        imgTenc.setOnClickListener(this);
        txtMessage.setOnClickListener(this);
        txtSina.setOnClickListener(this);
        txtTenc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rel_share_message:
            case R.id.img_share_message:
            case R.id.txt_share_message:
                break;
            case R.id.rel_share_sina:
            case R.id.img_share_sina:
            case R.id.txt_share_sina:
                UMSnsService.shareToSina(ShareActivity.this, shareStr, null);
                break;
            case R.id.rel_share_tenc:
            case R.id.img_share_tenc:
            case R.id.txt_share_tenc:
                UMSnsService.shareToTenc(ShareActivity.this, shareStr, null);
                break;

        }

    }

}
