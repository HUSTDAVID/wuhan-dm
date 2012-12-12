
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.ArticleMagzine;
import com.wh.dm.type.LoadInfo;
import com.wh.dm.type.Magazine;
import com.wh.dm.type.PictureMagzine;
import com.wh.dm.util.LoadUtil;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.UrlImageViewHelper;
import com.wh.dm.widget.DownloadAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
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
    private ArrayList<ArticleMagzine> allArticleMagzines;
    private ArrayList<PictureMagzine> allPictureMagazines;
    public static final int MSG_NO_MAGAZINE = 0;
    public static final int MSG_LOADING = 1;
    public static final int MSG_FINISH_LOAD = 2;
    public static final int MSG_START_LOAD = 3;
    public static final int MSG_PAUSE_LOAD = 4;
    public static final int MSG_OPEN_MAGAZINE = 5;
    private boolean insearting = false;
    private ArrayList<Integer> loadFlag;
    private ArrayList<Integer> pauseFlag;
    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private DatabaseImpl databaseImpl;
    DownloadAdapter adapter;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            int sid = msg.arg1;
            int position = msg.arg2;

            switch (msg.what) {
                case MSG_NO_MAGAZINE:
                    NotificationUtil.showShortToast(getResources().getString(R.string.no_magazine),
                            DownloadActivity.this);
                    loadList.get(position).setFinish(false);
                    loadList.get(position).setStart(false);
                    loadList.get(position).setOk(false);
                    loadList.get(position).setPause(false);
                    adapter.setList(loadList);
                    break;
                case MSG_LOADING:
                    adapter.setList(loadList);
                    break;
                case MSG_FINISH_LOAD:
                    loadList.get(position).setOk(true);
                    adapter.setList(loadList);
                    loadFlag.add(position);
                    if (checkAllLoad()) {
                        databaseImpl.addAllLoad(allPictureMagazines, allArticleMagzines);
                        allPictureMagazines.clear();
                        allArticleMagzines.clear();
                        int loadsize = loadFlag.size();
                        for (int i = 0; i < loadsize; i++) {
                            loadList.get(loadFlag.get(i)).setFinish(true);
                            loadList.get(loadFlag.get(i)).setStart(true);
                            loadList.get(loadFlag.get(i)).setPause(false);
                            loadList.get(loadFlag.get(i)).setPro(100);
                        }
                        int pauseSize = pauseFlag.size();
                        for (int i = 0; i < pauseSize; i++) {
                            loadList.get(pauseFlag.get(i)).setFinish(false);
                            loadList.get(pauseFlag.get(i)).setStart(true);
                            loadList.get(pauseFlag.get(i)).setPause(true);
                            loadList.get(pauseFlag.get(i)).setPro(20);
                        }
                        databaseImpl.deleteLoadInfo();
                        databaseImpl.addLoadInfo(loadList);
                        loadFlag.clear();
                        pauseFlag.clear();
                    }
                    break;
                case MSG_START_LOAD:
                    new LoadMagazineThread(DownloadActivity.this, this, sid, position).start();
                    int psize = pauseFlag.size();
                    for (int i = 0; i < psize; i++) {
                        if (position == pauseFlag.get(i)) {
                            pauseFlag.remove(i);
                        }
                    }
                    loadList.get(position).setStart(true);
                    loadList.get(position).setPause(false);
                    loadList.get(position).setFinish(false);
                    loadList.get(position).setOk(false);
                    loadList.get(position).setPro(5);
                    adapter.setList(loadList);
                    break;
                case MSG_PAUSE_LOAD:
                    loadList.get(position).setPause(true);
                    loadList.get(position).setStart(true);
                    pauseFlag.add(position);
                    adapter.setList(loadList);
                    break;
                case MSG_OPEN_MAGAZINE:
                    Intent intent_magazine = new Intent(DownloadActivity.this,
                            DM_Tab_2Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("dm", subMagazines.get(msg.arg2).getTemplate());
                    bundle.putInt("sid", sid);
                    intent_magazine.putExtras(bundle);
                    startActivity(intent_magazine);
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
        allArticleMagzines = new ArrayList<ArticleMagzine>();
        allPictureMagazines = new ArrayList<PictureMagzine>();
        loadFlag = new ArrayList<Integer>();
        pauseFlag = new ArrayList<Integer>();
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
        } else {
            int size = loadInfoList.size();
            for (int i = 0; i < size; i++) {
                if (loadInfoList.get(i).isFinish()) {
                    loadInfoList.get(i).setOk(true);
                }
            }
        }
        return loadInfoList;
    }

    // load thread
    public class LoadMagazineThread extends Thread {

        private Context context;
        private Handler handler;
        private int sid;
        private int position;
        private ArrayList<ArticleMagzine> articleMagzines = new ArrayList<ArticleMagzine>();
        private ArrayList<PictureMagzine> magazinePics = new ArrayList<PictureMagzine>();

        public LoadMagazineThread(Context _context, Handler _handler, int _sid, int _position) {

            this.position = _position;
            this.context = _context;
            this.handler = _handler;
            this.sid = _sid;
        }

        @Override
        public void run() {

            Looper.prepare();
            try {
                loadList.get(position).setPro(10);
                // send a message
                Message msg1 = new Message();
                msg1.what = MSG_LOADING;
                msg1.arg1 = sid;
                msg1.arg2 = position;
                handler.sendMessage(msg1);

                String content = wh_dmApi.loadMagzine(sid);
                if (content != null && content.length() > 0) {
                    loadList.get(position).setPro(20);
                    // send a message
                    Message msg2 = new Message();
                    msg2.what = MSG_LOADING;
                    msg2.arg1 = sid;
                    msg2.arg2 = position;
                    handler.sendMessage(msg2);
                    int template = LoadUtil.checkTemplate(content);
                    // 0 arcticle 1 image
                    if (template == 0) {
                        articleMagzines = LoadUtil.loadMagazine(content);
                        allArticleMagzines.addAll(articleMagzines);
                        loadList.get(position).setPro(30);
                        // send a message
                        Message msg3 = new Message();
                        msg3.what = MSG_LOADING;
                        msg3.arg1 = sid;
                        msg3.arg2 = position;
                        handler.sendMessage(msg3);
                        int size = articleMagzines.size();
                        ArticleMagzine body = new ArticleMagzine();
                        for (int i = 0; i < size; i++) {
                            body = articleMagzines.get(i);
                            new WebView(context).loadDataWithBaseURL("http://test1.jbr.net.cn:809",
                                    body.getBody(), "text/html", "utf-8", null);
                            if (body.getLitpic() != null && body.getLitpic().length() > 0) {
                                UrlImageViewHelper.setUrlDrawable(new ImageView(context),
                                        WH_DMHttpApiV1.URL_DOMAIN + body.getLitpic(),
                                        R.drawable.item_default, null);

                            }
                            // TODO:
                        }

                        // send finish
                        loadList.get(position).setPro(90);
                        Message msgFinish = new Message();
                        msgFinish.what = MSG_FINISH_LOAD;
                        msgFinish.arg1 = sid;
                        msgFinish.arg2 = position;
                        handler.sendMessage(msgFinish);

                    } else if (template == 1) {
                        magazinePics = LoadUtil.loadMagazinePic(content);
                        allPictureMagazines.addAll(magazinePics);
                        loadList.get(position).setPro(50);
                        // send a message
                        Message msg4 = new Message();
                        msg4.what = MSG_LOADING;
                        msg4.arg1 = sid;
                        msg4.arg2 = position;
                        handler.sendMessage(msg4);
                        int size = magazinePics.size();
                        PictureMagzine pic = new PictureMagzine();
                        for (int i = 0; i < size; i++) {
                            pic = magazinePics.get(i);
                            UrlImageViewHelper.setUrlDrawable(new ImageView(context),
                                    WH_DMHttpApiV1.URL_DOMAIN + pic.getPic(),
                                    R.drawable.item_default, null);
                            // TODO:
                        }
                        // send finish
                        loadList.get(position).setPro(90);
                        Message msgFinish = new Message();
                        msgFinish.what = MSG_FINISH_LOAD;
                        msgFinish.arg1 = sid;
                        msgFinish.arg2 = position;
                        handler.sendMessage(msgFinish);
                    }
                } else {
                    Message msgError = new Message();
                    msgError.what = MSG_NO_MAGAZINE;
                    msgError.arg1 = sid;
                    msgError.arg2 = position;
                    handler.sendMessage(msgError);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // send a error message
                Message msgError = new Message();
                msgError.what = MSG_NO_MAGAZINE;
                msgError.arg1 = sid;
                msgError.arg2 = position;
                handler.sendMessage(msgError);
            }

            super.run();
        }
    }

    private boolean checkAllLoad() {

        int size = loadList.size();
        LoadInfo info;
        for (int i = 0; i < size; i++) {
            info = loadList.get(i);
            if (info.isStart() && !info.isOk()) {
                return false;
            }
        }
        return true;
    }

    public class DeleteThread extends Thread {

        private int sid;
        private int template;

        public DeleteThread(int _sid, int _template) {

            this.sid = _sid;
            this.template = _template;
        }

        @Override
        public void run() {

            try {
                while (insearting) {
                    sleep(100);
                }
                insearting = true;
                databaseImpl.deleteLoad(sid, template);
                databaseImpl.deleteLoadInfo();
                databaseImpl.addLoadInfo(loadList);
                insearting = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.run();
        }

    }

}
