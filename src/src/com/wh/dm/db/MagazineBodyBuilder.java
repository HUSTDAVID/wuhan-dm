
package com.wh.dm.db;

import com.wh.dm.type.MagazineBody;

import android.content.ContentValues;
import android.database.Cursor;

public class MagazineBodyBuilder extends DatabaseBuilder<MagazineBody> {

    private static final String MagBody_Sid = "sid";
    private static final String MagBody_No = "no";
    private static final String MagBody_Id = "id";
    private static final String MagBody_Title = "title";
    private static final String MagBody_Source = "source";
    private static final String MegBody_Writer = "writer";
    private static final String MegBody_Author = "author";
    private static final String MagBody_Litpic = "litpic";
    private static final String MagBody_Pubdate = "pubdate";
    private static final String MagBody_Body = "body";

    @Override
    public MagazineBody build(Cursor c) {

        int ColumnSid = c.getColumnIndex(MagBody_Sid);
        int ColumnNo = c.getColumnIndex(MagBody_No);
        int ColumnId = c.getColumnIndex(MagBody_Id);
        int ColumnTitle = c.getColumnIndex(MagBody_Title);
        int ColumnAuthor = c.getColumnIndex(MegBody_Author);
        int ColumnWriter = c.getColumnIndex(MegBody_Writer);
        int ColumnSource = c.getColumnIndex(MagBody_Source);
        int ColumnLitpic = c.getColumnIndex(MagBody_Litpic);
        int ColumnPubdate = c.getColumnIndex(MagBody_Pubdate);
        int ColumnBody = c.getColumnIndex(MagBody_Body);

        MagazineBody magazinebody = new MagazineBody();

        magazinebody.setSid(c.getInt(ColumnSid));
        magazinebody.setAuthor(c.getString(ColumnAuthor));
        magazinebody.setWriter(c.getString(ColumnWriter));
        magazinebody.setNo(c.getInt(ColumnNo));
        magazinebody.setId(c.getInt(ColumnId));
        magazinebody.setTitle(c.getString(ColumnTitle));
        magazinebody.setSource(c.getString(ColumnSource));
        magazinebody.setLitpic(c.getString(ColumnLitpic));
        magazinebody.setPubdate(c.getString(ColumnPubdate));
        magazinebody.setBody(c.getString(ColumnBody));

        return magazinebody;
    }

    @Override
    public ContentValues deconstruct(MagazineBody magazinebody) {

        ContentValues values = new ContentValues();
        values.put(MagBody_Sid, magazinebody.getSid());
        values.put(MagBody_No, magazinebody.getNo());
        values.put(MagBody_Id, magazinebody.getId());
        values.put(MagBody_Title, magazinebody.getTitle());
        values.put(MagBody_Source, magazinebody.getSource());
        values.put(MegBody_Author, magazinebody.getAuthor());
        values.put(MegBody_Writer, magazinebody.getWriter());
        values.put(MagBody_Litpic, magazinebody.getLitpic());
        values.put(MagBody_Pubdate, magazinebody.getPubdate());
        values.put(MagBody_Body, magazinebody.getBody());

        return values;
    }

}
