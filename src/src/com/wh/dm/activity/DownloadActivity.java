
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.LoadInfo;
import com.wh.dm.type.Magazine;
import com.wh.dm.type.MagazineBody;
import com.wh.dm.widget.DownloadAdapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DownloadActivity extends Activity {

    private ListView lvDownload;
    private TextView txtTitle;
    private ImageButton btnBack;
    private ArrayList<LoadInfo> loadList;
    private ArrayList<Magazine> subMagazines;
    private ArrayList<MagazineBody> magazineBodys;

    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private DatabaseImpl databaseImpl;
    private LoadMagazineTask loadTask;

    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        MobclickAgent.onError(this);
        setContentView(R.layout.activity_download);
        init();
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

    public void init() {

        wh_dmApp = (WH_DMApp) getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        databaseImpl = wh_dmApp.getDatabase();
        subMagazines = databaseImpl.getSubcribedMagazine();
        magazineBodys = new ArrayList<MagazineBody>();
        loadList = new ArrayList<LoadInfo>();
        loadList = initLoadInfo(subMagazines);

        txtTitle = (TextView) findViewById(R.id.txt_header_title2);
        txtTitle.setText(getString(R.string.download));
        btnBack = (ImageButton) findViewById(R.id.Btn_back_header2);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        lvDownload = (ListView) findViewById(R.id.lv_download);

        DownloadAdapter adapter = new DownloadAdapter(this);
        adapter.setList(loadList);
        lvDownload.setAdapter(adapter);

        loadTask = new LoadMagazineTask();
        loadTask.execute();
    }

    private ArrayList<LoadInfo> initLoadInfo(ArrayList<Magazine> magazines) {

        ArrayList<LoadInfo> loadInfoList = new ArrayList<LoadInfo>();
        for (int i = 0; i < magazines.size(); i++) {
            LoadInfo loadInfo = new LoadInfo();
            Magazine magazine = magazines.get(i);
            loadInfo.setPicPath(magazine.getSpic());
            loadInfo.setTitle(magazine.getSname());
            loadInfo.setPro(0);
            loadInfo.setFinish(false);
            loadInfo.setPause(false);
            loadInfo.setStart(false);
            loadInfoList.add(loadInfo);
        }
        return loadInfoList;
    }

    private class LoadMagazineTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                magazineBodys = wh_dmApi.getMagazineBody(6);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}
