package com.wh.dm.activity;

import java.util.ArrayList;

import com.wh.dm.R;
import com.wh.dm.WH_DM;
import com.wh.dm.db.DatabaseImpl;
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

public class FashionNewsActivity extends Activity {
	private ListView lv;
	private HeadlineAdapter adapter;
	private View footer;
	private Button btnFoolter;
	private LayoutInflater mInfalater;
	private static int MSG_GET_FASHIONNEWS = 0;
	private GetFashionNewsTask getFashionNewsTask = null;
	private ProgressDialog progressDialog = null;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == MSG_GET_FASHIONNEWS) {
				if (getFashionNewsTask != null) {
					getFashionNewsTask.cancel(true);
					getFashionNewsTask = null;
				}
				getFashionNewsTask = new GetFashionNewsTask();
				getFashionNewsTask.execute();
			}
		};
	};

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_house);
		adapter = new HeadlineAdapter(FashionNewsActivity.this);
		lv = (ListView) findViewById(R.id.news_list_house);
		lv.setAdapter(adapter);
		mInfalater = getLayoutInflater();
		footer = mInfalater.inflate(R.layout.news_list_footer, null);
		btnFoolter = (Button) footer.findViewById(R.id.btn_news_footer);
		lv.addFooterView(footer);
		progressDialog = new ProgressDialog(getParent());
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		handler.sendEmptyMessage(MSG_GET_FASHIONNEWS);

	}

	private class GetFashionNewsTask extends
			AsyncTask<Void, Void, ArrayList<PicWithTxtNews>> {

		Exception reason = null;

		@Override
		protected void onPreExecute() {
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected ArrayList<PicWithTxtNews> doInBackground(Void... params) {
			ArrayList<PicWithTxtNews> headNews = null;
			try {
				headNews = (new WH_DM()).getFashionNews();
				(new DatabaseImpl(FashionNewsActivity.this))
						.addFashionNews(headNews);
				return headNews;
			} catch (Exception e) {
				reason = e;
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(final ArrayList<PicWithTxtNews> result) {
			if (result != null) {
				adapter.setList(result);
				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long arg3) {
						Intent intent = new Intent(FashionNewsActivity.this,
								NewsDetailsActivity.class);
						intent.putExtra("id", result.get(position).getId());
						startActivity(intent);

					}

				});
			} else {
				ArrayList<PicWithTxtNews> savedNews = (new DatabaseImpl(
						FashionNewsActivity.this)).getFashionNews();
				if (savedNews != null && savedNews.size() > 0) {
					adapter.setList(savedNews);
					lv.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long arg3) {
							Intent intent = new Intent(FashionNewsActivity.this,
									NewsDetailsActivity.class);
							intent.putExtra("id", result.get(position).getId());
							startActivity(intent);
						}

					});
				}
				Toast.makeText(FashionNewsActivity.this, reason.toString(),
						Toast.LENGTH_SHORT).show();
			}
			progressDialog.dismiss();
			super.onPostExecute(result);
		}

	}
}
