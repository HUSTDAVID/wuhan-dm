
package com.wh.dm.db;

import com.wh.dm.type.PictureMagzine;

import android.content.ContentValues;
import android.database.Cursor;

public class PictureMagzineBuilder extends DatabaseBuilder<PictureMagzine> {

    private static final String PictureMagzine_Sid = "sid";
    private static final String PictureMagzine_Id = "id";
    private static final String PictureMagzine_des = "description";
    private static final String PictureMagzine_Pic = "pic";
    private static final String PictureMagzine_Addtime = "addtime";

    @Override
    public PictureMagzine build(Cursor c) {

        int ColumnSid = c.getColumnIndex(PictureMagzine_Sid);
        int ColumnId = c.getColumnIndex(PictureMagzine_Id);
        int ColumnDes = c.getColumnIndex(PictureMagzine_des);
        int ColumnPic = c.getColumnIndex(PictureMagzine_Pic);
        int ColumnAddtime = c.getColumnIndex(PictureMagzine_Addtime);

        PictureMagzine pictureMagzine = new PictureMagzine();

        pictureMagzine.setSid(c.getInt(ColumnSid));
        pictureMagzine.setId(c.getInt(ColumnId));
        pictureMagzine.setDescription(c.getString(ColumnDes));
        pictureMagzine.setPic(c.getString(ColumnPic));
        pictureMagzine.setAddtime(c.getString(ColumnAddtime));

        return pictureMagzine;
    }

    @Override
    public ContentValues deconstruct(PictureMagzine t) {

        ContentValues values = new ContentValues();
        values.put(PictureMagzine_Sid, t.getSid());
        values.put(PictureMagzine_Id, t.getId());
        values.put(PictureMagzine_des, t.getDescription());
        values.put(PictureMagzine_Pic, t.getPic());
        values.put(PictureMagzine_Addtime, t.getAddtime());

        return values;
    }

}
