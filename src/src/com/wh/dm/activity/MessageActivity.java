
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MessageActivity extends Activity {

    private TextView txtHead;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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
}
