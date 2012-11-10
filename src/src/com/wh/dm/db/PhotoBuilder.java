
package com.wh.dm.db;

import com.wh.dm.type.Photo;

import android.content.ContentValues;
import android.database.Cursor;

public class PhotoBuilder extends DatabaseBuilder<Photo> {

    private static final String Photo_No = "no";
    private static final String Photo_Aid = "aid";
    private static final String Photo_Aname = "aname";
    private static final String Photo_Title = "title";
    private static final String Photo_Litpic = "litpic";
    private static final String Photo_Atype = "atype";
    private static final String Photo_Author = "author";
    private static final String Photo_Click = "click";
    private static final String Photo_Description = "description";
    private static final String Photo_Isgood = "isgood";
    private static final String Photo_Istop = "istop";
    private static final String Photo_Isflash = "isflash";
    private static final String Photo_Ordernum = "ordernum";
    private static final String Photo_Addtime = "addtime";
    private static final String Photo_Pcount = "pcount";
    private static final String Photo_Fcount = "fcount";
    private static final String Photo_Cname = "cname";

    @Override
    public Photo build(Cursor c) {

        int columnNo = c.getColumnIndex(Photo_No);
        int columnAid = c.getColumnIndex(Photo_Aid);
        int columnAname = c.getColumnIndex(Photo_Aname);
        int columnTitle = c.getColumnIndex(Photo_Title);
        int columnLitpic = c.getColumnIndex(Photo_Litpic);
        int columnAtype = c.getColumnIndex(Photo_Atype);
        int columnAuthor = c.getColumnIndex(Photo_Author);
        int columnClick = c.getColumnIndex(Photo_Click);
        int columnDescription = c.getColumnIndex(Photo_Description);
        int columnIsgood = c.getColumnIndex(Photo_Isgood);
        int columnistop = c.getColumnIndex(Photo_Istop);
        int columnisflash = c.getColumnIndex(Photo_Isflash);
        int columnOrderNum = c.getColumnIndex(Photo_Ordernum);
        int columnAddTime = c.getColumnIndex(Photo_Addtime);
        int conlumnPcount = c.getColumnIndex(Photo_Pcount);
        int conlumnFcount = c.getColumnIndex(Photo_Fcount);
        int conlumnCname = c.getColumnIndex(Photo_Cname);

        Photo photo = new Photo();

        photo.setNo(c.getInt(columnNo));
        photo.setAid(c.getInt(columnAid));
        photo.setAname(c.getString(columnAname));
        photo.setTitle(c.getString(columnTitle));
        photo.setLitpic(c.getString(columnLitpic));
        photo.setAtype(c.getInt(columnAtype));
        photo.setAuthor(c.getString(columnAuthor));
        photo.setClick(c.getInt(columnClick));
        photo.setDescription(c.getString(columnDescription));
        photo.setIsgood(c.getInt(columnIsgood));
        photo.setIstop(c.getInt(columnistop));
        photo.setIsflash(c.getInt(columnisflash));
        photo.setOrdernum(c.getInt(columnOrderNum));
        photo.setAddtime(c.getString(columnAddTime));
        photo.setPcount(c.getInt(conlumnPcount));
        photo.setFcount(c.getInt(conlumnFcount));
        photo.setCname(c.getString(conlumnCname));

        return photo;
    }

    @Override
    public ContentValues deconstruct(Photo photo) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Photo_No, photo.getNo());
        contentValues.put(Photo_Aid, photo.getAid());
        contentValues.put(Photo_Aname, photo.getAname());
        contentValues.put(Photo_Title, photo.getTitle());
        contentValues.put(Photo_Litpic, photo.getLitpic());
        contentValues.put(Photo_Atype, photo.getAtype());
        contentValues.put(Photo_Author, photo.getAuthor());
        contentValues.put(Photo_Click, photo.getClick());
        contentValues.put(Photo_Description, photo.getDescription());
        contentValues.put(Photo_Isgood, photo.getIsgood());
        contentValues.put(Photo_Istop, photo.getIstop());
        contentValues.put(Photo_Isflash, photo.getIsflash());
        contentValues.put(Photo_Ordernum, photo.getOrdernum());
        contentValues.put(Photo_Addtime, photo.getAddtime());
        contentValues.put(Photo_Pcount, photo.getPcount());
        contentValues.put(Photo_Fcount, photo.getFcount());
        contentValues.put(Photo_Cname, photo.getCname());
        return contentValues;
    }

}
