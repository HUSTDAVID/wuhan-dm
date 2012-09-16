
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
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                R.drawable.temp_subscription_bigimg);
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0)
                adapter.addItem(bmp, "й╠ип", false);
            else
                adapter.addItem(bmp, "й╠ип", true);
        }

        gvSub.setAdapter(adapter);
    }
}
