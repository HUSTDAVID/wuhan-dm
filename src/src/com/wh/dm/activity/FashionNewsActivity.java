
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DM;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FashionNewsActivity extends Activity {
    private ListView lv;
    private View footer;
    private Button btnFoolter;
    private LayoutInflater mInfalater;
    private static int MSG_GET_FASHIONNEWS = 0;
    private GetFashionNewsTask getFashionNewsTask = null;
    private ProgressDialog progressDialog = null;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == MSG_GET_FASHIONNEWS) {
                if (getFashionNewsTask != null) {
                    getFashionNewsTask.cancel(true);
                    getFashionNewsTask = null;
                }
                getFashionNewsTask = new GetFashionNewsTask();
                getFashionNewsTask.execute();
            }
        };
    };

    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_house);
        lv = (ListView) findViewById(R.id.news_list_house);
        mInfalater = getLayoutInflater();
        footer = mInfalater.inflate(R.layout.news_list_footer, null);
        btnFoolter = (Button) footer.findViewById(R.id.btn_news_footer);
        lv.addFooterView(footer);
        progressDialog = new ProgressDialog(getParent());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        handler.sendEmptyMessage(MSG_GET_FASHIONNEWS);

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

    private class GetFashionNewsTask extends AsyncTask<Void, Void, ArrayList<PicWithTxtNews>> {

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
                houseNews = (new WH_DM()).getFashionNews();
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
                HeadlineAdapter adapter = new HeadlineAdapter(FashionNewsActivity.this, result);
                if (result.size() < 20) {
                    lv.removeFooterView(footer);
                } else {
                    lv.addFooterView(footer);
                }
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long arg3) {

                        Intent intent = new Intent(FashionNewsActivity.this,
                                NewsDetailsActivity.class);
                        intent.putExtra("id", result.get(position).getId());
                        startActivity(intent);
                    }

                });
            } else {
                Toast.makeText(FashionNewsActivity.this, reason.toString(), Toast.LENGTH_SHORT)
                        .show();
            }
            progressDialog.dismiss();
            super.onPostExecute(result);
        }

    }
}
