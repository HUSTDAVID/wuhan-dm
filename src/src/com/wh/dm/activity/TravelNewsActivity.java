
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.PicWithTxtNews;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.widget.HeadlineAdapter;
import com.wh.dm.widget.PullToRefreshListView;
import com.wh.dm.widget.PullToRefreshListView.OnRefreshListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import java.util.ArrayList;

public class TravelNewsActivity extends Activity {

    private PullToRefreshListView lv;
    ArrayList<PicWithTxtNews> savedNews = null;
    private HeadlineAdapter adapter;
    private View footer;
    private Button btnFoolter;
    private LayoutInflater mInfalater;
    private static int MSG_GET_TRAVELNEWS = 0;
    private GetTravelNewsTask getTravelNewsTask = null;
    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private DatabaseImpl databaseImpl;
    private int curPage = 1;
    private String id;
    private boolean FLAG_PAGE_UP = false;
    private boolean isFirstLauncher = true;
    private boolean isAdapter = true;
    private boolean isFirstLoad = true;
    private final Handler handler = new Handler() {
        @Override
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

    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.activity_news_house);
        MobclickAgent.onError(this);
        SharedPreferences preference = PreferenceManager
                .getDefaultSharedPreferences(TravelNewsActivity.this);
        id = getIntent().getStringExtra("id");
        lv = (PullToRefreshListView) findViewById(R.id.news_list_house);
        mInfalater = getLayoutInflater();
        adapter = new HeadlineAdapter(this);
        footer = mInfalater.inflate(R.layout.news_list_footer, null);
        btnFoolter = (Button) footer.findViewById(R.id.btn_news_footer);
        btnFoolter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FLAG_PAGE_UP = true;
                curPage++;
                handler.sendEmptyMessage(MSG_GET_TRAVELNEWS);
            }
        });
        lv.addFooterView(footer);
        lv.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {

                lv.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        curPage = 1;
                        handler.sendEmptyMessage(MSG_GET_TRAVELNEWS);
                        lv.onRefreshComplete();
                    }
                }, 1000);
            }
        });
        wh_dmApp = (WH_DMApp) this.getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        databaseImpl = wh_dmApp.getDatabase();
        handler.sendEmptyMessage(MSG_GET_TRAVELNEWS);

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

    private class GetTravelNewsTask extends AsyncTask<Void, Void, ArrayList<PicWithTxtNews>> {

        Exception reason = null;

        @Override
        protected void onPreExecute() {

            if (isFirstLauncher) {
                savedNews = databaseImpl.getTravelNews();
                if (savedNews != null && savedNews.size() > 0) {
                    if (isAdapter) {
                        lv.setAdapter(adapter);
                        adapter.setList(savedNews);
                        isAdapter = false;
                    }
                    lv.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long arg3) {

                            Intent intent = new Intent(TravelNewsActivity.this,
                                    NewsDetailsActivity.class);
                            intent.putExtra("id", savedNews.get(position).getId());
                            startActivity(intent);
                        }

                    });
                }
                isFirstLauncher = false;
            }
            super.onPreExecute();
        }

        @Override
        protected ArrayList<PicWithTxtNews> doInBackground(Void... params) {

            ArrayList<PicWithTxtNews> houseNews = null;
            try {
                houseNews = wh_dmApi.getTravelNews(curPage, id);
                return houseNews;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(final ArrayList<PicWithTxtNews> result) {

            if (result != null && result.size() > 0) {
                if (isFirstLoad) {
                    databaseImpl.deleteTravelNews();
                    isFirstLoad = false;
                }
                if (FLAG_PAGE_UP) {
                    adapter.addList(result);
                    FLAG_PAGE_UP = false;

                } else {
                    if (isAdapter) {
                        lv.setAdapter(adapter);
                        isAdapter = false;
                    }
                    adapter.setList(result);
                }

                databaseImpl.addTravelNews(result);
                lv.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long arg3) {

                        Intent intent = new Intent(TravelNewsActivity.this,
                                NewsDetailsActivity.class);
                        intent.putExtra("id", adapter.getList().get(position).getId());
                        startActivity(intent);

                    }

                });

            } else {
                if (!FLAG_PAGE_UP) {
                    if (wh_dmApp.isConnected()) {

                    } else {
                        NotificationUtil.showShortToast(getString(R.string.check_network),
                                TravelNewsActivity.this);
                    }
                } else {
                    NotificationUtil.showShortToast(getString(R.string.no_more_news),
                            TravelNewsActivity.this);
                }
            }
            super.onPostExecute(result);
        }

    }
}
