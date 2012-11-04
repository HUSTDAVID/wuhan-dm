
package com.wh.dm.db;

import com.wh.dm.type.PicsNews;

import android.content.ContentValues;
import android.database.Cursor;

public class PicsNewsBuilder extends DatabaseBuilder<PicsNews> {

    private static final String News_No = "no";
    private static final String News_Id = "id";
    private static final String News_Typeid = "typeid";
    private static final String News_Title = "title";
    private static final String News_Litpic = "litpic";
    private static final String News_Sortrank = "sortrank";
    private static final String News_Isfirst = "isfirst";
    private static final String News_Ishot = "ishot";
    private static final String News_Isflash = "isflash";
    private static final String News_Date = "date";
    private static final String News_IsUrl = "isUrl";

    @Override
    public PicsNews build(Cursor c) {

        int columnNo = c.getColumnIndex(News_No);
        int columnId = c.getColumnIndex(News_Id);
        int columnTypeid = c.getColumnIndex(News_Typeid);
        int columnTitle = c.getColumnIndex(News_Title);
        int columnLitpic = c.getColumnIndex(News_Litpic);
        int columnSortrank = c.getColumnIndex(News_Sortrank);
        int columnIsfirst = c.getColumnIndex(News_Isfirst);
        int columnIshot = c.getColumnIndex(News_Ishot);
        int columnIsflash = c.getColumnIndex(News_Isflash);
        int columnDate = c.getColumnIndex(News_Date);
        int columnIsUrl = c.getColumnIndex(News_IsUrl);

        PicsNews news = new PicsNews();
        news.setNo(c.getInt(columnNo));
        news.setId(c.getInt(columnId));
        news.setTypeId(c.getInt(columnTypeid));
        news.setTite(c.getString(columnTitle));
        news.setLitpic(c.getString(columnLitpic));
        news.setSortrank(c.getInt(columnSortrank));
        news.setIsfirst(c.getInt(columnIsfirst) == 1 ? true : false);
        news.setIshot(c.getInt(columnIshot) == 1 ? true : false);
        news.setIsflash(c.getInt(columnIsflash) == 1 ? true : false);
        news.setDate(c.getString(columnDate));
        news.setIsUrl(c.getString(columnIsUrl));

        return news;
    }

    @Override
    public ContentValues deconstruct(PicsNews t) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(News_No, t.getNo());
        contentValues.put(News_Id, t.getId());
        contentValues.put(News_Typeid, t.getTypeId());
        contentValues.put(News_Title, t.getTitle());
        contentValues.put(News_Litpic, t.getLitpic());
        contentValues.put(News_Sortrank, t.getSortrank());
        contentValues.put(News_Isfirst, t.getIsfirst());
        contentValues.put(News_Ishot, t.getIshot());
        contentValues.put(News_Isflash, t.getIsflash());
        contentValues.put(News_Date, t.getDate());
        contentValues.put(News_IsUrl, t.getIsUrl());
        return contentValues;
    }

}
