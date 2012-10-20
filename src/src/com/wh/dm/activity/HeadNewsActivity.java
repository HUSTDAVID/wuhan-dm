package com.wh.dm.activity;

import java.io.IOException;
import java.util.ArrayList;

import com.wh.dm.R;
import com.wh.dm.WH_DM;
import com.wh.dm.error.UnKnownException;
import com.wh.dm.error.WH_DMException;
import com.wh.dm.type.PicWithTxtNews;
import com.wh.dm.type.PicsNews;
import com.wh.dm.util.UrlImageViewHelper;
import com.wh.dm.widget.HeadlineAdapter;
import com.wh.dm.widget.HorizontalPager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class HeadNewsActivity extends Activity {

	private String URL_DOMAIN = "http://test1.jbr.net.cn:809";
	private String[] titles;
	private int currentItem = 0;
	private View headerView;
	private LayoutInflater mInfalater;
	private ListView listView;
	private View footer;
	private Button btnFoolter;

	private final int SHOW_NEXT = 0011;
	private final boolean isRun = true;

	private HorizontalPager mPager;
	private RadioGroup mRadioGroup;
	private ImageView pic0;
	private ImageView pic1;
	private ImageView pic2;
	private ImageView pic3;

	private TextView txtNews;
	private ProgressDialog progressDialog;
	private static final int MSG_GET_PICSNEWS = 0;
	private static final int MSG_GET_HEADNEWS = 1;
	private GetPicsNewsTask getPicsNewsTask = null;
	private GetHeadNewsTask getHeadNewsTask = null;

	private ArrayList<PicsNews> picsNews = null;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_GET_PICSNEWS:
				if (getPicsNewsTask != null) {
					getPicsNewsTask.cancel(true);
					getPicsNewsTask = null;
				}
				getPicsNewsTask = new GetPicsNewsTask();
				getPicsNewsTask.execute();
				break;
			case MSG_GET_HEADNEWS:
				if (getHeadNewsTask != null) {
					getHeadNewsTask.cancel(true);
					getHeadNewsTask = null;
				}
				getHeadNewsTask = new GetHeadNewsTask();
				getHeadNewsTask.execute();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_headline);
		init();
	}

	private void init() {

		pic0 = (ImageView) findViewById(R.id.pic0);
		pic1 = (ImageView) findViewById(R.id.pic1);
		pic2 = (ImageView) findViewById(R.id.pic2);
		pic3 = (ImageView) findViewById(R.id.pic3);
		txtNews = (TextView) findViewById(R.id.txt_horizontal_title);

		mInfalater = getLayoutInflater();
		listView = (ListView) findViewById(R.id.list);
		footer = mInfalater.inflate(R.layout.news_list_footer, null);
		btnFoolter = (Button) footer.findViewById(R.id.btn_news_footer);
		listView.addFooterView(footer);
		mRadioGroup = (RadioGroup) findViewById(R.id.tabs);
		mRadioGroup.setOnCheckedChangeListener(onCheckedChangedListener);
		mPager = (HorizontalPager) findViewById(R.id.horizontal_pager);
		mPager.setOnScreenSwitchListener(onScreenSwitchListener);
		mPager.setCurrentScreen(0, true);

		progressDialog = new ProgressDialog(getParent());
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {

				if (getHeadNewsTask != null) {
					getHeadNewsTask.cancel(true);
					getHeadNewsTask = null;
				}
				if (getPicsNewsTask != null) {
					getPicsNewsTask.cancel(true);
					getPicsNewsTask = null;
				}
			}
		});

		handler.sendEmptyMessage(MSG_GET_PICSNEWS);
		handler.sendEmptyMessage(MSG_GET_HEADNEWS);
	}

	private class GetPicsNewsTask extends
			AsyncTask<Void, Void, ArrayList<PicsNews>> {
		Exception reason = null;
		@Override
		protected ArrayList<PicsNews> doInBackground(Void... arg0) {
			ArrayList<PicsNews> picsNews = null;

			try {
				picsNews = (new WH_DM()).getPicsNews();
				return picsNews;
			} catch (Exception e) {
				reason = e;
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(ArrayList<PicsNews> result) {
			if (result != null) {
				picsNews = result;
				UrlImageViewHelper.setUrlDrawable(pic0, URL_DOMAIN
						+ result.get(0).getLitpic());
				UrlImageViewHelper.setUrlDrawable(pic1, URL_DOMAIN
						+ result.get(1).getLitpic());
				if (thread.isAlive()) {
					thread.destroy();
				}
				thread.start();

			} else {
				Toast.makeText(HeadNewsActivity.this, reason.toString(),
						Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}
	}

	private class GetHeadNewsTask extends
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
				headNews = (new WH_DM()).getHeadNews();
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
				HeadlineAdapter adapter = new HeadlineAdapter(
						HeadNewsActivity.this, result);

				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long arg3) {
						Intent intent = new Intent(HeadNewsActivity.this,NewsDetailsActivity.class);
						intent.putExtra("id", result.get(position).getId());
						startActivity(intent);
					}

				});
			} else {
				Toast.makeText(HeadNewsActivity.this, reason.toString(),
						Toast.LENGTH_SHORT).show();
			}
			progressDialog.dismiss();
			super.onPostExecute(result);
		}

	}

	private final RadioGroup.OnCheckedChangeListener onCheckedChangedListener = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(final RadioGroup group, final int checkedId) {

			// Slide to the appropriate screen when the user checks a button.
			switch (checkedId) {
			case R.id.radio_btn_0:
				mPager.setCurrentScreen(0, true);
				break;
			case R.id.radio_btn_1:
				mPager.setCurrentScreen(1, true);
				break;
			case R.id.radio_btn_2:
				mPager.setCurrentScreen(2, true);
				break;
			case R.id.radio_btn_3:
				mPager.setCurrentScreen(3, true);
			default:
				break;
			}
		}
	};

	private final HorizontalPager.OnScreenSwitchListener onScreenSwitchListener = new HorizontalPager.OnScreenSwitchListener() {
		@Override
		public void onScreenSwitched(final int screen) {

			// Check the appropriate button when the user swipes screens.
			switch (screen) {
			case 0:
				mRadioGroup.check(R.id.radio_btn_0);
				if (picsNews != null) {
					txtNews.setText(picsNews.get(0).getTitle());
				}
				break;
			case 1:
				mRadioGroup.check(R.id.radio_btn_1);
				if (picsNews != null) {
					txtNews.setText(picsNews.get(1).getTitle());
				}
				break;
			case 2:
				mRadioGroup.check(R.id.radio_btn_2);
				break;
			case 3:
				mRadioGroup.check(R.id.radio_btn_3);
				break;
			default:
				break;
			}
		}

	};

	private final Handler mflipperHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			// TODO Auto-generated method stub
			switch (msg.what) {
			case SHOW_NEXT:
				showNextView();
				break;

			default:
				break;
			}
		}

	};

	private final Thread thread = new Thread() {
		@Override
		public void run() {

			while (isRun) {
				try {
					Thread.sleep(1000 * 4);
					Message msg = new Message();
					msg.what = SHOW_NEXT;
					mflipperHandler.sendMessage(msg);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
		}
	};

	private void showNextView() {

		currentItem = mPager.getCurrentScreen();
		if (currentItem < 3) {
			mPager.setCurrentScreen(++currentItem, true);
		} else {
			currentItem = 0;
			mPager.setCurrentScreen(currentItem, true);
		}
	}

}
