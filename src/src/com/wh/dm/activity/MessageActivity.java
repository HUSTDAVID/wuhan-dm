
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApp;
import com.wh.dm.type.PostMessage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageActivity extends Activity {

    private TextView txtHead;
    private ImageButton btnBack;
    private ArrayList<PostMessage> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        setContentView(R.layout.activity_message);
        txtHead = (TextView) findViewById(R.id.textView3);
        txtHead.setText(getString(R.string.push_message));
        btnBack = (ImageButton) findViewById(R.id.BackButton);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                MessageActivity.this.finish();

            }

        });

    }

    @Override
    protected void onResume() {

        WH_DMApp wh_dmApp = (WH_DMApp) this.getApplication();
        messages = wh_dmApp.getPostMessage();
        super.onResume();
    }
}
