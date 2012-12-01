
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.preference.Preferences;
import com.wh.dm.type.Magazine;
import com.wh.dm.type.TwoMagazine;
import com.wh.dm.util.MagazineUtil;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.widget.PullToRefreshListView;
import com.wh.dm.widget.PullToRefreshListView.OnRefreshListener;
import com.wh.dm.widget.SubscribeAdapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.ArrayList;

public class Sub_GirlActivity extends Activity {
    PullToRefreshListView lvSub;
    View footer;
    Button btnFooter;
    LayoutInflater mInflater;

    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private final int MSG_GET_MAGAZINE = 0;
    private GetMagazine getMagazine = null;
    private ArrayList<TwoMagazine> savedMagazine = null;
    private SubscribeAdapter adapter;
    private final int curPage = 1;
    private DatabaseImpl databaseImpl;
    private boolean FLAG_PAGE_UP = false;
    private boolean isFirstLauncher = true;
    private boolean isAdapter = true;
    private boolean isFirstLoad = true;
    private int id;
    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (MSG_GET_MAGAZINE == msg.what) {
                if (getMagazine != null) {
                    getMagazine.cancel(true);
                    getMagazine = null;
                }
                getMagazine = new GetMagazine();
                getMagazine.execute();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sub_item);
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

        SharedPreferences preference = PreferenceManager
                .getDefaultSharedPreferences(Sub_GirlActivity.this);
        id = preference.getInt(Preferences.MAGAZINE_TWO_ID, 0);
        lvSub = (PullToRefreshListView) findViewById(R.id.lv_subscribe);
        lvSub.setDivider(null);
        lvSub.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {

                // Your code to refresh the list contents goes here

                // Make sure you call listView.onRefreshComplete()
                // when the loading is done. This can be done from here or any
                // other place, like on a broadcast receive from your loading
                // service or the onPostExecute of your AsyncTask.

                // For the sake of this sample, the code will pause here to
                // force a delay when invoking the refresh
                lvSub.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        handler.sendEmptyMessage(MSG_GET_MAGAZINE);
                        lvSub.onRefreshComplete();
                    }
                }, 2000);
            }
        });

        footer = mInflater.inflate(R.layout.news_list_footer, null);
        footer.setBackgroundColor(getResources().getColor(R.color.bg_normal));
        btnFooter = (Button) footer.findViewById(R.id.btn_news_footer);
        lvSub.addFooterView(footer);

        wh_dmApp = (WH_DMApp) this.getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        databaseImpl = wh_dmApp.getDatabase();
        adapter = new SubscribeAdapter(this);
        handler.sendEmptyMessage(MSG_GET_MAGAZINE);
    }

    class GetMagazine extends AsyncTask<Void, Void, ArrayList<TwoMagazine>> {

        Exception reason;

        @Override
        protected void onPreExecute() {

            if (isFirstLauncher) {
                savedMagazine = MagazineUtil.toTwoMagazine(databaseImpl.getGirlMagazine());
                if (savedMagazine != null && savedMagazine.size() > 0) {
                    if (isAdapter) {
                        lvSub.setAdapter(adapter);
                        adapter.setList(savedMagazine);
                        isAdapter = false;
                    }
                }
                isFirstLauncher = false;
            }
            super.onPreExecute();
        }

        @Override
        protected ArrayList<TwoMagazine> doInBackground(Void... params) {

            ArrayList<Magazine> one = null;
            try {
                one = wh_dmApi.getMagazine(id);
                savedMagazine = MagazineUtil.toTwoMagazine(one);
                return savedMagazine;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<TwoMagazine> result) {

            if (result != null && result.size() > 0) {
                if (isFirstLoad) {
                    databaseImpl.deleteGirlMagazine();
                    isFirstLoad = false;
                }
                if (FLAG_PAGE_UP) {
                    adapter.addList(result);
                    FLAG_PAGE_UP = false;
                } else {
                    if (isAdapter) {
                        lvSub.setAdapter(adapter);
                        isAdapter = false;
                    }
                    adapter.setList(result);
                }
                databaseImpl.addGirlMagazine(MagazineUtil.toOneMagazine(result));
            } else {
                if (!FLAG_PAGE_UP) {
                    if (!FLAG_PAGE_UP) {
                        if (wh_dmApp.isConnected()) {
                            NotificationUtil.showShortToast(reason.toString(),
                                    Sub_GirlActivity.this);
                        }
                    } else {
                        NotificationUtil.showLongToast(getString(R.string.no_more_message),
                                Sub_GirlActivity.this);
                    }
                }
            }
        }

    }

}
