
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.Favorite;
import com.wh.dm.type.PostMessage;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.widget.CollectAdapter;
import com.wh.dm.widget.MergeAdapter;
import com.wh.dm.widget.PostMessageAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends Activity {

    public static final int MSG_REQUEST_CODE = 1001;
    private TextView txtHead;
    private ImageButton btnBack;
    private ImageButton btnDel;
    private WH_DMApp wh_dmApp;
    private DatabaseImpl databaseImpl;
    private View headerPush;
    private View headerCollect;
    private ListView listview;
    private ArrayList<PostMessage> messages = null;
    private PostMessageAdapter messageAdapter;
    private CollectAdapter newsCollectAdapter;
    private ArrayList<Favorite> favList = null;
    private MergeAdapter adapter = null;
    private LayoutInflater inflater;
    private LinearLayout layoutLoad;
    private View footer;
    private WH_DMApi wh_dmApi;
    private GetFavTask getNewsFavTask = null;
    private DelFavTask delNewsFavTask = null;
    private static int MSG_GET_FAV = 0;
    private static int MSG_DEL_FAV = 1;
    private int countsPerPage = 10;
    private int curPage = 1;
    private boolean FLAG_PAGE_UP = false;
    private boolean FLAG_GET_MESSAGE = true;
    public static boolean refreshCollect = false;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == MSG_GET_FAV) {
                if (getNewsFavTask != null) {
                    getNewsFavTask.cancel(true);
                    getNewsFavTask = null;
                }
                getNewsFavTask = new GetFavTask();
                getNewsFavTask.execute(countsPerPage, curPage);// param1:counts
                                                               // per page;
                // param2:current page
            }
            if (msg.what == MSG_DEL_FAV) {
                if (delNewsFavTask != null) {
                    delNewsFavTask.cancel(true);
                    delNewsFavTask = null;
                }
                delNewsFavTask = new DelFavTask();
                delNewsFavTask.execute();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        setContentView(R.layout.activity_message);

        init();

    }

    @Override
    protected void onResume() {

        super.onResume();
        MobclickAgent.onError(this);
        if (refreshCollect) {
            if (WH_DMApp.isLogin && wh_dmApp.isConnected())
                handler.sendEmptyMessage(MSG_GET_FAV);
            refreshCollect = false;
        }
    }

    private void init() {

        wh_dmApp = (WH_DMApp) getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        databaseImpl = wh_dmApp.getDatabase();
        // TODO
        // messages = databaseImpl.getPostMessage();
        layoutLoad = (LinearLayout) findViewById(R.id.layout_load);
        listview = (ListView) findViewById(R.id.lv_message_colect);
        inflater = getLayoutInflater();

        footer = inflater.inflate(R.layout.news_list_footer, null);
        Button btnFoolter = (Button) footer.findViewById(R.id.btn_news_footer);
        btnFoolter.setText("查看下" + countsPerPage + "条");
        btnFoolter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!wh_dmApp.isConnected()) {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            MessageActivity.this);
                    return;
                }
                if (!WH_DMApp.isLogin) {
                    NotificationUtil.showShortToast(getString(R.string.please_login),
                            MessageActivity.this);
                    Intent intent = new Intent(MessageActivity.this, LoginActivity.class);
                    startActivityForResult(intent, MSG_REQUEST_CODE);
                    return;
                }
                if (WH_DMApp.isLogin && wh_dmApp.isConnected()) {
                    FLAG_PAGE_UP = true;
                    curPage++;
                    handler.sendEmptyMessage(MSG_GET_FAV);
                }
            }
        });
        listview.addFooterView(footer);

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

        // get collect from server
        if (WH_DMApp.isLogin && wh_dmApp.isConnected())
            handler.sendEmptyMessage(MSG_GET_FAV);

        btnDel = (ImageButton) findViewById(R.id.DeleteButton);
        btnDel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                if (newsCollectAdapter.getCheckedID() == null
                        || newsCollectAdapter.getCheckedID().size() == 0) {
                    return;
                }
                if (!wh_dmApp.isConnected()) {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            MessageActivity.this);
                    return;
                }
                if (!WH_DMApp.isLogin) {
                    NotificationUtil.showShortToast(getString(R.string.please_login),
                            MessageActivity.this);
                    Intent intent = new Intent(MessageActivity.this, LoginActivity.class);
                    startActivityForResult(intent, MSG_REQUEST_CODE);
                    return;
                }
                handler.sendEmptyMessage(MSG_DEL_FAV);
            }
        });
    }

    private void addData() {

        // add push title
        headerPush = (View) inflater.inflate(R.layout.header_push_collect, null);
        TextView pushTitle = (TextView) headerPush.findViewById(R.id.txt_title);
        pushTitle.setText(getResources().getString(R.string.push_message));
        adapter.addView(headerPush);

        // add message
        messageAdapter = new PostMessageAdapter(this);

        adapter.addAdapter(messageAdapter);

        // add collect title
        headerCollect = (View) inflater.inflate(R.layout.header_push_collect, null);
        TextView colTitle = (TextView) headerCollect.findViewById(R.id.txt_title);
        colTitle.setText(getResources().getString(R.string.collections));
        adapter.addView(headerCollect);

        // add local collect
        newsCollectAdapter = new CollectAdapter(this);
        ArrayList<Favorite> collectNews = databaseImpl.getFavorite();
        if (collectNews != null) {
            newsCollectAdapter.setList(collectNews);
        } else {
            newsCollectAdapter.setList(new ArrayList<Favorite>());
            listview.removeFooterView(footer);
        }
        adapter.addAdapter(newsCollectAdapter);

        // set adapter
        listview.setAdapter(adapter);
    }

    private class GetFavTask extends AsyncTask<Integer, Void, ArrayList<Favorite>> {
        ArrayList<Favorite> result = null;
        Exception reason = null;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected ArrayList<Favorite> doInBackground(Integer... params) {

            try {
                if (FLAG_GET_MESSAGE) {
                    messages = wh_dmApi.getMessage();
                }
                result = wh_dmApi.getFav(params[0], params[1]);
                return result;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Favorite> result) {

            if (messages != null) {
                messageAdapter.setList(messages);
                FLAG_GET_MESSAGE = false;
            }
            // no result
            if (result == null || result.size() == 0) {
                if (FLAG_PAGE_UP) {
                    NotificationUtil.showShortToast(getString(R.string.no_more_collect),
                            MessageActivity.this);
                    FLAG_PAGE_UP = false;
                } else {
                    databaseImpl.deleteFavorite();
                    newsCollectAdapter.setList(new ArrayList<Favorite>());
                }
            }
            // get new data from network
            else {
                if (FLAG_PAGE_UP) {
                    databaseImpl.addFavorite(result);
                    newsCollectAdapter.addList(result);
                    FLAG_PAGE_UP = false;
                } else {
                    databaseImpl.deleteFavorite();
                    databaseImpl.addFavorite(result);
                    newsCollectAdapter.setList(result);
                }

                /*
                 * adapter.addAdapter(newsCollectAdapter);
                 * listview.setAdapter(adapter);
                 */
            }
            super.onPostExecute(result);
        }
    }

    private class DelFavTask extends AsyncTask<Void, Void, Boolean> {
        ArrayList<Favorite> delSuccessList;
        int delFailCount;
        Exception ex;

        @Override
        protected void onPreExecute() {

            delSuccessList = new ArrayList<Favorite>();
            delFailCount = 0;
            favList = newsCollectAdapter.getList();

            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            // TODO Auto-generated method stub
            try {
                List<Integer> list_nid = newsCollectAdapter.getCheckedID();
                for (int i = 0; i < list_nid.size(); i++) {
                    Favorite collect = favList.get(list_nid.get(i));
                    if (wh_dmApi.delFav(collect.getNid(), collect.getType())) {
                        delSuccessList.add(collect);
                        databaseImpl.deleteOneFavorite(collect.getNid());
                    } else {
                        delFailCount++;
                    }
                }
                favList.removeAll(delSuccessList);
                return true;
            } catch (Exception e) {
                ex = e;
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {
                NotificationUtil.showShortToast("删除成功", MessageActivity.this);
                /*
                 * if (delFailCount == 0)
                 * NotificationUtil.showShortToast("删除成功",
                 * MessageActivity.this); else {
                 * NotificationUtil.showLongToast(delFailCount + "条删除失败",
                 * MessageActivity.this); }
                 */
            } else {
                NotificationUtil.showLongToast("删除失败" + ex.toString(), MessageActivity.this);
            }
            newsCollectAdapter.RemoveAllInCheckedList();
            newsCollectAdapter.setList(favList);

            layoutLoad.setVisibility(View.GONE);
            super.onPostExecute(result);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MSG_REQUEST_CODE) {
            if (WH_DMApp.isLogin && wh_dmApp.isConnected())
                handler.sendEmptyMessage(MSG_GET_FAV);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
