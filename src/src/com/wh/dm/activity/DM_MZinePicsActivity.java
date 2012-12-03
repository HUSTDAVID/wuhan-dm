
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.type.PictureMagzine;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.UrlImageViewHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class DM_MZinePicsActivity extends Activity {

    private ViewPager v_Pager;
    private ProgressDialog progressDialog;
    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private ArrayList<View> views;
    private MyPagerAdapter adapter;
    private LayoutInflater inflater;
    private GetPicsTask getPicsTask = null;
    private static final int MSG_GET_PICS = 0;
    private int sid;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == MSG_GET_PICS) {
                if (getPicsTask != null) {
                    getPicsTask.cancel(true);
                    getPicsTask = null;
                }
                getPicsTask = new GetPicsTask();
                getPicsTask.execute(sid);

            }
        };
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        setContentView(R.layout.activity_dm_mzine_fullscreen);
        init();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        sid = bundle.getInt("sid");
        handler.sendEmptyMessage(MSG_GET_PICS);

    }

    public void init() {

        v_Pager = (ViewPager) findViewById(R.id.v_Pager);
        adapter = new MyPagerAdapter();
        v_Pager.setAdapter(adapter);
        inflater = getLayoutInflater();
        views = new ArrayList<View>();
        progressDialog = new ProgressDialog(getParent());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        wh_dmApp = (WH_DMApp) getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();

    }

    @Override
    public void onResume() {

        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {

        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void addData(ArrayList<PictureMagzine> magazines) {

        for (PictureMagzine magazine : magazines) {
            View view = inflater.inflate(R.layout.magzine2_style, null);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            UrlImageViewHelper.setUrlDrawable(img, WH_DMHttpApiV1.URL_DOMAIN + magazine.getPic());
            views.add(view);
        }
        v_Pager.setCurrentItem(0);
        adapter.setList(views);
    }

    public class MyPagerAdapter extends PagerAdapter {

        private ArrayList<View> mListView;

        public MyPagerAdapter() {

        }

        private MyPagerAdapter(ArrayList<View> list) {

            // TODO Auto-generated method stub
            this.mListView = list;
        }

        public void setList(ArrayList<View> list) {

            this.mListView = list;
            notifyDataSetChanged();
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {

            // TODO Auto-generated method stub
            ((ViewGroup) arg0).removeView(mListView.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {

            // TODO Auto-generated method stub

        }

        @Override
        public int getCount() {

            if (mListView == null) {
                return 0;
            } else {
                return mListView.size();
            }
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {

            // TODO Auto-generated method stub
            if (mListView != null) {
                ((ViewGroup) arg0).addView(mListView.get(arg1), 0);
                return mListView.get(arg1);
            } else {
                return null;
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            // TODO Auto-generated method stub
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {

            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

            // TODO Auto-generated method stub

        }

    }

    public class GetPicsTask extends AsyncTask<Integer, Void, ArrayList<PictureMagzine>> {
        Exception reason = null;

        @Override
        protected void onPreExecute() {

            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<PictureMagzine> doInBackground(Integer... params) {

            try {
                ArrayList<PictureMagzine> magazines = wh_dmApi.getPictureMagzine(params[0]);
                return magazines;
            } catch (Exception e) {
                reason = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<PictureMagzine> result) {

            if (result != null) {
                addData(result);
            } else {
                NotificationUtil.showShortToast("Ã»ÓÐÐÂ¿¯", DM_MZinePicsActivity.this);
            }
            progressDialog.dismiss();
            super.onPostExecute(result);
        }
    }
}
