package com.wh.dm;

import java.io.IOException;
import java.util.ArrayList;

import com.wh.dm.error.UnKnownException;
import com.wh.dm.error.WH_DMException;
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

}
