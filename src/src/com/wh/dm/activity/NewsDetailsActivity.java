
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.WH_DM;
import com.wh.dm.type.NewsContent;
import com.wh.dm.widget.NewsReplyAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

public class NewsDetailsActivity extends Activity {

    View headerView;
    private final int MSG_GET_NEWSDETAIL = 0;
    private int id;
    private GetNewsDetailTask getNewsDetailTask = null;
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

    LinearLayout bottomLayout1;
    RelativeLayout bottomLayout2;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            if (msg.what == MSG_GET_NEWSDETAIL) {
                if (getNewsDetailTask != null) {
                    getNewsDetailTask.cancel(true);
                    getNewsDetailTask = null;
                }
                getNewsDetailTask = new GetNewsDetailTask();
                getNewsDetailTask.execute(id);
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
        initViews();
        handler.sendEmptyMessage(MSG_GET_NEWSDETAIL);
    }

    @Override
    protected void onResume() {

        webSettings = webViewNewsBody.getSettings();
        setTextSize();
        super.onResume();
    }

    private void initViews() {

        progressDialog = new ProgressDialog(NewsDetailsActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        mInflater = getLayoutInflater();

        lvNews = (ListView) findViewById(R.id.lv_news_details);
        // news body message
        newsMessage = (View) mInflater.inflate(R.layout.news_details, null);
        newsTitle = (TextView) newsMessage.findViewById(R.id.txt_news_title);
        newsTime = (TextView) newsMessage.findViewById(R.id.txt_news_time);
        newsSource = (TextView) newsMessage.findViewById(R.id.txt_news_source);
        webViewNewsBody = (WebView) newsMessage.findViewById(R.id.webview_news_body);
        webViewNewsBody.getSettings().setDefaultTextEncodingName("utf-8");

        webSettings = webViewNewsBody.getSettings();
        setTextSize();
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
        footer = (View) mInflater.inflate(R.layout.news_more_comment, null);
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

                Intent intent = new Intent(NewsDetailsActivity.this, DM_NewsMoreReplyActivity.class);
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

                Intent intent = new Intent(NewsDetailsActivity.this, DM_NewsMoreReplyActivity.class);
                startActivity(intent);
            }
        });

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
                content = (new WH_DM()).getNewsContent(params[0]);
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
                webViewNewsBody.loadDataWithBaseURL(null, result.getBody(), "text/html", "UTF-8",
                        null);
                newsTitle.setText(result.getTitle());
                newsTime.setText(result.getPubdate());
            } else {
                Toast.makeText(NewsDetailsActivity.this, reason.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }
            progressDialog.dismiss();
            super.onPostExecute(result);
        }

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

}
