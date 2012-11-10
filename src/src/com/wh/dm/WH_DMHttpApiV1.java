
package com.wh.dm;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wh.dm.error.UnKnownException;
import com.wh.dm.error.WH_DMException;
import com.wh.dm.http.HttpApiBasic;
import com.wh.dm.type.Comment;
import com.wh.dm.type.NewsContent;
import com.wh.dm.type.Photo;
import com.wh.dm.type.PhotoDetails;
import com.wh.dm.type.PicWithTxtNews;
import com.wh.dm.type.PicsNews;
import com.wh.dm.type.Reply;
import com.wh.dm.type.Result;
import com.wh.dm.type.TwoPhotos;
import com.wh.dm.type.Vote;
import com.wh.dm.type.VoteItem;
import com.wh.dm.util.PhotoUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WH_DMHttpApiV1 {
    private Gson gson = null;
    private static final boolean DEBUG = WH_DMApi.DEBUG;
    public static final String URL_DOMAIN = "http://test1.jbr.net.cn:809";
    private static final String URL_API_DOMAIN = "http://test1.jbr.net.cn:809/api/News.aspx";
    private static final String URL_API_NEWS = "/News.aspx";
    private DefaultHttpClient mHttpClient;
    private final HttpApiBasic mHttpApi;

    public WH_DMHttpApiV1(String domain, String clientVersion, boolean useOAuth) {

        mHttpApi = new HttpApiBasic();
        gson = new Gson();
    }

    public WH_DMHttpApiV1() {

        mHttpApi = new HttpApiBasic();
        gson = new Gson();
    }

    public ArrayList<PicsNews> getPicsNews() throws WH_DMException, UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listn"));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getPicsNews", "getPicsNews");
        }
        Type type = new TypeToken<ArrayList<PicsNews>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<PicWithTxtNews> getHeadNews(int page) throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listt"), new BasicNameValuePair("cid", "1"),
                new BasicNameValuePair("spage", "12"),
                new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getHeadNews", "getHeadNews");
            Log.d("gson", content);

        }
        Type type = new TypeToken<ArrayList<PicWithTxtNews>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<PicWithTxtNews> getHouseNews(int page) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listp"), new BasicNameValuePair("cid", "211"), new BasicNameValuePair("spage",
                "12"), new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getHouseNews", "getHouseNews");
            Log.d("gson", content);

        }
        Type type = new TypeToken<ArrayList<PicWithTxtNews>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<PicWithTxtNews> getCarNews(int page) throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listp"), new BasicNameValuePair("cid", "212"), new BasicNameValuePair("spage",
                "12"), new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getCarNews", "getCarNews");
            Log.d("gson", content);

        }
        Type type = new TypeToken<ArrayList<PicWithTxtNews>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<PicWithTxtNews> getFashionNews(int page) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listp"), new BasicNameValuePair("cid", "213"), new BasicNameValuePair("spage",
                "12"), new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getFashionNews", "getFashionNews");
            Log.d("gson", content);

        }
        Type type = new TypeToken<ArrayList<PicWithTxtNews>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<PicWithTxtNews> getLifeNews(int page) throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listp"), new BasicNameValuePair("cid", "214"), new BasicNameValuePair("spage",
                "12"), new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getLifeNews", "getLifeNews");
            Log.d("gson", content);

        }
        Type type = new TypeToken<ArrayList<PicWithTxtNews>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<PicWithTxtNews> getTravelNews(int page) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listp"), new BasicNameValuePair("cid", "215"), new BasicNameValuePair("spage",
                "12"), new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getTravelNews", "getTravelNews");
            Log.d("gson", content);

        }
        Type type = new TypeToken<ArrayList<PicWithTxtNews>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public NewsContent[] getNewsContent(int id) throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpget = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listd"), new BasicNameValuePair("id", String.valueOf(id)));
        String content = mHttpApi.doHttpRequest(httpget);
        if (DEBUG) {
            Log.d("getNewsContent", "getNewsContent");
            Log.d("gson", content);

        }
        return gson.fromJson(content, NewsContent[].class);
    }

    public ArrayList<Comment> getComment(int id) throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listfb"), new BasicNameValuePair("id", String.valueOf(id)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getComment", "getComment");
            Log.d("gson", content);

        }
        Type type = new TypeToken<ArrayList<Comment>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<Reply> getReply(int fid) throws WH_DMException, UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listrp"), new BasicNameValuePair("fid", String.valueOf(fid)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getComment", "getComment");
            Log.d("gson", content);

        }
        Type type = new TypeToken<ArrayList<Reply>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public boolean addReview(String _content, int id) throws WH_DMException, UnKnownException,
            IOException {

        /*
         * HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN, new
         * BasicNameValuePair("act", "addfb"), new BasicNameValuePair("id",
         * String.valueOf(id)), new BasicNameValuePair( "fcontent", _content));
         * String content = mHttpApi.doHttpPost(httPost);
         */
        String content = UseHttpClientPost();
        Result result = gson.fromJson(content, Result.class);
        Log.d("review", content);
        return result.getResult();

    }

    private String UseHttpClientPost() {

        String resultData = new String();
        String urlStr = new String("http://test:test1.jbr.net.cn:809/api/News.aspx");
        HttpPost httpPost = new HttpPost(urlStr);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("act", "addfb"));
        params.add(new BasicNameValuePair("id", "322"));
        params.add(new BasicNameValuePair("fcontent", "xx"));
        try {
            HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            httpPost.setEntity(entity);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                resultData = EntityUtils.toString(response.getEntity());

            } else {
                resultData = "request error!!!";
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            resultData = "error:" + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            resultData = "error:" + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            resultData = "error:" + e.getMessage();
        }
        return resultData;
    }

    public boolean addReply(String rcontent, int id, int fid) throws WH_DMException,
            UnKnownException, IOException {

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN, new BasicNameValuePair("act",
                "Reply"), new BasicNameValuePair("id", String.valueOf(id)), new BasicNameValuePair(
                "fcontent", rcontent));
        String content = mHttpApi.doHttpPost(httPost);
        Result result = gson.fromJson(content, Result.class);
        Log.d("review", content);
        return result.getResult();
    }

    public boolean addTop(int fid) throws WH_DMException, UnKnownException, IOException {

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN,
                new BasicNameValuePair("act", "ftop"),
                new BasicNameValuePair("fid", String.valueOf(fid)));
        String content = mHttpApi.doHttpPost(httPost);
        Result result = gson.fromJson(content, Result.class);
        return result.getResult();
    }

    // photos
    public ArrayList<TwoPhotos> getPhotos(int page) throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "lista"), new BasicNameValuePair("spage", "12"), new BasicNameValuePair("page",
                String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getPhotos", "getPhotos");
        }
        Type type = new TypeToken<ArrayList<Photo>>() {
        }.getType();
        return PhotoUtil.chagePhoto((ArrayList<Photo>) gson.fromJson(content, type));
    }

    public ArrayList<TwoPhotos> getHotPhotos(int page) throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listta"), new BasicNameValuePair("cid", "216"), new BasicNameValuePair("spage",
                "12"), new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getPhotos", "getPhotos");
        }
        Type type = new TypeToken<ArrayList<Photo>>() {
        }.getType();
        return PhotoUtil.chagePhoto((ArrayList<Photo>) gson.fromJson(content, type));
    }

    public ArrayList<TwoPhotos> getCarPhotos(int page) throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listta"), new BasicNameValuePair("cid", "217"), new BasicNameValuePair("spage",
                "12"), new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getPhotos", "getPhotos");
        }
        Type type = new TypeToken<ArrayList<Photo>>() {
        }.getType();
        return PhotoUtil.chagePhoto((ArrayList<Photo>) gson.fromJson(content, type));
    }

    public ArrayList<TwoPhotos> getGirlPhotos(int page) throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listta"), new BasicNameValuePair("cid", "218"), new BasicNameValuePair("spage",
                "12"), new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getPhotos", "getPhotos");
        }
        Type type = new TypeToken<ArrayList<Photo>>() {
        }.getType();
        return PhotoUtil.chagePhoto((ArrayList<Photo>) gson.fromJson(content, type));
    }

    public ArrayList<TwoPhotos> getPhotographPhotos(int page) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listta"), new BasicNameValuePair("cid", "219"), new BasicNameValuePair("spage",
                "12"), new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getPhotos", "getPhotos");
        }
        Type type = new TypeToken<ArrayList<Photo>>() {
        }.getType();
        return PhotoUtil.chagePhoto((ArrayList<Photo>) gson.fromJson(content, type));
    }

    public ArrayList<TwoPhotos> getFunPhotos(int page) throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listta"), new BasicNameValuePair("cid", "220"), new BasicNameValuePair("spage",
                "12"), new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getPhotos", "getPhotos");
        }
        Type type = new TypeToken<ArrayList<Photo>>() {
        }.getType();
        return PhotoUtil.chagePhoto((ArrayList<Photo>) gson.fromJson(content, type));
    }

    public ArrayList<PhotoDetails> getPhotoDetails(int aid) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listpic"), new BasicNameValuePair("aid", String.valueOf(aid)));
        String content = mHttpApi.doHttpRequest(httpGet);
        Type type = new TypeToken<ArrayList<PhotoDetails>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    // vote
    public ArrayList<Vote> getVote() throws WH_DMException, UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listv"));
        String content = mHttpApi.doHttpRequest(httpGet);
        Type type = new TypeToken<ArrayList<Vote>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<VoteItem> getVoteItems(int vid) throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listvr"), new BasicNameValuePair("vid", String.valueOf(vid)));
        String content = mHttpApi.doHttpRequest(httpGet);
        Type type = new TypeToken<ArrayList<VoteItem>>() {
        }.getType();
        return gson.fromJson(content, type);
    }
}
