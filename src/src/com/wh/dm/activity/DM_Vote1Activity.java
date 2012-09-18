
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DM_Vote1Activity extends Activity {
    /** Called when the activity is first created. */
    private Button btn_close;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote1);
        init();
    }

    public void init() {

        btn_close = (Button) findViewById(R.id.vote_button_close);
        btn_close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });
    }
}
