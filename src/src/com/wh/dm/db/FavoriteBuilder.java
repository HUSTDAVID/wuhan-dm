package com.wh.dm.db;
import android.content.ContentValues;
import android.database.Cursor;

import com.wh.dm.type.Favorite;

public class FavoriteBuilder extends DatabaseBuilder<Favorite> {
    private static final String Fav_Id="id";
    private static final String Fav_Title="title";
    private static final String Fav_Pic="litpic";
    private static final String Fav_Data="pubdate";
	@Override
	public Favorite build(Cursor c) {
		// TODO Auto-generated method stub
		int columnId = c.getColumnIndex(Fav_Id);
		int columnTitle = c.getColumnIndex(Fav_Title);
		int columnPic = c.getColumnIndex(Fav_Pic);
		int columnData = c.getColumnIndex(Fav_Data);
		
		Favorite news = new Favorite();
		 
	    news.setId(c.getInt(columnId));
	    news.setTitle(c.getString(columnTitle));
	    news.setLitpic(c.getString(columnPic));
	    news.setPubdate(c.getString(columnData));
	        
		return news;
	}

	@Override
	public ContentValues deconstruct(Favorite t) {
		// TODO Auto-generated method stub
		ContentValues contentValues = new ContentValues();
	    
		contentValues.put(Fav_Id, t.getId());
		contentValues.put(Fav_Title, t.getTitle());
		contentValues.put(Fav_Pic, t.getLitpic());
		contentValues.put(Fav_Data, t.getPubdate());
		
		return contentValues;
	}

}
