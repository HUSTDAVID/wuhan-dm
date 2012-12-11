
package com.wh.dm.util;

import com.wh.dm.R;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String getTimeInterval(String date, Context context) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date updateDate = dateFormat.parse(date);
            Date curDate = new Date(System.currentTimeMillis());
            long diff = curDate.getTime() - updateDate.getTime();
            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = ((diff / (1000)) - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            if (day != 0) {
                return new String("" + day + context.getString(R.string.review_day));
            }
            if (hour != 0) {
                return new String("" + hour + context.getString(R.string.review_hour));
            }
            if (min != 0) {
                return new String("" + min + context.getString(R.string.review_min));
            }
            if (sec != 0) {
                return new String("" + sec + context.getString(R.string.review_sec));
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return date;
    }

    public static long getCurtime(String time) {

        Date curdate = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date = dateFormat.parse(time);
            curdate.setHours(date.getHours());
            curdate.setMinutes(date.getMinutes());
            curdate.setSeconds(date.getSeconds());
            return curdate.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    public static String showCurTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyƒÍMM‘¬dd»’   HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

}
