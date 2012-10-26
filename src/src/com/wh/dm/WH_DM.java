package com.wh.dm;

import java.io.IOException;
import java.util.ArrayList;

import com.wh.dm.error.UnKnownException;
import com.wh.dm.error.WH_DMException;
import com.wh.dm.type.Comment;
import com.wh.dm.type.NewsContent;
import com.wh.dm.type.PicWithTxtNews;
import com.wh.dm.type.PicsNews;

public class WH_DM {

	public static final boolean DEBUG = true;
	private WH_DMHttpApiV1 mWH_DMHttpApiV1 ;

	public WH_DM(WH_DMHttpApiV1 httpApi){
		mWH_DMHttpApiV1 = httpApi;
	}
	public WH_DM(){
		mWH_DMHttpApiV1 = new WH_DMHttpApiV1();
	}

	public ArrayList<PicsNews> getPicsNews() throws WH_DMException, UnKnownException, IOException{
		return mWH_DMHttpApiV1.getPicsNews();
	}
	public ArrayList<PicWithTxtNews> getHeadNews() throws WH_DMException, UnKnownException, IOException{
		return mWH_DMHttpApiV1.getHeadNews();
	}
	public ArrayList<PicWithTxtNews> getHouseNews() throws WH_DMException, UnKnownException, IOException{
		return mWH_DMHttpApiV1.getHouseNews();
	}
	public ArrayList<PicWithTxtNews> getCarNews() throws WH_DMException, UnKnownException, IOException{
		return mWH_DMHttpApiV1.getCarNews();
	}
	public ArrayList<PicWithTxtNews> getFashionNews() throws WH_DMException, UnKnownException, IOException{
		return mWH_DMHttpApiV1.getFashionNews();
	}
	public ArrayList<PicWithTxtNews> getLifeNews() throws WH_DMException, UnKnownException, IOException{
		return mWH_DMHttpApiV1.getLifeNews();
	}
	public ArrayList<PicWithTxtNews> getTravelNews() throws WH_DMException, UnKnownException, IOException{
		return mWH_DMHttpApiV1.getTravelNews();
	}
	public NewsContent[] getNewsContent(int id) throws WH_DMException, UnKnownException, IOException{
		return mWH_DMHttpApiV1.getNewsContent(id);
	}
	public ArrayList<Comment> getComment(int fid) throws WH_DMException, UnKnownException, IOException{
		return mWH_DMHttpApiV1.getComment(fid);
	}


}
