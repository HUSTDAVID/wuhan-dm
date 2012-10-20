package com.wh.dm;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.wh.dm.error.UnKnownException;
import com.wh.dm.error.WH_DMException;
import com.wh.dm.http.HttpApiBasic;
import com.wh.dm.http.HttpApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wh.dm.type.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class WH_DMHttpApiV1 {
	private Gson gson = null;
	private static final boolean DEBUG = WH_DM.DEBUG;
	public static final String URL_DOMAIN ="http://test1.jbr.net.cn:809";
	private static final String URL_API_DOMAIN="http://test1.jbr.net.cn:809/api/News.aspx";
	private static final String URL_API_NEWS="/News.aspx";
	private DefaultHttpClient mHttpClient;
	private HttpApiBasic mHttpApi;

	public WH_DMHttpApiV1(String domain,String clientVersion,boolean useOAuth){
		mHttpApi = new HttpApiBasic();
		gson = new Gson();
	}

	public WH_DMHttpApiV1(){
		mHttpApi = new HttpApiBasic();
		gson = new Gson();
	}

	public ArrayList<PicsNews> getPicsNews() throws WH_DMException, UnKnownException, IOException{
		HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN,new BasicNameValuePair("act","listn"));
		String content = mHttpApi.doHttpRequest(httpGet);
		if(DEBUG){
			Log.d("getPicsNews","getPicsNews");
		}
		Type type = new TypeToken<ArrayList<PicsNews>>(){}.getType();
		return gson.fromJson(content, type);
	}

	public ArrayList<PicWithTxtNews> getHeadNews() throws WH_DMException, UnKnownException, IOException{
		HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act","listt"),
							new BasicNameValuePair("cid","1"),new BasicNameValuePair("spage","12"),
							new BasicNameValuePair("page","1"));
		String content = mHttpApi.doHttpRequest(httpGet);
		if(DEBUG){
			Log.d("getHeadNews","getHeadNews");
			Log.d("gson",content);

		}
		Type type = new TypeToken<ArrayList<PicWithTxtNews>>(){}.getType();
		return gson.fromJson(content, type);
	}
	public ArrayList<PicWithTxtNews> getHouseNews() throws WH_DMException, UnKnownException, IOException{
		HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN,new BasicNameValuePair("act","listp"),
				new BasicNameValuePair("cid","211"));
		String content = mHttpApi.doHttpRequest(httpGet);
		if(DEBUG){
			Log.d("getHouseNews","getHouseNews");
			Log.d("gson",content);

		}
		Type type = new TypeToken<ArrayList<PicWithTxtNews>>(){}.getType();
		return gson.fromJson(content,type);
	}
	public ArrayList<PicWithTxtNews> getCarNews() throws WH_DMException, UnKnownException, IOException{
		HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN,new BasicNameValuePair("act","listp"),
				new BasicNameValuePair("cid","212"));
		String content = mHttpApi.doHttpRequest(httpGet);
		if(DEBUG){
			Log.d("getCarNews","getCarNews");
			Log.d("gson",content);

		}
		Type type = new TypeToken<ArrayList<PicWithTxtNews>>(){}.getType();
		return gson.fromJson(content,type);
	}
	public ArrayList<PicWithTxtNews> getFashionNews() throws WH_DMException, UnKnownException, IOException{
		HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN,new BasicNameValuePair("act","listp"),
				new BasicNameValuePair("cid","213"));
		String content = mHttpApi.doHttpRequest(httpGet);
		if(DEBUG){
			Log.d("getFashionNews","getFashionNews");
			Log.d("gson",content);

		}
		Type type = new TypeToken<ArrayList<PicWithTxtNews>>(){}.getType();
		return gson.fromJson(content,type);
	}
	public ArrayList<PicWithTxtNews> getLifeNews() throws WH_DMException, UnKnownException, IOException{
		HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN,new BasicNameValuePair("act","listp"),
				new BasicNameValuePair("cid","214"));
		String content = mHttpApi.doHttpRequest(httpGet);
		if(DEBUG){
			Log.d("getLifeNews","getLifeNews");
			Log.d("gson",content);

		}
		Type type = new TypeToken<ArrayList<PicWithTxtNews>>(){}.getType();
		return gson.fromJson(content,type);
	}
	public ArrayList<PicWithTxtNews> getTravelNews() throws WH_DMException, UnKnownException, IOException{
		HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN,new BasicNameValuePair("act","listp"),
				new BasicNameValuePair("cid","215"));
		String content = mHttpApi.doHttpRequest(httpGet);
		if(DEBUG){
			Log.d("getTravelNews","getTravelNews");
			Log.d("gson",content);

		}
		Type type = new TypeToken<ArrayList<PicWithTxtNews>>(){}.getType();
		return gson.fromJson(content,type);
	}
}
