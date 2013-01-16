
package com.wh.dm.activity;

import com.umeng.api.sns.UMSnsService;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.preference.Preferences;
import com.wh.dm.type.Comment;
import com.wh.dm.type.Favorite;
import com.wh.dm.type.NewsContent;
import com.wh.dm.type.PostResult;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.TextUtil;
import com.wh.dm.util.TimeUtil;
import com.wh.dm.widget.NewsReplyAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsDetailsActivity extends Activity {

    View headerView;
    private final int MSG_GET_NEWSDETAIL = 0;
    private final int MSG_GET_COMMENT = 1;
    private final int ADD_REVIEW = 2;
    private final int ADD_FAV = 3;
    public static final int MSG_PUSH_TOP = 4;
    public static final int MSG_REPLY = 5;
    private final int curStatus = 0;
    private int id;
    private String fid;
    private int time;
    private final int curPage = 1;
    private int numReply = 0;
    private boolean isReply = false;
    private boolean isReview = true;
    private GetNewsDetailTask getNewsDetailTask = null;
    private GetCommentTask getCommentTask = null;
    private AddReviewTask addReviewTask = null;
    private AddFavTask addFavTask = null;
    private PushTopTask pushTopTask = null;
    private ReplyTask replyTask = null;
    private TextView newsTitle;
    TextView newsTime;
    TextView newsSource;
    TextView txtReplynum;
    WebView webViewNewsBody;
    WebSettings webSettings;
    Parcelable lvState;

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
    // private ProgressDialog progressDialog;
    private LinearLayout loadLayout;
    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private DatabaseImpl databaseImpl;
    LinearLayout bottomLayout1;
    RelativeLayout bottomLayout2;
    NewsContent newsContent;
    public static boolean freshComment = false;

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
                    getCommentTask.execute(id);
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
                                NewsDetailsActivity.this);
                    }
                    break;
                case ADD_FAV:
                    if (addFavTask != null) {
                        addFavTask.cancel(true);
                        addFavTask = null;
                    }
                    addFavTask = new AddFavTask();
                    addFavTask.execute(id);
                    break;

                case MSG_PUSH_TOP:
                    if (pushTopTask != null) {
                        pushTopTask.cancel(true);
                        pushTopTask = null;
                    }

                    Bundle bundle = msg.getData();
                    pushTopTask = new PushTopTask();
                    pushTopTask.execute(bundle.getString("fid"));
                    break;

                case MSG_REPLY:
                    Bundle bundle1 = msg.getData();
                    isReply = bundle1.getBoolean("isReply");
                    if (isReply) {
                        if (replyTask != null) {
                            replyTask.cancel(true);
                            replyTask = null;
                        }
                        replyTask = new ReplyTask();
                        replyTask.execute(fid);
                        isReview = true;

                    } else {
                        fid = bundle1.getString("fid");
                        isReview = false;
                        bottomLayout1.setVisibility(View.GONE);
                        bottomLayout2.setVisibility(View.VISIBLE);
                        edtReply.setText("");

                        edtReply.requestFocus();
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .showSoftInput(edtReply, 0);
                    }
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
        initViews();
        wh_dmApp = (WH_DMApp) this.getApplication();
        databaseImpl = wh_dmApp.getDatabase();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        handler.sendEmptyMessage(MSG_GET_NEWSDETAIL);

    }

    @Override
    public void onBackPressed() {

        if (bottomLayout2.getVisibility() == View.VISIBLE) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    edtReply.getWindowToken(), 0);
            bottomLayout2.setVisibility(View.GONE);
            bottomLayout1.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    private void initViews() {

        loadLayout = (LinearLayout) findViewById(R.id.detail_load);

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
        webViewNewsBody.getSettings().setPluginsEnabled(true);
        webViewNewsBody.getSettings().setPluginState(PluginState.ON);
        webViewNewsBody.getSettings().setAllowFileAccess(true);
        webViewNewsBody.getSettings().setPluginsPath(
                "/data/data/" + getPackageName() + "/app_plugins/");

        edtxMyReplyforBtn = (EditText) findViewById(R.id.edtx_news_my_reply);
        btnMyShare = (Button) findViewById(R.id.btn_news_share);
        btnMyFavorite = (Button) findViewById(R.id.btn_news_my_favorite);
        btnMyFavorite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wh_dmApp.isConnected()) {
                    if (WH_DMApp.isLogin) {
                        handler.sendEmptyMessage(ADD_FAV);
                    } else {
                        NotificationUtil.showShortToast(getString(R.string.please_login),
                                NewsDetailsActivity.this);
                        Intent intent = new Intent(NewsDetailsActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            NewsDetailsActivity.this);
                }
            }
        });
        edtxMyReplyforBtn.setFocusable(false);
        lvNews.addHeaderView(newsMessage, null, false);

        // watch more comments
        footer = mInflater.inflate(R.layout.news_more_comment_white, null);
        footer.setVisibility(View.INVISIBLE);
        lvNews.addFooterView(footer, null, false);

        adapter = new NewsReplyAdapter(this);
        adapter.setHandler(handler);
        lvNews.setAdapter(adapter);

        btnMore = (Button) findViewById(R.id.btn_news_more);
        btnMore.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String share = "";
                String head = getResources().getString(R.string.share_news);
                String info = "";
                if (newsContent != null) {
                    info = "\"" + newsContent.getTitle() + "\" "
                            + getString(R.string.share_url_main) + "id=" + newsContent.getId()
                            + "&mid=0&type=0";
                }
                share = head + info;
                Intent intent = new Intent(NewsDetailsActivity.this, NewsMoreReplyActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("share", share);
                startActivity(intent);

            }
        });

        // init reply views
        bottomLayout1 = (LinearLayout) findViewById(R.id.linear1_news_details_bottom);
        bottomLayout2 = (RelativeLayout) findViewById(R.id.linear2_news_details_bottom);

        edtxMyReplyforBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                bottomLayout1.setVisibility(View.GONE);
                bottomLayout2.setVisibility(View.VISIBLE);
                edtReply.setText("");
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
                if (isReview) {
                    handler.sendEmptyMessage(ADD_REVIEW);

                } else {
                    Message msg = new Message();
                    msg.what = MSG_REPLY;
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isReply", true);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
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

                String share = "";
                String head = getResources().getString(R.string.share_news);
                String info = "";
                if (newsContent != null) {
                    info = "\"" + newsContent.getTitle() + "\" "
                            + getString(R.string.share_url_main) + "id=" + newsContent.getId()
                            + "&mid=0&type=0";
                }
                share = head + info;
                Intent intent = new Intent(NewsDetailsActivity.this, NewsMoreReplyActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("share", share);
                startActivity(intent);
            }
        });

        UMSnsService.UseLocation = true;
        UMSnsService.LocationAuto = true;
        UMSnsService.LOCATION_VALID_TIME = 180000; // 30MINS
        btnMyShare.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String share = "";
                String head = getResources().getString(R.string.share_news);
                String info = "";
                if (newsContent != null) {
                    info = "\"" + newsContent.getTitle() + "\" "
                            + getString(R.string.share_url_main) + "id=" + newsContent.getId()
                            + "&mid=0&type=0";
                }
                share = head + info;
                Intent intent = new Intent(NewsDetailsActivity.this, ShareActivity.class);
                intent.putExtra("share", share);
                startActivity(intent);

            }
        });
        txtReplynum = (TextView) findViewById(R.id.txt_total_reply);

    }

    @Override
    protected void onResume() {

        webSettings = webViewNewsBody.getSettings();
        setTextSize();
        if (freshComment) {
            handler.sendEmptyMessage(MSG_GET_COMMENT);
            freshComment = false;
        }
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
            webSettings.setTextSize(TextSize.NORMAL); // 出现其他情况设置正常字号
        }
    }

    private class GetNewsDetailTask extends AsyncTask<Integer, Void, NewsContent> {
        Exception reason = null;
        ArrayList<Comment> comments = null;

        @Override
        protected void onPreExecute() {

            loadLayout.setVisibility(View.VISIBLE);
            lvNews.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected NewsContent doInBackground(Integer... params) {

            NewsContent[] content = null;

            try {
                content = wh_dmApi.getNewsContent(params[0]);
                comments = wh_dmApi.getComment(id, curPage);
                newsContent = content[0];
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
                webViewNewsBody.loadDataWithBaseURL("www.baidu.com", result.getBody(), "text/html",
                        "utf-8", null);
                newsTitle.setText(result.getTitle());
                newsTime.setText(TimeUtil.getTimeInterval(result.getPubdate(),
                        NewsDetailsActivity.this));
                newsSource.setText(result.getSource());
                footer.setVisibility(View.VISIBLE);
                if (comments != null && comments.size() > 0) {
                    int commentNum = 5;
                    if (comments.size() < 5) {
                        commentNum = comments.size();
                    }
                    adapter.clearItem();
                    for (int i = 0; i < commentNum; i++) {
                        Comment comment = comments.get(i);
                        adapter.addItem(comment.getUsername(), TimeUtil.getTimeInterval(
                                comment.getDtime(), NewsDetailsActivity.this), comment.getMsg(), ""
                                + comment.getGood(), comment.getId());
                    }
                } else {
                    lvNews.removeFooterView(footer);
                }
                numReply = result.getFcount();
                txtReplynum.setText("" + result.getFcount() + getString(R.string.news_reply_count));

            } else {
                result = databaseImpl.getNewsContent(id);
                if (result != null) {

                    webViewNewsBody.loadDataWithBaseURL(null, result.getBody(), "text/html",
                            "utf-8", null);
                    newsTitle.setText(result.getTitle());
                    newsTime.setText(result.getPubdate());
                }
                if (wh_dmApp.isConnected()) {

                } else {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            NewsDetailsActivity.this);
                }
            }

            loadLayout.setVisibility(View.GONE);
            lvNews.setVisibility(View.VISIBLE);
            super.onPostExecute(result);
        }
    }

    private class GetCommentTask extends AsyncTask<Integer, Void, ArrayList<Comment>> {
        Exception reason = null;
        ArrayList<Comment> comments = null;

        @Override
        protected void onPreExecute() {

            lvState = lvNews.onSaveInstanceState();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Comment> doInBackground(Integer... params) {

            // if (NetworkConnection.checkInternetConnection()) {
            if (wh_dmApp.isConnected()) {
                try {
                    comments = wh_dmApi.getComment(params[0], curPage);
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
            if (comments != null && comments.size() > 0) {
                int commentNum = 5;
                if (comments.size() < 5) {
                    commentNum = comments.size();
                }
                adapter.clearItem();
                for (int i = 0; i < commentNum; i++) {
                    Comment comment = comments.get(i);
                    adapter.addItem(comment.getUsername(),
                            TimeUtil.getTimeInterval(comment.getDtime(), NewsDetailsActivity.this),
                            comment.getMsg(), "" + comment.getGood(), comment.getId());
                }
            } else {
                lvNews.removeFooterView(footer);
            }
            // txtReplynum.setText("" + result.getFcount() +
            // getString(R.string.news_reply_count));
            if (lvState != null) {
                lvNews.onRestoreInstanceState(lvState);
            }
            super.onPostExecute(result);
        }

    }

    private class AddReviewTask extends AsyncTask<String, Void, Boolean> {
        boolean result = false;
        Exception reason = null;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                result = wh_dmApi.addReview(params[0], id);
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
                        NewsDetailsActivity.this);
                txtReplynum.setText("" + (++numReply) + getString(R.string.news_reply_count));
                handler.sendEmptyMessage(MSG_GET_COMMENT);
            } else {
                NotificationUtil.showShortToast(getString(R.string.review_fail),
                        NewsDetailsActivity.this);
            }
            super.onPostExecute(result);
        }

    }

    private class AddFavTask extends AsyncTask<Integer, Void, Boolean> {
        boolean result = false;
        Exception reason = null;
        PostResult postresult = null;

        @Override
        protected void onPreExecute() {

            loadLayout.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {

            // TODO Auto-generated method stub
            try {
                postresult = wh_dmApi.addFav(params[0], 0);
                if (postresult.getResult()) {
                    Favorite news = new Favorite();
                    news.setNo(1);
                    news.setFid(1);
                    news.setNid(params[0]);
                    news.setType(0);
                    news.setTitle(newsTitle.getText().toString());
                    Date now = new Date();
                    SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateStr = s.format(now);
                    news.setAddTime(dateStr);
                    ArrayList<Favorite> addNews = new ArrayList<Favorite>();
                    addNews.add(news);
                    databaseImpl.addFavorite(addNews);
                    return true;
                } else
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
                // CollectNewsActivity.isNewCollect = true;
                MessageActivity.refreshCollect = true;
                NotificationUtil.showShortToast(getString(R.string.favorite_succeed),
                        NewsDetailsActivity.this);
            } else {
                if (postresult == null)
                    NotificationUtil.showShortToast(getString(R.string.favorite_fail) + ":未知原因",
                            NewsDetailsActivity.this);
                else
                    NotificationUtil.showShortToast(postresult.getMsg(), NewsDetailsActivity.this);
            }
            loadLayout.setVisibility(View.GONE);
            super.onPostExecute(result);
        }

    }

    private class ReplyTask extends AsyncTask<String, Void, Boolean> {
        Exception reason = null;

        @Override
        protected Boolean doInBackground(String... params) {

            boolean isReply = false;
            try {
                isReply = wh_dmApi.addReply(getFcontent(), params[0]);
                return isReply;
            } catch (Exception e) {
                e.printStackTrace();
                reason = e;
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {
                NotificationUtil.showShortToast(getString(R.string.thanks_gelivable),
                        NewsDetailsActivity.this);
            } else {
                NotificationUtil.showShortToast(getString(R.string.reply_fail),
                        NewsDetailsActivity.this);
            }
            super.onPostExecute(result);
        }

    }

    private class PushTopTask extends AsyncTask<String, Void, PostResult> {
        Exception reason = null;

        @Override
        protected PostResult doInBackground(String... params) {

            PostResult result = null;
            try {
                result = wh_dmApi.addTop(params[0],
                        Preferences.getMachineId(NewsDetailsActivity.this));
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                reason = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(PostResult result) {

            if (result != null && result.getResult()) {
                NotificationUtil.showShortToast(getString(R.string.thanks_gelivable),
                        NewsDetailsActivity.this);
                handler.sendEmptyMessage(MSG_GET_COMMENT);
            } else {
                if (result != null) {
                    NotificationUtil.showShortToast(result.getMsg(), NewsDetailsActivity.this);
                } else if (wh_dmApp.isConnected()) {

                } else {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            NewsDetailsActivity.this);
                }
            }
            super.onPostExecute(result);
        }

    }
}
