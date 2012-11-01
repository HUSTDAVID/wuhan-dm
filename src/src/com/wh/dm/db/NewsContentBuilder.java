package com.wh.dm.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.wh.dm.type.NewsContent;

public class NewsContentBuilder extends DatabaseBuilder<NewsContent> {

	private static final String News_No = "no";
	private static final String News_Id = "id";
	private static final String News_Typeid = "typeid";
	private static final String News_Sortrank = "sortrank";
	private static final String News_Title = "title";
	private static final String News_Source = "source";
	private static final String News_Litpic = "litpic";
	private static final String News_Pubdate = "pubdate";
	private static final String News_Isfirst = "isfirst";
	private static final String News_Ishot = "ishot";
	private static final String News_IsUrl = "isUrl";
	private static final String News_Body = "body";

	@Override
	public NewsContent build(Cursor c) {
		int columnNo = c.getColumnIndex(News_No);
		int columnId = c.getColumnIndex(News_Id);
		int columnTypeid = c.getColumnIndex(News_Typeid);
		int columnSortrank = c.getColumnIndex(News_Sortrank);
		int columnTitle = c.getColumnIndex(News_Title);
		int columnSource = c.getColumnIndex(News_Source);
		int columnLitpic = c.getColumnIndex(News_Litpic);
		int columnPubdate = c.getColumnIndex(News_Pubdate);
		int columnIsfirst = c.getColumnIndex(News_Isfirst);
		int columnIshot = c.getColumnIndex(News_Ishot);
		int columnIsUrl = c.getColumnIndex(News_IsUrl);
		int columnBody = c.getColumnIndex(News_Body);

		NewsContent newsContent = new NewsContent();
		newsContent.setNo(c.getInt(columnNo));
		newsContent.setId(c.getInt(columnId));
		newsContent.setTypeid(c.getInt(columnTypeid));
		newsContent.setSortrank(c.getInt(columnSortrank));
		newsContent.setTitle(c.getString(columnTitle));
		newsContent.setSource(c.getString(columnSource));
		newsContent.setLitpic(c.getString(columnLitpic));
		newsContent.setPubdate(c.getString(columnPubdate));
		newsContent.setIsfirst(c.getInt(columnIsfirst) == 1 ? true : false);
		newsContent.setIshot(c.getInt(columnIshot) == 1 ? true : false);
		newsContent.setIsurl(c.getString(columnIsUrl));
		newsContent.setBody(c.getString(columnBody));
		return newsContent;
	}

	@Override
	public ContentValues deconstruct(NewsContent t) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(News_No,t.getNo());
		contentValues.put(News_Id, t.getId());
		contentValues.put(News_Typeid, t.getTypeid());
		contentValues.put(News_Sortrank,t.getSortrank());
		contentValues.put(News_Title,t.getTitle());
		contentValues.put(News_Source, t.getSource());
		contentValues.put(News_Litpic, t.getLitpic());
		contentValues.put(News_Pubdate, t.getPubdate());
		contentValues.put(News_Isfirst,t.getIsfirst());
		contentValues.put(News_Ishot, t.getIshot());
		contentValues.put(News_IsUrl, t.getIsurl());
		contentValues.put(News_Body, t.getBody());
		return contentValues;
	}

}
