
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.PictureMagzine;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.UrlImageViewHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DM_MZinePicsActivity extends Activity {

    private ViewPager v_Pager;
    // private ProgressDialog progressDialog;
    private LinearLayout layout_load;
    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private DatabaseImpl databaseImpl;
    private ArrayList<View> views;
    private int totalPage = 1;
    private int curPage = 1;
    private TextView txtPage;
    private MyPagerAdapter adapter;
    private LayoutInflater inflater;
    private GetPicsTask getPicsTask = null;
    private static final int MSG_GET_PICS = 0;
    private int sid;
    private boolean isLoad = false;
    private TextView txtDes;
    private ImageView imgArrow;
    private ImageView imgComment;
    private ArrayList<PictureMagzine> magazines;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == MSG_GET_PICS) {
                if (getPicsTask != null) {
                    getPicsTask.cancel(true);
                    getPicsTask = null;
                }
                getPicsTask = new GetPicsTask();
                getPicsTask.execute(sid);

            } else if (DownloadActivity.MSG_LOAD_ONE_IMAGE == msg.what) {
                UrlImageViewHelper.isLoad = false;
                layout_load.setVisibility(View.GONE);
                ;
            }
        };
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        setContentView(R.layout.activity_dm_mzine_fullscreen);
        init();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        sid = bundle.getInt("sid");
        isLoad = databaseImpl.isLoad(sid);
        handler.sendEmptyMessage(MSG_GET_PICS);

    }

    public void init() {

        layout_load = (LinearLayout) findViewById(R.id.picture_load);

        txtPage = (TextView) findViewById(R.id.txt_magazine_page);
        txtDes = (TextView) findViewById(R.id.txt_mzine_des);
        imgArrow = (ImageView) findViewById(R.id.img_mzine_arrow);
        imgComment = (ImageView) findViewById(R.id.img_mzine_comment);

        v_Pager = (ViewPager) findViewById(R.id.v_Pager);
        adapter = new MyPagerAdapter();
        v_Pager.setAdapter(adapter);
        v_Pager.setOnPageChangeListener(new GuidePageChangeListener());
        inflater = getLayoutInflater();
        views = new ArrayList<View>();
        wh_dmApp = (WH_DMApp) getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        databaseImpl = wh_dmApp.getDatabase();

        v_Pager.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return false;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (imgComment.getVisibility() == View.GONE) {
                        imgComment.setVisibility(View.VISIBLE);
                    } else {
                        imgComment.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });

        imgArrow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (magazines != null && magazines.get(curPage - 1).getDescription() != null
                        && magazines.get(curPage - 1).getDescription().length() > 0) {
                    if (txtDes.getVisibility() == View.VISIBLE) {
                        txtDes.setVisibility(View.GONE);
                        imgArrow.setImageResource(R.drawable.mzine_arrow_up);
                    } else {
                        txtDes.setVisibility(View.VISIBLE);
                        imgArrow.setImageResource(R.drawable.mzine_arrow_down);
                    }
                } else {
                    txtDes.setVisibility(View.GONE);
                    imgArrow.setImageResource(R.drawable.mzine_arrow_up);
                }

            }
        });

        imgComment.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (magazines != null) {
                    Intent intent = new Intent(DM_MZinePicsActivity.this,
                            NewsMoreReplyActivity.class);
                    intent.putExtra("id", magazines.get(curPage - 1).getId());
                    startActivity(intent);
                } else {
                    NotificationUtil.showShortToast("没有新刊", DM_MZinePicsActivity.this);
                }
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

    public void addData(ArrayList<PictureMagzine> magazines) {

        for (PictureMagzine magazine : magazines) {
            View view = inflater.inflate(R.layout.magzine2_style, null);
            views.add(view);
        }
        UrlImageViewHelper.isLoad = true;
        ImageView img = (ImageView) views.get(0).findViewById(R.id.img);
        UrlImageViewHelper.sendFinishMsg2(img, WH_DMHttpApiV1.URL_DOMAIN
                + magazines.get(0).getPic(), handler);
        v_Pager.setCurrentItem(0);
        totalPage = views.size();
        txtPage.setText(curPage + "/" + totalPage);
        txtDes.setText(magazines.get(curPage - 1).getDescription());
        adapter.setList(views);
    }

    public class MyPagerAdapter extends PagerAdapter {

        private ArrayList<View> mListView;

        public MyPagerAdapter() {

        }

        private MyPagerAdapter(ArrayList<View> list) {

            // TODO Auto-generated method stub
            this.mListView = list;
        }

        public void setList(ArrayList<View> list) {

            this.mListView = list;
            notifyDataSetChanged();
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {

            // TODO Auto-generated method stub
            ((ViewGroup) arg0).removeView(mListView.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {

            // TODO Auto-generated method stub

        }

        @Override
        public int getCount() {

            if (mListView == null) {
                return 0;
            } else {
                return mListView.size();
            }
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {

            // TODO Auto-generated method stub
            if (mListView != null) {
                ((ViewGroup) arg0).addView(mListView.get(arg1), 0);
                return mListView.get(arg1);
            } else {
                return null;
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            // TODO Auto-generated method stub
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {

            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

            // TODO Auto-generated method stub

        }

    }

    public class GetPicsTask extends AsyncTask<Integer, Void, ArrayList<PictureMagzine>> {
        Exception reason = null;

        @Override
        protected void onPreExecute() {

            if (!isLoad) {
                // progressDialog.show();
                layout_load.setVisibility(View.VISIBLE);
            }
            super.onPreExecute();
        }

        @Override
        protected ArrayList<PictureMagzine> doInBackground(Integer... params) {

            try {
                if (!isLoad) {
                    magazines = wh_dmApi.getPictureMagzine(params[0]);
                } else {
                    magazines = databaseImpl.getMagazinePic(sid);
                }
                return magazines;
            } catch (Exception e) {
                reason = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<PictureMagzine> result) {

            if (result != null && result.size() > 0) {
                addData(result);
            } else {
                NotificationUtil.showShortToast("没有新刊", DM_MZinePicsActivity.this);
            }

            super.onPostExecute(result);
        }
    }

    private class GuidePageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {

            UrlImageViewHelper.isLoad = true;
            ImageView img = (ImageView) views.get(arg0).findViewById(R.id.img);
            UrlImageViewHelper.setUrlDrawable(img, WH_DMHttpApiV1.URL_DOMAIN
                    + magazines.get(arg0).getPic());
            curPage = 1 + arg0;
            txtPage.setText(curPage + "/" + totalPage);
            txtDes.setText(magazines.get(arg0).getDescription());
            imgComment.setVisibility(View.GONE);
            if (!isLoad) {
                layout_load.setVisibility(View.VISIBLE);
            }

        }
    }

}
