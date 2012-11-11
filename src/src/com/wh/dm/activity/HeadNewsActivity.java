
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.PicWithTxtNews;
import com.wh.dm.type.PicsNews;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.UrlImageViewHelper;
import com.wh.dm.widget.HeadlineAdapter;
import com.wh.dm.widget.HorizontalPager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HeadNewsActivity extends Activity implements OnClickListener {

    private final String URL_DOMAIN = "http://test1.jbr.net.cn:809";
    private String[] titles;
    private int currentItem = 0;
    private View headerView;
    private LayoutInflater mInfalater;
    private ListView listView;
    private View footer;
    private Button btnFoolter;

    private final int SHOW_NEXT = 0011;
    private final boolean isRun = true;

    private HorizontalPager mPager;
    private RadioGroup mRadioGroup;
    private ImageView pic0;
    private ImageView pic1;
    private ImageView pic2;
    private ImageView pic3;

    private TextView txtNews;

    private static final int MSG_GET_PICSNEWS = 0;
    private static final int MSG_GET_HEADNEWS = 1;
    private GetPicsNewsTask getPicsNewsTask = null;
    private GetHeadNewsTask getHeadNewsTask = null;
    private ArrayList<PicsNews> picsNews = null;
    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private DatabaseImpl databaseImpl;

    ArrayList<PicWithTxtNews> savedNews = null;
    private HeadlineAdapter adapter;
    private int curPage = 1;
    private boolean FLAG_PAGE_UP = false;
    private boolean isFirstLoad = true;
    private boolean isAdapter = true;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_GET_PICSNEWS:
                    if (getPicsNewsTask != null) {
                        getPicsNewsTask.cancel(true);
                        getPicsNewsTask = null;
                    }
                    getPicsNewsTask = new GetPicsNewsTask();
                    getPicsNewsTask.execute();
                    break;
                case MSG_GET_HEADNEWS:
                    if (getHeadNewsTask != null) {
                        getHeadNewsTask.cancel(true);
                        getHeadNewsTask = null;
                    }
                    getHeadNewsTask = new GetHeadNewsTask();
                    getHeadNewsTask.execute();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_headline);
        init();
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

    private void init() {

        pic0 = (ImageView) findViewById(R.id.pic0);
        pic0.setOnClickListener(this);
        pic1 = (ImageView) findViewById(R.id.pic1);
        pic1.setOnClickListener(this);
        pic2 = (ImageView) findViewById(R.id.pic2);
        pic2.setOnClickListener(this);
        pic3 = (ImageView) findViewById(R.id.pic3);
        pic3.setOnClickListener(this);
        txtNews = (TextView) findViewById(R.id.txt_horizontal_title);

        mInfalater = getLayoutInflater();
        adapter = new HeadlineAdapter(this);
        listView = (ListView) findViewById(R.id.list);
        footer = mInfalater.inflate(R.layout.news_list_footer, null);
        btnFoolter = (Button) footer.findViewById(R.id.btn_news_footer);
        btnFoolter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FLAG_PAGE_UP = true;
                curPage++;
                handler.sendEmptyMessage(MSG_GET_HEADNEWS);
            }
        });
        listView.addFooterView(footer);
        mRadioGroup = (RadioGroup) findViewById(R.id.tabs);
        mRadioGroup.setOnCheckedChangeListener(onCheckedChangedListener);
        mPager = (HorizontalPager) findViewById(R.id.horizontal_pager);
        mPager.setOnScreenSwitchListener(onScreenSwitchListener);
        mPager.setCurrentScreen(0, true);

        wh_dmApp = (WH_DMApp) this.getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        databaseImpl = wh_dmApp.getDatabase();
        savedNews = databaseImpl.getHeadNews();
        if (savedNews != null && savedNews.size() > 0) {
            listView.setAdapter(adapter);
            adapter.setList(savedNews);
            listView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {

                    Intent intent = new Intent(HeadNewsActivity.this, NewsDetailsActivity.class);
                    intent.putExtra("id", savedNews.get(position).getId());
                    startActivity(intent);
                }

            });
            isAdapter = false;

        }
        handler.sendEmptyMessage(MSG_GET_HEADNEWS);
        handler.sendEmptyMessage(MSG_GET_PICSNEWS);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.pic0:
                if (picsNews != null) {
                    Intent intent0 = new Intent(HeadNewsActivity.this, NewsDetailsActivity.class);
                    intent0.putExtra("id", picsNews.get(0).getId());
                    startActivity(intent0);
                }
                break;
            case R.id.pic1:
                if (picsNews != null) {
                    Intent intent1 = new Intent(HeadNewsActivity.this, NewsDetailsActivity.class);
                    intent1.putExtra("id", picsNews.get(1).getId());
                    startActivity(intent1);
                }
                break;
            case R.id.pic2:
                if (picsNews != null) {
                    Intent intent2 = new Intent(HeadNewsActivity.this, NewsDetailsActivity.class);
                    intent2.putExtra("id", picsNews.get(2).getId());
                    startActivity(intent2);
                }
                break;
            case R.id.pic3:
                if (picsNews != null) {
                    Intent intent3 = new Intent(HeadNewsActivity.this, NewsDetailsActivity.class);
                    intent3.putExtra("id", picsNews.get(3).getId());
                    startActivity(intent3);
                }
                break;
            default:
                break;

        }

    }

    private class GetPicsNewsTask extends AsyncTask<Void, Void, ArrayList<PicsNews>> {
        Exception reason = null;

        @Override
        protected ArrayList<PicsNews> doInBackground(Void... arg0) {

            ArrayList<PicsNews> picsNews = null;
            try {
                picsNews = wh_dmApi.getPicsNews();
                return picsNews;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<PicsNews> result) {

            if (result != null) {
                picsNews = result;
                if (wh_dmApp.isLoadImg) {
                    UrlImageViewHelper.setUrlDrawable(pic0, URL_DOMAIN + result.get(0).getLitpic());
                    UrlImageViewHelper.setUrlDrawable(pic1, URL_DOMAIN + result.get(1).getLitpic());
                    UrlImageViewHelper.setUrlDrawable(pic2, URL_DOMAIN + result.get(2).getLitpic());
                    UrlImageViewHelper.setUrlDrawable(pic3, URL_DOMAIN + result.get(3).getLitpic());
                }
                if (thread.isAlive()) {
                    thread.destroy();
                }
                thread.start();

            }
            super.onPostExecute(result);
        }
    }

    private class GetHeadNewsTask extends AsyncTask<Void, Void, ArrayList<PicWithTxtNews>> {

        Exception reason = null;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected ArrayList<PicWithTxtNews> doInBackground(Void... params) {

            ArrayList<PicWithTxtNews> houseNews = null;
            try {
                houseNews = wh_dmApi.getHeadNews(curPage);
                return houseNews;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(final ArrayList<PicWithTxtNews> result) {

            if (result != null && result.size() > 0) {
                if (isFirstLoad) {
                    databaseImpl.deleteHeadNews();
                    isFirstLoad = false;
                }
                if (FLAG_PAGE_UP) {
                    adapter.addList(result);
                    FLAG_PAGE_UP = false;

                } else {
                    if (isAdapter) {
                        listView.setAdapter(adapter);
                        isAdapter = false;
                    }
                    adapter.setList(result);
                }

                databaseImpl.addHeadNews(result);
                listView.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long arg3) {

                        Intent intent = new Intent(HeadNewsActivity.this, NewsDetailsActivity.class);
                        intent.putExtra("id", adapter.getList().get(position).getId());
                        startActivity(intent);

                    }

                });

            } else {
                if (!FLAG_PAGE_UP) {
                    if (wh_dmApp.isConnected()) {
                        NotificationUtil.showShortToast(getString(R.string.check_network),
                                HeadNewsActivity.this);
                    } else {
                        NotificationUtil.showShortToast(getString(R.string.check_network),
                                HeadNewsActivity.this);
                    }
                } else {
                    NotificationUtil.showShortToast(getString(R.string.no_more_message),
                            HeadNewsActivity.this);
                }
            }
            super.onPostExecute(result);

        }
    }

    private final RadioGroup.OnCheckedChangeListener onCheckedChangedListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final RadioGroup group, final int checkedId) {

            // Slide to the appropriate screen when the user checks a button.
            switch (checkedId) {
                case R.id.radio_btn_0:
                    mPager.setCurrentScreen(0, true);
                    break;
                case R.id.radio_btn_1:
                    mPager.setCurrentScreen(1, true);
                    break;
                case R.id.radio_btn_2:
                    mPager.setCurrentScreen(2, true);
                    break;
                case R.id.radio_btn_3:
                    mPager.setCurrentScreen(3, true);
                default:
                    break;
            }
        }
    };

    private final HorizontalPager.OnScreenSwitchListener onScreenSwitchListener = new HorizontalPager.OnScreenSwitchListener() {
        @Override
        public void onScreenSwitched(final int screen) {

            // Check the appropriate button when the user swipes screens.
            switch (screen) {
                case 0:
                    mRadioGroup.check(R.id.radio_btn_0);
                    if (picsNews != null) {
                        txtNews.setText(picsNews.get(0).getTitle());
                    }
                    break;
                case 1:
                    mRadioGroup.check(R.id.radio_btn_1);
                    if (picsNews != null) {
                        txtNews.setText(picsNews.get(1).getTitle());
                    }
                    break;
                case 2:
                    mRadioGroup.check(R.id.radio_btn_2);
                    if (picsNews != null) {
                        txtNews.setText(picsNews.get(2).getTitle());
                    }
                    break;
                case 3:
                    mRadioGroup.check(R.id.radio_btn_3);
                    if (picsNews != null) {
                        txtNews.setText(picsNews.get(3).getTitle());
                    }
                    break;
                default:
                    break;
            }
        }

    };

    private final Handler mflipperHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            // TODO Auto-generated method stub
            switch (msg.what) {
                case SHOW_NEXT:
                    showNextView();
                    break;

                default:
                    break;
            }
        }

    };

    private final Thread thread = new Thread() {
        @Override
        public void run() {

            while (isRun) {
                try {
                    Thread.sleep(1000 * 4);
                    Message msg = new Message();
                    msg.what = SHOW_NEXT;
                    mflipperHandler.sendMessage(msg);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        }
    };

    private void showNextView() {

        currentItem = mPager.getCurrentScreen();
        if (currentItem < 3) {
            mPager.setCurrentScreen(++currentItem, true);
        } else {
            currentItem = 0;
            mPager.setCurrentScreen(currentItem, true);
        }
    }

}
