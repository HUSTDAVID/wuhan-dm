
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.FavoriteNews;
import com.wh.dm.type.PostMessage;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.widget.CollectAdapter;
import com.wh.dm.widget.MergeAdapter;
import com.wh.dm.widget.PostMessageAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends Activity {

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
    private ArrayList<FavoriteNews> favList = null;
    private MergeAdapter adapter = null;
    private LayoutInflater inflater;
    private int messageTotle = 0;
    private int colTotle = 0;
    private WH_DMApi wh_dmApi;
    private GetNewsFavTask getNewsFavTask = null;
    private DelNewsFavTask delNewsFavTask = null;
    private static int MSG_GET_FAV = 0;
    private static int MSG_DEL_FAV = 1;
    private ProgressDialog progressDialog;
    private int countsPerPage = 12;
    private int curPage = 1;
    private boolean FLAG_PAGE_UP = false;
    public static boolean refreshCollect = false;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == MSG_GET_FAV) {
                if (getNewsFavTask != null) {
                    getNewsFavTask.cancel(true);
                    getNewsFavTask = null;
                }
                getNewsFavTask = new GetNewsFavTask();
                getNewsFavTask.execute(countsPerPage, curPage);// param1:counts
                                                               // per page;
                // param2:current page
            }
            if (msg.what == MSG_DEL_FAV) {
                if (delNewsFavTask != null) {
                    delNewsFavTask.cancel(true);
                    delNewsFavTask = null;
                }
                delNewsFavTask = new DelNewsFavTask();
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
    protected void onResume(){
    	super.onResume();
    	MobclickAgent.onError(this);
    	if(refreshCollect){
    		if (WH_DMApp.isLogin && WH_DMApp.isConnected)
    	        handler.sendEmptyMessage(MSG_GET_FAV);
    		refreshCollect = false;
    	}
    }

    private void init() {

        wh_dmApp = (WH_DMApp) getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        databaseImpl = wh_dmApp.getDatabase();
        messages = databaseImpl.getPostMessage();
        if (messages != null) {
            messageTotle = messages.size();
        }
        listview = (ListView) findViewById(R.id.lv_message_colect);
        inflater = getLayoutInflater();

        View footer = inflater.inflate(R.layout.news_list_footer, null);
        Button btnFoolter = (Button) footer.findViewById(R.id.btn_news_footer);
        btnFoolter.setText("查看下" + countsPerPage + "条");
        btnFoolter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!WH_DMApp.isConnected) {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            MessageActivity.this);
                    return;
                }
                if (!WH_DMApp.isLogin) {
                    NotificationUtil.showShortToast(getString(R.string.please_login),
                            MessageActivity.this);
                    return;
                }
                if (WH_DMApp.isLogin && WH_DMApp.isConnected) {
                    FLAG_PAGE_UP = true;
                    curPage++;
                    handler.sendEmptyMessage(MSG_GET_FAV);
                }
            }
        });
        listview.addFooterView(footer);

        adapter = new MergeAdapter();

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

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
        if (WH_DMApp.isLogin && WH_DMApp.isConnected)
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
                if (!WH_DMApp.isConnected) {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            MessageActivity.this);
                    return;
                }
                if (!WH_DMApp.isLogin) {
                    NotificationUtil.showShortToast(getString(R.string.please_login),
                            MessageActivity.this);
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
        if (messages != null) {
            messageAdapter.setList(messages);
        }
        adapter.addAdapter(messageAdapter);

        // add collect title
        headerCollect = (View) inflater.inflate(R.layout.header_push_collect, null);
        TextView colTitle = (TextView) headerCollect.findViewById(R.id.txt_title);
        colTitle.setText(getResources().getString(R.string.collections));
        adapter.addView(headerCollect);

        // add local collect
        newsCollectAdapter = new CollectAdapter(this);
        ArrayList<FavoriteNews> collectNews = databaseImpl.getNewsFavorite();
        if (collectNews != null) {
            newsCollectAdapter.setList(collectNews);
        }
        adapter.addAdapter(newsCollectAdapter);

        // set adapter
        listview.setAdapter(adapter);
    }

    private class GetNewsFavTask extends AsyncTask<Integer, Void, ArrayList<FavoriteNews>> {
        ArrayList<FavoriteNews> result = null;
        Exception reason = null;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected ArrayList<FavoriteNews> doInBackground(Integer... params) {

            // TODO Auto-generated method stub
            try {
                result = wh_dmApi.getNewsFav(params[0], params[1]);
                return result;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<FavoriteNews> result) {

            // no result
            if (result == null || result.size() == 0) {
                if (FLAG_PAGE_UP) {
                    NotificationUtil.showShortToast(getString(R.string.no_more_message),
                            MessageActivity.this);
                    FLAG_PAGE_UP = false;
                } else {
                    databaseImpl.deleteNewsFavorite();
                    newsCollectAdapter.setList(new ArrayList<FavoriteNews>());
                }
            }
            // get new data from network
            else {
                if (FLAG_PAGE_UP) {
                    databaseImpl.addNewsFavorite(result);
                    newsCollectAdapter.addList(result);
                    FLAG_PAGE_UP = false;
                } else {
                    databaseImpl.deleteNewsFavorite();
                    databaseImpl.addNewsFavorite(result);
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

    private class DelNewsFavTask extends AsyncTask<Void, Void, Boolean> {
        ArrayList<FavoriteNews> delSuccessList;
        int delFailCount;
        Exception ex;

        @Override
        protected void onPreExecute() {

            delSuccessList = new ArrayList<FavoriteNews>();
            delFailCount = 0;
            favList = newsCollectAdapter.getList();

            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            // TODO Auto-generated method stub
            try {
                List<Integer> list_nid = newsCollectAdapter.getCheckedID();
                for (int i = 0; i < list_nid.size(); i++) {
                    if (wh_dmApi.delFav(favList.get(list_nid.get(i)).getId(), 0)) {
                        delSuccessList.add(favList.get(list_nid.get(i)));
                        databaseImpl.deleteOneNewsFavorite(favList.get(list_nid.get(i)).getId());
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
                if (delFailCount == 0)
                    NotificationUtil.showShortToast("删除成功", MessageActivity.this);
                else {
                    NotificationUtil.showLongToast(delFailCount + "条删除失败", MessageActivity.this);
                }
            } else {
                NotificationUtil.showLongToast("删除失败" + ex.toString(), MessageActivity.this);
            }
            newsCollectAdapter.RemoveAllInCheckedList();
            newsCollectAdapter.setList(favList);

            progressDialog.dismiss();
            super.onPostExecute(result);
        }

    }
}
