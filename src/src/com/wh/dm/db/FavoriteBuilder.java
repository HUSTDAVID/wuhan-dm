package com.wh.dm.db;
import android.content.ContentValues;
import android.database.Cursor;

import com.wh.dm.type.Favorite;

public class FavoriteBuilder extends DatabaseBuilder<Favorite> {
	private static final String Fav_No="no";
    private static final String Fav_Fid="fid";
    private static final String Fav_Nid="nid";
    private static final String Fav_Type="type";
    private static final String Fav_Title="title";
    private static final String Fav_AddTime="addtime";
	@Override
	public Favorite build(Cursor c) {
		// TODO Auto-generated method stub
		int columnNo = c.getColumnIndex(Fav_No);
		int columnFid = c.getColumnIndex(Fav_Fid);
		int columnNid = c.getColumnIndex(Fav_Nid);
		int columnType = c.getColumnIndex(Fav_Type);
		int columnTitle = c.getColumnIndex(Fav_Title);
		int columnAddTime = c.getColumnIndex(Fav_AddTime);
		
		Favorite news = new Favorite();
		
		news.setNo(c.getInt(columnNo));
	    news.setFid(c.getInt(columnFid));
	    news.setNid(c.getInt(columnNid));
	    news.setType(c.getInt(columnType));
	    news.setTitle(c.getString(columnTitle));
	    news.setAddTime(c.getString(columnAddTime));    
		return news;
	}

	@Override
	public ContentValues deconstruct(Favorite t) {
		// TODO Auto-generated method stub
		ContentValues contentValues = new ContentValues();
	    
		contentValues.put(Fav_No, t.getNo());
		contentValues.put(Fav_Fid, t.getFid());
		contentValues.put(Fav_Nid, t.getNid());
		contentValues.put(Fav_Type, t.getType());
		contentValues.put(Fav_Title, t.getTitle());
		contentValues.put(Fav_AddTime, t.getAddTime());
		
		return contentValues;
	}

}
