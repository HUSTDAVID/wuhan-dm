package com.wh.dm.db;
import android.content.ContentValues;
import android.database.Cursor;

import com.wh.dm.type.FavoritePhoto;

public class PhotoFavoriteBuilder extends DatabaseBuilder<FavoritePhoto> {
	private static final String PhotoFav_No="no";
    private static final String PhotoFav_Aid="aid";
    private static final String PhotoFav_Aname="aname";
    private static final String PhotoFav_Pic="litpic";
    private static final String PhotoFav_Date="addtime";
    private static final String PhotoFav_Sid="sid";
	@Override
	public FavoritePhoto build(Cursor c) {
		// TODO Auto-generated method stub
		int columnNo = c.getColumnIndex(PhotoFav_No);
		int columnId = c.getColumnIndex(PhotoFav_Aid);
		int columnTitle = c.getColumnIndex(PhotoFav_Aname);
		int columnPic = c.getColumnIndex(PhotoFav_Pic);
		int columnDate = c.getColumnIndex(PhotoFav_Date);
		int ColoumnSid = c.getColumnIndex(PhotoFav_Sid);
		
		FavoritePhoto photo = new FavoritePhoto();
		
		photo.setNo(c.getInt(columnNo));
		photo.setAid(c.getInt(columnId));
		photo.setAname(c.getString(columnTitle));
		photo.setLitpic(c.getString(columnPic));
		photo.setAddtime(c.getString(columnDate));
		photo.setSid(c.getString(ColoumnSid));    
		return photo;
	}

	@Override
	public ContentValues deconstruct(FavoritePhoto t) {
		// TODO Auto-generated method stub
		ContentValues contentValues = new ContentValues();
	    
		contentValues.put(PhotoFav_No, t.getNo());
		contentValues.put(PhotoFav_Aid, t.getAid());
		contentValues.put(PhotoFav_Aname, t.getAname());
		contentValues.put(PhotoFav_Pic, t.getLitpic());
		contentValues.put(PhotoFav_Date, t.getAddtime());
		contentValues.put(PhotoFav_Sid, t.getSid());
		
		return contentValues;
	}

}
