
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.Magazine;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.widget.SubManagerAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SubManagerActivity extends Activity {

    private static final int MSG_GET_MAGAZINE = 0;
    private static final int MSG_UNSUBCRIBE = 1;
    private static final int MSG_REFRESH = 2;
    private GetSubcribedMagazine getMgzsTask = null;
    private UnSubcribeTask unSubcribeTask = null;

    private ListView lvSubManager;
    private SubManagerAdapter adapter;
    private ImageButton btnBack;
    private ProgressDialog progressDialog;
    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private DatabaseImpl databaseImpl;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == MSG_GET_MAGAZINE) {
                if (getMgzsTask != null) {
                    getMgzsTask.cancel(true);
                    getMgzsTask = null;
                }
                getMgzsTask = new GetSubcribedMagazine();
                getMgzsTask.execute();
            } else if (msg.what == MSG_UNSUBCRIBE) {
                if (unSubcribeTask != null) {
                    unSubcribeTask.cancel(true);
                    unSubcribeTask = null;
                }
                if (WH_DMApp.isLogin) {
                    unSubcribeTask = new UnSubcribeTask();
                    Bundle bundle = msg.getData();
                    unSubcribeTask.execute(bundle.getInt("id"));
                } else {
                    // TODO
                    NotificationUtil.showShortToast(getString(R.string.please_login),
                            SubManagerActivity.this);
                    Intent intent = new Intent(SubManagerActivity.this, LoginActivity.class);
                    intent.putExtra("activity", "submanager");
                    startActivityForResult(intent, 0);
                }
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dm_sub_manager);

        initViews();
        wh_dmApp = (WH_DMApp) getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        databaseImpl = wh_dmApp.getDatabase();
        handler.sendEmptyMessage(MSG_GET_MAGAZINE);
    }

    @Override
    public void onResume() {

        super.onResume();
        MobclickAgent.onResume(this);
        handler.sendEmptyMessage(MSG_GET_MAGAZINE);
    }

    @Override
    public void onPause() {

        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initViews() {

        // init header
        TextView txtTitle = (TextView) findViewById(R.id.txt_header3_title);
        TextView txtQRCode = (TextView) findViewById(R.id.txt_total_reply);
        btnBack = (ImageButton) findViewById(R.id.img_header3_back);
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });
        txtTitle.setText(getResources().getString(R.string.subscribe));
        txtQRCode.setText("");
        txtQRCode.setBackgroundResource(R.drawable.temp_2d);

        // init listview
        lvSubManager = (ListView) findViewById(R.id.lv_sub_manager);

        adapter = new SubManagerAdapter(this);
        adapter.setHandler(handler);
        lvSubManager.setAdapter(adapter);
        progressDialog = new ProgressDialog(SubManagerActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

    private class GetSubcribedMagazine extends AsyncTask<Void, Void, ArrayList<Magazine>> {
        Exception reason = null;

        @Override
        protected void onPreExecute() {

            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Magazine> doInBackground(Void... params) {

            ArrayList<Magazine> magazines = null;
            try {
                magazines = wh_dmApi.getSubcribedMagazines();
                return magazines;
            } catch (Exception e) {
                reason = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Magazine> result) {

            if (result != null) {
                sendBroadcast(new Intent(WH_DMApp.INTENT_ACTION_SUBCRIBE_CHANGE));
                adapter.setList(result);
                databaseImpl.addMagazines(result);

            } else {
                adapter.clear();
                if (WH_DMApp.isLogin) {
                    NotificationUtil.showShortToast(getString(R.string.no_subcribe),
                            SubManagerActivity.this);
                } else {
                    // TODO
                    NotificationUtil.showShortToast(getString(R.string.please_login),
                            SubManagerActivity.this);
                    Intent intent = new Intent(SubManagerActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
            progressDialog.dismiss();
            super.onPostExecute(result);
        }

    }

    private class UnSubcribeTask extends AsyncTask<Integer, Void, Integer> {
        Exception reason = null;

        @Override
        protected Integer doInBackground(Integer... params) {

            boolean result = false;
            try {
                result = wh_dmApi.unsubcribe(params[0]);
                return params[0];
            } catch (Exception e) {
                reason = e;
                return 0;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {

            if (result != 0) {
                sendBroadcast(new Intent(WH_DMApp.INTENT_ACTION_SUBCRIBE_CHANGE));
                databaseImpl.delMagazine(result);
            } else {
                NotificationUtil.showShortToast(getString(R.string.unsub_fail),
                        SubManagerActivity.this);
            }
            super.onPostExecute(result);
        }

    }

}
