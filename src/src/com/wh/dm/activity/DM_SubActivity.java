
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.widget.SubscibeRightAdapter;
import com.wh.dm.widget.SubscribeLeftAdapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

public class DM_SubActivity extends Activity {

    static final int LEFT_CHANGE = 100;

    ListView lvSubLeft;
    ListView lvSubRight;
    MyHandler myHandler;

    int currentItem = 0;
    int nextItem = 0;
    int currentLeftId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_subscibe);

        initViews();
        loadDataLeft();
        loadDataRight(0);
        myHandler = new MyHandler();
    }

    private void initViews() {

        lvSubLeft = (ListView) findViewById(R.id.lv_sub_left);
        lvSubRight = (ListView) findViewById(R.id.lv_sub_right);

        TextView txtTitle = (TextView) findViewById(R.id.txt_header_title2);
        txtTitle.setText(getResources().getString(R.string.subscribe));
    }

    private void loadDataLeft() {

        String[] typeStr = {
                "房产", "汽车", "生活", "旅游", "时尚"
        };
        Bitmap bmp = (Bitmap) BitmapFactory.decodeResource(getResources(), R.drawable.sub_left1);
        Bitmap[] typeBmp = {
                bmp, bmp, bmp, bmp, bmp
        };

        SubscribeLeftAdapter leftAdapter = new SubscribeLeftAdapter(DM_SubActivity.this);
        for (int i = 0; i < typeStr.length; i++) {
            leftAdapter.addItem(typeStr[i], typeBmp[i], i);
        }
        lvSubLeft.setAdapter(leftAdapter);

        lvSubLeft.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String, Object> map = (HashMap<String, Object>) parent.getAdapter()
                        .getItem(position);
                currentLeftId = (Integer) map.get("id");

                /*
                 * parent.getChildAt(currentItem).setBackgroundColor(
                 * getResources().getColor(R.color.sub_left_bg));
                 * view.setBackgroundColor
                 * (getResources().getColor(R.color.sub_right_bg)); nextItem =
                 * position;
                 */
                nextItem = position;
                myHandler.sendEmptyMessage(LEFT_CHANGE);

            }
        });

    }

    private void loadDataRight(int leftId) {

        String[] leftStr = {
                "房产", "汽车", "生活", "旅游", "时尚"
        };
        String[] rightStr = new String[10];

        SubscibeRightAdapter rightAdapter = new SubscibeRightAdapter(DM_SubActivity.this);
        for (int i = 0; i < 10; i++) {
            rightStr[i] = "这里是" + leftStr[leftId] + i;
            rightAdapter.addItem(rightStr[i], false, leftId);
        }

        lvSubRight.setAdapter(rightAdapter);
    }

    class MyHandler extends Handler {

        public MyHandler() {

        }

        public MyHandler(Looper l) {

        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case LEFT_CHANGE:
                    lvSubLeft.getChildAt(currentItem).setBackgroundColor(
                            getResources().getColor(R.color.sub_left_bg));
                    lvSubLeft.getChildAt(nextItem).setBackgroundColor(
                            getResources().getColor(R.color.sub_right_bg));
                    currentItem = nextItem;
                    loadDataRight(currentLeftId);
                    break;
            }

        }

    }
}
