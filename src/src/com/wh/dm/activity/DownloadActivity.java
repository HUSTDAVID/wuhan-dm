package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.widget.DownloadAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class DownloadActivity extends Activity{

	private ListView lvDownload;
	private TextView txtTitle;
	private ImageButton btnBack;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.activity_download);
		init();
	}
	public void init(){
		txtTitle =(TextView)findViewById(R.id.txt_header_title2);
		txtTitle.setText(getString(R.string.download));
		btnBack = (ImageButton)findViewById(R.id.Btn_back_header2);
		btnBack.setOnClickListener(new OnClickListener(){
			public void onClick(View view){
				finish();
			}
		});
		lvDownload =(ListView)findViewById(R.id.lv_download);
		DownloadAdapter adapter = new DownloadAdapter(this);
		lvDownload.setAdapter(adapter);
	}
}
