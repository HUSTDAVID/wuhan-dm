
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.db.DatabaseImpl;
import com.wh.dm.type.ArticleMagzine;
import com.wh.dm.type.Magazine;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.TimeUtil;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DM_MZineArticleActivity extends Activity {

    private final String URL_DOMAIN = "http://api.meike15.com";
    private ArrayList<View> views;
    private View view1, view2, view3;
    private ViewPager viewPager;
    private MyPagerAdapter adapter = null;
    private LayoutInflater inflater;
    private static int MSG_GET_MAGZINE = 0;
    private GetMagzineTask getMagzineTask = null;
    private Random random = null;
    private WH_DMApi wh_dmApi;
    private WH_DMApp wh_dmApp;
    private DatabaseImpl databaseImpl;
    private Magazine curMagazine;
    private int totalPage = 1;
    private int curPage = 1;
    private TextView txtPage;
    private boolean isLoad;
    private int sid;
    // private ProgressDialog progressDialog = null;
    private LinearLayout loadLayout;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == MSG_GET_MAGZINE) {
                if (getMagzineTask != null) {
                    getMagzineTask.cancel(true);
                    getMagzineTask = null;
                }
                getMagzineTask = new GetMagzineTask();
                getMagzineTask.execute(sid);
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dm_mzine1);
        inflater = getLayoutInflater();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        sid = bundle.getInt("sid");

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

    public void init() {

        loadLayout = (LinearLayout) findViewById(R.id.magazine_load);

        txtPage = (TextView) findViewById(R.id.txt_magazine_page);
        LayoutInflater inflater = getLayoutInflater();
        views = new ArrayList<View>();
        viewPager = (ViewPager) findViewById(R.id.v_Pager);
        adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());

        wh_dmApp = (WH_DMApp) getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        databaseImpl = wh_dmApp.getDatabase();
        curMagazine = databaseImpl.getMagazine(sid);

        isLoad = databaseImpl.isLoad(sid);
        handler.sendEmptyMessage(MSG_GET_MAGZINE);

    }

    public void addData(ArrayList<ArticleMagzine> magzines) {

        random = new Random(2);
        int pageSize = magzines.size() / 6;
        View view = null;
        for (int i = 0, lower = 0, upper = 5; i < pageSize; i++) {
            magzines = modifiData(magzines, lower, upper);
            int r = random.nextInt(2);
            switch (r) {
                case 0:
                    view = inflater.inflate(R.layout.magzine1_style1, null);
                    initViewOne(view, lower, upper, magzines);
                    break;
                case 1:
                    view = inflater.inflate(R.layout.magzine1_style2, null);
                    initViewTwo(view, lower, upper, magzines);
                    break;
                case 2:
                    view = inflater.inflate(R.layout.magzine1_style3, null);
                    initViewThree(view, lower, upper, magzines);
                    break;

            }
            lower = upper + 1;
            upper = upper + 6;
            views.add(view);

        }
        viewPager.setCurrentItem(0);
        totalPage = views.size();
        adapter.setList(views);
    }

    private void initViewOne(View v, final int low, int up, final ArrayList<ArticleMagzine> articles) {

        final ImageView img_header = (ImageView) v.findViewById(R.id.img_header);
        UrlImageViewHelper.setUrlDrawable(img_header,
                WH_DMHttpApiV1.URL_DOMAIN + curMagazine.getTitlepic(), R.drawable.magazine_title,
                null);
        final LinearLayout view1_linear1 = (LinearLayout) v.findViewById(R.id.layout1_magazine_1);
        final LinearLayout view1_linear2 = (LinearLayout) v.findViewById(R.id.layout2_magazine_1);
        final LinearLayout view1_linear3 = (LinearLayout) v.findViewById(R.id.layout3_magazine_1);
        final LinearLayout view1_linear4 = (LinearLayout) v.findViewById(R.id.layout4_magazine_1);
        final LinearLayout view1_linear5 = (LinearLayout) v.findViewById(R.id.layout5_magazine_1);
        final RelativeLayout style1 = (RelativeLayout) v.findViewById(R.id.style1);

        final TextView view1_txtTile1 = (TextView) v.findViewById(R.id.txt_magazine_1_title1);
        final TextView view1_txtTile1_1 = (TextView) v.findViewById(R.id.txt_magazine_1_source1);
        final TextView view1_txtTile1_2 = (TextView) v.findViewById(R.id.txt_magazine_1_time1);

        final TextView view1_txtTile2 = (TextView) v.findViewById(R.id.txt_magazine_1_title2);
        final TextView view1_txtTile2_1 = (TextView) v.findViewById(R.id.txt_magazine_1_source2);
        final TextView view1_txtTile2_2 = (TextView) v.findViewById(R.id.txt_magazine_1_time2);

        final TextView view1_txtTile3 = (TextView) v.findViewById(R.id.txt_magazine_1_title3);
        final TextView view1_txtTile3_1 = (TextView) v.findViewById(R.id.txt_magazine_1_source3);
        final TextView view1_txtTile3_2 = (TextView) v.findViewById(R.id.txt_magazine_1_time3);

        final TextView view1_txtTile4 = (TextView) v.findViewById(R.id.txt_magazine_1_title4);
        final TextView view1_txtTile4_1 = (TextView) v.findViewById(R.id.txt_magazine_1_source4);
        final TextView view1_txtTile4_2 = (TextView) v.findViewById(R.id.txt_magazine_1_time4);

        final TextView view1_txtTile5 = (TextView) v.findViewById(R.id.txt_magazine_1_title5);
        final TextView view1_txtTile5_1 = (TextView) v.findViewById(R.id.txt_magazine_1_source5);
        final TextView view1_txtTile5_2 = (TextView) v.findViewById(R.id.txt_magazine_1_time5);

        final TextView view1_txtTile6 = (TextView) v.findViewById(R.id.txt_magazine_1_title6);

        final ImageView img_magazine_1 = (ImageView) v.findViewById(R.id.img_magazine_1);
        final TextView txtLarge = (TextView) v.findViewById(R.id.txtLarge1);
        final Intent intent = new Intent(DM_MZineArticleActivity.this,
                MagazineDetailsActivity.class);
        OnClickListener listener1 = new OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.style1:
                        intent.putExtra("sid", articles.get(low + 5).getId());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("source", articles.get(low + 5).getSource());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;
                    case R.id.layout1_magazine_1:
                        view1_txtTile1.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low).getId());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("source", articles.get(low).getSource());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;
                    case R.id.layout2_magazine_1:
                        view1_txtTile2.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 1).getId());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("source", articles.get(low + 1).getSource());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;
                    case R.id.layout3_magazine_1:
                        view1_txtTile3.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 2).getId());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("source", articles.get(low + 2).getSource());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;
                    case R.id.layout4_magazine_1:
                        view1_txtTile4.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 3).getId());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("source", articles.get(low + 3).getSource());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;
                    case R.id.layout5_magazine_1:
                        view1_txtTile5.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 4).getId());
                        intent.putExtra("source", articles.get(low + 4).getSource());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;

                }
            }
        };
        view1_linear1.setOnClickListener(listener1);
        view1_linear2.setOnClickListener(listener1);
        view1_linear3.setOnClickListener(listener1);
        view1_linear4.setOnClickListener(listener1);
        view1_linear5.setOnClickListener(listener1);
        style1.setOnClickListener(listener1);

        ArrayList<HashMap<String, TextView>> maps = new ArrayList();
        HashMap<String, TextView> map1 = new HashMap();
        map1.put("title", view1_txtTile1);
        map1.put("source", view1_txtTile1_1);
        map1.put("time", view1_txtTile1_2);

        HashMap<String, TextView> map2 = new HashMap();
        map2.put("title", view1_txtTile2);
        map2.put("source", view1_txtTile2_1);
        map2.put("time", view1_txtTile2_2);

        HashMap<String, TextView> map3 = new HashMap();
        map3.put("title", view1_txtTile3);
        map3.put("source", view1_txtTile3_1);
        map3.put("time", view1_txtTile3_2);

        HashMap<String, TextView> map4 = new HashMap();
        map4.put("title", view1_txtTile4);
        map4.put("source", view1_txtTile4_1);
        map4.put("time", view1_txtTile4_2);

        HashMap<String, TextView> map5 = new HashMap();
        map5.put("title", view1_txtTile5);
        map5.put("source", view1_txtTile5_1);
        map5.put("time", view1_txtTile5_2);

        HashMap<String, TextView> map6 = new HashMap();
        map6.put("title", view1_txtTile6);

        maps.add(map1);
        maps.add(map2);
        maps.add(map3);
        maps.add(map4);
        maps.add(map5);
        maps.add(map6);

        for (int i = low, j = 0; i <= up; i++, j++) {
            maps.get(j).get("title").setText(articles.get(i).getTitle());
            if (j != 5) {
                maps.get(j)
                        .get("time")
                        .setText(
                                TimeUtil.getTimeInterval(articles.get(i).getPubdate(),
                                        DM_MZineArticleActivity.this));
                maps.get(j).get("source").setText(articles.get(i).getSource());
            }
        }
        if (up <= articles.size()) {
            if (!wh_dmApp.isLoadImg || articles.get(up).getLitpic().equals("")
                    || articles.get(up).getLitpic() == null) {
                txtLarge.setVisibility(View.VISIBLE);
                txtLarge.setText(view1_txtTile6.getText().toString());
                view1_txtTile6.setVisibility(View.GONE);

            } else {
                UrlImageViewHelper.setUrlDrawable(img_magazine_1, URL_DOMAIN
                        + articles.get(up).getLitpic());
            }

        }

    }

    private void initViewTwo(View v, final int low, int up, final ArrayList<ArticleMagzine> articles) {

        final ImageView img_header = (ImageView) v.findViewById(R.id.img_header);
        UrlImageViewHelper.setUrlDrawable(img_header,
                WH_DMHttpApiV1.URL_DOMAIN + curMagazine.getTitlepic(), R.drawable.magazine_title,
                null);
        final LinearLayout view2_linear1 = (LinearLayout) v.findViewById(R.id.layout1_magazine_2);
        final LinearLayout view2_linear2 = (LinearLayout) v.findViewById(R.id.layout2_magazine_2);
        final LinearLayout view2_linear3 = (LinearLayout) v.findViewById(R.id.layout3_magazine_2);
        final LinearLayout view2_linear4 = (LinearLayout) v.findViewById(R.id.layout4_magazine_2);
        final LinearLayout view2_linear5 = (LinearLayout) v.findViewById(R.id.layout5_magazine_2);
        final RelativeLayout style2 = (RelativeLayout) v.findViewById(R.id.style2);

        final TextView view2_txtTile1 = (TextView) v.findViewById(R.id.txt_magazine_2_title1);
        final TextView view2_txtTile1_1 = (TextView) v.findViewById(R.id.txt_magazine_2_source1);
        final TextView view2_txtTile1_2 = (TextView) v.findViewById(R.id.txt_magazine_2_time1);

        final TextView view2_txtTile2 = (TextView) v.findViewById(R.id.txt_magazine_2_title2);
        final TextView view2_txtTile2_1 = (TextView) v.findViewById(R.id.txt_magazine_2_source2);
        final TextView view2_txtTile2_2 = (TextView) v.findViewById(R.id.txt_magazine_2_time2);

        final TextView view2_txtTile3 = (TextView) v.findViewById(R.id.txt_magazine_2_title3);
        final TextView view2_txtTile3_1 = (TextView) v.findViewById(R.id.txt_magazine_2_source3);
        final TextView view2_txtTile3_2 = (TextView) v.findViewById(R.id.txt_magazine_2_time3);

        final TextView view2_txtTile4 = (TextView) v.findViewById(R.id.txt_magazine_2_title4);
        final TextView view2_txtTile4_1 = (TextView) v.findViewById(R.id.txt_magazine_2_source4);
        final TextView view2_txtTile4_2 = (TextView) v.findViewById(R.id.txt_magazine_2_time4);

        final TextView view2_txtTile5 = (TextView) v.findViewById(R.id.txt_magazine_2_title5);
        final TextView view2_txtTile5_1 = (TextView) v.findViewById(R.id.txt_magazine_2_source5);
        final TextView view2_txtTile5_2 = (TextView) v.findViewById(R.id.txt_magazine_2_time5);

        final TextView view2_txtTile6 = (TextView) v.findViewById(R.id.txt_magazine_2_title6);

        final ImageView img_magazine_2 = (ImageView) v.findViewById(R.id.img_magazine_2);
        final TextView txtLarge = (TextView) v.findViewById(R.id.txtLarge2);

        final Intent intent = new Intent(DM_MZineArticleActivity.this,
                MagazineDetailsActivity.class);
        OnClickListener listener2 = new OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.style2:
                        intent.putExtra("sid", articles.get(low + 5).getId());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("source", articles.get(low + 5).getSource());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;
                    case R.id.layout1_magazine_2:

                        view2_txtTile1.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low).getId());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("source", articles.get(low).getSource());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;
                    case R.id.layout2_magazine_2:

                        view2_txtTile2.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 1).getId());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("source", articles.get(low + 1).getSource());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;
                    case R.id.layout3_magazine_2:

                        view2_txtTile3.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 2).getId());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("source", articles.get(low + 2).getSource());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;
                    case R.id.layout4_magazine_2:

                        view2_txtTile4.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 3).getId());
                        intent.putExtra("source", articles.get(low + 3).getSource());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;
                    case R.id.layout5_magazine_2:

                        view2_txtTile5.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 4).getId());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("source", articles.get(low + 4).getSource());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;
                }

            }
        };
        view2_linear1.setOnClickListener(listener2);
        view2_linear2.setOnClickListener(listener2);
        view2_linear3.setOnClickListener(listener2);
        view2_linear4.setOnClickListener(listener2);
        view2_linear5.setOnClickListener(listener2);
        style2.setOnClickListener(listener2);

        ArrayList<HashMap<String, TextView>> maps = new ArrayList();
        HashMap<String, TextView> map1 = new HashMap();
        map1.put("title", view2_txtTile1);
        map1.put("source", view2_txtTile1_1);
        map1.put("time", view2_txtTile1_2);

        HashMap<String, TextView> map2 = new HashMap();
        map2.put("title", view2_txtTile2);
        map2.put("source", view2_txtTile2_1);
        map2.put("time", view2_txtTile2_2);

        HashMap<String, TextView> map3 = new HashMap();
        map3.put("title", view2_txtTile3);
        map3.put("source", view2_txtTile3_1);
        map3.put("time", view2_txtTile3_2);

        HashMap<String, TextView> map4 = new HashMap();
        map4.put("title", view2_txtTile4);
        map4.put("source", view2_txtTile4_1);
        map4.put("time", view2_txtTile4_2);

        HashMap<String, TextView> map5 = new HashMap();
        map5.put("title", view2_txtTile5);
        map5.put("source", view2_txtTile5_1);
        map5.put("time", view2_txtTile5_2);

        HashMap<String, TextView> map6 = new HashMap();
        map6.put("title", view2_txtTile6);

        maps.add(map1);
        maps.add(map2);
        maps.add(map3);
        maps.add(map4);
        maps.add(map5);
        maps.add(map6);

        for (int i = low, j = 0; i <= up; i++, j++) {
            maps.get(j).get("title").setText(articles.get(i).getTitle());
            if (j != 5) {
                maps.get(j)
                        .get("time")
                        .setText(
                                TimeUtil.getTimeInterval(articles.get(i).getPubdate(),
                                        DM_MZineArticleActivity.this));
                maps.get(j).get("source").setText(articles.get(i).getSource());
            }
        }
        if (up <= articles.size()) {
            if (!wh_dmApp.isLoadImg || articles.get(up).getLitpic().equals("")
                    || articles.get(up).getLitpic() == null) {
                txtLarge.setVisibility(View.VISIBLE);
                txtLarge.setText(view2_txtTile6.getText().toString());
                view2_txtTile6.setVisibility(View.GONE);

            } else {
                UrlImageViewHelper.setUrlDrawable(img_magazine_2, URL_DOMAIN
                        + articles.get(up).getLitpic());
            }

        }
    }

    private void initViewThree(View v, final int low, int up,
            final ArrayList<ArticleMagzine> articles) {

        final ImageView img_header = (ImageView) v.findViewById(R.id.img_header);
        UrlImageViewHelper.setUrlDrawable(img_header,
                WH_DMHttpApiV1.URL_DOMAIN + curMagazine.getTitlepic(), R.drawable.magazine_title,
                null);
        final LinearLayout view3_linear1 = (LinearLayout) v.findViewById(R.id.layout1_magazine_3);
        final LinearLayout view3_linear2 = (LinearLayout) v.findViewById(R.id.layout2_magazine_3);
        final LinearLayout view3_linear3 = (LinearLayout) v.findViewById(R.id.layout3_magazine_3);
        final LinearLayout view3_linear4 = (LinearLayout) v.findViewById(R.id.layout4_magazine_3);
        final LinearLayout view3_linear5 = (LinearLayout) v.findViewById(R.id.layout5_magazine_3);
        final RelativeLayout style3 = (RelativeLayout) v.findViewById(R.id.style3);

        final TextView view3_txtTile1 = (TextView) v.findViewById(R.id.txt_magazine_3_title1);
        final TextView view3_txtTile1_1 = (TextView) v.findViewById(R.id.txt_magazine_3_source1);
        final TextView view3_txtTile1_2 = (TextView) v.findViewById(R.id.txt_magazine_3_time1);

        final TextView view3_txtTile2 = (TextView) v.findViewById(R.id.txt_magazine_3_title2);
        final TextView view3_txtTile2_1 = (TextView) v.findViewById(R.id.txt_magazine_3_source2);
        final TextView view3_txtTile2_2 = (TextView) v.findViewById(R.id.txt_magazine_3_time2);

        final TextView view3_txtTile3 = (TextView) v.findViewById(R.id.txt_magazine_3_title3);
        final TextView view3_txtTile3_1 = (TextView) v.findViewById(R.id.txt_magazine_3_source3);
        final TextView view3_txtTile3_2 = (TextView) v.findViewById(R.id.txt_magazine_3_time3);

        final TextView view3_txtTile4 = (TextView) v.findViewById(R.id.txt_magazine_3_title4);
        final TextView view3_txtTile4_1 = (TextView) v.findViewById(R.id.txt_magazine_3_source4);
        final TextView view3_txtTile4_2 = (TextView) v.findViewById(R.id.txt_magazine_3_time4);

        final TextView view3_txtTile5 = (TextView) v.findViewById(R.id.txt_magazine_3_title5);
        final TextView view3_txtTile5_1 = (TextView) v.findViewById(R.id.txt_magazine_3_source5);
        final TextView view3_txtTile5_2 = (TextView) v.findViewById(R.id.txt_magazine_3_time5);

        final TextView view3_txtTile6 = (TextView) v.findViewById(R.id.txt_magazine_3_title6);
        final ImageView img_magazine_3 = (ImageView) v.findViewById(R.id.img_magazine_3);
        final TextView txtLarge = (TextView) v.findViewById(R.id.txtLarge3);

        final Intent intent = new Intent(DM_MZineArticleActivity.this,
                MagazineDetailsActivity.class);
        OnClickListener listener3 = new OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.layout1_magazine_3:

                        view3_txtTile1.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low).getId());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("source", articles.get(low).getSource());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;
                    case R.id.layout2_magazine_3:

                        view3_txtTile2.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 1).getId());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("source", articles.get(low + 1).getSource());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;
                    case R.id.layout3_magazine_3:

                        view3_txtTile3.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 2).getId());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("source", articles.get(low + 2).getSource());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;
                    case R.id.layout4_magazine_3:

                        view3_txtTile4.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 3).getId());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("source", articles.get(low + 3).getSource());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;
                    case R.id.layout5_magazine_3:

                        view3_txtTile5.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 4).getId());
                        intent.putExtra("source", articles.get(low + 4).getSource());
                        intent.putExtra("titleImg", curMagazine.getTitlepic());
                        intent.putExtra("is_load", isLoad);
                        intent.putExtra("mid", sid);
                        startActivity(intent);
                        break;
                }

            }
        };
        view3_linear1.setOnClickListener(listener3);
        view3_linear2.setOnClickListener(listener3);
        view3_linear3.setOnClickListener(listener3);
        view3_linear4.setOnClickListener(listener3);
        view3_linear5.setOnClickListener(listener3);

        ArrayList<HashMap<String, TextView>> maps = new ArrayList();
        HashMap<String, TextView> map1 = new HashMap();
        map1.put("title", view3_txtTile1);
        map1.put("source", view3_txtTile1_1);
        map1.put("time", view3_txtTile1_2);

        HashMap<String, TextView> map2 = new HashMap();
        map2.put("title", view3_txtTile2);
        map2.put("source", view3_txtTile2_1);
        map2.put("time", view3_txtTile2_2);

        HashMap<String, TextView> map3 = new HashMap();
        map3.put("title", view3_txtTile3);
        map3.put("source", view3_txtTile3_1);
        map3.put("time", view3_txtTile3_2);

        HashMap<String, TextView> map4 = new HashMap();
        map4.put("title", view3_txtTile4);
        map4.put("source", view3_txtTile4_1);
        map4.put("time", view3_txtTile4_2);

        HashMap<String, TextView> map5 = new HashMap();
        map5.put("title", view3_txtTile5);
        map5.put("source", view3_txtTile5_1);
        map5.put("time", view3_txtTile5_2);

        HashMap<String, TextView> map6 = new HashMap();
        map6.put("title", view3_txtTile6);

        maps.add(map1);
        maps.add(map2);
        maps.add(map3);
        maps.add(map4);
        maps.add(map5);
        maps.add(map6);

        ArrayList<TextView> txtViews = new ArrayList<TextView>();
        txtViews.add(view3_txtTile1);
        txtViews.add(view3_txtTile2);
        txtViews.add(view3_txtTile3);
        txtViews.add(view3_txtTile4);
        txtViews.add(view3_txtTile5);
        txtViews.add(view3_txtTile6);

        for (int i = low, j = 0; i <= up; i++, j++) {
            maps.get(j).get("title").setText(articles.get(i).getTitle());
            if (j != 5) {
                maps.get(j)
                        .get("time")
                        .setText(
                                TimeUtil.getTimeInterval(articles.get(i).getPubdate(),
                                        DM_MZineArticleActivity.this));
                maps.get(j).get("source").setText(articles.get(i).getSource());
            }
        }
        if (up <= articles.size()) {
            if (!wh_dmApp.isLoadImg || articles.get(up).getLitpic().equals("")
                    || articles.get(up).getLitpic() == null) {
                txtLarge.setVisibility(View.VISIBLE);
                txtLarge.setText(view3_txtTile6.getText().toString());
                view3_txtTile6.setVisibility(View.GONE);

            } else {
                UrlImageViewHelper.setUrlDrawable(img_magazine_3, URL_DOMAIN
                        + articles.get(up).getLitpic());
            }

        }

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

    private class GetMagzineTask extends AsyncTask<Integer, Void, ArrayList<ArticleMagzine>> {
        Exception reason = null;

        @Override
        protected void onPreExecute() {

            if (!isLoad) {
                loadLayout.setVisibility(View.VISIBLE);
            }
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ArticleMagzine> doInBackground(Integer... params) {

            ArrayList<ArticleMagzine> articles = null;
            try {

                if (isLoad) {
                    Log.d("database", "start" + TimeUtil.showCurTime());
                    articles = databaseImpl.getMagazineBody(sid);
                    Log.d("database", "finish" + TimeUtil.showCurTime());
                } else {
                    articles = wh_dmApi.getArticleMagzine(params[0]);
                }
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
            }
            return articles;
        }

        @Override
        protected void onPostExecute(ArrayList<ArticleMagzine> result) {

            if (result != null) {
                Log.d("add", "start" + TimeUtil.showCurTime());
                addData(result);
                Log.d("add", "finish" + TimeUtil.showCurTime());
                txtPage.setText(curPage + "/" + totalPage);

            } else {
                if (wh_dmApp.isConnected()) {
                    NotificationUtil.showShortToast("Ã»ÓÐÐÂ¿¯", DM_MZineArticleActivity.this);
                } else {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            DM_MZineArticleActivity.this);
                }
            }
            if (!isLoad) {
                loadLayout.setVisibility(View.GONE);
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

            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {

            curPage = 1 + arg0;
            txtPage.setText(curPage + "/" + totalPage);

        }
    }

    private ArrayList<ArticleMagzine> modifiData(ArrayList<ArticleMagzine> articles, int low, int up) {

        ArticleMagzine article = new ArticleMagzine();

        for (int i = low; i <= up; i++) {
            if (articles.get(i).isIspic() && i != up) {
                // if (articles.get(i).getLitpic() != null &&
                // articles.get(i).getLitpic().length() > 0
                // && i != up) {
                for (int j = i; j < up; j++) {
                    article = articles.get(j);
                    articles.set(j, articles.get(j + 1));
                    articles.set(j + 1, article);
                }

                break;
            }
        }

        article = articles.get(up);
        return articles;
    }
}
