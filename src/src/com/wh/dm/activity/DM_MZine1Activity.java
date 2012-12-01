
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.type.ArticleMagzine;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.UrlImageViewHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import java.util.Random;

public class DM_MZine1Activity extends Activity {

    private final String URL_DOMAIN = "http://test1.jbr.net.cn:809";
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
    private ProgressDialog progressDialog = null;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {

            if (msg.what == MSG_GET_MAGZINE) {
                if (getMagzineTask != null) {
                    getMagzineTask.cancel(true);
                    getMagzineTask = null;
                }
                getMagzineTask = new GetMagzineTask();
                getMagzineTask.execute(6, 6);
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
        init();
        handler.sendEmptyMessage(MSG_GET_MAGZINE);
        // addData(magzines);

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

        LayoutInflater inflater = getLayoutInflater();
        views = new ArrayList<View>();
        viewPager = (ViewPager) findViewById(R.id.v_Pager);
        adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);

        progressDialog = new ProgressDialog(getParent());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        wh_dmApp = (WH_DMApp) getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();

    }

    public void addData(ArrayList<ArticleMagzine> magzines) {

        random = new Random(2);
        int pageSize = magzines.size() / 6;
        View view = null;
        for (int i = 0, lower = 0, upper = 5; i < pageSize; i++) {
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
        adapter.setList(views);
    }

    private void initViewOne(View v, final int low, int up, final ArrayList<ArticleMagzine> articles) {

        final LinearLayout view1_linear1 = (LinearLayout) v.findViewById(R.id.layout1_magazine_1);
        final LinearLayout view1_linear2 = (LinearLayout) v.findViewById(R.id.layout2_magazine_1);
        final LinearLayout view1_linear3 = (LinearLayout) v.findViewById(R.id.layout3_magazine_1);
        final LinearLayout view1_linear4 = (LinearLayout) v.findViewById(R.id.layout4_magazine_1);
        final LinearLayout view1_linear5 = (LinearLayout) v.findViewById(R.id.layout5_magazine_1);
        final RelativeLayout style1 = (RelativeLayout) v.findViewById(R.id.style1);

        final TextView view1_txtTile1 = (TextView) v.findViewById(R.id.txt_magazine_1_title1);
        final TextView view1_txtTile2 = (TextView) v.findViewById(R.id.txt_magazine_1_title2);
        final TextView view1_txtTile3 = (TextView) v.findViewById(R.id.txt_magazine_1_title3);
        final TextView view1_txtTile4 = (TextView) v.findViewById(R.id.txt_magazine_1_title4);
        final TextView view1_txtTile5 = (TextView) v.findViewById(R.id.txt_magazine_1_title5);
        final TextView view1_txtTile6 = (TextView) v.findViewById(R.id.txt_magazine_1_title6);

        final ImageView img_magazine_1 = (ImageView) v.findViewById(R.id.img_magazine_1);
        final TextView txtLarge = (TextView) v.findViewById(R.id.txtLarge1);
        final Intent intent = new Intent(DM_MZine1Activity.this, MagazineDetailsActivity.class);

        OnClickListener listener1 = new OnClickListener() {
            @Override
            public void onClick(View v) {

                int backColor = getResources().getColor(R.color.bg_clicked);
                switch (v.getId()) {
                    case R.id.style1:
                        intent.putExtra("sid", articles.get(low + 5).getId());
                        startActivity(intent);
                        break;
                    case R.id.layout1_magazine_1:
                        view1_linear1.setBackgroundColor(backColor);
                        view1_txtTile1.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low).getId());
                        startActivity(intent);
                        break;
                    case R.id.layout2_magazine_1:
                        view1_linear2.setBackgroundColor(backColor);
                        view1_txtTile2.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 1).getId());
                        startActivity(intent);
                        break;
                    case R.id.layout3_magazine_1:
                        view1_linear3.setBackgroundColor(backColor);
                        view1_txtTile3.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 2).getId());
                        startActivity(intent);
                        break;
                    case R.id.layout4_magazine_1:
                        view1_linear4.setBackgroundColor(backColor);
                        view1_txtTile4.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 3).getId());
                        startActivity(intent);
                        break;
                    case R.id.layout5_magazine_1:
                        view1_linear5.setBackgroundColor(backColor);
                        view1_txtTile5.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 4).getId());
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
        ArrayList<TextView> txtViews = new ArrayList<TextView>();
        txtViews.add(view1_txtTile1);
        txtViews.add(view1_txtTile2);
        txtViews.add(view1_txtTile3);
        txtViews.add(view1_txtTile4);
        txtViews.add(view1_txtTile5);
        txtViews.add(view1_txtTile6);

        if (up <= articles.size()) {
            for (int i = low, j = 0; i <= up; i++, j++) {
                ArticleMagzine article = articles.get(i);
                if (article.getTitle() != null && article.getTitle().length() > 0) {
                    txtViews.get(j).setText(article.getTitle());
                }
            }
            for (int i = low, j = 0; i <= up; i++, j++) {
                ArticleMagzine article = articles.get(i);
                final int aid = article.getId();
                if (article.getLitpic() != null && article.getLitpic().length() > 0) {
                    String title = new String(view1_txtTile6.getText().toString());
                    view1_txtTile6.setText(article.getTitle());
                    UrlImageViewHelper.setUrlDrawable(img_magazine_1,
                            URL_DOMAIN + article.getLitpic());

                    img_magazine_1.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(DM_MZine1Activity.this,
                                    MagazineDetailsActivity.class);
                            intent.putExtra("sid", aid);
                            startActivity(intent);
                        }
                    });

                    txtViews.get(j).setText(title);
                    return;
                }

            }
            txtLarge.setVisibility(View.VISIBLE);
            txtLarge.setText(view1_txtTile6.getText().toString());
            view1_txtTile6.setVisibility(View.GONE);
        }

    }

    private void initViewTwo(View v, final int low, int up, final ArrayList<ArticleMagzine> articles) {

        final LinearLayout view2_linear1 = (LinearLayout) v.findViewById(R.id.layout1_magazine_2);
        final LinearLayout view2_linear2 = (LinearLayout) v.findViewById(R.id.layout2_magazine_2);
        final LinearLayout view2_linear3 = (LinearLayout) v.findViewById(R.id.layout3_magazine_2);
        final LinearLayout view2_linear4 = (LinearLayout) v.findViewById(R.id.layout4_magazine_2);
        final LinearLayout view2_linear5 = (LinearLayout) v.findViewById(R.id.layout5_magazine_2);
        final RelativeLayout style2 = (RelativeLayout) v.findViewById(R.id.style2);

        final TextView view2_txtTile1 = (TextView) v.findViewById(R.id.txt_magazine_2_title1);
        final TextView view2_txtTile2 = (TextView) v.findViewById(R.id.txt_magazine_2_title2);
        final TextView view2_txtTile3 = (TextView) v.findViewById(R.id.txt_magazine_2_title3);
        final TextView view2_txtTile4 = (TextView) v.findViewById(R.id.txt_magazine_2_title4);
        final TextView view2_txtTile5 = (TextView) v.findViewById(R.id.txt_magazine_2_title5);
        final TextView view2_txtTile6 = (TextView) v.findViewById(R.id.txt_magazine_2_title6);
        final ImageView img_magazine_2 = (ImageView) v.findViewById(R.id.img_magazine_2);
        final TextView txtLarge = (TextView) v.findViewById(R.id.txtLarge2);

        final Intent intent = new Intent(DM_MZine1Activity.this, MagazineDetailsActivity.class);
        OnClickListener listener2 = new OnClickListener() {
            @Override
            public void onClick(View v) {

                int backColor = getResources().getColor(R.color.bg_clicked);
                switch (v.getId()) {
                    case R.id.style2:
                        intent.putExtra("sid", articles.get(low + 5).getId());
                        startActivity(intent);
                        break;
                    case R.id.layout1_magazine_2:
                        view2_linear1.setBackgroundColor(backColor);
                        view2_txtTile1.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low).getId());
                        startActivity(intent);
                        break;
                    case R.id.layout2_magazine_2:
                        view2_linear2.setBackgroundColor(backColor);
                        view2_txtTile2.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 1).getId());
                        startActivity(intent);
                        break;
                    case R.id.layout3_magazine_2:
                        view2_linear3.setBackgroundColor(backColor);
                        view2_txtTile3.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 2).getId());
                        startActivity(intent);
                        break;
                    case R.id.layout4_magazine_2:
                        view2_linear4.setBackgroundColor(backColor);
                        view2_txtTile4.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 3).getId());
                        startActivity(intent);
                        break;
                    case R.id.layout5_magazine_2:
                        view2_linear5.setBackgroundColor(backColor);
                        view2_txtTile5.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 4).getId());
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

        ArrayList<TextView> txtViews = new ArrayList<TextView>();
        txtViews.add(view2_txtTile1);
        txtViews.add(view2_txtTile2);
        txtViews.add(view2_txtTile3);
        txtViews.add(view2_txtTile4);
        txtViews.add(view2_txtTile5);
        txtViews.add(view2_txtTile6);

        if (up <= articles.size()) {
            for (int i = low, j = 0; i <= up; i++, j++) {
                ArticleMagzine article = articles.get(i);
                final int aid = article.getId();
                if (article.getTitle() != null && article.getTitle().length() > 0) {
                    txtViews.get(j).setText(article.getTitle());
                }
            }
            for (int i = low, j = 0; i <= up; i++, j++) {
                ArticleMagzine article = articles.get(i);
                final int aid = article.getId();
                if (article.getLitpic() != null && article.getLitpic().length() > 0) {
                    String title = new String(view2_txtTile6.getText().toString());
                    view2_txtTile6.setText(article.getTitle());
                    UrlImageViewHelper.setUrlDrawable(img_magazine_2,
                            URL_DOMAIN + article.getLitpic());
                    img_magazine_2.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(DM_MZine1Activity.this,
                                    MagazineDetailsActivity.class);
                            intent.putExtra("sid", aid);
                            startActivity(intent);

                        }

                    });
                    txtViews.get(j).setText(title);
                    return;
                }

            }
            txtLarge.setVisibility(View.VISIBLE);
            txtLarge.setText(view2_txtTile6.getText().toString());
            view2_txtTile6.setVisibility(View.GONE);
        }
    }

    private void initViewThree(View v, final int low, int up,
            final ArrayList<ArticleMagzine> articles) {

        final LinearLayout view3_linear1 = (LinearLayout) v.findViewById(R.id.layout1_magazine_3);
        final LinearLayout view3_linear2 = (LinearLayout) v.findViewById(R.id.layout2_magazine_3);
        final LinearLayout view3_linear3 = (LinearLayout) v.findViewById(R.id.layout3_magazine_3);
        final LinearLayout view3_linear4 = (LinearLayout) v.findViewById(R.id.layout4_magazine_3);
        final LinearLayout view3_linear5 = (LinearLayout) v.findViewById(R.id.layout5_magazine_3);
        final RelativeLayout style3 = (RelativeLayout) v.findViewById(R.id.style3);

        final TextView view3_txtTile1 = (TextView) v.findViewById(R.id.txt_magazine_3_title1);
        final TextView view3_txtTile2 = (TextView) v.findViewById(R.id.txt_magazine_3_title2);
        final TextView view3_txtTile3 = (TextView) v.findViewById(R.id.txt_magazine_3_title3);
        final TextView view3_txtTile4 = (TextView) v.findViewById(R.id.txt_magazine_3_title4);
        final TextView view3_txtTile5 = (TextView) v.findViewById(R.id.txt_magazine_3_title5);
        final TextView view3_txtTile6 = (TextView) v.findViewById(R.id.txt_magazine_3_title6);
        final ImageView img_magazine_3 = (ImageView) v.findViewById(R.id.img_magazine_3);
        final TextView txtLarge = (TextView) v.findViewById(R.id.txtLarge3);

        final Intent intent = new Intent(DM_MZine1Activity.this, MagazineDetailsActivity.class);
        OnClickListener listener3 = new OnClickListener() {
            @Override
            public void onClick(View v) {

                int backColor = getResources().getColor(R.color.bg_clicked);

                switch (v.getId()) {
                    case R.id.layout1_magazine_3:
                        view3_linear1.setBackgroundColor(backColor);
                        view3_txtTile1.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low).getId());
                        startActivity(intent);
                        break;
                    case R.id.layout2_magazine_3:
                        view3_linear2.setBackgroundColor(backColor);
                        view3_txtTile2.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 1).getId());
                        startActivity(intent);
                        break;
                    case R.id.layout3_magazine_3:
                        view3_linear3.setBackgroundColor(backColor);
                        view3_txtTile3.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 2).getId());
                        startActivity(intent);
                        break;
                    case R.id.layout4_magazine_3:
                        view3_linear4.setBackgroundColor(backColor);
                        view3_txtTile4.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 3).getId());
                        startActivity(intent);
                        break;
                    case R.id.layout5_magazine_3:
                        view3_linear5.setBackgroundColor(backColor);
                        view3_txtTile5.setTextColor(getResources().getColor(
                                R.color.mzine_txt_clicked));
                        intent.putExtra("sid", articles.get(low + 4).getId());
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

        ArrayList<TextView> txtViews = new ArrayList<TextView>();
        txtViews.add(view3_txtTile1);
        txtViews.add(view3_txtTile2);
        txtViews.add(view3_txtTile3);
        txtViews.add(view3_txtTile4);
        txtViews.add(view3_txtTile5);
        txtViews.add(view3_txtTile6);

        if (up <= articles.size()) {
            for (int i = low, j = 0; i <= up; i++, j++) {
                ArticleMagzine article = articles.get(i);
                final int aid = article.getId();
                if (article.getTitle() != null && article.getTitle().length() > 0) {
                    txtViews.get(j).setText(article.getTitle());
                }
            }
            for (int i = low, j = 0; i <= up; i++, j++) {
                ArticleMagzine article = articles.get(i);
                final int aid = article.getId();
                if (article.getLitpic() != null && article.getLitpic().length() > 0) {
                    String title = new String(view3_txtTile6.getText().toString());
                    view3_txtTile6.setText(article.getTitle());
                    UrlImageViewHelper.setUrlDrawable(img_magazine_3,
                            URL_DOMAIN + article.getLitpic());
                    img_magazine_3.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(DM_MZine1Activity.this,
                                    MagazineDetailsActivity.class);
                            intent.putExtra("sid", aid);
                            startActivity(intent);
                        }

                    });
                    txtViews.get(j).setText(title);
                    return;
                }

            }
            txtLarge.setVisibility(View.VISIBLE);
            txtLarge.setText(view3_txtTile6.getText().toString());
            view3_txtTile6.setVisibility(View.GONE);
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

            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ArticleMagzine> doInBackground(Integer... params) {

            ArrayList<ArticleMagzine> articles = null;
            try {
                articles = wh_dmApi.getArticleMagzine(params[0], params[1]);
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
            }
            return articles;
        }

        @Override
        protected void onPostExecute(ArrayList<ArticleMagzine> result) {

            if (result != null) {
                addData(result);

            } else {
                NotificationUtil.showShortToast("Çë¼ì²éÍøÂçÁ¬½Ó", DM_MZine1Activity.this);
            }
            progressDialog.dismiss();
            super.onPostExecute(result);
        }
    }

}
