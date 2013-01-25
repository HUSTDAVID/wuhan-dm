
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.type.Article;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.TimeUtil;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

public class MeikeInformActivity extends Activity {

    private TextView txtTitle;
    private TextView txtTime;
    private WebView webview;
    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private GetInformTask getInfoTask;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meikeinform);
        id = getIntent().getIntExtra("id", 0);
        initViews();
    }

    private void initViews() {

        // init header
        TextView txtHead = (TextView) findViewById(R.id.txt_header_title2);
        txtHead.setText(getString(R.string.meike_info));

        ImageButton btnBack = (ImageButton) findViewById(R.id.Btn_back_header2);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                MeikeInformActivity.this.finish();

            }

        });

        wh_dmApp = (WH_DMApp) getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        txtTitle = (TextView) findViewById(R.id.txt_inform_title);
        txtTime = (TextView) findViewById(R.id.txt_informs_time);
        webview = (WebView) findViewById(R.id.webview_inform_body);

        if (getInfoTask != null) {
            getInfoTask.cancel(true);
            getInfoTask = null;
        }
        getInfoTask = new GetInformTask();
        getInfoTask.execute();
    }

    class GetInformTask extends AsyncTask<Void, Void, Article> {

        @Override
        protected Article doInBackground(Void... params) {

            try {
                Article article = wh_dmApi.getArticle(id);
                return article;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Article result) {

            if (result != null) {
                webview.loadDataWithBaseURL("www.baidu.com", result.getBody(), "text/html",
                        "utf-8", null);
                txtTitle.setText(result.getTitle());
                txtTime.setText(TimeUtil.getTimeInterval(result.getPubdate(),
                        MeikeInformActivity.this));
            } else {
                if (wh_dmApp.isConnected()) {

                } else {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            MeikeInformActivity.this);
                }
            }
            super.onPostExecute(result);
        }

    }

}
