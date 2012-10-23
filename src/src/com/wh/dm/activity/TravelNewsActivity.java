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

public class TravelNewsActivity extends Activity {

	private ListView lv;
	private View footer;
	private Button btnFoolter;
	private LayoutInflater mInfalater;
	private static int MSG_GET_TRAVELNEWS = 0;
	private GetTravelNewsTask getTravelNewsTask = null;
	private ProgressDialog progressDialog = null;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == MSG_GET_TRAVELNEWS) {
				if (getTravelNewsTask != null) {
					getTravelNewsTask.cancel(true);
					getTravelNewsTask = null;
				}
				getTravelNewsTask = new GetTravelNewsTask();
				getTravelNewsTask.execute();
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
		handler.sendEmptyMessage(MSG_GET_TRAVELNEWS);

	}

	private class GetTravelNewsTask extends
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
				houseNews = (new WH_DM()).getTravelNews();
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
						TravelNewsActivity.this, result);
				if(result.size()<20){
					lv.removeFooterView(footer);
				}else{
					lv.addFooterView(footer);
				}
				lv.setAdapter(adapter);
				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long arg3) {
						Intent intent = new Intent(TravelNewsActivity.this,
								NewsDetailsActivity.class);
						intent.putExtra("id", result.get(position).getId());
						startActivity(intent);
					}

				});
			} else {
				Toast.makeText(TravelNewsActivity.this, reason.toString(),
						Toast.LENGTH_SHORT).show();
			}
			progressDialog.dismiss();
			super.onPostExecute(result);
		}

	}
}
