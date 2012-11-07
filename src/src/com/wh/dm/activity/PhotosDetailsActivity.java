
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.umeng.api.sns.UMSnsService;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.type.PhotoDetails;
import com.wh.dm.util.UrlImageViewHelper;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PhotosDetailsActivity extends Activity {
    private ViewPager viewPager;
    private ArrayList<View> pageViews;

    private ViewGroup main;
    private ViewGroup group;
    private ImageButton btnBack;

    TextView txtBody;
    TextView txtTitle;
    TextView txtPage;
    ImageView imgArrow;
    int totalPhotos = 0;
    int currentPhoto = 1;

    LayoutInflater inflater;

    // bottom
    RelativeLayout RelBottom1;
    RelativeLayout RelBottom2;
    EditText edtChangeToReply;
    EditText edtReply;
    Button btnReply;
    Button btnDownload;
    Button btnStore;
    Button btnShare;

    private static final int MSG_GET_ALL = 0;
    private static final int MSG_GET_ONG_IMG = 1;
    private GetPhotoDetailsTask getPhotoDetailsTask = null;
    private ArrayList<PhotoDetails> photosDetails;
    private int aid;
    private WH_DMApp wh_dmApp;
    private WH_DMApi wh_dmApi;
    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_GET_ALL:
                    if (getPhotoDetailsTask != null) {
                        getPhotoDetailsTask.cancel(true);
                        getPhotoDetailsTask = null;
                    }
                    getPhotoDetailsTask = new GetPhotoDetailsTask();
                    getPhotoDetailsTask.execute(aid);
                    break;
                case MSG_GET_ONG_IMG:
                    break;
            }

            super.handleMessage(msg);
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        initViews();

    }

    public void onResume() {

        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {

        super.onPause();
        MobclickAgent.onPause(this);
    }

    // init views
    private void initViews() {

        wh_dmApp = (WH_DMApp) this.getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        aid = getIntent().getIntExtra("aid", 0);
        inflater = getLayoutInflater();
        View v1 = inflater.inflate(R.layout.activity_photos_details, null);
        View v2 = inflater.inflate(R.layout.activity_photos_details1, null);
        View v3 = inflater.inflate(R.layout.activity_photos_details, null);
        View v4 = inflater.inflate(R.layout.activity_photos_details1, null);
        View v5 = inflater.inflate(R.layout.activity_photos_details, null);
        View v6 = inflater.inflate(R.layout.activity_photos_details1, null);
        View v7 = inflater.inflate(R.layout.activity_photos_details, null);
        View v8 = inflater.inflate(R.layout.activity_photos_details1, null);
        View v9 = inflater.inflate(R.layout.activity_photos_details, null);

        totalPhotos = 9;

        pageViews = new ArrayList<View>();
        pageViews.add(v1);
        pageViews.add(v2);
        pageViews.add(v3);
        pageViews.add(v4);
        pageViews.add(v5);
        pageViews.add(v6);
        pageViews.add(v7);
        pageViews.add(v8);
        pageViews.add(v9);

        main = (ViewGroup) inflater.inflate(R.layout.activity_photosmain, null);

        group = (ViewGroup) main.findViewById(R.id.viewGroup);
        viewPager = (ViewPager) main.findViewById(R.id.guidePages);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(12, 12);
        params.setMargins(0, 0, 20, 10);

        setContentView(main);

        viewPager.setAdapter(new GuidePageAdapter());
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());

        //
        RelativeLayout rel = (RelativeLayout) main.findViewById(R.id.rel_photosmain_details);

        txtTitle = (TextView) main.findViewById(R.id.txt_photos_details_title);
        txtBody = (TextView) main.findViewById(R.id.txt_photos_details_body);
        txtPage = (TextView) main.findViewById(R.id.txt_photos_details_num);
        txtPage.setText(currentPhoto + "/" + totalPhotos);
        imgArrow = (ImageView) main.findViewById(R.id.img_photos_details_arrow);

        txtBody.setText(getIntent().getStringExtra("description"));
        txtTitle.setText(getIntent().getStringExtra("title"));

        rel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                int visility = txtBody.getVisibility();
                if (visility == View.VISIBLE) {
                    txtBody.setVisibility(View.GONE);
                    imgArrow.setImageResource(R.drawable.photos_arrow_up);
                } else if (visility == View.GONE) {
                    txtBody.setVisibility(View.VISIBLE);
                    imgArrow.setImageResource(R.drawable.photos_arrow_down);
                }

            }
        });

        // bottom
        RelBottom1 = (RelativeLayout) main.findViewById(R.id.layout_photos_detail_bottom1);
        RelBottom2 = (RelativeLayout) main.findViewById(R.id.layout_photos_detail_bottom2);
        edtChangeToReply = (EditText) main.findViewById(R.id.edtx_photos_my_reply);
        edtReply = (EditText) main.findViewById(R.id.edt_photos_details_input);
        btnReply = (Button) main.findViewById(R.id.btn_photos_details_reply);

        edtChangeToReply.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                RelBottom1.setVisibility(View.GONE);
                RelBottom2.setVisibility(View.VISIBLE);

                edtReply.requestFocus();
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(
                        edtReply, 0);
            }
        });

        btnReply.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                RelBottom2.setVisibility(View.GONE);
                RelBottom1.setVisibility(View.VISIBLE);
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(edtReply.getWindowToken(), 0);
            }
        });
        btnBack = (ImageButton) findViewById(R.id.img_header3_black_back);
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });

        btnShare = (Button) main.findViewById(R.id.btn_photos_share);
        btnShare.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String head = getResources().getString(R.string.share_info);
                String info = txtTitle.getText().toString();
                UMSnsService.share(PhotosDetailsActivity.this, head + info, null);
            }
        });

        btnDownload = (Button) main.findViewById(R.id.btn_photos_down);
        btnDownload.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub

            }
        });

        handler.sendEmptyMessage(MSG_GET_ALL);
    }

    private class TextViewOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {

            // TODO Auto-generated method stub

        }
    }

    private class GuidePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {

            return pageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {

            // TODO Auto-generated method stub
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {

            // TODO Auto-generated method stub
            ((ViewPager) arg0).removeView(pageViews.get(arg1));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {

            // TODO Auto-generated method stub
            ((ViewPager) arg0).addView(pageViews.get(arg1));
            return pageViews.get(arg1);
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

        @Override
        public void finishUpdate(View arg0) {

            // TODO Auto-generated method stub

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

            currentPhoto = 1 + arg0;
            txtPage.setText(currentPhoto + "/" + totalPhotos);

        }
    }

    private class GetPhotoDetailsTask extends AsyncTask<Integer, Void, ArrayList<PhotoDetails>> {

        Exception reason = null;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected ArrayList<PhotoDetails> doInBackground(Integer... params) {

            try {
                photosDetails = wh_dmApi.getPhotoDetails(aid);
                return photosDetails;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(ArrayList<PhotoDetails> result) {

            if (result != null && result.size() > 0) {

                View v1 = inflater.inflate(R.layout.activity_photos_details, null);
                ImageView inflater = (ImageView) v1.findViewById(R.id.img_photos);
                UrlImageViewHelper.setUrlDrawable(inflater,
                        WH_DMHttpApiV1.URL_DOMAIN + result.get(0).getPic(),
                        R.drawable.item_default, null);
            } else {

            }
            super.onPostExecute(result);
        }

    }
}
