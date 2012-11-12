
package com.wh.dm.http;

import com.wh.dm.error.UnKnownException;
import com.wh.dm.error.WH_DMException;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;

public interface HttpApi {

    abstract public String doHttpRequest(HttpRequestBase httpRequest) throws WH_DMException,
            UnKnownException, IOException;

    abstract public HttpGet createHttpGet(String url, NameValuePair... nameValuePairs);

    abstract public HttpPost createHttpPost(String url, NameValuePair... nameValuePairs);
}
