
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.FavoriteNews;
import com.wh.dm.type.PostMessage;
import com.wh.dm.widget.MergeAdapter;
import com.wh.dm.widget.PostMessageAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageActivity extends Activity {

    private TextView txtHead;
    private ImageButton btnBack;
    private WH_DMApp wh_dmApp;
    private DatabaseImpl databaseImpl;
    private View headerPush;
    private View headerCollect;
    private ListView listview;
    private ArrayList<PostMessage> messages = null;
    private PostMessageAdapter messageAdapter;
    private ArrayList<FavoriteNews> favList = null;
    private MergeAdapter adapter = null;
    private LayoutInflater inflater;
    private int messageTotle = 0;
    private int colTotle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        setContentView(R.layout.activity_message);

        init();

    }

    private void init() {

        wh_dmApp = (WH_DMApp) getApplication();
        databaseImpl = wh_dmApp.getDatabase();
        messages = databaseImpl.getPostMessage();
        if (messages != null) {
            messageTotle = messages.size();
        }
        listview = (ListView) findViewById(R.id.lv_message_colect);
        inflater = getLayoutInflater();
        adapter = new MergeAdapter();

        txtHead = (TextView) findViewById(R.id.textView3);
        txtHead.setText(getString(R.string.message_collect));
        btnBack = (ImageButton) findViewById(R.id.BackButton);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                MessageActivity.this.finish();

            }

        });

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Toast.makeText(MessageActivity.this,
                // String.valueOf(position), Toast.LENGTH_SHORT)
                // .show();

            }
        });
        addData();

    }

    private void addData() {

        // add push title
        headerPush = (View) inflater.inflate(R.layout.header_push_collect, null);
        TextView pushTitle = (TextView) headerPush.findViewById(R.id.txt_title);
        pushTitle.setText("推送消息");
        adapter.addView(headerPush);

        // add message
        messageAdapter = new PostMessageAdapter(this);
        if (messages != null) {
            messageAdapter.setList(messages);
        }
        adapter.addAdapter(messageAdapter);

        // add collect title
        headerCollect = (View) inflater.inflate(R.layout.header_push_collect, null);
        TextView colTitle = (TextView) headerCollect.findViewById(R.id.txt_title);
        colTitle.setText("我的收藏");
        adapter.addView(headerCollect);

        // add collect

        // set adapter
        listview.setAdapter(adapter);
    }

}
