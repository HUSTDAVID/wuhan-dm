
package com.wh.dm.util;

import android.content.Context;
import android.widget.Toast;

public class NotificationUtil {

    public static void showShortToast(String temp, Context context) {

        Toast.makeText(context, temp, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(String temp, Context context) {

        Toast.makeText(context, temp, Toast.LENGTH_LONG).show();
    }
}
