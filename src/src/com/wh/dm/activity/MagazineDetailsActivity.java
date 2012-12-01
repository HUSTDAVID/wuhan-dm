
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.type.Article;
import com.wh.dm.util.NotificationUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

public class MagazineDetailsActivity extends Activity {

    private static final int MSG_GET_ARTICLE = 0;
    private TextView txtReply;
    private TextView txtTitle;
    private ImageButton btnBack;
    private View newsMessage;
    private TextView newsTitle;
    private TextView newsTime;
    private TextView newsSource;
    private TextView txtReplynum;
    private WebView webViewNewsBody;
    private WebSettings webSettings;
    private GetArticleTask getArticleTask = null;
    private int sid;
    private ProgressDialog progressDialog;
    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == MSG_GET_ARTICLE) {
                if (getArticleTask != null) {
                    getArticleTask.cancel(true);
                    getArticleTask = null;
                }
                getArticleTask = new GetArticleTask();
                getArticleTask.execute(sid);
            }
        };
    };

    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.activity_magazine_details);
        Intent intent = getIntent();
        sid = intent.getIntExtra("sid", 458);
        init();
        wh_dmApp = (WH_DMApp) this.getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        handler.sendEmptyMessage(MSG_GET_ARTICLE);

    }

    public void init() {

        txtReply = (TextView) findViewById(R.id.txt_total_reply);
        txtReply.setVisibility(View.GONE);
        txtTitle = (TextView) findViewById(R.id.txt_header3_title);
        txtTitle.setText("‘”÷æ");
        btnBack = (ImageButton) findViewById(R.id.img_header3_back);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        progressDialog = new ProgressDialog(MagazineDetailsActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        newsTitle = (TextView) findViewById(R.id.txt_news_title);
        newsTime = (TextView) findViewById(R.id.txt_news_time);
        newsSource = (TextView) findViewById(R.id.txt_news_source);
        webViewNewsBody = (WebView) findViewById(R.id.webview_news_body);
        webViewNewsBody.getSettings().setDefaultTextEncodingName("utf-8");
        webViewNewsBody.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        // add news body data
        newsTitle.setText(getResources().getString(R.string.news_title));
        newsTime.setText(getResources().getString(R.string.news_time));
        newsSource.setText(getResources().getString(R.string.news_source));

    }

    private class GetArticleTask extends AsyncTask<Integer, Void, Article> {
        Exception reason = null;

        @Override
        protected void onPreExecute() {

            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Article doInBackground(Integer... params) {

            Article article = null;
            try {
                article = wh_dmApi.getArticle(sid);
            } catch (Exception e) {
                reason = e;
                return null;
            }
            return article;
        }

        @Override
        protected void onPostExecute(Article result) {

            if (result != null) {

                webViewNewsBody.loadDataWithBaseURL("www.jbr.net.cn.html", result.getBody(),
                        "text/html", "utf-8", null);
                newsTitle.setText(result.getTitle());
                newsTime.setText(result.getPubdate());
            } else {
                if (wh_dmApp.isConnected()) {
                    NotificationUtil
                            .showShortToast(reason.toString(), MagazineDetailsActivity.this);
                } else {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            MagazineDetailsActivity.this);
                }
            }
            progressDialog.dismiss();
            super.onPostExecute(result);
        }

    }
}
