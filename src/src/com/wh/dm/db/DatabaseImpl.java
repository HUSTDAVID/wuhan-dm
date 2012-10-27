package com.wh.dm.db;

import java.util.ArrayList;

import com.wh.dm.type.Comment;
import com.wh.dm.type.NewsContent;
import com.wh.dm.type.PicWithTxtNews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseImpl implements Database {

	private static final String DB_NAME = "wh_dm.db";
	private static final String TABLE_HEAD = "head";
	private static final String TABLE_HOUSE = "house";
	private static final String TABLE_CAR = "car";
	private static final String TABLE_FASHION = "fashion";
	private static final String TABLE_LIFE = "life";
	private static final String TABLE_TRAVEL = "travel";
	private static final String TABLE_NEWSDETAIL ="newsdetail";
	private Context context;

	public DatabaseImpl(Context _context) {
		this.context = _context;
		create();
	}

	public void create() {
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_HEAD
				+ " (no INTEGER UNIQUE, id INTEGER, typeid INTEGER, title VARCHAR, litpic VARCHAR,"
				+ " description VARCHAR, sortrank INTEGER, isfirst INTEGER, ishot INTEGER, date VARCHAR, isUrl VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_HOUSE
				+ " (no INTEGER UNIQUE, id INTEGER, typeid INTEGER, title VARCHAR, litpic VARCHAR,"
				+ " description VARCHAR, sortrank INTEGER, isfirst INTEGER, ishot INTEGER, date VARCHAR, isUrl VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_CAR
				+ " (no INTEGER UNIQUE, id INTEGER, typeid INTEGER, title VARCHAR, litpic VARCHAR,"
				+ " description VARCHAR, sortrank INTEGER, isfirst INTEGER, ishot INTEGER, date VARCHAR, isUrl VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_FASHION
				+ " (no INTEGER UNIQUE, id INTEGER, typeid INTEGER, title VARCHAR, litpic VARCHAR,"
				+ " description VARCHAR, sortrank INTEGER, isfirst INTEGER, ishot INTEGER, date VARCHAR, isUrl VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_LIFE
				+ " (no INTEGER UNIQUE, id INTEGER, typeid INTEGER, title VARCHAR, litpic VARCHAR,"
				+ " description VARCHAR, sortrank INTEGER, isfirst INTEGER, ishot INTEGER, date VARCHAR, isUrl VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_TRAVEL
				+ " (no INTEGER UNIQUE, id INTEGER, typeid INTEGER, title VARCHAR, litpic VARCHAR,"
				+ " description VARCHAR, sortrank INTEGER, isfirst INTEGER, ishot INTEGER, date VARCHAR, isUrl VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_NEWSDETAIL
				+ " (uid INTEGER PRIMARY KEY AUTOINCREMENT,no INTEGER, id INTEGER, typeid INTEGER, sortrank INTEGER, title VARCHAR, source VARCHAR,"
				+ " litpic VARCHAR, pubdate VARCHAR, isfirst INTEGER, ishot INTEGER, isUrl VARCHAR, body VARCHAR)");
		db.close();
	}

	@Override
	public void deleteAllData() {
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		db.delete(TABLE_HEAD, null, null);
		db.delete(TABLE_HOUSE, null, null);
		db.delete(TABLE_CAR, null, null);
		db.delete(TABLE_FASHION, null, null);
		db.delete(TABLE_LIFE, null, null);
		db.delete(TABLE_TRAVEL, null, null);
		db.close();

	}

	@Override
	public void deleteAllNews() {
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		db.delete(TABLE_HEAD, null, null);
		db.delete(TABLE_HOUSE, null, null);
		db.delete(TABLE_CAR, null, null);
		db.delete(TABLE_FASHION, null, null);
		db.delete(TABLE_LIFE, null, null);
		db.delete(TABLE_TRAVEL, null, null);
		db.close();

	}

	@Override
	public void deleteHeadNews() {
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		db.delete(TABLE_HEAD, null, null);
		db.delete(TABLE_HOUSE, null, null);
		db.delete(TABLE_CAR, null, null);
		db.delete(TABLE_FASHION, null, null);
		db.delete(TABLE_LIFE, null, null);
		db.delete(TABLE_TRAVEL, null, null);
		db.close();
	}

	@Override
	public void deleteHouseNews() {
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		db.delete(TABLE_HEAD, null, null);
		db.close();

	}

	@Override
	public void deleteCarNews() {
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		db.delete(TABLE_CAR, null, null);
		db.close();

	}

	@Override
	public void deleteFashionNews() {

		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		db.delete(TABLE_FASHION, null, null);
		db.close();
	}

	@Override
	public void deleteLifeNews() {
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		db.delete(TABLE_LIFE, null, null);
		db.close();

	}

	@Override
	public void deleteTravelNews() {
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		db.delete(TABLE_TRAVEL, null, null);
		db.close();

	}

	@Override
	public void deleteNewsContent() {
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
		db.delete(TABLE_NEWSDETAIL, null, null);
		db.close();
	}

	@Override
	public ArrayList<PicWithTxtNews> getHeadNews() {
		ArrayList<PicWithTxtNews> news = new ArrayList<PicWithTxtNews>();
		PicWithTxtNewsBuilder builder = new PicWithTxtNewsBuilder();
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		try {
			Cursor query = db.query(TABLE_HEAD, null, null, null, null, null,
					null);
			if (query != null) {
				query.moveToFirst();
				while (!query.isAfterLast()) {
					news.add(builder.build(query));
					query.moveToNext();
				}
			}
			query.close();
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("db", e.toString());
		} finally {
			db.close();
			return news;
		}

	}

	@Override
	public ArrayList<PicWithTxtNews> getHouseNews() {
		ArrayList<PicWithTxtNews> news = new ArrayList<PicWithTxtNews>();
		PicWithTxtNewsBuilder builder = new PicWithTxtNewsBuilder();
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		Cursor query = db
				.query(TABLE_HOUSE, null, null, null, null, null, null);
		if (query != null) {
			query.moveToFirst();
			while (!query.isAfterLast()) {
				news.add(builder.build(query));
				query.moveToNext();
			}
		}
		query.close();
		db.close();
		return news;
	}

	@Override
	public ArrayList<PicWithTxtNews> getCarNews() {
		ArrayList<PicWithTxtNews> news = new ArrayList<PicWithTxtNews>();
		PicWithTxtNewsBuilder builder = new PicWithTxtNewsBuilder();
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		Cursor query = db.query(TABLE_CAR, null, null, null, null, null, null);
		if (query != null) {
			query.moveToFirst();
			while (!query.isAfterLast()) {
				news.add(builder.build(query));
				query.moveToNext();
			}
		}
		query.close();
		db.close();
		return news;
	}

	@Override
	public ArrayList<PicWithTxtNews> getFashionNews() {
		ArrayList<PicWithTxtNews> news = new ArrayList<PicWithTxtNews>();
		PicWithTxtNewsBuilder builder = new PicWithTxtNewsBuilder();
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		Cursor query = db.query(TABLE_FASHION, null, null, null, null, null,
				null);
		if (query != null) {
			query.moveToFirst();
			while (!query.isAfterLast()) {
				news.add(builder.build(query));
				query.moveToNext();
			}
		}
		query.close();
		db.close();
		return news;
	}

	@Override
	public ArrayList<PicWithTxtNews> getLifeNews() {
		ArrayList<PicWithTxtNews> news = new ArrayList<PicWithTxtNews>();
		PicWithTxtNewsBuilder builder = new PicWithTxtNewsBuilder();
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		Cursor query = db.query(TABLE_LIFE, null, null, null, null, null, null);
		if (query != null) {
			query.moveToFirst();
			while (!query.isAfterLast()) {
				news.add(builder.build(query));
				query.moveToNext();
			}
		}
		query.close();
		db.close();
		return news;
	}

	@Override
	public ArrayList<PicWithTxtNews> getTravelNews() {
		ArrayList<PicWithTxtNews> news = new ArrayList<PicWithTxtNews>();
		PicWithTxtNewsBuilder builder = new PicWithTxtNewsBuilder();
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		Cursor query = db.query(TABLE_TRAVEL, null, null, null, null, null,
				null);
		if (query != null) {
			query.moveToFirst();
			while (!query.isAfterLast()) {
				news.add(builder.build(query));
				query.moveToNext();
			}
		}
		query.close();
		db.close();
		return news;
	}

	@Override
	public void addHeadNews(ArrayList<PicWithTxtNews> news) {
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		for (int i = 0; i < news.size(); i++) {
			try {
				ContentValues values = new ContentValues();
				values.putAll((new PicWithTxtNewsBuilder()).deconstruct(news
						.get(i)));
				db.insert(TABLE_HEAD, null, values);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		db.close();

	}

	@Override
	public void addHouseNews(ArrayList<PicWithTxtNews> news) {
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		for (int i = 0; i < news.size(); i++) {
			try {
				ContentValues values = new ContentValues();
				values.putAll((new PicWithTxtNewsBuilder()).deconstruct(news
						.get(i)));
				db.insert(TABLE_HOUSE, null, values);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		db.close();
	}

	@Override
	public void addCarNews(ArrayList<PicWithTxtNews> news) {
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		for (int i = 0; i < news.size(); i++) {
			try {
				ContentValues values = new ContentValues();
				values.putAll((new PicWithTxtNewsBuilder()).deconstruct(news
						.get(i)));
				db.insert(TABLE_CAR, null, values);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		db.close();

	}

	@Override
	public void addFashionNews(ArrayList<PicWithTxtNews> news) {
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		for (int i = 0; i < news.size(); i++) {
			try {
				ContentValues values = new ContentValues();
				values.putAll((new PicWithTxtNewsBuilder()).deconstruct(news
						.get(i)));
				db.insert(TABLE_FASHION, null, values);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		db.close();

	}

	@Override
	public void addLifeNews(ArrayList<PicWithTxtNews> news) {
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		for (int i = 0; i < news.size(); i++) {
			try {
				ContentValues values = new ContentValues();
				values.putAll((new PicWithTxtNewsBuilder()).deconstruct(news
						.get(i)));
				db.insert(TABLE_LIFE, null, values);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		db.close();

	}

	@Override
	public void addTravelNews(ArrayList<PicWithTxtNews> news) {
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);
		for (int i = 0; i < news.size(); i++) {
			try {
				ContentValues values = new ContentValues();
				values.putAll((new PicWithTxtNewsBuilder()).deconstruct(news
						.get(i)));
				db.insert(TABLE_TRAVEL, null, values);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		db.close();

	}

	@Override
	public void addNewsContent(NewsContent content) {
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, 
				Context.MODE_PRIVATE, null);
		ContentValues values = new ContentValues();
		values.putAll((new NewsContentBuilder()).deconstruct(content));
		try {
			db.insert(TABLE_NEWSDETAIL, null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			db.close();
		}
	}

	@Override
	public NewsContent getNewsContent(int id) {
		NewsContent newsContent = null;
		SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
		String[] selectionArgs ={""+id};
		Cursor query = db.query(TABLE_NEWSDETAIL, null, "id = ?", selectionArgs, null, null, null);
		if(query!=null&&query.getCount()>0){
			query.moveToFirst();
			newsContent = (new NewsContentBuilder()).build(query);
			query.close();
			db.close();
			return newsContent;
		}else{
			return null;
		}

	}

}
