
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.LoadInfo;
import com.wh.dm.type.Magazine;
import com.wh.dm.type.MagazineBody;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.UrlImageViewHelper;
import com.wh.dm.widget.DownloadAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    public static final int MSG_NO_MAGAZINE = 0;
    public static final int MSG_LOADING = 1;
    public static final int MSG_FINISH_LOAD = 2;
    public static final int MSG_START_LOAD = 3;
    public static final int MSG_PAUSE_LOAD = 4;
    public static final int MSG_OPEN_MAGAZINE = 5;

    private int flag;

    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private DatabaseImpl databaseImpl;
    DownloadAdapter adapter;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            int sid = msg.arg1;
            int pro = msg.arg2;
            int size = loadList.size();
            for (int i = 0; i < size; i++) {
                if (sid == loadList.get(i).getSid()) {
                    flag = i;
                    break;
                }
            }
            switch (msg.what) {
                case MSG_NO_MAGAZINE:
                    NotificationUtil.showShortToast("ÔÝÎÞÐÂ¿¯", DownloadActivity.this);
                    loadList.get(flag).setStart(false);
                    loadList.get(flag).setFinish(false);
                    loadList.get(flag).setPause(false);
                    adapter.setList(loadList);
                    break;
                case MSG_LOADING:
                    loadList.get(flag).setPro(pro);
                    adapter.setList(loadList);
                    databaseImpl.deleteLoadInfo();
                    databaseImpl.addLoadInfo(loadList);
                    break;
                case MSG_FINISH_LOAD:
                    loadList.get(flag).setPro(100);
                    loadList.get(flag).setFinish(true);
                    loadList.get(flag).setStart(true);
                    loadList.get(flag).setPause(false);
                    adapter.setList(loadList);
                    databaseImpl.deleteLoadInfo();
                    databaseImpl.addLoadInfo(loadList);
                    break;
                case MSG_START_LOAD:
                    new LoadMagazineThread(DownloadActivity.this, this, sid).start();
                    // thread.start();
                    loadList.get(flag).setStart(true);
                    loadList.get(flag).setPause(false);
                    loadList.get(flag).setFinish(false);
                    adapter.setList(loadList);
                    break;
                case MSG_PAUSE_LOAD:
                    // i do not know how to pause.may be need to delete it. or
                    // save all thread and stop it.
                    loadList.get(flag).setPause(true);
                    loadList.get(flag).setStart(true);
                    loadList.get(flag).setFinish(false);
                    adapter.setList(loadList);
                    break;
                case MSG_OPEN_MAGAZINE:
                    Intent intent = new Intent(DownloadActivity.this, DM_MZineArticleActivity.class);
                    intent.putExtra("sid", sid);
                    startActivity(intent);
                    break;
            }

            super.handleMessage(msg);
        }

    };

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

        adapter = new DownloadAdapter(this, handler);
        adapter.setList(loadList);
        lvDownload.setAdapter(adapter);

        // this task is use for test down load interface
        // loadTask = new LoadMagazineTask();
        // loadTask.execute();
    }

    private ArrayList<LoadInfo> initLoadInfo(ArrayList<Magazine> magazines) {

        ArrayList<LoadInfo> loadInfoList = new ArrayList<LoadInfo>();

        loadInfoList = databaseImpl.getLoadInfo();
        if (loadInfoList == null || loadInfoList.size() == 0) {
            for (int i = 0; i < magazines.size(); i++) {
                LoadInfo loadInfo = new LoadInfo();
                Magazine magazine = magazines.get(i);
                loadInfo.setPicPath(magazine.getSpic());
                loadInfo.setTitle(magazine.getSname());
                loadInfo.setSid(magazine.getSid());
                loadInfo.setPro(0);
                loadInfo.setFinish(false);
                loadInfo.setPause(false);
                loadInfo.setStart(false);

                loadInfoList.add(loadInfo);
            }
            databaseImpl.addLoadInfo(loadInfoList);
        }
        return loadInfoList;
    }

    // load thread
    public class LoadMagazineThread extends Thread {

        private Context context;
        private Handler handler;
        private int sid;
        private ArrayList<MagazineBody> messageBodys = new ArrayList<MagazineBody>();

        public LoadMagazineThread(Context _context, Handler _handler, int _sid) {

            this.context = _context;
            this.handler = _handler;
            this.sid = _sid;
        }

        @Override
        public void run() {

            // use a message send infomation to ui thread

            try {

                // this is test
                //
                messageBodys = wh_dmApi.getMagazineBody(sid);
                if (messageBodys != null && messageBodys.size() > 0) {
                    databaseImpl.addMagazineBody(messageBodys);
                    int size = messageBodys.size();
                    int step = 70 / size;
                    int pro = 30;
                    MagazineBody body = new MagazineBody();
                    Message msg = new Message();
                    msg.what = MSG_LOADING;
                    msg.arg1 = sid;
                    msg.arg2 = pro;
                    handler.sendMessage(msg);
                    for (int i = 0; i < size; i++) {
                        body = messageBodys.get(i);
                        if (body.getLitpic() != null && body.getLitpic().length() > 0) {
                            UrlImageViewHelper.setUrlDrawable(new ImageView(context),
                                    WH_DMHttpApiV1.URL_DOMAIN + body.getLitpic(),
                                    R.drawable.item_default, null);
                            pro += step;
                            Message msgPro = new Message();
                            msgPro.what = MSG_LOADING;
                            msgPro.arg1 = sid;
                            msgPro.arg2 = pro;
                            handler.sendMessage(msgPro);
                        }
                    }
                    Message msgFinish = new Message();
                    msgFinish.what = MSG_FINISH_LOAD;
                    msgFinish.arg1 = sid;
                    msgFinish.arg2 = 100;
                    handler.sendMessage(msgFinish);

                } else {
                    Message msgError = new Message();
                    msgError.what = MSG_NO_MAGAZINE;
                    msgError.arg1 = sid;
                    handler.sendMessage(msgError);
                }

            } catch (Exception e) {
                Message msgError = new Message();
                msgError.what = MSG_NO_MAGAZINE;
                msgError.arg1 = sid;
                e.printStackTrace();
                handler.sendMessage(msgError);
            }
            super.run();
        }

    }

}
