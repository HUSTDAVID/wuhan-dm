
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.FavoriteNews;
import com.wh.dm.type.PostResult;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.widget.CollectAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CollectNewsActivity extends Activity {

    private ProgressDialog progressDialog;
    private ListView collect_list;
   
    private View footer;
    private Button btnFoolter;

    private LayoutInflater mInfalater;
    private DatabaseImpl databaseImpl;
    private int curPage = 1;
    private final int countsPerPage = 12;
    private boolean FLAG_PAGE_UP = false;
    private boolean isFirstLauncher = true;// load local data
    
    private static int MSG_GET_FAV = 0;
    private static int MSG_DEL_FAV = 1;
    private WH_DMApi wh_dmApi;
    private WH_DMApp wh_dmApp;
    private ArrayList<FavoriteNews> favList = null;
    CollectAdapter adapter = null;
    private GetFavTask getFavTask = null;
    private DelFavTask delFavTask = null;
    public static boolean isNewCollect=false;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == MSG_GET_FAV) {
                if (getFavTask != null) {
                    getFavTask.cancel(true);
                    getFavTask = null;
                }
                getFavTask = new GetFavTask();
                getFavTask.execute(countsPerPage, curPage);// param1:counts per
                                                           // page;
                                                           // param2:current
                                                           // page
            }
            if (msg.what == MSG_DEL_FAV) {
                if (delFavTask != null) {
                    delFavTask.cancel(true);
                    delFavTask = null;
                }
                delFavTask = new DelFavTask();
                delFavTask.execute();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        setContentView(R.layout.activity_dm_collect);

        collect_list = (ListView) findViewById(R.id.list);
        adapter = new CollectAdapter(this);
        mInfalater = getLayoutInflater();
        footer = mInfalater.inflate(R.layout.news_list_footer, null);
        btnFoolter = (Button) footer.findViewById(R.id.btn_news_footer);
        btnFoolter.setText("查看下"+countsPerPage+"条");
        btnFoolter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FLAG_PAGE_UP = true;
                curPage++;
                handler.sendEmptyMessage(MSG_GET_FAV);
            }
        });
        collect_list.addFooterView(footer);

        //btnDel = (ImageButton) findViewById(R.id.DeleteButton);
        CollectMainActivity.btnDel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                if (adapter.getCheckedID() == null || adapter.getCheckedID().size() == 0) {
                    return;
                }
                if (!WH_DMApp.isConnected) {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            CollectNewsActivity.this);
                    return;
                }
                if (!WH_DMApp.isLogin) {
                    NotificationUtil.showShortToast(getString(R.string.please_login),
                            CollectNewsActivity.this);
                    return;
                }
                handler.sendEmptyMessage(MSG_DEL_FAV);
            }
        });
     

        progressDialog = new ProgressDialog(CollectNewsActivity.this.getParent());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        wh_dmApp = (WH_DMApp) this.getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        databaseImpl = wh_dmApp.getDatabase();

        
         handler.sendEmptyMessage(MSG_GET_FAV);
      

    }

    @Override
    public void onResume() {
    	
    	  CollectMainActivity.btnDel.setOnClickListener(new View.OnClickListener() {

              @Override
              public void onClick(View v) {

                  // TODO Auto-generated method stub
                  if (adapter.getCheckedID() == null || adapter.getCheckedID().size() == 0) {
                      return;
                  }
                  if (!WH_DMApp.isConnected) {
                      NotificationUtil.showShortToast(getString(R.string.check_network),
                              CollectNewsActivity.this);
                      return;
                  }
                  if (!WH_DMApp.isLogin) {
                      NotificationUtil.showShortToast(getString(R.string.please_login),
                              CollectNewsActivity.this);
                      return;
                  }
                  handler.sendEmptyMessage(MSG_DEL_FAV);
              }
          });
    	  
    	if(isNewCollect){
    		handler.sendEmptyMessage(MSG_GET_FAV);
    		isNewCollect=false;
    	}
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {

        if (getFavTask != null) {
            getFavTask.cancel(true);
            getFavTask = null;
        }
        if (delFavTask != null) {
            delFavTask.cancel(true);
            delFavTask = null;
        }
        adapter.RemoveAllInCheckedList();
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private class GetFavTask extends AsyncTask<Integer, Void, ArrayList<FavoriteNews>> {
        ArrayList<FavoriteNews> result = null;
        Exception reason = null;

        @Override
        protected void onPreExecute() {

            // get local database data
            if (isFirstLauncher) {
                favList = databaseImpl.getNewsFavorite();
                if (favList != null & favList.size() > 0) {
                    adapter.setList(favList);
                    collect_list.setAdapter(adapter);
                    /*collect_list.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                long arg3) {

                            // TODO Auto-generated method stub
                            Intent intent = new Intent(CollectNewsActivity.this,
                                    NewsDetailsActivity.class);
                            intent.putExtra("id", adapter.getList().get(position).getId());
                            startActivity(intent);
                        }

                    });*/
                }
                isFirstLauncher = false;
            }
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<FavoriteNews> doInBackground(Integer... params) {

            // TODO Auto-generated method stub
            try {
                if (WH_DMApp.isLogin&&WH_DMApp.isConnected)
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
                            CollectNewsActivity.this);
                    FLAG_PAGE_UP = false;
                } else {
                    if (WH_DMApp.isConnected) {
                        if (!WH_DMApp.isLogin)
                            NotificationUtil.showShortToast(getString(R.string.please_login),
                                    CollectNewsActivity.this);
                        else{
                            NotificationUtil.showShortToast("没有数据", CollectNewsActivity.this);
                            databaseImpl.deleteNewsFavorite();
                            adapter.setList(new ArrayList<FavoriteNews>());
                            collect_list.setAdapter(adapter);
                        }
                    } else
                        NotificationUtil.showShortToast(getString(R.string.check_network),
                                CollectNewsActivity.this);
                }

            }
            // get new data from network
            else {
                if (FLAG_PAGE_UP) {
                    adapter.addList(result);
                    FLAG_PAGE_UP = false;
                } else {
                    databaseImpl.deleteNewsFavorite();
                    adapter.setList(result);
                }
                databaseImpl.addNewsFavorite(result);
                collect_list.setAdapter(adapter);
                /*collect_list.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                        // TODO Auto-generated method stub
                        Intent intent = new Intent(CollectNewsActivity.this, NewsDetailsActivity.class);
                        intent.putExtra("id", adapter.getList().get(position).getId());
                        startActivity(intent);
                    }
                });*/

            }

            progressDialog.dismiss();
            super.onPostExecute(result);
        }
    }

    private class DelFavTask extends AsyncTask<Void, Void, Boolean> {
        ArrayList<FavoriteNews> delSuccessList;
        int delFailCount;
        Exception ex;

        @Override
        protected void onPreExecute() {

            delSuccessList = new ArrayList<FavoriteNews>();
            delFailCount = 0;
            favList = adapter.getList();
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            // TODO Auto-generated method stub
            try {
                List<Integer> list_nid = adapter.getCheckedID();
                for (int i = 0; i < list_nid.size(); i++) {
                    if (wh_dmApi.delFav(favList.get(list_nid.get(i)).getId(),0)) {
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
                    NotificationUtil.showShortToast("删除成功", CollectNewsActivity.this);
                else {
                    NotificationUtil.showLongToast(delFailCount + "条删除失败", CollectNewsActivity.this);
                }
            } else {
                NotificationUtil.showLongToast("删除失败" + ex.toString(), CollectNewsActivity.this);
            }
            adapter.RemoveAllInCheckedList();
            adapter.setList(favList);
            collect_list.setAdapter(adapter);
           /* collect_list.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                    // TODO Auto-generated method stub
                    Intent intent = new Intent(CollectNewsActivity.this, NewsDetailsActivity.class);
                    intent.putExtra("id", adapter.getList().get(position).getId());
                    startActivity(intent);
                }

            });*/
            progressDialog.dismiss();
            super.onPostExecute(result);
        }

    }
}
