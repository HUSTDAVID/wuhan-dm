
package com.wh.dm.activity;

import com.umeng.api.sns.UMSnsService;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.Comment;
import com.wh.dm.type.NewsContent;
import com.wh.dm.util.NetworkConnection;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.widget.NewsReplyAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsDetailsActivity extends Activity {

    View headerView;
    private final int MSG_GET_NEWSDETAIL = 0;
    private final int MSG_GET_COMMENT = 1;
    private final int curStatus = 0;

    private int id;
    private int fid;
    private int time;
    private GetNewsDetailTask getNewsDetailTask = null;
    private GetCommentTask getCommentTask = null;
    TextView newsTitle;
    TextView newsTime;
    TextView newsSource;

    WebView webViewNewsBody;
    WebSettings webSettings;

    LayoutInflater mInflater;
    private ListView lvNews;
    private View newsMessage;
    private View footer;
    private EditText edtxMyReplyforBtn;
    private EditText edtReply;
    private TextView edtMoreReply;
    private Button btnReply;
    private Button btnMyShare;
    private Button btnMyFavorite;
    private Button btnMore;
    private ImageButton btnBack;
    private NewsReplyAdapter adapter;
    private ProgressDialog progressDialog;
    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private DatabaseImpl databaseImpl;
    LinearLayout bottomLayout1;
    RelativeLayout bottomLayout2;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case MSG_GET_NEWSDETAIL:
                    if (getNewsDetailTask != null) {
                        getNewsDetailTask.cancel(true);
                        getNewsDetailTask = null;
                    }
                    getNewsDetailTask = new GetNewsDetailTask();
                    getNewsDetailTask.execute(id);
                    break;
                case MSG_GET_COMMENT:
                    if (getCommentTask != null) {
                        getCommentTask.cancel(true);
                        getCommentTask = null;
                    }
                    getCommentTask = new GetCommentTask();
                    getCommentTask.execute(fid);
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_details);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 211);
        Log.d("id", "" + id);
        Log.d("status", "" + curStatus);
        initViews();
        wh_dmApp = (WH_DMApp) this.getApplication();
        databaseImpl = wh_dmApp.getDatabase();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        handler.sendEmptyMessage(MSG_GET_NEWSDETAIL);
        UMSnsService.UseLocation = true;
        UMSnsService.LocationAuto = true;
        UMSnsService.LOCATION_VALID_TIME = 180000; // 30MINS
        Button sharetxt = (Button) findViewById(R.id.btn_news_share);

        sharetxt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                UMSnsService.share(NewsDetailsActivity.this, "说些什么...", null);
            }
        });
    }

    private void initViews() {

        progressDialog = new ProgressDialog(NewsDetailsActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        mInflater = getLayoutInflater();

        lvNews = (ListView) findViewById(R.id.lv_news_details);
        // news body message
        newsMessage = mInflater.inflate(R.layout.news_details, null);
        newsTitle = (TextView) newsMessage.findViewById(R.id.txt_news_title);
        newsTime = (TextView) newsMessage.findViewById(R.id.txt_news_time);
        newsSource = (TextView) newsMessage.findViewById(R.id.txt_news_source);
        webViewNewsBody = (WebView) newsMessage.findViewById(R.id.webview_news_body);
        webViewNewsBody.getSettings().setDefaultTextEncodingName("utf-8");
        // webViewNewsBody.loadUrl("file:///android_asset/news.html");

        // add news body data
        newsTitle.setText(getResources().getString(R.string.news_title));
        newsTime.setText(getResources().getString(R.string.news_time));
        newsSource.setText(getResources().getString(R.string.news_source));

        edtxMyReplyforBtn = (EditText) findViewById(R.id.edtx_news_my_reply);
        btnMyShare = (Button) findViewById(R.id.btn_news_share);
        btnMyFavorite = (Button) findViewById(R.id.btn_news_my_favorite);

        edtxMyReplyforBtn.setFocusable(false);

        lvNews.addHeaderView(newsMessage, null, false);

        // watch more comments
        footer = mInflater.inflate(R.layout.news_more_comment, null);
        lvNews.addFooterView(footer, null, false);

        adapter = new NewsReplyAdapter(this);
        // for (int i = 0; i < 3; i++) {
        adapter.addItem("手机版网友", "13小时前", "没什么谈的，人不敬我，我何必敬人。", "顶1212");
        // }
        lvNews.setAdapter(adapter);

        btnMyShare.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        btnMore = (Button) findViewById(R.id.btn_news_more);
        btnMore.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewsDetailsActivity.this, NewsMoreReplyActivity.class);
                startActivity(intent);

            }
        });

        // inti reply views
        bottomLayout1 = (LinearLayout) findViewById(R.id.linear1_news_details_bottom);
        bottomLayout2 = (RelativeLayout) findViewById(R.id.linear2_news_details_bottom);

        edtxMyReplyforBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                bottomLayout1.setVisibility(View.GONE);
                bottomLayout2.setVisibility(View.VISIBLE);
                edtReply.requestFocus();
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(
                        edtReply, 0);
            }
        });

        edtReply = (EditText) findViewById(R.id.edt_news_details_input);
        btnReply = (Button) findViewById(R.id.btn_news_details_reply);

        btnReply.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                bottomLayout1.setVisibility(View.VISIBLE);
                bottomLayout2.setVisibility(View.GONE);
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(edtReply.getWindowToken(), 0);

            }
        });
        btnBack = (ImageButton) findViewById(R.id.img_header3_back);
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });
        edtMoreReply = (TextView) findViewById(R.id.txt_total_reply);
        edtMoreReply.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewsDetailsActivity.this, NewsMoreReplyActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {

        webSettings = webViewNewsBody.getSettings();
        setTextSize();
        super.onResume();
    }

    private void setTextSize() {

        SharedPreferences sPreference = getSharedPreferences("com.wh.dm_preferences", 0);
        String size = sPreference.getString("text_size", "key2");
        if (size.equals("key0")) {
            webSettings.setTextSize(TextSize.LARGER);
        } else if (size.equals("key1")) {
            webSettings.setTextSize(TextSize.LARGEST);
        } else if (size.equals("key2")) {
            webSettings.setTextSize(TextSize.NORMAL);
        } else if (size.equals("key3")) {
            webSettings.setTextSize(TextSize.SMALLER);
        } else if (size.equals("key4")) {
            webSettings.setTextSize(TextSize.SMALLEST);
        } else {
            webSettings.setTextSize(TextSize.NORMAL); // 出现其他情况设置正在字号
        }
    }

    private class GetNewsDetailTask extends AsyncTask<Integer, Void, NewsContent> {
        Exception reason = null;

        @Override
        protected void onPreExecute() {

            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected NewsContent doInBackground(Integer... params) {

            NewsContent[] content = null;

            try {
                content = wh_dmApi.getNewsContent(params[0]);
                return content[0];
            } catch (Exception e) {
                e.printStackTrace();
                reason = e;
                return null;
            }

        }

        @Override
        protected void onPostExecute(NewsContent result) {

            if (result != null) {
                databaseImpl.addNewsContent(result);
                time++;
                Log.d("time", "" + time);
                webViewNewsBody.loadDataWithBaseURL(null, result.getBody(), "text/html", "utf-8",
                        null);
                newsTitle.setText(result.getTitle());
                newsTime.setText(result.getPubdate());
            } else {
                result = databaseImpl.getNewsContent(id);
                if (result != null) {

                    webViewNewsBody.loadDataWithBaseURL(null, result.getBody(), "text/html",
                            "utf-8", null);
                    newsTitle.setText(result.getTitle());
                    newsTime.setText(result.getPubdate());
                }
                if (wh_dmApp.isConnected()) {
                    NotificationUtil.showShortToast(reason.toString(), NewsDetailsActivity.this);
                } else {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            NewsDetailsActivity.this);
                }
            }
            progressDialog.dismiss();
            super.onPostExecute(result);
        }

    }

    private class GetCommentTask extends AsyncTask<Integer, Void, ArrayList<Comment>> {
        Exception reason = null;
        ArrayList<Comment> comments = null;

        @Override
        protected void onPreExecute() {

            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Comment> doInBackground(Integer... params) {

            if (NetworkConnection.checkInternetConnection()) {
                try {
                    comments = (new WH_DMApi()).getComment(params[0]);
                    return comments;
                } catch (Exception e) {
                    e.printStackTrace();
                    reason = e;
                }
                return comments;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Comment> result) {

            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }

    }

}
