
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.TwoPhotos;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.PhotoUtil;
import com.wh.dm.widget.PhotoAdapter;
import com.wh.dm.widget.PullToRefreshListView;
import com.wh.dm.widget.PullToRefreshListView.OnRefreshListener;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class HotPhotosActivity extends Activity {

    RelativeLayout relPhotosHeader;
    TextView txtPhotosHeader;

    PullToRefreshListView lvPhotos;
    View footer;
    Button btnFooter;
    LayoutInflater mInflater;

    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private DatabaseImpl databaseImp1;
    private int curPage = 1;
    private boolean FLAG_PAGE_UP = false;
    private boolean isFirstLanucher = true;
    private boolean isAdapter = false;
    private boolean isFirstLoad = true;
    private static int MSG_GET_PHOTOS = 0;
    private GetPhotosTask getPhotosTask = null;
    private PhotoAdapter adapter;
    ArrayList<TwoPhotos> savePhotos = null;

    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == MSG_GET_PHOTOS) {
                if (getPhotosTask != null) {
                    getPhotosTask.cancel(true);
                    getPhotosTask = null;
                }
                getPhotosTask = new GetPhotosTask();
                getPhotosTask.execute();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dm_photos_all);
        mInflater = getLayoutInflater();
        initViews();
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

    private void initViews() {

        // init header
        // relPhotosHeader = (RelativeLayout) findViewById(R.id.rel_header3);
        // txtPhotosHeader = (TextView) findViewById(R.id.txt_header3_title);
        // relPhotosHeader.setBackgroundResource(R.drawable.topbar_black_bg);
        // txtPhotosHeader.setText(getResources().getString(R.string.photo));

        lvPhotos = (PullToRefreshListView) findViewById(R.id.lv_photos_all);
        lvPhotos.setDivider(null);
        wh_dmApp = (WH_DMApp) this.getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        databaseImp1 = wh_dmApp.getDatabase();
        adapter = new PhotoAdapter(this);

        lvPhotos.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {

                // Your code to refresh the list contents goes here

                // Make sure you call listView.onRefreshComplete()
                // when the loading is done. This can be done from here or any
                // other place, like on a broadcast receive from your loading
                // service or the onPostExecute of your AsyncTask.

                // For the sake of this sample, the code will pause here to
                // force a delay when invoking the refresh
                lvPhotos.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        curPage = 1;
                        FLAG_PAGE_UP = false;
                        isAdapter = false;
                        handler.sendEmptyMessage(MSG_GET_PHOTOS);
                        lvPhotos.onRefreshComplete();
                    }
                }, 1000);
            }
        });

        footer = mInflater.inflate(R.layout.news_list_footer, null);
        footer.setBackgroundResource(R.drawable.photos_bg);
        btnFooter = (Button) footer.findViewById(R.id.btn_news_footer);
        lvPhotos.addFooterView(footer);
        btnFooter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                FLAG_PAGE_UP = true;
                curPage++;
                handler.sendEmptyMessage(MSG_GET_PHOTOS);

            }
        });

        handler.sendEmptyMessage(MSG_GET_PHOTOS);
    }

    private class GetPhotosTask extends AsyncTask<Void, Void, ArrayList<TwoPhotos>> {

        Exception reason = null;

        @Override
        protected void onPreExecute() {

            if (isFirstLanucher) {
                savePhotos = PhotoUtil.chagePhoto(databaseImp1.getHotPhoto());
                if (savePhotos != null && savePhotos.size() > 0) {
                    lvPhotos.setAdapter(adapter);
                    adapter.setList(savePhotos);
                } else {
                    isAdapter = true;
                }
                isFirstLanucher = false;
            }

            super.onPreExecute();
        }

        @Override
        protected ArrayList<TwoPhotos> doInBackground(Void... params) {

            ArrayList<TwoPhotos> photos = null;
            try {
                photos = wh_dmApi.getHotPhotos(curPage);
                return photos;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<TwoPhotos> result) {

            if (result != null && result.size() > 0) {

                if (isFirstLoad) {
                    databaseImp1.deleteHotPhoto();
                    isFirstLoad = false;
                }
                if (FLAG_PAGE_UP) {
                    adapter.addList(result);
                    FLAG_PAGE_UP = false;
                } else {
                    if (isAdapter) {
                        lvPhotos.setAdapter(adapter);
                        isAdapter = false;
                    }
                    adapter.setList(result);
                }

                databaseImp1.addHotPhoto(PhotoUtil.chageOnePhoto(result));

            } else {
                if (!FLAG_PAGE_UP) {
                    savePhotos = PhotoUtil.chagePhoto(databaseImp1.getHotPhoto());
                    if (savePhotos != null && savePhotos.size() > 0) {
                        if (isFirstLanucher) {
                            lvPhotos.setAdapter(adapter);
                        }
                        adapter.setList(savePhotos);
                    }

                    if (wh_dmApp.isConnected()) {
                        NotificationUtil.showShortToast(
                                getResources().getString(R.string.badconnect),
                                HotPhotosActivity.this);
                    } else {
                        NotificationUtil.showShortToast(getString(R.string.check_network),
                                HotPhotosActivity.this);
                    }
                } else {
                    NotificationUtil.showLongToast(getString(R.string.no_more_message),
                            HotPhotosActivity.this);
                }
            }

            super.onPostExecute(result);
        }

    }

}
