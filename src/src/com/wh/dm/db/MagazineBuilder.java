
package com.wh.dm.db;

import com.wh.dm.type.Magazine;

import android.content.ContentValues;
import android.database.Cursor;

public class MagazineBuilder extends DatabaseBuilder<Magazine> {

    private static final String MAGAZINE_NO = "no";
    private static final String MAGAZINE_SID = "sid";
    private static final String MAGAZINE_CID = "cid";
    private static final String MAGAZINE_EDITOR = "editor";
    private static final String MAGAZINE_TEMPLATE = "template";
    private static final String MAGAZINE_MEMO = "memo";
    private static final String MAGAZINE_ISFEEDBACK = "isfeedback";
    private static final String MAGAZINE_LIMIT = "_limit";
    private static final String MAGAZINE_ADDTIME = "addtime";
    private static final String MAGAZINE_SNAME = "sname";
    private static final String MAGAZINE_SHORTNAME = "shortname";
    private static final String MAGAZINE_PIC = "pic";
    private static final String MAGAZINE_SPIC = "spic";
    private static final String MAGAZINE_TITLEPIC = "titlepic";

    @Override
    public Magazine build(Cursor c) {

        Magazine magazine = new Magazine();
        int columnNo = c.getColumnIndex(MAGAZINE_NO);
        int columnSid = c.getColumnIndex(MAGAZINE_SID);
        int columnCid = c.getColumnIndex(MAGAZINE_CID);
        int columnEditor = c.getColumnIndex(MAGAZINE_EDITOR);
        int columnTemplate = c.getColumnIndex(MAGAZINE_TEMPLATE);
        int columnMemo = c.getColumnIndex(MAGAZINE_MEMO);
        int columnIsfeedback = c.getColumnIndex(MAGAZINE_ISFEEDBACK);
        int columnLimit = c.getColumnIndex(MAGAZINE_LIMIT);
        int columnAddtime = c.getColumnIndex(MAGAZINE_ADDTIME);
        int columnSname = c.getColumnIndex(MAGAZINE_SNAME);
        int columnShortName = c.getColumnIndex(MAGAZINE_SHORTNAME);
        int columnPic = c.getColumnIndex(MAGAZINE_PIC);
        int columnSpic = c.getColumnIndex(MAGAZINE_SPIC);
        int columnTitlePic = c.getColumnIndex(MAGAZINE_TITLEPIC);

        magazine.setNo(c.getInt(columnNo));
        magazine.setSid(c.getInt(columnSid));
        magazine.setCid(c.getString(columnCid));
        magazine.setEditor(c.getString(columnEditor));
        magazine.setTemplate(c.getInt(columnTemplate));
        magazine.setMemo(c.getString(columnMemo));
        magazine.setIsfeedback(c.getInt(columnIsfeedback));
        magazine.setLimit(c.getInt(columnLimit));
        magazine.setAddtime(c.getString(columnAddtime));
        magazine.setSname(c.getString(columnSname));
        magazine.setShortname(c.getString(columnShortName));
        magazine.setPic(c.getString(columnPic));
        magazine.setSpic(c.getString(columnSpic));
        magazine.setTitlepic(c.getString(columnTitlePic));

        return magazine;
    }

    @Override
    public ContentValues deconstruct(Magazine magazine) {

        ContentValues values = new ContentValues();
        values.put(MAGAZINE_NO, magazine.getNo());
        values.put(MAGAZINE_SID, magazine.getSid());
        values.put(MAGAZINE_CID, magazine.getCid());
        values.put(MAGAZINE_EDITOR, magazine.getEditor());
        values.put(MAGAZINE_TEMPLATE, magazine.getTemplate());
        values.put(MAGAZINE_MEMO, magazine.getMemo());
        values.put(MAGAZINE_ISFEEDBACK, magazine.getIsfeedback());
        values.put(MAGAZINE_LIMIT, magazine.getLimit());
        values.put(MAGAZINE_ADDTIME, magazine.getAddtime());
        values.put(MAGAZINE_SNAME, magazine.getSname());
        values.put(MAGAZINE_SHORTNAME, magazine.getShortname());
        values.put(MAGAZINE_PIC, magazine.getPic());
        values.put(MAGAZINE_SPIC, magazine.getSpic());
        values.put(MAGAZINE_TITLEPIC, magazine.getTitlepic());
        return values;
    }

}
