
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DM;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.PicWithTxtNews;
import com.wh.dm.widget.HeadlineAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HouseNewsActivity extends Activity {

    private ListView lv;
    ArrayList<PicWithTxtNews> savedNews = null;
    private HeadlineAdapter adapter;

    private ProgressDialog progressDialog;
    private GetHouseNewsTask getHouseNewsTask = null;
    private static int MSG_GET_HOUSENEWS = 0;
    private View footer;
    private Button btnFoolter;
    private LayoutInflater mInfalater;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == MSG_GET_HOUSENEWS) {
                if (getHouseNewsTask != null) {
                    getHouseNewsTask.cancel(true);
                    getHouseNewsTask = null;
                }
                getHouseNewsTask = new GetHouseNewsTask();
                getHouseNewsTask.execute();
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_house);
        lv = (ListView) findViewById(R.id.news_list_house);
        mInfalater = getLayoutInflater();
        adapter = new HeadlineAdapter(this);
        footer = mInfalater.inflate(R.layout.news_list_footer, null);
        btnFoolter = (Button) footer.findViewById(R.id.btn_news_footer);
        progressDialog = new ProgressDialog(getParent());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {

                if (getHouseNewsTask != null) {
                    getHouseNewsTask.cancel(true);
                    getHouseNewsTask = null;
                }
            }

        });
        handler.sendEmptyMessage(MSG_GET_HOUSENEWS);
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

    private class GetHouseNewsTask extends AsyncTask<Void, Void, ArrayList<PicWithTxtNews>> {

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
                houseNews = (new WH_DM()).getHouseNews();
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
                (new DatabaseImpl(HouseNewsActivity.this)).addHouseNews(result);
                lv.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long arg3) {

                        Intent intent = new Intent(HouseNewsActivity.this,
                                NewsDetailsActivity.class);
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
                savedNews = (new DatabaseImpl(HouseNewsActivity.this)).getHouseNews();
                if (savedNews != null && savedNews.size() > 0) {
                    lv.addFooterView(footer);
                    lv.setAdapter(adapter);
                    adapter.setList(savedNews);
                    lv.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long arg3) {

                            Intent intent = new Intent(HouseNewsActivity.this,
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
                Toast.makeText(HouseNewsActivity.this, reason.toString(), Toast.LENGTH_SHORT)
                        .show();
            }
            progressDialog.dismiss();
            super.onPostExecute(result);
        }

    }
}
