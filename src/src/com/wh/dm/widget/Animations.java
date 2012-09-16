
package com.wh.dm.widget;

import com.wh.dm.R;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Animations {
    Animation DelDown, DelUp;

    public Animation getDownAnimation(Context context) {

        return AnimationUtils.loadAnimation(context, R.anim.del_down);
    }
}
