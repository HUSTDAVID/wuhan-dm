
package com.wh.dm.activity;

import com.wh.dm.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DM_PhotosDetailsActivity extends Activity {
    private ViewPager viewPager;
    private ArrayList<View> pageViews;

    private ViewGroup main;
    private ViewGroup group;
    private ImageButton btnBack;

    TextView txtBody;
    TextView txtPage;
    ImageView imgArrow;
    int totalPhotos = 0;
    int currentPhoto = 1;

    Button btnVote;

    // bottom
    RelativeLayout RelBottom1;
    RelativeLayout RelBottom2;
    EditText edtChangeToReply;
    EditText edtReply;
    Button btnReply;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        initViews();

    }

    // init views
    private void initViews() {

        LayoutInflater inflater = getLayoutInflater();
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
        txtBody = (TextView) main.findViewById(R.id.txt_photos_details_body);
        txtPage = (TextView) main.findViewById(R.id.txt_photos_details_num);
        txtPage.setText(currentPhoto + "/" + totalPhotos);
        imgArrow = (ImageView) main.findViewById(R.id.img_photos_details_arrow);

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
            }
        });

        btnReply.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                RelBottom2.setVisibility(View.GONE);
                RelBottom1.setVisibility(View.VISIBLE);
            }
        });
        btnBack =(ImageButton)findViewById(R.id.img_header3_black_back);
        btnBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();

			}

        });
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
}
