
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.preference.Preferences;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import java.util.ArrayList;

public class LifeNewsActivity extends Activity {

    private PullToRefreshListView lv;
    ArrayList<PicWithTxtNews> savedNews = null;
    private HeadlineAdapter adapter;
    private static int MSG_GET_LIFENEWS = 0;
    private GetLifeNewsTask getLifeNewsTask = null;
    private View footer;
    private Button btnFoolter;
    private LayoutInflater mInfalater;
    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private DatabaseImpl databaseImpl;
    private int curPage = 1;
    private int id;
    private boolean FLAG_PAGE_UP = false;
    private boolean isFirstLauncher = true;
    private boolean isAdapter = true;
    private boolean isFirstLoad = true;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == MSG_GET_LIFENEWS) {
                if (getLifeNewsTask != null) {
                    getLifeNewsTask.cancel(true);
                    getLifeNewsTask = null;
                }
                getLifeNewsTask = new GetLifeNewsTask();
                getLifeNewsTask.execute();
            }
        };
    };

    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_house);
        SharedPreferences preference = PreferenceManager
                .getDefaultSharedPreferences(LifeNewsActivity.this);
        id = preference.getInt(Preferences.NEWS_FOUR_ID, 214);
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
                handler.sendEmptyMessage(MSG_GET_LIFENEWS);
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
                        handler.sendEmptyMessage(MSG_GET_LIFENEWS);
                        lv.onRefreshComplete();
                    }
                }, 1000);
            }
        });
        wh_dmApp = (WH_DMApp) this.getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        databaseImpl = wh_dmApp.getDatabase();
        handler.sendEmptyMessage(MSG_GET_LIFENEWS);

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

    private class GetLifeNewsTask extends AsyncTask<Void, Void, ArrayList<PicWithTxtNews>> {

        Exception reason = null;

        @Override
        protected void onPreExecute() {

            if (isFirstLauncher) {
                savedNews = databaseImpl.getLifeNews();
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

                            Intent intent = new Intent(LifeNewsActivity.this,
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
                houseNews = wh_dmApi.getLifeNews(curPage, id);
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
                    databaseImpl.deleteLifeNews();
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
                databaseImpl.addLifeNews(result);
                lv.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long arg3) {

                        Intent intent = new Intent(LifeNewsActivity.this, NewsDetailsActivity.class);
                        intent.putExtra("id", adapter.getList().get(position).getId());
                        startActivity(intent);

                    }

                });

            } else {
                if (!FLAG_PAGE_UP) {
                    if (wh_dmApp.isConnected()) {
                        NotificationUtil.showShortToast(
                                getResources().getString(R.string.badconnect),
                                LifeNewsActivity.this);
                    } else {
                        NotificationUtil.showShortToast(getString(R.string.check_network),
                                LifeNewsActivity.this);
                    }
                } else {
                    NotificationUtil.showLongToast(getString(R.string.no_more_message),
                            LifeNewsActivity.this);
                }
            }
            super.onPostExecute(result);
        }

    }
}
