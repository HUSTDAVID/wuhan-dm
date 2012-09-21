
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DM_PhotosDetailsAcitivity extends Activity {

    LayoutInflater mInflater;
    ImageView imgArrow;
    TextView txtDetailsTitle;
    TextView txtDetailsBody;
    TextView txtDetailsNum;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photos_details);

        initViews();
    }

    private void initViews() {

        // init header
        mInflater = (LayoutInflater.from(this));
        View header = mInflater.inflate(R.layout.header_title3, null);
        RelativeLayout relBg = (RelativeLayout) header.findViewById(R.id.rel_header3);
        relBg.setBackgroundResource(R.drawable.topbar_red_bg);
        TextView txtTitle = (TextView) header.findViewById(R.id.txt_header3_title);
        txtTitle.setText(getResources().getString(R.string.photo));

        // init center
        imgArrow = (ImageView) findViewById(R.id.img_photos_details_arrow);
        txtDetailsTitle = (TextView) findViewById(R.id.txt_photos_details_title);
        txtDetailsBody = (TextView) findViewById(R.id.img_photos_details_arrow);
        txtDetailsNum = (TextView) findViewById(R.id.img_photos_details_arrow);
    }
}
