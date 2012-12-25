
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.type.PicWithTxtNews;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.widget.HeadlineAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class NewsOtherSortActivity extends Activity {
    private ListView lv;
    ArrayList<PicWithTxtNews> savedNews = null;
    private HeadlineAdapter adapter;
    private View footer;
    private Button btnFoolter;
    private LayoutInflater mInfalater;
    private static int MSG_GET_TRAVELNEWS = 0;
    private GetTravelNewsTask getTravelNewsTask = null;
    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private boolean FLAG_PAGE_UP = false;
    private int curPage = 1;
    private int id;
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

        id = getIntent().getIntExtra("id", 268);
        lv = (ListView) findViewById(R.id.news_list_house);
        mInfalater = getLayoutInflater();
        adapter = new HeadlineAdapter(this);
        footer = mInfalater.inflate(R.layout.news_list_footer, null);
        btnFoolter = (Button) footer.findViewById(R.id.btn_news_footer);
        btnFoolter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                curPage++;
                FLAG_PAGE_UP = true;
                handler.sendEmptyMessage(MSG_GET_TRAVELNEWS);
            }
        });
        lv.addFooterView(footer);
        wh_dmApp = (WH_DMApp) this.getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
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

            if (result != null) {
                if (curPage == 1) {
                    adapter.setList(result);
                } else {
                    adapter.addList(result);
                    FLAG_PAGE_UP = false;
                }
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long arg3) {

                        Intent intent = new Intent(NewsOtherSortActivity.this,
                                NewsDetailsActivity.class);
                        intent.putExtra("id", adapter.getList().get(position).getId());
                        startActivity(intent);

                    }

                });
            } else {
                if (!FLAG_PAGE_UP) {
                    if (wh_dmApp.isConnected()) {
                        NotificationUtil.showShortToast(
                                getResources().getString(R.string.badconnect),
                                NewsOtherSortActivity.this);
                    } else {
                        NotificationUtil.showShortToast(getString(R.string.check_network),
                                NewsOtherSortActivity.this);
                    }
                } else {
                    NotificationUtil.showLongToast(getString(R.string.no_more_message),
                            NewsOtherSortActivity.this);
                }
            }
            super.onPostExecute(result);
        }

    }
}
