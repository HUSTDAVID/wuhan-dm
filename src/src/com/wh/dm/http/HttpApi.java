package com.wh.dm.http;

import java.io.IOException;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import com.wh.dm.error.*;

public interface HttpApi {

	abstract public String doHttpRequest(HttpRequestBase httpRequest) throws WH_DMException,UnKnownException,IOException;
	abstract public String doHttpPost(String url,NameValuePair... nameValuePairs)throws WH_DMException,UnKnownException;
	abstract public HttpGet createHttpGet(String url,NameValuePair... nameValuePairs);
	abstract public HttpPost createHttpPost(String url,NameValuePair... nameValuePairs);
}
