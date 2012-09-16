
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DM_PhotosDetailsAcitivity extends Activity {

    LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photos_details);

        initViews();
    }

    private void initViews() {

        mInflater = (LayoutInflater.from(this));
        View header = mInflater.inflate(R.layout.header_title3, null);
        RelativeLayout relBg = (RelativeLayout) header.findViewById(R.id.rel_header3);
        relBg.setBackgroundResource(R.drawable.topbar_red_bg);
        TextView txtTitle = (TextView) header.findViewById(R.id.txt_header3_title);
        txtTitle.setText(getResources().getString(R.string.photo));
    }
}
