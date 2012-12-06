package com.wh.dm.db;
import android.content.ContentValues;
import android.database.Cursor;

import com.wh.dm.type.FavoriteNews;

public class NewsFavoriteBuilder extends DatabaseBuilder<FavoriteNews> {
	private static final String NewsFav_No="no";
    private static final String NewsFav_Id="id";
    private static final String NewsFav_Title="title";
    private static final String NewsFav_Pic="litpic";
    private static final String NewsFav_Date="pubdate";
    private static final String NewsFav_Sid="sid";
	@Override
	public FavoriteNews build(Cursor c) {
		// TODO Auto-generated method stub
		int columnNo = c.getColumnIndex(NewsFav_No);
		int columnId = c.getColumnIndex(NewsFav_Id);
		int columnTitle = c.getColumnIndex(NewsFav_Title);
		int columnPic = c.getColumnIndex(NewsFav_Pic);
		int columnDate = c.getColumnIndex(NewsFav_Date);
		int ColoumnSid = c.getColumnIndex(NewsFav_Sid);
		
		FavoriteNews news = new FavoriteNews();
		
		news.setNo(c.getInt(columnNo));
	    news.setId(c.getInt(columnId));
	    news.setTitle(c.getString(columnTitle));
	    news.setLitpic(c.getString(columnPic));
	    news.setPubdate(c.getString(columnDate));
	    news.setSid(c.getString(ColoumnSid));    
		return news;
	}

	@Override
	public ContentValues deconstruct(FavoriteNews t) {
		// TODO Auto-generated method stub
		ContentValues contentValues = new ContentValues();
	    
		contentValues.put(NewsFav_No, t.getNo());
		contentValues.put(NewsFav_Id, t.getId());
		contentValues.put(NewsFav_Title, t.getTitle());
		contentValues.put(NewsFav_Pic, t.getLitpic());
		contentValues.put(NewsFav_Date, t.getPubdate());
		contentValues.put(NewsFav_Sid, t.getSid());
		
		return contentValues;
	}

}
