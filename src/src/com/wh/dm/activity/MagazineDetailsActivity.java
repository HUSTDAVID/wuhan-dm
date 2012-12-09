
package com.wh.dm.activity;

import com.umeng.api.sns.UMSnsService;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.Article;
import com.wh.dm.type.Comment;
import com.wh.dm.type.MagazineBody;
import com.wh.dm.type.PostResult;
import com.wh.dm.util.NetworkConnection;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.TextUtil;
import com.wh.dm.util.TimeUtil;
import com.wh.dm.util.UrlImageViewHelper;
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
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MagazineDetailsActivity extends Activity {

    View headerView;
    private final int MSG_GET_NEWSDETAIL = 0;
    private final int MSG_GET_COMMENT = 1;
    private final int ADD_REVIEW = 2;
    private final int ADD_FAV = 3;
    private final int curStatus = 0;
    private int sid;
    private String titleUrl;
    private String source;
    private int time;
    private final int curPage = 1;
    private GetCommentTask getCommentTask = null;
    private AddReviewTask addReviewTask = null;
    private GetArticleTask getArticleTask = null;
    private AddFavTask addFavTask = null;
    private TextView newsTitle;
    TextView newsTime;
    TextView newsSource;
    TextView txtReplynum;
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
    private boolean isLoad;
    LinearLayout bottomLayout1;
    RelativeLayout bottomLayout2;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case MSG_GET_NEWSDETAIL:
                    if (getArticleTask != null) {
                        getArticleTask.cancel(true);
                        getArticleTask = null;
                    }
                    getArticleTask = new GetArticleTask();
                    getArticleTask.execute(sid);
                    break;
                case MSG_GET_COMMENT:
                    if (getCommentTask != null) {
                        getCommentTask.cancel(true);
                        getCommentTask = null;
                    }
                    getCommentTask = new GetCommentTask();
                    getCommentTask.execute(sid);
                    break;
                case ADD_REVIEW:
                    if (addReviewTask != null) {
                        addReviewTask.cancel(true);
                        addReviewTask = null;
                    }
                    if (!TextUtil.isEmpty(getFcontent())) {
                        addReviewTask = new AddReviewTask();
                        addReviewTask.execute(getFcontent());
                    } else {
                        NotificationUtil.showShortToast(getString(R.string.review_null),
                                MagazineDetailsActivity.this);
                    }
                    break;
                case ADD_FAV:
                    if (addFavTask != null) {
                        addFavTask.cancel(true);
                        addFavTask = null;
                    }
                    addFavTask = new AddFavTask();
                    addFavTask.execute(sid);
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_magazine_details);
        Intent intent = getIntent();
        sid = intent.getIntExtra("sid", 458);
        isLoad = intent.getBooleanExtra("is_load", false);
        titleUrl = intent.getStringExtra("titleImg");
        source = intent.getStringExtra("source");
        initViews();
        wh_dmApp = (WH_DMApp) this.getApplication();
        databaseImpl = wh_dmApp.getDatabase();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        handler.sendEmptyMessage(MSG_GET_NEWSDETAIL);

    }

    @Override
    public void onBackPressed() {

        if (bottomLayout2.getVisibility() == View.VISIBLE) {
            bottomLayout2.setVisibility(View.GONE);
            bottomLayout1.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    private void initViews() {

        final ImageView img_header = (ImageView) findViewById(R.id.img_header);
        UrlImageViewHelper.setUrlDrawable(img_header, WH_DMHttpApiV1.URL_DOMAIN + titleUrl,
                R.drawable.magazine_title, null);

        progressDialog = new ProgressDialog(MagazineDetailsActivity.this);
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
        webViewNewsBody.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

        edtxMyReplyforBtn = (EditText) findViewById(R.id.edtx_news_my_reply);
        btnMyShare = (Button) findViewById(R.id.btn_news_share);
        btnMyFavorite = (Button) findViewById(R.id.btn_news_my_favorite);
        btnMyFavorite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                if (WH_DMApp.isConnected) {
                    if (WH_DMApp.isLogin) {
                        handler.sendEmptyMessage(ADD_FAV);
                    } else {
                        NotificationUtil.showShortToast(getString(R.string.please_login),
                                MagazineDetailsActivity.this);
                        Intent intent = new Intent(MagazineDetailsActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            MagazineDetailsActivity.this);
                }
            }
        });
        edtxMyReplyforBtn.setFocusable(false);
        lvNews.addHeaderView(newsMessage, null, false);

        // watch more comments
        footer = mInflater.inflate(R.layout.news_more_comment, null);
        lvNews.addFooterView(footer, null, false);

        adapter = new NewsReplyAdapter(this);
        lvNews.setAdapter(adapter);

        btnMore = (Button) findViewById(R.id.btn_news_more);
        btnMore.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MagazineDetailsActivity.this,
                        NewsMoreReplyActivity.class);
                intent.putExtra("id", sid);
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
                if (WH_DMApp.isLogin) {
                    handler.sendEmptyMessage(ADD_REVIEW);
                } else {
                    NotificationUtil.showShortToast(getString(R.string.please_login),
                            MagazineDetailsActivity.this);
                    Intent intent = new Intent(MagazineDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        UMSnsService.UseLocation = true;
        UMSnsService.LocationAuto = true;
        UMSnsService.LOCATION_VALID_TIME = 180000; // 30MINS
        btnMyShare.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                UMSnsService.share(MagazineDetailsActivity.this, "说些什么...", null);
            }
        });
        txtReplynum = (TextView) findViewById(R.id.txt_total_reply);

    }

    @Override
    protected void onResume() {

        webSettings = webViewNewsBody.getSettings();
        setTextSize();
        super.onResume();
    }

    private String getFcontent() {

        return edtReply.getText().toString();
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

    private class GetArticleTask extends AsyncTask<Integer, Void, Article> {
        Exception reason = null;
        ArrayList<Comment> comments = null;

        @Override
        protected void onPreExecute() {

            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Article doInBackground(Integer... params) {

            Article article = null;
            try {
                if (isLoad) {
                    MagazineBody one = new MagazineBody();
                    article = new Article();
                    one = databaseImpl.getOneMagazineBody(sid);
                    article.setBody(one.getBody());
                    article.setId(one.getId());
                    article.setLitpic(one.getLitpic());
                    article.setPubdate(one.getPubdate());
                    article.setSid(one.getSid());
                    article.setTitle(one.getTitle());
                } else {
                    article = wh_dmApi.getArticle(sid);
                }
                // comments = wh_dmApi.getComment(sid, curPage);
            } catch (Exception e) {
                reason = e;
                return null;
            }
            return article;
        }

        @Override
        protected void onPostExecute(Article result) {

            if (result != null) {

                String data = result.getBody();

                webViewNewsBody.loadDataWithBaseURL("www.baidu.com", result.getBody(), "text/html",
                        "utf-8", null);
                newsTitle.setText(result.getTitle());
                newsTime.setText(result.getPubdate());
                if (comments != null && comments.size() > 0) {
                    int commentNum = 5;
                    if (comments.size() < 5) {
                        commentNum = comments.size();
                    }
                    for (int i = 0; i < commentNum; i++) {
                        Comment comment = comments.get(i);
                        adapter.addItem(getString(R.string.news_user), TimeUtil.getTimeInterval(
                                comment.getDtime(), MagazineDetailsActivity.this),
                                comment.getMsg(), "" + comment.getGood());
                    }
                } else {
                    lvNews.removeFooterView(footer);
                }
            } else {
                if (wh_dmApp.isConnected()) {
                    NotificationUtil.showShortToast(getResources().getString(R.string.badconnect),
                            MagazineDetailsActivity.this);
                } else {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            MagazineDetailsActivity.this);
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
                    comments = (new WH_DMApi()).getComment(params[0], curPage);
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

            super.onPostExecute(result);
        }

    }

    private class AddReviewTask extends AsyncTask<String, Void, Boolean> {
        boolean result = false;
        Exception reason = null;

        @Override
        protected void onPreExecute() {

            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                result = wh_dmApi.addReview(params[0], sid);
                return true;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {
                NotificationUtil.showShortToast(getString(R.string.review_succeed),
                        MagazineDetailsActivity.this);
            } else {
                NotificationUtil.showShortToast(getString(R.string.review_fail),
                        MagazineDetailsActivity.this);
            }
            progressDialog.dismiss();
            super.onPostExecute(result);
        }

    }

    private class AddFavTask extends AsyncTask<Integer, Void, Boolean> {
        boolean result = false;
        Exception reason = null;
        PostResult postresult = null;

        @Override
        protected void onPreExecute() {

            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {

            // TODO Auto-generated method stub
            try {
                postresult = wh_dmApi.addFav(params[0], 0);
                if (postresult.getResult())
                    return true;
                else
                    return false;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {
                NotificationUtil.showShortToast(getString(R.string.favorite_succeed),
                        MagazineDetailsActivity.this);
            } else {
                if (postresult == null)
                    NotificationUtil.showShortToast(getString(R.string.favorite_fail) + ":未知原因",
                            MagazineDetailsActivity.this);
                else
                    NotificationUtil.showShortToast(postresult.getMsg(),
                            MagazineDetailsActivity.this);
            }
            progressDialog.dismiss();
            super.onPostExecute(result);
        }

    }
}
