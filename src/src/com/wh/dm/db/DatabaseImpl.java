package com.wh.dm.db;

import java.util.ArrayList;

import com.wh.dm.type.PicWithTxtNews;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseImpl implements Database{

	private static final String DM_NAME ="wh_dm";
	private static final String TABLE_HEAD = "head";
	private static final String TABLE_HOUSE ="house";
	private static final String TABLE_CAR ="car";
	private static final String TABLE_FASHION ="fashion";
	private static final String TABLE_LIFE ="life";
	private static final String TABLE_TRAVEL="travel";
	private Context context;

	public DatabaseImpl(Context _context){
		this.context = _context;
		create();
	}
	public void create(){
		SQLiteDatabase db = context.openOrCreateDatabase(DM_NAME,Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+TABLE_HEAD
				+" (no INTEGER UNIQUE, id INTEGER, typeid INTEGER, title VARCHAR, litpic VARCHAR," +
				" description VARCHAR, sortrank INTEGER, isfirst INTEGER, ishot INTEGER, date VARCHAR, isUrl VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+TABLE_HOUSE
				+" (no INTEGER UNIQUE, id INTEGER, typeid INTEGER, title VARCHAR, litpic VARCHAR," +
				" description VARCHAR, sortrank INTEGER, isfirst INTEGER, ishot INTEGER, date VARCHAR, isUrl VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+TABLE_CAR
				+" (no INTEGER UNIQUE, id INTEGER, typeid INTEGER, title VARCHAR, litpic VARCHAR," +
				" description VARCHAR, sortrank INTEGER, isfirst INTEGER, ishot INTEGER, date VARCHAR, isUrl VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+TABLE_FASHION
				+" (no INTEGER UNIQUE, id INTEGER, typeid INTEGER, title VARCHAR, litpic VARCHAR," +
				" description VARCHAR, sortrank INTEGER, isfirst INTEGER, ishot INTEGER, date VARCHAR, isUrl VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+TABLE_LIFE
				+" (no INTEGER UNIQUE, id INTEGER, typeid INTEGER, title VARCHAR, litpic VARCHAR," +
				" description VARCHAR, sortrank INTEGER, isfirst INTEGER, ishot INTEGER, date VARCHAR, isUrl VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+TABLE_TRAVEL
				+" (no INTEGER UNIQUE, id INTEGER, typeid INTEGER, title VARCHAR, litpic VARCHAR," +
				" description VARCHAR, sortrank INTEGER, isfirst INTEGER, ishot INTEGER, date VARCHAR, isUrl VARCHAR)");
		db.close();
	}
	@Override
	public void deleteAllData() {
		// TODO Auto-generated method stub

	}
	@Override
	public void deleteAllNews() {
		// TODO Auto-generated method stub

	}
	@Override
	public void addHeadNews() {
		// TODO Auto-generated method stub

	}
	@Override
	public void addHouseNews() {
		// TODO Auto-generated method stub

	}
	@Override
	public void addCarNews() {
		// TODO Auto-generated method stub

	}
	@Override
	public void addFashionNews() {
		// TODO Auto-generated method stub

	}
	@Override
	public void addLifeNews() {
		// TODO Auto-generated method stub

	}
	@Override
	public void addTravelNews() {
		// TODO Auto-generated method stub

	}
	@Override
	public void deleteHeadNews() {
		// TODO Auto-generated method stub

	}
	@Override
	public void deleteHouseNews() {
		// TODO Auto-generated method stub

	}
	@Override
	public void deleteCarNews() {
		// TODO Auto-generated method stub

	}
	@Override
	public void deleteFashionNews() {
		// TODO Auto-generated method stub

	}
	@Override
	public void deleteLifeNews() {
		// TODO Auto-generated method stub

	}
	@Override
	public void deleteTravelNews() {
		// TODO Auto-generated method stub

	}
	@Override
	public ArrayList<PicWithTxtNews> getHeadNews() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<PicWithTxtNews> getHouseNews() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<PicWithTxtNews> getCarNews() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<PicWithTxtNews> getFashionNews() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<PicWithTxtNews> getLifeNews() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<PicWithTxtNews> getTravelNews() {
		// TODO Auto-generated method stub
		return null;
	}
}
