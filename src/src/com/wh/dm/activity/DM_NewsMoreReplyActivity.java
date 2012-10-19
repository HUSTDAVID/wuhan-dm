
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.widget.NewsReplyFloorAdapter;
import com.wh.dm.widget.NewsReplyMoreAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DM_NewsMoreReplyActivity extends Activity {

    LayoutInflater mInflater;

    private EditText edtxMyReplyforBtn;
    private Button btnReply;
    private EditText edtReply;

    LinearLayout bottomLayout1;
    RelativeLayout bottomLayout2;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_reply);

        initViews();
    }

    private void initViews() {

        // add data for listview
        lv = (ListView) findViewById(R.id.lv_news_reply);
        NewsReplyMoreAdapter adapter = new NewsReplyMoreAdapter(this);
        NewsReplyFloorAdapter floorAdapter = new NewsReplyFloorAdapter(this);
        floorAdapter.addItem("手机版网友", "谈判，谈什么？狗日的屡屡不改，何必谈", 1);
        floorAdapter.addItem("手机版网友", "就是，我们也出动我们的潜艇", 2);
        for (int i = 0; i < 5; i++) {
            adapter.addItem("手机版网友", "13小时前", "没什么谈的，人不敬我，我何必敬人。", "顶1212", floorAdapter);
        }
        lv.setAdapter(adapter);

        // init header
        TextView txtTitle = (TextView) findViewById(R.id.txt_header3_title);
        txtTitle.setText(getResources().getString(R.string.reply));
        TextView txtReply = (TextView) findViewById(R.id.txt_total_reply);
        txtReply.setText(getResources().getString(R.string.context));

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

            }
        });
    }
}
