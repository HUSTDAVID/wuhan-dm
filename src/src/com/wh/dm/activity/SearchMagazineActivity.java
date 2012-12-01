
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.type.Magazine;
import com.wh.dm.type.TwoMagazine;
import com.wh.dm.util.MagazineUtil;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.widget.PullToRefreshListView;
import com.wh.dm.widget.PullToRefreshListView.OnRefreshListener;
import com.wh.dm.widget.SubscribeAdapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.ArrayList;

public class SearchMagazineActivity extends Activity {
    PullToRefreshListView lvSub;
    View footer;
    Button btnFooter;
    LayoutInflater mInflater;

    private String key;
    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private final int MSG_GET_MAGAZINE = 0;
    private GetMagazine getMagazine = null;
    private SubscribeAdapter adapter;
    private ArrayList<TwoMagazine> magazineList = null;
    private final int curPage = 1;

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

        key = getIntent().getStringExtra("key");
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
        adapter = new SubscribeAdapter(this);
        handler.sendEmptyMessage(MSG_GET_MAGAZINE);
    }

    class GetMagazine extends AsyncTask<Void, Void, ArrayList<TwoMagazine>> {

        Exception reason = null;

        @Override
        protected ArrayList<TwoMagazine> doInBackground(Void... params) {

            ArrayList<Magazine> one = null;
            try {
                one = wh_dmApi.getSearchMagazine(key);
                magazineList = MagazineUtil.toTwoMagazine(one);
                return magazineList;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<TwoMagazine> result) {

            if (magazineList != null && magazineList.size() > 0) {
                adapter.setList(result);
                lvSub.setAdapter(adapter);
            } else {
                NotificationUtil.showShortToast("没有搜索到相关杂志", SearchMagazineActivity.this);
            }

        }
    }

}
