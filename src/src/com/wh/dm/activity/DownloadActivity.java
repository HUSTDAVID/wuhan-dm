
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DownloadActivity extends Activity {

    private ListView lvDownload;
    private TextView txtTitle;
    private ImageButton btnBack;
    private ArrayList<LoadInfo> loadList;
    private ArrayList<Magazine> subMagazines;
    private ArrayList<ArticleMagzine> articleMagzines;
    public static final int MSG_NO_MAGAZINE = 0;
    public static final int MSG_LOADING = 1;
    public static final int MSG_FINISH_LOAD = 2;
    public static final int MSG_START_LOAD = 3;
    public static final int MSG_PAUSE_LOAD = 4;
    public static final int MSG_OPEN_MAGAZINE = 5;
    private boolean insearting = false;

    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private DatabaseImpl databaseImpl;
    DownloadAdapter adapter;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            int sid = msg.arg1;
            int position = msg.arg2;
            int size = loadList.size();

            switch (msg.what) {
                case MSG_NO_MAGAZINE:
                    NotificationUtil.showShortToast("ÔÝÎÞÐÂ¿¯", DownloadActivity.this);
                    adapter.setList(loadList);
                    break;
                case MSG_LOADING:
                    adapter.setList(loadList);
                    break;
                case MSG_FINISH_LOAD:
                    adapter.setList(loadList);
                    break;
                case MSG_START_LOAD:
                    new LoadMagazineThread(DownloadActivity.this, this, sid, position).start();
                    loadList.get(position).setStart(true);
                    loadList.get(position).setPause(false);
                    loadList.get(position).setFinish(false);
                    loadList.get(position).setPro(5);
                    adapter.setList(loadList);
                    break;
                case MSG_PAUSE_LOAD:
                    // i do not know how to pause.may be need to delete it. or
                    // save all thread and stop it.
                    // loadList.get(position).setPause(true);
                    // loadList.get(position).setStart(false);
                    // loadList.get(position).setFinish(false);
                    // loadList.get(position).setPro(5);
                    // adapter.setList(loadList);
                    // int template = subMagazines.get(position).getTemplate();
                    // (new DeleteThread(sid, template)).start();
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
        articleMagzines = new ArrayList<ArticleMagzine>();
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

            // use a message send infomation to ui thread
            Looper.prepare();
            try {
                int fileSize = 0;
                URL url = new URL("http://test1.jbr.net.cn:809/api/Magazine.aspx?act=down&sid="
                        + sid);
                URLConnection conn = url.openConnection();
                conn.connect();
                fileSize = conn.getContentLength();
                if (fileSize <= 0) {
                    Message msgError = new Message();
                    msgError.what = MSG_NO_MAGAZINE;
                    msgError.arg1 = sid;
                    while (insearting) {
                        sleep(100);
                    }
                    insearting = true;
                    loadList.get(position).setStart(false);
                    loadList.get(position).setFinish(false);
                    loadList.get(position).setPause(false);
                    databaseImpl.deleteLoadInfo();
                    databaseImpl.addLoadInfo(loadList);
                    insearting = false;
                    handler.sendMessage(msgError);

                }
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                is.close();
                Message msgReaderIs = new Message();
                msgReaderIs.what = MSG_LOADING;
                msgReaderIs.arg1 = sid;
                while (insearting) {
                    sleep(100);
                }
                insearting = true;
                loadList.get(position).setPro(10);
                databaseImpl.deleteLoadInfo();
                databaseImpl.addLoadInfo(loadList);
                insearting = false;
                handler.sendMessage(msgReaderIs);
                int template = LoadUtil.checkTemplate(sb.toString());
                // 0 arcticle 1 image
                if (template == 0) {
                    articleMagzines = LoadUtil.loadMagazine(sb.toString());
                    Message msgChangeType = new Message();
                    msgChangeType.what = MSG_LOADING;
                    msgChangeType.arg1 = sid;
                    while (insearting) {
                        sleep(100);
                    }
                    insearting = true;
                    if (loadList.get(position).isPause()) {

                    } else {
                        loadList.get(position).setPro(20);
                        databaseImpl.deleteLoadInfo();
                        databaseImpl.addLoadInfo(loadList);
                        handler.sendMessage(msgChangeType);
                    }
                    insearting = false;

                    if (articleMagzines != null && articleMagzines.size() > 0) {
                        while (insearting) {
                            sleep(100);
                        }
                        insearting = true;
                        if (loadList.get(position).isPause()) {

                        } else {
                            databaseImpl.addMagazineBody(articleMagzines);
                        }
                        insearting = false;
                        int size = articleMagzines.size();
                        ArticleMagzine body = new ArticleMagzine();
                        WebView webView = new WebView(context);
                        ImageView imageView = new ImageView(context);
                        for (int i = 0; i < size; i++) {
                            body = articleMagzines.get(i);
                            webView.loadDataWithBaseURL("http://test1.jbr.net.cn:809",
                                    body.getBody(), "text/html", "utf-8", null);
                            if (body.getLitpic() != null && body.getLitpic().length() > 0) {
                                UrlImageViewHelper.setUrlDrawable(imageView,
                                        WH_DMHttpApiV1.URL_DOMAIN + body.getLitpic(),
                                        R.drawable.item_default, null);
                            }
                            if (i % 15 == 0) {
                                Message messagePro = new Message();
                                messagePro.what = MSG_LOADING;
                                messagePro.arg1 = sid;

                                while (insearting) {
                                    sleep(100);
                                }
                                insearting = true;
                                if (loadList.get(position).isPause()) {
                                    insearting = false;
                                    break;
                                }
                                loadList.get(position).setPro(20 + i * 80 / size);
                                databaseImpl.deleteLoadInfo();
                                databaseImpl.addLoadInfo(loadList);
                                insearting = false;
                                handler.sendMessage(messagePro);
                            }
                        }

                        Message msgFinish = new Message();
                        msgFinish.what = MSG_FINISH_LOAD;
                        msgFinish.arg1 = sid;
                        while (insearting) {
                            sleep(100);
                        }
                        insearting = true;
                        if (loadList.get(position).isPause()) {

                        } else {
                            loadList.get(position).setPro(100);
                            loadList.get(position).setFinish(true);
                            loadList.get(position).setStart(true);
                            loadList.get(position).setPause(false);
                            databaseImpl.deleteLoadInfo();
                            databaseImpl.addLoadInfo(loadList);
                            handler.sendMessage(msgFinish);
                        }
                        insearting = false;

                    } else {
                        Message msgError = new Message();
                        msgError.what = MSG_NO_MAGAZINE;
                        msgError.arg1 = sid;
                        handler.sendMessage(msgError);

                    }
                } else {

                    magazinePics = LoadUtil.loadMagazinePic(sb.toString());
                    if (magazinePics != null && magazinePics.size() > 0) {
                        Message message = new Message();
                        message.what = MSG_LOADING;
                        message.arg1 = sid;
                        while (insearting) {
                            sleep(100);
                        }
                        insearting = true;
                        if (loadList.get(position).isPause()) {

                        } else {
                            loadList.get(position).setPro(30);
                            databaseImpl.addMagazinePic(magazinePics);
                            handler.sendMessage(message);

                        }
                        insearting = false;
                        int size = magazinePics.size();
                        PictureMagzine pic = new PictureMagzine();
                        ImageView imageView = new ImageView(context);
                        for (int i = 0; i < size; i++) {
                            pic = magazinePics.get(i);
                            UrlImageViewHelper.setUrlDrawable(imageView, WH_DMHttpApiV1.URL_DOMAIN
                                    + pic.getPic(), R.drawable.item_default, null);
                            if (i % 10 == 0) {
                                Message messagePro = new Message();
                                messagePro.what = MSG_LOADING;
                                messagePro.arg1 = sid;
                                while (insearting) {
                                    sleep(100);
                                }
                                insearting = true;
                                if (loadList.get(position).isPause()) {

                                } else {
                                    loadList.get(position).setPro(30 + i * 80 / size);
                                    databaseImpl.deleteLoadInfo();
                                    databaseImpl.addLoadInfo(loadList);
                                    insearting = false;
                                    handler.sendMessage(messagePro);
                                }
                            }

                        }

                        Message msgFinish = new Message();
                        msgFinish.what = MSG_FINISH_LOAD;
                        msgFinish.arg1 = sid;
                        while (insearting) {
                            sleep(100);
                        }
                        insearting = true;
                        if (loadList.get(position).isPause()) {

                        } else {
                            loadList.get(position).setPro(100);
                            loadList.get(position).setFinish(true);
                            loadList.get(position).setStart(true);
                            loadList.get(position).setPause(false);
                            loadList.get(position).setPro(100);
                            databaseImpl.deleteLoadInfo();
                            databaseImpl.addLoadInfo(loadList);
                            handler.sendMessage(msgFinish);
                        }
                        insearting = false;

                    } else {
                        Message msgError = new Message();
                        msgError.what = MSG_NO_MAGAZINE;
                        msgError.arg1 = sid;
                        handler.sendMessage(msgError);

                    }

                }

            } catch (Exception e) {
                Message msgError = new Message();
                msgError.what = MSG_NO_MAGAZINE;
                msgError.arg1 = sid;
                handler.sendMessage(msgError);
                e.printStackTrace();

            }
            super.run();
        }
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
