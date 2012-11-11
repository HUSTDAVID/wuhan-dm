
package com.wh.dm.db;

import com.wh.dm.type.PhotoDetails;

import android.content.ContentValues;
import android.database.Cursor;

public class PhotoDetailsBuilder extends DatabaseBuilder<PhotoDetails> {

    private static final String PhotoDet_No = "no";
    private static final String PhotoDet_Id = "id";
    private static final String PhotoDet_Aid = "aid";
    private static final String PhotoDet_Cover = "cover";
    private static final String PhotoDet_Click = "click";
    private static final String PhotoDet_Description = "description";
    private static final String PhotoDet_Ordernum = "ordernum";
    private static final String PhotoDet_Pic = "pic";
    private static final String PhotoDet_Addtime = "addtime";
    private static final String PhotoDet_Edittime = "edittime";
    private static final String PhotoDet_Ext1 = "ext1";
    private static final String PhotoDet_Ext2 = "ext2";
    private static final String PhotoDet_Ext3 = "ext3";

    @Override
    public PhotoDetails build(Cursor c) {

        int colunmNo = c.getColumnIndex(PhotoDet_No);
        int colunmId = c.getColumnIndex(PhotoDet_Id);
        int colunmAid = c.getColumnIndex(PhotoDet_Aid);
        int colunmCover = c.getColumnIndex(PhotoDet_Cover);
        int colunmClick = c.getColumnIndex(PhotoDet_Click);
        int colunmDescription = c.getColumnIndex(PhotoDet_Description);
        int colunmOrdernum = c.getColumnIndex(PhotoDet_Ordernum);
        int colunmPic = c.getColumnIndex(PhotoDet_Pic);
        int colunmAddtime = c.getColumnIndex(PhotoDet_Addtime);
        int colunmEdittime = c.getColumnIndex(PhotoDet_Edittime);
        int colunmExt1 = c.getColumnIndex(PhotoDet_Ext1);
        int colunmExt2 = c.getColumnIndex(PhotoDet_Ext2);
        int colunmExt3 = c.getColumnIndex(PhotoDet_Ext3);

        PhotoDetails photoDet = new PhotoDetails();
        photoDet.setNo(c.getInt(colunmNo));
        photoDet.setId(c.getInt(colunmId));
        photoDet.setAid(c.getInt(colunmAid));
        photoDet.setCover(c.getInt(colunmCover));
        photoDet.setClick(c.getInt(colunmClick));
        photoDet.setDescription(c.getString(colunmDescription));
        photoDet.setOrdernum(c.getInt(colunmOrdernum));
        photoDet.setPic(c.getString(colunmPic));
        photoDet.setAddtime(c.getString(colunmAddtime));
        photoDet.setEdittime(c.getString(colunmEdittime));
        photoDet.setExt1(c.getInt(colunmExt1));
        photoDet.setExt2(c.getString(colunmExt2));
        photoDet.setExt3(c.getString(colunmExt3));

        return photoDet;
    }

    @Override
    public ContentValues deconstruct(PhotoDetails photoDet) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(PhotoDet_No, photoDet.getNo());
        contentValues.put(PhotoDet_Id, photoDet.getId());
        contentValues.put(PhotoDet_Aid, photoDet.getAid());
        contentValues.put(PhotoDet_Cover, photoDet.getCover());
        contentValues.put(PhotoDet_Click, photoDet.getClick());
        contentValues.put(PhotoDet_Description, photoDet.getDescription());
        contentValues.put(PhotoDet_Ordernum, photoDet.getOrdernum());
        contentValues.put(PhotoDet_Pic, photoDet.getPic());
        contentValues.put(PhotoDet_Addtime, photoDet.getAddtime());
        contentValues.put(PhotoDet_Edittime, photoDet.getEdittime());
        contentValues.put(PhotoDet_Ext1, photoDet.getExt1());
        contentValues.put(PhotoDet_Ext2, photoDet.getExt2());
        contentValues.put(PhotoDet_Ext3, photoDet.getExt3());

        return contentValues;
    }

}
