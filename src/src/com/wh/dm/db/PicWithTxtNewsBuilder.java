package com.wh.dm.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.wh.dm.type.PicWithTxtNews;

public class PicWithTxtNewsBuilder extends DatabaseBuilder<PicWithTxtNews>{

	private static final String News_No ="no";
	private static final String News_Id ="id";
	private static final String News_Typeid ="typeid";
	private static final String News_Title ="title";
	private static final String News_Litpic ="litpic";
	private static final String News_Description ="description";
	private static final String News_Sortrank ="sortrank";
	private static final String News_Isfirst = "isfirst";
	private static final String News_Ishot ="ishot";
	private static final String News_Date ="date";
	private static final String News_IsUrl ="isUrl";
	@Override
	public PicWithTxtNews build(Cursor c) {

		int columnNo = c.getColumnIndex(News_No);
		int columnId = c.getColumnIndex(News_Id);
		int columnTypeid = c.getColumnIndex(News_Typeid);
		int columnTitle = c.getColumnIndex(News_Title);
		int columnLitpic = c.getColumnIndex(News_Litpic);
		int columnDescription = c.getColumnIndex(News_Description);
		int columnSortrank = c.getColumnIndex(News_Sortrank);
		int columnIsfirst = c.getColumnIndex(News_Isfirst);
		int columnIshot = c.getColumnIndex(News_Ishot);
		int columnDate = c.getColumnIndex(News_Date);
		int columnIsUrl = c.getColumnIndex(News_IsUrl);

		PicWithTxtNews news = new PicWithTxtNews();
		news.setNo(c.getInt(columnNo));
		news.setId(c.getInt(columnId));
		news.setTypeId(c.getInt(columnTypeid));
		news.setTitle(c.getString(columnTitle));
		news.setLitpic(c.getString(columnLitpic));
		news.setDescription(c.getString(columnDescription));
		news.setSortrank(c.getInt(columnSortrank));
		news.setIsfirst(c.getInt(columnIsfirst)==1?true:false);
		news.setIshot(c.getInt(columnIshot)==1?true:false);
		news.setIsUrl(c.getString(columnIsUrl));

		return news;
	}

	@Override
	public ContentValues deconstruct(PicWithTxtNews t) {

		ContentValues contentValues = new ContentValues();
		contentValues.put(News_No, t.getNo());
		contentValues.put(News_Id, t.getId());
		contentValues.put(News_Typeid, t.getTypeId());
		contentValues.put(News_Title, t.getTitle());
		contentValues.put(News_Litpic, t.getLitpic());
		contentValues.put(News_Description, t.getDescription());
		contentValues.put(News_Sortrank,t.getSortrank());
		contentValues.put(News_Isfirst, t.getIsfirst());
		contentValues.put(News_Ishot, t.getIshot());
		contentValues.put(News_Date,t.getDate());
		contentValues.put(News_IsUrl,t.getIsUrl());
		return contentValues;
	}

}
