
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.Cover;
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
    private DatabaseImpl databaseImpl;
    private final int MSG_GET_MAGAZINE = 0;
    private final int MSG_SUBCRIBE = 1;
    private GetMagazine getMagazine = null;
    private SubcribeTask subMagazine = null;
    private SubscribeAdapter adapter;
    private ArrayList<TwoMagazine> magazineList = null;

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
            } else if (MSG_SUBCRIBE == msg.what) {
                if (subMagazine != null) {
                    subMagazine.cancel(true);
                    subMagazine = null;
                }

                subMagazine = new SubcribeTask();
                Bundle bundle = msg.getData();
                subMagazine.execute(bundle.getInt("cid"));
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

        wh_dmApp = (WH_DMApp) this.getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        databaseImpl = wh_dmApp.getDatabase();
        adapter = new SubscribeAdapter(this);
        adapter.setHandler(handler);
        handler.sendEmptyMessage(MSG_GET_MAGAZINE);
    }

    class GetMagazine extends AsyncTask<Void, Void, ArrayList<TwoMagazine>> {

        Exception reason = null;

        @Override
        protected void onPreExecute() {

            adapter.setDatabaseImpl(databaseImpl);
            super.onPreExecute();
        }

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
                addStatus(adapter);
            } else {
                if (wh_dmApp.isConnected()) {
                    NotificationUtil.showShortToast(
                            getResources().getString(R.string.no_ref_magazine),
                            SearchMagazineActivity.this);
                } else {
                    NotificationUtil.showShortToast(getResources()
                            .getString(R.string.check_network), SearchMagazineActivity.this);
                }
            }

        }
    }

    private class SubcribeTask extends AsyncTask<Integer, Void, Cover> {

        Exception reason = null;

        @Override
        protected Cover doInBackground(Integer... params) {

            Cover cover = null;
            try {
                cover = wh_dmApi.subcribe(params[0]);
                return cover;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Cover result) {

            if (result != null) {

            } else {
                if (wh_dmApp.isConnected()) {
                    NotificationUtil.showShortToast(getString(R.string.sub_fail),
                            SearchMagazineActivity.this);
                }
            }
            super.onPostExecute(result);
        }

    }

    public void addStatus(SubscribeAdapter adapter) {

        ArrayList<Magazine> magazines = databaseImpl.getSubcribedMagazine();
        for (int i = 0; i < adapter.getCount(); i++) {
            TwoMagazine mg = (TwoMagazine) adapter.getItem(i);
            Magazine left = mg.getLeftMagazine();
            Magazine right = mg.getRightMagazine();
            for (int j = 0; j < magazines.size(); j++) {
                String leftName = left.getSname();
                String rightName = right.getSname();
                if (leftName != null && leftName.equals(magazines.get(j).getSname())) {
                    left.setEditor("subcribed");
                }
                if (rightName != null && rightName.equals(magazines.get(j).getSname())) {
                    right.setEditor("subcribed");
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

}
