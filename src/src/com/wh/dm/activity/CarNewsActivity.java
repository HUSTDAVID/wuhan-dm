package com.wh.dm.activity;

import java.util.ArrayList;

import com.wh.dm.R;
import com.wh.dm.WH_DM;
import com.wh.dm.type.PicWithTxtNews;
import com.wh.dm.widget.HeadlineAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CarNewsActivity extends Activity {

	private ListView lv;
	private static int MSG_GET_CARNEWS = 0;
	private GetCarNewsTask getCarNewsTask = null;
	private ProgressDialog progressDialog = null;
	private View footer;
	private Button btnFoolter;
	private LayoutInflater mInfalater;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==MSG_GET_CARNEWS){
				if(getCarNewsTask!=null){
					getCarNewsTask.cancel(true);
					getCarNewsTask = null;
				}
				getCarNewsTask = new GetCarNewsTask();
				getCarNewsTask.execute();
			}
		};
	};
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_news_house);
		lv = (ListView) findViewById(R.id.news_list_house);
		mInfalater = getLayoutInflater();
		footer = mInfalater.inflate(R.layout.news_list_footer, null);
		btnFoolter = (Button) footer.findViewById(R.id.btn_news_footer);
		lv.addFooterView(footer);
		progressDialog = new ProgressDialog(getParent());
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		handler.sendEmptyMessage(MSG_GET_CARNEWS);

	}

	private class GetCarNewsTask extends
			AsyncTask<Void, Void, ArrayList<PicWithTxtNews>> {

		Exception reason = null;

		@Override
		protected void onPreExecute() {
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected ArrayList<PicWithTxtNews> doInBackground(Void... params) {
			ArrayList<PicWithTxtNews> houseNews = null;
			try {
				houseNews = (new WH_DM()).getCarNews();
				return houseNews;
			} catch (Exception e) {
				reason = e;
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(final ArrayList<PicWithTxtNews> result) {
			if (result != null) {
				HeadlineAdapter adapter = new HeadlineAdapter(
						CarNewsActivity.this, result);
				
				lv.setAdapter(adapter);
				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long arg3) {
						Intent intent = new Intent(CarNewsActivity.this,
								NewsDetailsActivity.class);
						intent.putExtra("id", result.get(position).getId());
						startActivity(intent);
					}

				});
			} else {
				Toast.makeText(CarNewsActivity.this, reason.toString(),
						Toast.LENGTH_SHORT).show();
			}
			progressDialog.dismiss();
			super.onPostExecute(result);
		}

	}

}
