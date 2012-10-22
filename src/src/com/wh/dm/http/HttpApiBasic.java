package com.wh.dm.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.wh.dm.WH_DM;
import com.wh.dm.error.UnKnownException;
import com.wh.dm.error.WH_DMException;

public class HttpApiBasic implements HttpApi {

	protected static final boolean DEBUG = WH_DM.DEBUG;
	private static final String DEFALUT_CLIENT_VERSION= "com.wh.dm";
	private static final String CLIENT_VERSION_HEADER ="User-Agent";
	private static final int TIMEOUT = 60;

	private DefaultHttpClient mHttpClient;
	private String mClientVersion;

	public HttpApiBasic(DefaultHttpClient httpClient,String clientVersion){
		mHttpClient = httpClient;
		if(clientVersion!=null){
			mClientVersion = clientVersion;

		}else{
			mClientVersion = DEFALUT_CLIENT_VERSION;
		}
	}
	public HttpApiBasic(){
		mHttpClient = new DefaultHttpClient();
	}

	@Override
	public HttpGet createHttpGet(String url, NameValuePair... nameValuePairs) {
		String query = URLEncodedUtils.format(fullParams(nameValuePairs), HTTP.UTF_8);
		HttpGet httpGet = new HttpGet(url+"?"+query);
		//httpGet.addHeader(CLIENT_VERSION_HEADER, mClientVersion);
		if(DEBUG){
			Log.d("HttpGet", "HttpGet:");
		}
		return httpGet;
	}

	@Override
	public HttpPost createHttpPost(String url, NameValuePair... nameValuePairs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String doHttpPost(String url, NameValuePair... nameValuePairs)
			throws WH_DMException, UnKnownException {


		if(DEBUG){
			Log.d("doHttpPost", "doHttpPost executes");
		}

		return null;
	}

	@Override
	public String doHttpRequest(HttpRequestBase httpRequest)
			throws WH_DMException, UnKnownException, IOException {

		HttpResponse response = executeHttpRequest(httpRequest);
		int statusCode = response.getStatusLine().getStatusCode();
		switch(statusCode){
		case 200:
			String content = EntityUtils.toString(response.getEntity());
			return content;
		case 400:
			throw new WH_DMException("");

		case 401:
			response.getEntity().consumeContent();
			throw new WH_DMException("请求参数不符合API规定");
		case 404:
			response.getEntity().consumeContent();
			throw new WH_DMException("未授权");
		case 500:
			response.getEntity().consumeContent();
			throw new WH_DMException("资源不存在");
		default:
			response.getEntity().consumeContent();
			throw new UnKnownException("内部错误");
		}

	}

	public HttpResponse executeHttpRequest(HttpRequestBase httpRequest) throws IOException{

		if(DEBUG){
			Log.d("executeHttpRequest","executeHttpRequest executes");
		}
		mHttpClient.getConnectionManager().closeExpiredConnections();
		return mHttpClient.execute(httpRequest);
	}

	private List<NameValuePair> fullParams(NameValuePair...nameValuePairs){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for(int i=0;i<nameValuePairs.length;i++){
			NameValuePair param = nameValuePairs[i];
			if(param.getValue()!=null){
				if(DEBUG){
					Log.d("fullParams","Param:"+param);
				}
				params.add(param);
			}
		}
		return params;
	}

}
