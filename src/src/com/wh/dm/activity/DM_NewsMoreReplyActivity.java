
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class DM_NewsMoreReplyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_reply);

        initViews();
    }

    private void initViews() {

        // init header
        TextView txtTitle = (TextView) findViewById(R.id.txt_header3_title);
        txtTitle.setText(getResources().getString(R.string.reply));
        TextView txtReply = (TextView) findViewById(R.id.txt_total_reply);
        txtReply.setText(getResources().getString(R.string.context));
    }
}
