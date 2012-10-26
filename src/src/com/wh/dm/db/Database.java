package com.wh.dm.db;

import com.wh.dm.type.PicWithTxtNews;
import java.util.*;

public interface Database {

	public void deleteAllData();
	public void deleteAllNews();
	public void addHeadNews(ArrayList<PicWithTxtNews> news);
	public void addHouseNews(ArrayList<PicWithTxtNews> news);
	public void addCarNews(ArrayList<PicWithTxtNews> news);
	public void addFashionNews(ArrayList<PicWithTxtNews> news);
	public void addLifeNews(ArrayList<PicWithTxtNews> news);
	public void addTravelNews(ArrayList<PicWithTxtNews> news);

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
