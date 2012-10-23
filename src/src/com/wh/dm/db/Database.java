package com.wh.dm.db;

import com.wh.dm.type.PicWithTxtNews;
import java.util.*;

public interface Database {

	public void deleteAllData();
	public void deleteAllNews();
	public void addHeadNews();
	public void addHouseNews();
	public void addCarNews();
	public void addFashionNews();
	public void addLifeNews();
	public void addTravelNews();

	public void deleteHeadNews();
	public void deleteHouseNews();
	public void deleteCarNews();
	public void deleteFashionNews();
	public void deleteLifeNews();
	public void deleteTravelNews();

	public ArrayList<PicWithTxtNews> getHeadNews();
	public ArrayList<PicWithTxtNews> getHouseNews();
	public ArrayList<PicWithTxtNews> getCarNews();
	public ArrayList<PicWithTxtNews> getFashionNews();
	public ArrayList<PicWithTxtNews> getLifeNews();
	public ArrayList<PicWithTxtNews> getTravelNews();
}
