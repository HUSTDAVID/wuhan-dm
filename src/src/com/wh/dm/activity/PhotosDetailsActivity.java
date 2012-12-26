
package com.wh.dm.activity;

import com.umeng.analytics.MobclickAgent;
import com.wh.dm.R;
import com.wh.dm.WH_DMApi;
import com.wh.dm.WH_DMApp;
import com.wh.dm.WH_DMHttpApiV1;
import com.wh.dm.type.PhotoDetails;
import com.wh.dm.type.PostResult;
import com.wh.dm.util.FileUtil;
import com.wh.dm.util.NotificationUtil;
import com.wh.dm.util.UrlImageViewHelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.InputStream;
import java.util.ArrayList;

public class PhotosDetailsActivity extends Activity {
    private ViewPager viewPager;
    private ArrayList<View> pageViews;

    private ViewGroup main;
    private ViewGroup group;
    private ImageButton btnBack;

    TextView txtFcounts;
    TextView txtBody;
    TextView txtTitle;
    TextView txtPage;
    ImageView imgArrow;
    int totalPhotos = 1;
    int currentPhoto = 1;
    private LinearLayout layoutLoad;

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
    private static final int MSG_LOAD_IMAGE = 2;
    private static final int MSG_ADD_REVIEW = 3;
    private static final int MSG_STORE_IMAGE = 4;
    private GetPhotoDetailsTask getPhotoDetailsTask = null;
    private LoadImageTask loadImageTask = null;
    private AddReviewTask addReviewTask = null;
    private AddFavTask addFavTask = null;
    private ArrayList<PhotoDetails> photosDetails;
    private int aid;
    private int id;
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
                case MSG_LOAD_IMAGE:
                    if (loadImageTask != null) {
                        loadImageTask.cancel(true);
                        loadImageTask = null;
                    }
                    loadImageTask = new LoadImageTask();
                    loadImageTask.execute(photosDetails.get(currentPhoto - 1).getPic());
                    break;
                case MSG_ADD_REVIEW:
                    if (addReviewTask != null) {
                        addReviewTask.cancel(true);
                        addReviewTask = null;
                    }
                    addReviewTask = new AddReviewTask();
                    addReviewTask.execute();
                    break;
                case MSG_STORE_IMAGE:
                    if (addFavTask != null) {
                        addFavTask.cancel(true);
                        addFavTask = null;
                    }
                    addFavTask = new AddFavTask();
                    addFavTask.execute(aid);
                    break;
                case DownloadActivity.MSG_LOAD_ONE_IMAGE:
                    UrlImageViewHelper.isLoad = false;
                    layoutLoad.setVisibility(View.GONE);
                    break;

            }

            super.handleMessage(msg);
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.onError(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        initViews();

    }

    @Override
    public void onBackPressed() {

        if (RelBottom2.getVisibility() == View.VISIBLE) {
            RelBottom2.setVisibility(View.GONE);
            RelBottom1.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
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

    // init views
    private void initViews() {

        wh_dmApp = (WH_DMApp) this.getApplication();
        wh_dmApi = wh_dmApp.getWH_DMApi();
        aid = getIntent().getIntExtra("aid", 0);
        inflater = getLayoutInflater();
        pageViews = new ArrayList<View>();

        main = (ViewGroup) inflater.inflate(R.layout.activity_photosmain, null);
        UrlImageViewHelper.isLoad = true;
        layoutLoad = (LinearLayout) main.findViewById(R.id.picture_load);

        group = (ViewGroup) main.findViewById(R.id.viewGroup);
        viewPager = (ViewPager) main.findViewById(R.id.guidePages);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(12, 12);
        params.setMargins(0, 0, 20, 10);

        setContentView(main);

        viewPager.setAdapter(new GuidePageAdapter());
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());

        //
        RelativeLayout rel = (RelativeLayout) main.findViewById(R.id.rel_photosmain_details);

        txtFcounts = (TextView) main.findViewById(R.id.txt_total_black_reply);
        txtTitle = (TextView) main.findViewById(R.id.txt_photos_details_title);
        txtBody = (TextView) main.findViewById(R.id.txt_photos_details_body);
        txtPage = (TextView) main.findViewById(R.id.txt_photos_details_num);
        txtPage.setText(currentPhoto + "/" + totalPhotos);
        imgArrow = (ImageView) main.findViewById(R.id.img_photos_details_arrow);

        txtFcounts.setText(getIntent().getIntExtra("fcount", 0)
                + getResources().getString(R.string.reply));
        txtBody.setText(getIntent().getStringExtra("description"));
        txtTitle.setText(getIntent().getStringExtra("title"));
        txtFcounts.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PhotosDetailsActivity.this, PhotoReplyActivity.class);
                intent.putExtra("id", aid);
                startActivity(intent);

            }
        });

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

                handler.sendEmptyMessage(MSG_ADD_REVIEW);
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

                String share = "";
                String head = getResources().getString(R.string.share_photo);
                String info = "\"" + txtTitle.getText().toString() + "\""
                        + getString(R.string.news_url_main) + aid;
                share = head + info;
                Intent intent = new Intent(PhotosDetailsActivity.this, ShareActivity.class);
                intent.putExtra("share", share);
                startActivity(intent);
            }
        });

        btnDownload = (Button) main.findViewById(R.id.btn_photos_down);
        btnDownload.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                handler.sendEmptyMessage(MSG_LOAD_IMAGE);
            }
        });
        btnStore = (Button) main.findViewById(R.id.btn_photos_favorite);
        btnStore.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (WH_DMApp.isConnected) {
                    if (WH_DMApp.isLogin) {
                        handler.sendEmptyMessage(MSG_STORE_IMAGE);
                    } else {
                        NotificationUtil.showShortToast(getString(R.string.please_login),
                                PhotosDetailsActivity.this);
                        Intent intent = new Intent(PhotosDetailsActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    NotificationUtil.showShortToast(getString(R.string.check_network),
                            PhotosDetailsActivity.this);
                }

            }

        });

        handler.sendEmptyMessage(MSG_GET_ALL);
    }

    public String getFcontent() {

        return edtReply.getText().toString();

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

            // TODO
            layoutLoad.setVisibility(View.VISIBLE);
            UrlImageViewHelper.isLoad = true;
            currentPhoto = 1 + arg0;
            ImageView imageView = (ImageView) pageViews.get(arg0).findViewById(R.id.img_photos);
            UrlImageViewHelper.setUrlDrawable(imageView, WH_DMHttpApiV1.URL_DOMAIN
                    + photosDetails.get(arg0).getPic(), R.drawable.photo_details_default, null);
            txtPage.setText(currentPhoto + "/" + totalPhotos);
            txtBody.setText(photosDetails.get(arg0).getDescription());

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

                pageViews.clear();
                totalPhotos = result.size();
                txtPage.setText(currentPhoto + "/" + totalPhotos);
                for (int i = 0; i < result.size(); i++) {
                    View view = inflater.inflate(R.layout.activity_photos_details, null);
                    pageViews.add(view);
                }
                ImageView imageView = (ImageView) pageViews.get(0).findViewById(R.id.img_photos);
                // UrlImageViewHelper.setUrlDrawable(imageView,
                // WH_DMHttpApiV1.URL_DOMAIN
                // + photosDetails.get(0).getPic(),
                // R.drawable.photo_details_default, null);
                UrlImageViewHelper.isLoad = true;
                UrlImageViewHelper.sendFinishMsg(imageView, WH_DMHttpApiV1.URL_DOMAIN
                        + photosDetails.get(0).getPic(), R.drawable.photo_details_default, null,
                        handler);
                txtPage.setText(currentPhoto + "/" + totalPhotos);
                txtBody.setText(photosDetails.get(0).getDescription());

            } else {

            }
            super.onPostExecute(result);
        }
    }

    // task for load and save image
    class LoadImageTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            boolean isSave = false;
            InputStream is = null;
            try {
                String imageUrl = WH_DMHttpApiV1.URL_DOMAIN + params[0];
                is = FileUtil.getImageStream(imageUrl);
                if (is != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    isSave = FileUtil.savePicture(bitmap);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return isSave;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {
                NotificationUtil.showShortToast(
                        getResources().getString(R.string.load_image_Success),
                        PhotosDetailsActivity.this);
            } else {
                NotificationUtil.showShortToast(getResources().getString(R.string.load_image_fail),
                        PhotosDetailsActivity.this);
            }
            super.onPostExecute(result);
        }

    }

    private class AddReviewTask extends AsyncTask<String, Void, Boolean> {
        boolean result = false;
        Exception reason = null;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                result = wh_dmApi.addPhotoReview(getFcontent(), aid);
                return true;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {
                NotificationUtil.showShortToast(getString(R.string.review_succeed),
                        PhotosDetailsActivity.this);
            } else {
                NotificationUtil.showShortToast(getString(R.string.review_fail),
                        PhotosDetailsActivity.this);
            }
            super.onPostExecute(result);
        }

    }

    private class AddFavTask extends AsyncTask<Integer, Void, Boolean> {
        boolean result = false;
        Exception reason = null;
        PostResult postresult = null;

        @Override
        protected void onPreExecute() {

            // progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {

            // TODO Auto-generated method stub
            try {
                postresult = wh_dmApi.addFav(params[0], 1);
                if (postresult.getResult())
                    return true;
                else
                    return false;
            } catch (Exception e) {
                reason = e;
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {
                CollectPhotoActivity.isNewCollect = true;
                NotificationUtil.showShortToast(getString(R.string.favorite_succeed),
                        PhotosDetailsActivity.this);
            } else {
                if (postresult == null)
                    NotificationUtil.showShortToast(getString(R.string.favorite_fail) + ":Î´ÖªÔ­Òò",
                            PhotosDetailsActivity.this);
                else
                    NotificationUtil
                            .showShortToast(postresult.getMsg(), PhotosDetailsActivity.this);
            }
            // progressDialog.dismiss();
            super.onPostExecute(result);
        }

    }

}
