
package com.wh.dm;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wh.dm.error.UnKnownException;
import com.wh.dm.error.WH_DMException;
import com.wh.dm.http.HttpApiBasic;
import com.wh.dm.type.Comment;
import com.wh.dm.type.Magazine;
import com.wh.dm.type.NewsContent;
import com.wh.dm.type.NewsType;
import com.wh.dm.type.Photo;
import com.wh.dm.type.PhotoDetails;
import com.wh.dm.type.PhotoSort;
import com.wh.dm.type.PicWithTxtNews;
import com.wh.dm.type.PicsNews;
import com.wh.dm.type.PostResult;
import com.wh.dm.type.Reply;
import com.wh.dm.type.TwoPhotos;
import com.wh.dm.type.Vote;
import com.wh.dm.type.VoteItem;
import com.wh.dm.util.PhotoUtil;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class WH_DMHttpApiV1 {
    private Gson gson = null;
    private static final boolean DEBUG = WH_DMApi.DEBUG;
    public static final String URL_DOMAIN = "http://test1.jbr.net.cn:809";
    private static final String URL_API_DOMAIN = "http://test1.jbr.net.cn:809/api/News.aspx";
    private static final String URL_API_NEWS = "/api/News.aspx";
    private static final String URL_API_MEM = "/api/Mem.aspx";
    public static final String URL_API_MAGAZINE = "/api/Magazine.aspx";
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

    public ArrayList<NewsType> getNewsType() throws WH_DMException, UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listc"));
        String content = mHttpApi.doHttpRequest(httpGet);
        Type type = new TypeToken<ArrayList<NewsType>>() {
        }.getType();
        return gson.fromJson(content, type);

    }

    public ArrayList<PicsNews> getPicsNews() throws WH_DMException, UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listn"));
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

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listt"), new BasicNameValuePair("cid", "1"), new BasicNameValuePair(
                "spage", "12"), new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getHeadNews", "getHeadNews");
            Log.d("gson", content);

        }
        Type type = new TypeToken<ArrayList<PicWithTxtNews>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<PicWithTxtNews> getHouseNews(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listp"), new BasicNameValuePair("cid", String.valueOf(id)),
                new BasicNameValuePair("spage", "12"),
                new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getHouseNews", "getHouseNews");
            Log.d("gson", content);

        }
        Type type = new TypeToken<ArrayList<PicWithTxtNews>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<PicWithTxtNews> getCarNews(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listp"), new BasicNameValuePair("cid", String.valueOf(id)),
                new BasicNameValuePair("spage", "12"),
                new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getCarNews", "getCarNews");
            Log.d("gson", content);

        }
        Type type = new TypeToken<ArrayList<PicWithTxtNews>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<PicWithTxtNews> getFashionNews(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listp"), new BasicNameValuePair("cid", String.valueOf(id)),
                new BasicNameValuePair("spage", "12"),
                new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getFashionNews", "getFashionNews");
            Log.d("gson", content);

        }
        Type type = new TypeToken<ArrayList<PicWithTxtNews>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<PicWithTxtNews> getLifeNews(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listp"), new BasicNameValuePair("cid", String.valueOf(id)),
                new BasicNameValuePair("spage", "12"),
                new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getLifeNews", "getLifeNews");
            Log.d("gson", content);

        }
        Type type = new TypeToken<ArrayList<PicWithTxtNews>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<PicWithTxtNews> getTravelNews(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listp"), new BasicNameValuePair("cid", String.valueOf(id)),
                new BasicNameValuePair("spage", "12"),
                new BasicNameValuePair("page", String.valueOf(page)));
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

        HttpGet httpget = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listd"), new BasicNameValuePair("id", String.valueOf(id)));
        String content = mHttpApi.doHttpRequest(httpget);
        if (DEBUG) {
            Log.d("getNewsContent", "getNewsContent");
            Log.d("gson", content);

        }
        return gson.fromJson(content, NewsContent[].class);
    }

    public ArrayList<Comment> getComment(int id, int page) throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listfb"), new BasicNameValuePair("id", String.valueOf(id)),
                new BasicNameValuePair("spage", "12"), new BasicNameValuePair("page", "" + page));
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

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listrp"), new BasicNameValuePair("fid", String.valueOf(fid)));
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

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_NEWS,
                new BasicNameValuePair("act", "addfb"),
                new BasicNameValuePair("id", String.valueOf(id)), new BasicNameValuePair(
                        "fcontent", _content));
        String content = mHttpApi.doHttpRequest(httPost);

        PostResult result = gson.fromJson(content, PostResult.class);
        Log.d("review", content);
        return result.getResult();

    }

    public boolean addReply(String rcontent, String fid) throws WH_DMException, UnKnownException,
            IOException {

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_NEWS,
                new BasicNameValuePair("act", "Reply"), new BasicNameValuePair("fid", fid),
                new BasicNameValuePair("rcontent", rcontent));
        String content = mHttpApi.doHttpRequest(httPost);
        PostResult result = gson.fromJson(content, PostResult.class);
        Log.d("review", content);
        return result.getResult();
    }

    public boolean addTop(String fid) throws WH_DMException, UnKnownException, IOException {

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_NEWS,
                new BasicNameValuePair("act", "ftop"), new BasicNameValuePair("fid", fid));
        String content = mHttpApi.doHttpRequest(httPost);
        PostResult result = gson.fromJson(content, PostResult.class);
        return result.getResult();
    }

    // photos

    public ArrayList<PhotoSort> getPhotoSort() throws WH_DMException, UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listac"));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getPhotoSort", "getPhotoSort");
        }
        Type type = new TypeToken<ArrayList<PhotoSort>>() {
        }.getType();
        return (ArrayList<PhotoSort>) gson.fromJson(content, type);
    }

    public ArrayList<TwoPhotos> getPhotos(int page) throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "lista"), new BasicNameValuePair("spage", "12"), new BasicNameValuePair(
                "page", String.valueOf(page)));
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

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listh"), new BasicNameValuePair("spage", "12"), new BasicNameValuePair(
                "page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getPhotos", "getPhotos");
        }
        Type type = new TypeToken<ArrayList<Photo>>() {
        }.getType();
        return PhotoUtil.chagePhoto((ArrayList<Photo>) gson.fromJson(content, type));
    }

    public ArrayList<TwoPhotos> getCarPhotos(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listta"), new BasicNameValuePair("cid", String.valueOf(id)),
                new BasicNameValuePair("spage", "12"),
                new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getPhotos", "getPhotos");
        }
        Type type = new TypeToken<ArrayList<Photo>>() {
        }.getType();
        return PhotoUtil.chagePhoto((ArrayList<Photo>) gson.fromJson(content, type));
    }

    public ArrayList<TwoPhotos> getGirlPhotos(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listta"), new BasicNameValuePair("cid", String.valueOf(id)),
                new BasicNameValuePair("spage", "12"),
                new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getPhotos", "getPhotos");
        }
        Type type = new TypeToken<ArrayList<Photo>>() {
        }.getType();
        return PhotoUtil.chagePhoto((ArrayList<Photo>) gson.fromJson(content, type));
    }

    public ArrayList<TwoPhotos> getPhotographPhotos(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listta"), new BasicNameValuePair("cid", String.valueOf(id)),
                new BasicNameValuePair("spage", "12"),
                new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getPhotos", "getPhotos");
        }
        Type type = new TypeToken<ArrayList<Photo>>() {
        }.getType();
        return PhotoUtil.chagePhoto((ArrayList<Photo>) gson.fromJson(content, type));
    }

    public ArrayList<TwoPhotos> getFunPhotos(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listta"), new BasicNameValuePair("cid", String.valueOf(id)),
                new BasicNameValuePair("spage", "12"),
                new BasicNameValuePair("page", String.valueOf(page)));
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
                "listpic"), new BasicNameValuePair("id", String.valueOf(aid)));
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

    public boolean register(String regemail, String regepass) throws WH_DMException,
            UnKnownException, IOException {

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_MEM,
                new BasicNameValuePair("act", "reg"), new BasicNameValuePair("regemail", regemail),
                new BasicNameValuePair("regpass", regepass));
        String content = mHttpApi.doHttpRequest(httPost);
        Log.d("content", content);
        PostResult result = gson.fromJson(content, PostResult.class);
        return result.getResult();

    }

    public boolean login(String logemail, String logpassword) throws WH_DMException,
            UnKnownException, IOException {

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_MEM,
                new BasicNameValuePair("act", "login"),
                new BasicNameValuePair("logemail", logemail), new BasicNameValuePair("logpass",
                        logpassword));
        String content = mHttpApi.doHttpRequest(httPost);
        PostResult result = gson.fromJson(content, PostResult.class);
        return result.getResult();
    }

    public ArrayList<Magazine> getMagazine() throws WH_DMException, UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_MAGAZINE,
                new BasicNameValuePair("act", "list"),
                new BasicNameValuePair("pi", String.valueOf(1)), new BasicNameValuePair("pz",
                        String.valueOf(20)), new BasicNameValuePair("cid", null));
        String content = mHttpApi.doHttpRequest(httpGet);
        Type type = new TypeToken<ArrayList<Magazine>>() {
        }.getType();
        return gson.fromJson(content, type);
    }
}
