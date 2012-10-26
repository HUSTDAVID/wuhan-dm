package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.WH_DM;
import com.wh.dm.widget.HeadlineAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.PicWithTxtNews;
import java.util.*;

public class HouseNewsActivity extends Activity {

	private ListView lv;
	private ProgressDialog progressDialog;
	private GetHouseNewsTask getHouseNewsTask = null;
	private static int MSG_GET_HOUSENEWS = 0;
	private View footer;
	private Button btnFoolter;
	private LayoutInflater mInfalater;
	private HeadlineAdapter adapter;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == MSG_GET_HOUSENEWS) {
				if (getHouseNewsTask != null) {
					getHouseNewsTask.cancel(true);
					getHouseNewsTask = null;
				}
				getHouseNewsTask = new GetHouseNewsTask();
				getHouseNewsTask.execute();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_house);
		adapter = new HeadlineAdapter(HouseNewsActivity.this);
		lv = (ListView) findViewById(R.id.news_list_house);
        lv.setAdapter(adapter);

		mInfalater = getLayoutInflater();
		footer = mInfalater.inflate(R.layout.news_list_footer, null);
		btnFoolter = (Button) footer.findViewById(R.id.btn_news_footer);
		lv.addFooterView(footer);

		progressDialog = new ProgressDialog(getParent());
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {

				if (getHouseNewsTask != null) {
					getHouseNewsTask.cancel(true);
					getHouseNewsTask = null;
				}
			}

		});
		handler.sendEmptyMessage(MSG_GET_HOUSENEWS);
	}

	private class GetHouseNewsTask extends
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
				headNews = (new WH_DM()).getHouseNews();
				(new DatabaseImpl(HouseNewsActivity.this))
						.addHouseNews(headNews);
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
						Intent intent = new Intent(HouseNewsActivity.this,
								NewsDetailsActivity.class);
						intent.putExtra("id", result.get(position).getId());
						startActivity(intent);

					}

				});
			} else {
				ArrayList<PicWithTxtNews> savedNews = (new DatabaseImpl(
						HouseNewsActivity.this)).getHouseNews();
				if (savedNews != null && savedNews.size() > 0) {
					adapter.setList(savedNews);
					lv.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long arg3) {
							Intent intent = new Intent(HouseNewsActivity.this,
									NewsDetailsActivity.class);
							intent.putExtra("id", result.get(position).getId());
							startActivity(intent);
						}

					});
				}
				Toast.makeText(HouseNewsActivity.this, reason.toString(),
						Toast.LENGTH_SHORT).show();
			}
			progressDialog.dismiss();
			super.onPostExecute(result);
		}

	}
}
