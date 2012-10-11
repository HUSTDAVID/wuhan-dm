
package com.wh.dm.activity;

import com.wh.dm.R;
import com.wh.dm.widget.SubscribeAdapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.widget.GridView;

public class DM_Sub_HotActivity extends Activity {

    GridView gvSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sub_item);

        initViews();
    }

    private void initViews() {

        gvSub = (GridView) findViewById(R.id.gv_subscribe);

        SubscribeAdapter adapter = new SubscribeAdapter(this);

        Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.tempdm1);
        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.tempdm2);
        Bitmap bmp3 = BitmapFactory.decodeResource(getResources(), R.drawable.dm_temp1);
        Bitmap bmp4 = BitmapFactory.decodeResource(getResources(), R.drawable.dm_temp2);
        Bitmap bmp5 = BitmapFactory.decodeResource(getResources(), R.drawable.dm_temp3);
        Bitmap bmp6 = BitmapFactory.decodeResource(getResources(), R.drawable.fashion_travel);
        adapter.addItem(bmp3, "����", false);
        adapter.addItem(bmp4, "������", false);
        adapter.addItem(bmp5, "�й�ʯ��ʯ��", false);
        adapter.addItem(bmp6, "ʱ������", false);
        adapter.addItem(bmp1, "�ⲩ��", true);
        adapter.addItem(bmp2, "���չ��", true);

        gvSub.setAdapter(adapter);
    }
}
