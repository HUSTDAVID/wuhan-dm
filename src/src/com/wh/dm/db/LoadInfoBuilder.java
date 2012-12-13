
package com.wh.dm.db;

import com.wh.dm.type.LoadInfo;

import android.content.ContentValues;
import android.database.Cursor;

public class LoadInfoBuilder extends DatabaseBuilder<LoadInfo> {

    private static final String LoadInfo_Sid = "sid";
    private static final String LoadInfo_PicPath = "pic_path";
    private static final String LoadInfo_Title = "title";
    private static final String LoadInfo_IsFinish = "is_finish";
    private static final String LoadInfo_IsStart = "is_start";
    private static final String LoadInfo_Pro = "pro";

    @Override
    public LoadInfo build(Cursor c) {

        int ColumnSid = c.getColumnIndex(LoadInfo_Sid);
        int ColumnPicPath = c.getColumnIndex(LoadInfo_PicPath);
        int ColumnTitle = c.getColumnIndex(LoadInfo_Title);
        int ColumnIsFinish = c.getColumnIndex(LoadInfo_IsFinish);
        int ColumnIsStart = c.getColumnIndex(LoadInfo_IsStart);
        int ColumnPro = c.getColumnIndex(LoadInfo_Pro);

        LoadInfo info = new LoadInfo();
        info.setSid(c.getInt(ColumnSid));
        info.setPicPath(c.getString(ColumnPicPath));
        info.setTitle(c.getString(ColumnTitle));
        info.setFinish(c.getInt(ColumnIsFinish) == 0 ? false : true);
        info.setStart(c.getInt(ColumnIsStart) == 0 ? false : true);
        info.setPro(c.getInt(ColumnPro));

        return info;
    }

    @Override
    public ContentValues deconstruct(LoadInfo t) {

        ContentValues values = new ContentValues();
        values.put(LoadInfo_Sid, t.getSid());
        values.put(LoadInfo_PicPath, t.getPicPath());
        values.put(LoadInfo_Title, t.getTitle());
        values.put(LoadInfo_IsFinish, t.isFinish() == true ? 1 : 0);
        values.put(LoadInfo_IsStart, t.isStart() == true ? 1 : 0);
        values.put(LoadInfo_Pro, t.getPro());

        return values;
    }
}
