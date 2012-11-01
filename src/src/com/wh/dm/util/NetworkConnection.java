package com.wh.dm.util;

import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkConnection {

	private static final int timeout = 5;
	private static final String test_website ="www.baidu.com";
	public static  boolean checkInternetConnection() {
		try {
			HttpURLConnection urlConnection = null;
			URL url = new URL(test_website);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0"
					+ " (compatible; MSIE 6.0; Windows 2000)");

			urlConnection.setRequestProperty("Content-type",
					"text/html; charset=" + "utf-8");
			urlConnection.setConnectTimeout(1000 * timeout);
			urlConnection.connect();
			if (urlConnection.getResponseCode() == 200) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
