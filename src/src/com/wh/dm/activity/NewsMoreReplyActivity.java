
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.type.Comment;
import com.wh.dm.type.Reply;
import com.wh.dm.type.Review;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.TextUtil;
import com.wh.dm.widget.NewsReplyFloorAdapter;
import com.wh.dm.widget.NewsReplyMoreAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsMoreReplyActivity extends Activity {

    private static final int MSG_GET_COMMENT = 0;
    private static final int MSG_PUSH_TOP = 1;
    private static final int MSG_REPLY = 2;
    private static final int MSG_REVIEW = 3;
    LayoutInflater mInflater;

    private EditText edtxMyReplyforBtn;
    private Button btnReply;
    private EditText edtReply;
    private Button btnNews;

    LinearLayout bottomLayout1;
    RelativeLayout bottomLayout2;

    private ListView lv;
    private ProgressDialog progressDialog;
    private NewsReplyMoreAdapter adapter;
    private GetCommentTask getCommentTask = null;
    private final PushTopTask pushTopTask = null;
    private final ReplyTask replyTask = null;
    private ReviewTask reviewTask = null;
    private NewsReplyFloorAdapter floorAdapter;
    private WH_DMApi wh_dmApi;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case MSG_GET_COMMENT:
                    if (getCommentTask != null) {
                        getCommentTask.cancel(true);
                        getCommentTask = null;
                    }
                    getCommentTask = new GetCommentTask();
                    getCommentTask.execute(323);
                    break;
                case MSG_PUSH_TOP:
                    break;
                case MSG_REPLY:
                    break;
                case MSG_REVIEW:
                    if (reviewTask != null) {
                        reviewTask.cancel(true);
                        reviewTask = null;
                    }
                    reviewTask = new ReviewTask();
                    String review = ((EditText) findViewById(R.id.edtx_news_my_reply)).getText()
                            .toString();
                    if (!TextUtil.isEmpty(review)) {
                        reviewTask.execute(review);
                    }
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_reply);

        initViews();
        wh_dmApi = ((WH_DMApp) getApplication()).getWH_DMApi();
        handler.sendEmptyMessage(MSG_GET_COMMENT);

        ImageButton btnBack = (ImageButton) findViewById(R.id.img_header3_back);
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });
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

    private void initViews() {

        // add data for listview
        progressDialog = new ProgressDialog(NewsMoreReplyActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {

                // TODO Auto-generated method stub

            }
        });

        lv = (ListView) findViewById(R.id.lv_news_reply);
        adapter = new NewsReplyMoreAdapter(this);
        NewsReplyFloorAdapter floorAdapter = new NewsReplyFloorAdapter(this);
        lv.setAdapter(adapter);

        // init header
        TextView txtTitle = (TextView) findViewById(R.id.txt_header3_title);
        txtTitle.setText(getResources().getString(R.string.reply));
        TextView txtReply = (TextView) findViewById(R.id.txt_total_reply);
        txtReply.setText(getResources().getString(R.string.context));
        txtReply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                NewsMoreReplyActivity.this.finish();

            }
        });

        // inti reply views
        bottomLayout1 = (LinearLayout) findViewById(R.id.linear1_news_details_bottom);
        bottomLayout2 = (RelativeLayout) findViewById(R.id.linear2_news_details_bottom);

        edtxMyReplyforBtn = (EditText) findViewById(R.id.edtx_news_my_reply);
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
                handler.sendEmptyMessage(MSG_REVIEW);

            }
        });

    }

    private class GetCommentTask extends AsyncTask<Integer, Void, ArrayList<Review>> {

        Exception reason = null;

        @Override
        protected void onPreExecute() {

            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Review> doInBackground(Integer... params) {

            ArrayList<Comment> comments = null;
            ArrayList<Reply> replys = null;
            ArrayList<Review> reviews = new ArrayList<Review>();
            WH_DMApi wh_dm = new WH_DMApi();

            try {
                comments = wh_dm.getComment(params[0]);
                int length = comments.size();
                for (int i = 0; i < length; i++) {
                    int id = comments.get(i).getId();
                    replys = wh_dm.getReply(comments.get(i).getId());
                    Review review = new Review();
                    review.setComment(comments.get(i));
                    if (replys != null) {
                        review.setReply(replys);
                    }
                    reviews.add(review);
                }
                return reviews;
            } catch (Exception e) {
                e.printStackTrace();
                reason = e;
                return null;
            }

        }

        @Override
        protected void onPostExecute(ArrayList<Review> result) {

            if (result != null && result.size() > 0) {
                floorAdapter = new NewsReplyFloorAdapter(NewsMoreReplyActivity.this);
                for (int i = 0; i < result.size(); i++) {
                    ArrayList<Reply> replys = result.get(i).getReply();
                    Comment comment = result.get(i).getComment();
                    if (replys != null && replys.size() > 0) {
                        floorAdapter.setList(result.get(i).getReply());
                        adapter.addItem(getString(R.string.review_name), comment.getDtime(),
                                comment.getMsg(), "" + comment.getGood(), floorAdapter);
                    } else {
                        adapter.addItem(getString(R.string.review_name), comment.getDtime(),
                                comment.getMsg(), "" + comment.getGood(), null);
                    }
                }
            } else {
                Toast.makeText(NewsMoreReplyActivity.this, reason.toString(), Toast.LENGTH_SHORT)
                        .show();
            }
            progressDialog.dismiss();
            super.onPostExecute(result);

        }

    }

    private class PushTopTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(String... params) {

            // TODO Auto-generated method stub
            return null;
        }

        @Override
        protected void onPreExecute() {

            handler.sendEmptyMessage(MSG_GET_COMMENT);
            super.onPreExecute();
        }

    }

    private class ReplyTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            // TODO Auto-generated method stub
            return null;
        }

    }

    private class ReviewTask extends AsyncTask<String, Void, Boolean> {
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
                // result = wh_dmApi.addReview(params[0], 323);\
                result = wh_dmApi.addReply(params[0], 323, 51);
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
                NotificationUtil.showShortToast("success", NewsMoreReplyActivity.this);
            } else {
                NotificationUtil.showShortToast(reason.toString(), NewsMoreReplyActivity.this);
            }
            progressDialog.dismiss();
            super.onPostExecute(result);
        }

    }

}
