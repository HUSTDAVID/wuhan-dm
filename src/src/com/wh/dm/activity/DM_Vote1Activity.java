
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class DM_Vote1Activity extends Activity {
    /** Called when the activity is first created. */

    ProgressBar progressBar1;
    ProgressBar progressBar2;
    ProgressBar progressBar3;
    private ImageButton btnBack;
    private Button btnClose;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote1);
        init();
    }

    public void init() {

        progressBar1 = (ProgressBar) findViewById(R.id.pro_vote_result1);
        progressBar2 = (ProgressBar) findViewById(R.id.pro_vote_result2);
        progressBar3 = (ProgressBar) findViewById(R.id.pro_vote_result3);
        btnBack =(ImageButton)findViewById(R.id.img_header3_back);
        btnBack.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View view){
        		finish();
        	}
        });
        btnClose =(Button)findViewById(R.id.vote_button_close);
        btnClose.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		finish();
        	}
        });
        setProgress(progressBar1, 43);
        setProgress(progressBar2, 15);
        setProgress(progressBar3, 42);

    }

    private void setProgress(ProgressBar progress, int percent) {

        LayoutParams params = progress.getLayoutParams();
        params.width = (int) (percent * 3.5);
        progress.setLayoutParams(params);
    }
}
