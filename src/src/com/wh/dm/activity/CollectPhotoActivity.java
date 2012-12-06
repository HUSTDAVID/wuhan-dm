package com.wh.dm.activity;

import java.util.ArrayList;
import java.util.List;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMApi;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.FavoritePhoto;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.widget.PhotoCollectAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CollectPhotoActivity extends Activity {
	private ProgressDialog progressDialog;
	private ListView lv_photo;
	private WH_DMApp wh_dmApp;
	private WH_DMApi wh_dmApi;
	private PhotoCollectAdapter adapter;
	 private DatabaseImpl databaseImp1;
	 private boolean isFirstLauncher=true;
	 private boolean FLAG_PAGE_UP=false;
	 private Button btnFoolter;
	 private View footer;
	 private LayoutInflater mInfalater;
	 private int MSG_GET_FAV=0;
	 private int MSG_DEL_FAV=1;
	 private GetFavTask getFavTask=null;
	 private DelFavTask delFavTask=null;
	 private int countsPerPage=12;
	 private int curPage=1;
	 private ArrayList<FavoritePhoto> favList = null;
	 public final Handler handler = new Handler() {
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
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	        MobclickAgent.onError(this);
	        setContentView(R.layout.activity_dm_collect);

	        lv_photo = (ListView) findViewById(R.id.list);
	        wh_dmApp = (WH_DMApp) this.getApplication();
	        wh_dmApi = wh_dmApp.getWH_DMApi();
	        databaseImp1 = wh_dmApp.getDatabase();
	        
	        progressDialog = new ProgressDialog(CollectPhotoActivity.this.getParent());
	        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        
	        adapter = new PhotoCollectAdapter(this);
	        
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
	        lv_photo.addFooterView(footer);
	        
	        

	        
	        handler.sendEmptyMessage(MSG_GET_FAV);
	}
	
	@Override
	public void onResume(){
		
		CollectMainActivity.btnDel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                if (adapter.getCheckedID() == null || adapter.getCheckedID().size() == 0) {
                    return;
                }
                if (!WH_DMApp.isConnected) {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            CollectPhotoActivity.this);
                    return;
                }
                if (!WH_DMApp.isLogin) {
                    NotificationUtil.showShortToast(getString(R.string.please_login),
                    		CollectPhotoActivity.this);
                    return;
                }
                handler.sendEmptyMessage(MSG_DEL_FAV);
            }
        });
		
		super.onResume();
		MobclickAgent.onPause(this);
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
	
	 
	 private class GetFavTask extends AsyncTask<Integer, Void, ArrayList<FavoritePhoto>> {
	        ArrayList<FavoritePhoto> result = null;
	        Exception reason = null;

	        @Override
	        protected void onPreExecute() {

	            // get local database data
	            if (isFirstLauncher) {
	                favList = databaseImp1.getPhotoFavorite();
	                if (favList != null & favList.size() > 0) {
	                    adapter.setList(favList);
	                    lv_photo.setAdapter(adapter);
	                }
	                isFirstLauncher = false;
	            }
	            progressDialog.show();
	            super.onPreExecute();
	        }

	        @Override
	        protected ArrayList<FavoritePhoto> doInBackground(Integer... params) {

	            // TODO Auto-generated method stub
	            try {
	                if (WH_DMApp.isLogin&&WH_DMApp.isConnected)
	                    result = wh_dmApi.getPhotoFav(params[0], params[1]);
	                return result;
	            } catch (Exception e) {
	                reason = e;
	                e.printStackTrace();
	                return null;
	            }
	        }

	        @Override
	        protected void onPostExecute(ArrayList<FavoritePhoto> result) {
	            if (result == null || result.size() == 0) {
	            	
	                if (FLAG_PAGE_UP) {
	                    NotificationUtil.showShortToast(getString(R.string.no_more_message),
	                            CollectPhotoActivity.this);
	                    FLAG_PAGE_UP = false;
	                } else {
	                    if (WH_DMApp.isConnected) {
	                        if (!WH_DMApp.isLogin)
	                            NotificationUtil.showShortToast(getString(R.string.please_login),
	                                    CollectPhotoActivity.this);
	                        else{
	                            NotificationUtil.showShortToast("没有数据", CollectPhotoActivity.this);
	                            databaseImp1.deletePhotoFavorite();//if has no data,clear local database
	                            }
	                    } else
	                        NotificationUtil.showShortToast(getString(R.string.check_network),
	                        		CollectPhotoActivity.this);
	                }

	            }
	            // get new data from network
	            else {
	                if (FLAG_PAGE_UP) {
	                    adapter.addList(result);
	                    FLAG_PAGE_UP = false;
	                } else {
	                	databaseImp1.deletePhotoFavorite();
	                    adapter.setList(result);
	                }
	                databaseImp1.addPhotoFavorite(result);
	                lv_photo.setAdapter(adapter);

	            }

	            progressDialog.dismiss();
	            super.onPostExecute(result);
	        }
	    }

	 private class DelFavTask extends AsyncTask<Void, Void, Boolean> {
	        ArrayList<FavoritePhoto> delSuccessList;
	        int delFailCount;
	        Exception ex;

	        @Override
	        protected void onPreExecute() {

	            delSuccessList = new ArrayList<FavoritePhoto>();
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
	                    if (wh_dmApi.delFav(favList.get(list_nid.get(i)).getAid(),1)) {
	                        delSuccessList.add(favList.get(list_nid.get(i)));
	                        databaseImp1.deleteOnePhotoFavorite(favList.get(list_nid.get(i)).getAid());
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
	                    NotificationUtil.showShortToast("删除成功", CollectPhotoActivity.this);
	                else {
	                    NotificationUtil.showLongToast(delFailCount + "条删除失败", CollectPhotoActivity.this);
	                }
	            } else {
	                NotificationUtil.showLongToast("删除失败" + ex.toString(), CollectPhotoActivity.this);
	            }
	            adapter.RemoveAllInCheckedList();
	            adapter.setList(favList);
	            lv_photo.setAdapter(adapter);
	           
	            progressDialog.dismiss();
	            super.onPostExecute(result);
	        }

	    }
}
