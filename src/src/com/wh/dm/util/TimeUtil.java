
package com.wh.dm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String getTimeInterval(String date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date updateDate = dateFormat.parse(date);
            Date curDate = new Date(System.currentTimeMillis());
            long diff = curDate.getTime() - updateDate.getTime();
            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            if (day != 0) {
                return new String("" + day + "��ǰ");
            }
            if (hour != 0) {
                return new String("" + hour + "Сʱǰ");
            }
            if (min != 0) {
                return new String("" + min + "����ǰ");
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return date;
    }
}
