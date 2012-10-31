
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.PicWithTxtNews;
import com.wh.dm.widget.HeadlineAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import java.util.ArrayList;

public class CarNewsActivity extends Activity {

    private ListView lv;
    ArrayList<PicWithTxtNews> savedNews = null;
    private HeadlineAdapter adapter;
    private static int MSG_GET_CARNEWS = 0;
    private GetCarNewsTask getCarNewsTask = null;
    private ProgressDialog progressDialog = null;
    private View footer;
    private Button btnFoolter;
    private LayoutInflater mInfalater;
    private WH_DMApi wh_dmApi;
    private DatabaseImpl databaseImpl;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == MSG_GET_CARNEWS) {
                if (getCarNewsTask != null) {
                    getCarNewsTask.cancel(true);
                    getCarNewsTask = null;
                }
                getCarNewsTask = new GetCarNewsTask();
                getCarNewsTask.execute();
            }
        };
    };

    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.activity_news_house);
        lv = (ListView) findViewById(R.id.news_list_house);
        adapter = new HeadlineAdapter(this);
        mInfalater = getLayoutInflater();
        footer = mInfalater.inflate(R.layout.news_list_footer, null);
        btnFoolter = (Button) footer.findViewById(R.id.btn_news_footer);
        progressDialog = new ProgressDialog(getParent());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        wh_dmApi = ((WH_DMApp) this.getApplication()).getWH_DMApi();
        databaseImpl = ((WH_DMApp) this.getApplication()).getDatabase();
        handler.sendEmptyMessage(MSG_GET_CARNEWS);

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

    private class GetCarNewsTask extends AsyncTask<Void, Void, ArrayList<PicWithTxtNews>> {

        Exception reason = null;

        @Override
        protected void onPreExecute() {

            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<PicWithTxtNews> doInBackground(Void... params) {

            ArrayList<PicWithTxtNews> houseNews = null;
            try {
                // houseNews = (new WH_DMApi()).getCarNews();
                houseNews = wh_dmApi.getCarNews();
                return houseNews;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(final ArrayList<PicWithTxtNews> result) {

            lv.addFooterView(footer);
            lv.setAdapter(adapter);
            if (result != null) {
                adapter.setList(result);
                databaseImpl.deleteCarNews();
                databaseImpl.addCarNews(result);
                // (new DatabaseImpl(CarNewsActivity.this)).addCarNews(result);
                lv.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long arg3) {

                        Intent intent = new Intent(CarNewsActivity.this, NewsDetailsActivity.class);
                        intent.putExtra("id", result.get(position).getId());
                        startActivity(intent);

                    }

                });
                if (result.size() < 20) {
                    lv.removeFooterView(footer);
                } else {
                    footer.setVisibility(View.VISIBLE);
                }

            } else {
                savedNews = databaseImpl.getCarNews();
                // savedNews = (new
                // DatabaseImpl(CarNewsActivity.this)).getHouseNews();
                if (savedNews != null && savedNews.size() > 0) {
                    adapter.setList(savedNews);
                    lv.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long arg3) {

                            Intent intent = new Intent(CarNewsActivity.this,
                                    NewsDetailsActivity.class);
                            intent.putExtra("id", savedNews.get(position).getId());
                            startActivity(intent);
                        }

                    });
                }
                if (savedNews.size() < 20) {
                    lv.removeFooterView(footer);
                } else {
                    footer.setVisibility(View.VISIBLE);
                }
                Toast.makeText(CarNewsActivity.this, reason.toString(), Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
            super.onPostExecute(result);
        }

    }

}
