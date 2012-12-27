
package com.wh.dm.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;

public class BannerListView extends ListView {

    private GestureDetector gestureDetector;

    public BannerListView(Context context) {

        super(context);
    }

    public BannerListView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public void setGestureDetector(GestureDetector gestureDetector) {

        this.gestureDetector = gestureDetector;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int position = pointToPosition(x, y);
        if (position == 0) {
            gestureDetector.onTouchEvent(ev);
            return true;
        }
        return super.onTouchEvent(ev);
    }

}
