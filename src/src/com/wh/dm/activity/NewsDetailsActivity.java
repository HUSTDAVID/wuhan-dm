
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.widget.NewsReplyAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NewsDetailsActivity extends Activity {

    View headerView;

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

    LinearLayout bottomLayout1;
    RelativeLayout bottomLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_details);

        initViews();
    }

    private void initViews() {

        mInflater = getLayoutInflater();

        lvNews = (ListView) findViewById(R.id.lv_news_details);
        // news body message
        newsMessage = (View) mInflater.inflate(R.layout.news_details, null);
        TextView newsTitle = (TextView) newsMessage.findViewById(R.id.txt_news_title);
        TextView newsTime = (TextView) newsMessage.findViewById(R.id.txt_news_time);
        TextView newsSource = (TextView) newsMessage.findViewById(R.id.txt_news_source);
        WebView webViewNewsBody = (WebView) newsMessage.findViewById(R.id.webview_news_body);
        webViewNewsBody.loadUrl("file:///android_asset/news.html");

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

                Intent intent = new Intent(NewsDetailsActivity.this,
                        DM_NewsMoreReplyActivity.class);
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

                Intent intent = new Intent(NewsDetailsActivity.this,
                        DM_NewsMoreReplyActivity.class);
                startActivity(intent);
            }
        });

    }

}
