
package com.wh.dm;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wh.dm.error.UnKnownException;
import com.wh.dm.error.WH_DMException;
import com.wh.dm.http.HttpApiBasic;
import com.wh.dm.type.Article;
import com.wh.dm.type.ArticleMagzine;
import com.wh.dm.type.Comment;
import com.wh.dm.type.Cover;
import com.wh.dm.type.Favorite;
import com.wh.dm.type.Magazine;
import com.wh.dm.type.MagazineSort;
import com.wh.dm.type.NewsContent;
import com.wh.dm.type.NewsType;
import com.wh.dm.type.Photo;
import com.wh.dm.type.PhotoDetails;
import com.wh.dm.type.PhotoSort;
import com.wh.dm.type.PicWithTxtNews;
import com.wh.dm.type.PicsNews;
import com.wh.dm.type.PictureMagzine;
import com.wh.dm.type.PostMessage;
import com.wh.dm.type.PostResult;
import com.wh.dm.type.Reply;
import com.wh.dm.type.TwoPhotos;
import com.wh.dm.type.Vote;
import com.wh.dm.type.VoteResult;
import com.wh.dm.type.VoteResultPercent;
import com.wh.dm.util.PhotoUtil;
import com.wh.dm.util.VoteUitl;

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
    public static final String URL_DOMAIN = "http://api.meike15.com";
    private static final String URL_API_DOMAIN = "http://api.meike15.com/api/News.aspx";
    private static final String URL_API_NEWS = "/api/News.aspx";
    private static final String URL_API_MEM = "/api/Mem.aspx";
    public static final String URL_API_MAGAZINE = "/api/Magazine.aspx";
    private static final String URL_API_FEEDBACK = "/api/feedback.aspx";
    private static final String URL_API_FAV = "/api/fav.aspx";

    private static final String URL_API_LOADPIC = "/api/loadpic.aspx";
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

    public ArrayList<PicWithTxtNews> getHouseNews(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listp"), new BasicNameValuePair("cid", id), new BasicNameValuePair("spage",
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

    public ArrayList<PicWithTxtNews> getCarNews(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listp"), new BasicNameValuePair("cid", id), new BasicNameValuePair("spage",
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

    public ArrayList<PicWithTxtNews> getFashionNews(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listp"), new BasicNameValuePair("cid", id), new BasicNameValuePair("spage",
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

    public ArrayList<PicWithTxtNews> getLifeNews(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listp"), new BasicNameValuePair("cid", id), new BasicNameValuePair("spage",
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

    public ArrayList<PicWithTxtNews> getTravelNews(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listp"), new BasicNameValuePair("cid", id), new BasicNameValuePair("spage",
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

    public ArrayList<Comment> getPhotoComment(int id, int page) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listtfb"), new BasicNameValuePair("id", String.valueOf(id)),
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

    public boolean addPhotoReview(String _content, int id) throws WH_DMException, UnKnownException,
            IOException {

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_NEWS,
                new BasicNameValuePair("act", "addtfb"),
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

    public PostResult addTop(String fid, String machine) throws WH_DMException, UnKnownException,
            IOException {

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_NEWS,
                new BasicNameValuePair("act", "ftop"), new BasicNameValuePair("fid", fid),
                new BasicNameValuePair("machine", machine));
        String content = mHttpApi.doHttpRequest(httPost);
        PostResult result = gson.fromJson(content, PostResult.class);
        return result;
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

    public ArrayList<TwoPhotos> getCarPhotos(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listta"), new BasicNameValuePair("cid", id), new BasicNameValuePair(
                "spage", "12"), new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getPhotos", "getPhotos");
        }
        Type type = new TypeToken<ArrayList<Photo>>() {
        }.getType();
        return PhotoUtil.chagePhoto((ArrayList<Photo>) gson.fromJson(content, type));
    }

    public ArrayList<TwoPhotos> getGirlPhotos(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listta"), new BasicNameValuePair("cid", id), new BasicNameValuePair(
                "spage", "12"), new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getPhotos", "getPhotos");
        }
        Type type = new TypeToken<ArrayList<Photo>>() {
        }.getType();
        return PhotoUtil.chagePhoto((ArrayList<Photo>) gson.fromJson(content, type));
    }

    public ArrayList<TwoPhotos> getPhotographPhotos(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listta"), new BasicNameValuePair("cid", id), new BasicNameValuePair(
                "spage", "12"), new BasicNameValuePair("page", String.valueOf(page)));
        String content = mHttpApi.doHttpRequest(httpGet);
        if (DEBUG) {
            Log.d("getPhotos", "getPhotos");
        }
        Type type = new TypeToken<ArrayList<Photo>>() {
        }.getType();
        return PhotoUtil.chagePhoto((ArrayList<Photo>) gson.fromJson(content, type));
    }

    public ArrayList<TwoPhotos> getFunPhotos(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_NEWS, new BasicNameValuePair(
                "act", "listta"), new BasicNameValuePair("cid", id), new BasicNameValuePair(
                "spage", "12"), new BasicNameValuePair("page", String.valueOf(page)));
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
    public ArrayList<Vote> getVote(int sid, String machine) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "listv"), new BasicNameValuePair("sid", String.valueOf(sid)),
                new BasicNameValuePair("machine", machine));
        String content = mHttpApi.doHttpRequest(httpGet);

        return VoteUitl.parseVote(content);
    }

    public VoteResult postVote(int aid, String vtitle, String machine) throws WH_DMException,
            UnKnownException, IOException {

        HttpPost httpPost = mHttpApi.createHttpPost(URL_API_DOMAIN, new BasicNameValuePair("act",
                "vote"), new BasicNameValuePair("vid", String.valueOf(aid)),
                new BasicNameValuePair("vtitle", vtitle),
                new BasicNameValuePair("machine", machine));
        String content = mHttpApi.doHttpRequest(httpPost);
        Type type = new TypeToken<VoteResult>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public String getVoteNum(int vid) throws WH_DMException, UnKnownException, IOException {

        HttpPost httpPost = mHttpApi.createHttpPost(URL_API_DOMAIN, new BasicNameValuePair("act",
                "vnum"), new BasicNameValuePair("vid", String.valueOf(vid)));
        String content = mHttpApi.doHttpRequest(httpPost);
        return content;

    }

    public ArrayList<VoteResultPercent> getVoteResultPercent(int vid) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_API_DOMAIN, new BasicNameValuePair("act",
                "result"), new BasicNameValuePair("vid", String.valueOf(vid)));
        String content = mHttpApi.doHttpRequest(httpGet);
        Type type = new TypeToken<ArrayList<VoteResultPercent>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public PostResult register(String regemail, String regepass, String machineId)
            throws WH_DMException, UnKnownException, IOException {

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_MEM,
                new BasicNameValuePair("act", "reg"), new BasicNameValuePair("mail", regemail),
                new BasicNameValuePair("pass", regepass), new BasicNameValuePair("machine",
                        machineId));
        String content = mHttpApi.doHttpRequest(httPost);
        Log.d("content", content);
        PostResult result = gson.fromJson(content, PostResult.class);
        return result;

    }

    public boolean login(String logemail, String logpassword, String machine)
            throws WH_DMException, UnKnownException, IOException {

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_MEM,
                new BasicNameValuePair("act", "login"), new BasicNameValuePair("mail", logemail),
                new BasicNameValuePair("pass", logpassword), new BasicNameValuePair("machine",
                        machine));
        String content = mHttpApi.doHttpRequest(httPost);
        PostResult result = gson.fromJson(content, PostResult.class);
        return result.getResult();
    }

    public PostResult login2(String logemail, String logpassword, String machine)
            throws WH_DMException, UnKnownException, IOException {

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_MEM,
                new BasicNameValuePair("act", "login"), new BasicNameValuePair("mail", logemail),
                new BasicNameValuePair("pass", logpassword), new BasicNameValuePair("machine",
                        machine));
        String content = mHttpApi.doHttpRequest(httPost);
        return gson.fromJson(content, PostResult.class);

    }

    public PostResult loginById(String machine) throws WH_DMException, UnKnownException,
            IOException {

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_MEM,
                new BasicNameValuePair("act", "load"), new BasicNameValuePair("machine", machine),
                new BasicNameValuePair("mail", null), new BasicNameValuePair("pass", null));
        String content = mHttpApi.doHttpRequest(httPost);
        PostResult result = gson.fromJson(content, PostResult.class);
        return result;
    }

    public ArrayList<Magazine> getMagazine(String cid, int page) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_MAGAZINE,
                new BasicNameValuePair("act", "list"),
                new BasicNameValuePair("pi", String.valueOf(page)), new BasicNameValuePair("pz",
                        String.valueOf(20)), new BasicNameValuePair("cid", cid));
        String content = mHttpApi.doHttpRequest(httpGet);
        Type type = new TypeToken<ArrayList<Magazine>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<Magazine> getDefaultMagazine() throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_MAGAZINE,
                new BasicNameValuePair("act", "default"));
        String content = mHttpApi.doHttpRequest(httpGet);
        Type type = new TypeToken<ArrayList<Magazine>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<Magazine> getSearchMagazine(String key) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_MAGAZINE,
                new BasicNameValuePair("act", "list"), new BasicNameValuePair("key", key));
        String content = mHttpApi.doHttpRequest(httpGet);
        Type type = new TypeToken<ArrayList<Magazine>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<MagazineSort> getMagazineSort() throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_MAGAZINE,
                new BasicNameValuePair("act", "cls"));
        String content = mHttpApi.doHttpRequest(httpGet);
        Type type = new TypeToken<ArrayList<MagazineSort>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public ArrayList<ArticleMagzine> getArticleMagzine(int sid) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_MAGAZINE,
                new BasicNameValuePair("act", "maglist"),
                new BasicNameValuePair("sid", String.valueOf(sid)));
        String content = mHttpApi.doHttpRequest(httpGet);
        Type type = new TypeToken<ArrayList<ArticleMagzine>>() {
        }.getType();
        ArrayList<ArticleMagzine> magzines = gson.fromJson(content, type);
        return magzines;
    }

    public ArrayList<PictureMagzine> getPictureMagzine(int sid) throws WH_DMException,
            UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_MAGAZINE,
                new BasicNameValuePair("act", "maglist"),
                new BasicNameValuePair("sid", String.valueOf(sid)));
        String content = mHttpApi.doHttpRequest(httpGet);
        Type type = new TypeToken<ArrayList<PictureMagzine>>() {
        }.getType();
        return gson.fromJson(content, type);

    }

    public Article getArticle(int sid) throws WH_DMException, UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_MAGAZINE,
                new BasicNameValuePair("act", "cont"),
                new BasicNameValuePair("id", String.valueOf(sid)));
        String content = mHttpApi.doHttpRequest(httpGet);
        Type type = new TypeToken<ArrayList<Article>>() {
        }.getType();
        Article article = ((ArrayList<Article>) gson.fromJson(content, type)).get(0);
        return article;

    }

    public PostResult commitFeedBack(String contactways, String fcontent) throws WH_DMException,
            UnKnownException, IOException {

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_FEEDBACK,
                new BasicNameValuePair("Act", "feedback"), new BasicNameValuePair("User",
                        contactways), new BasicNameValuePair("Memo", fcontent));
        String content = mHttpApi.doHttpRequest(httPost);
        PostResult result = gson.fromJson(content, PostResult.class);
        return result;
    }

    public Cover subcribe(int id) throws WH_DMException, UnKnownException, IOException {

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_MAGAZINE,
                new BasicNameValuePair("act", "order"), new BasicNameValuePair("mid", "" + id));
        String content = mHttpApi.doHttpRequest(httPost);
        PostResult result = gson.fromJson(content, PostResult.class);
        if (result.getResult()) {
            Cover cover = new Cover();
            String[] msg = (result.getMsg()).split(",", 3);
            cover.setId(Integer.parseInt(msg[0]));
            cover.setMagazineName(msg[1]);
            cover.setMagazinePic(msg[2]);
            return cover;
        } else {
            return null;
        }
    }

    public boolean unsubcribe(int id) throws WH_DMException, UnKnownException, IOException {

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_MAGAZINE,
                new BasicNameValuePair("act", "orderc"), new BasicNameValuePair("mid", "" + id));
        String content = mHttpApi.doHttpRequest(httPost);
        PostResult result = gson.fromJson(content, PostResult.class);
        return result.getResult();
    }

    public PostResult addFav(int nid, int type) throws WH_DMException, UnKnownException,
            IOException {

        HttpPost httpPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_FAV,
                new BasicNameValuePair("act", "add"),
                new BasicNameValuePair("nid", String.valueOf(nid)), new BasicNameValuePair("type",
                        String.valueOf(type)));
        String content = mHttpApi.doHttpRequest(httpPost);
        PostResult result = gson.fromJson(content, PostResult.class);
        return result;
    }

    public ArrayList<Favorite> getFav(int pz, int pi) throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_FAV, new BasicNameValuePair(
                "act", "list"), new BasicNameValuePair("pz", String.valueOf(pz)),
                new BasicNameValuePair("pi", String.valueOf(pi)));
        String content = mHttpApi.doHttpRequest(httpGet);
        // return content;
        Type typetoken = new TypeToken<ArrayList<Favorite>>() {
        }.getType();
        try {
            return gson.fromJson(content, typetoken);
        } catch (Exception ex) {
            gson.fromJson(content, PostResult.class);
            return null;
        }

    }

    public boolean delFav(int nid, int type) throws WH_DMException, UnKnownException, IOException {

        HttpPost httpPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_FAV,
                new BasicNameValuePair("act", "del"),
                new BasicNameValuePair("nid", String.valueOf(nid)), new BasicNameValuePair("type",
                        String.valueOf(type)));
        String content = mHttpApi.doHttpRequest(httpPost);
        PostResult result = gson.fromJson(content, PostResult.class);
        return result.getResult();
    }

    public ArrayList<Magazine> getSubcribedMagazines() throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_MAGAZINE,
                new BasicNameValuePair("act", "orderlist"));
        String content = mHttpApi.doHttpRequest(httpGet);
        Type type = new TypeToken<ArrayList<Magazine>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    public PostResult getLoadPic() throws WH_DMException, UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_LOADPIC);
        String content = mHttpApi.doHttpRequest(httpGet);
        return gson.fromJson(content, PostResult.class);
    }

    public ArrayList<PostMessage> getMessages() throws WH_DMException, UnKnownException,
            IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_MAGAZINE,
                new BasicNameValuePair("act", "push"));
        String content = mHttpApi.doHttpRequest(httpGet);
        Type type = new TypeToken<ArrayList<PostMessage>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    // load magazine
    public String loadMagzine(int sid) throws WH_DMException, UnKnownException, IOException {

        HttpGet httpGet = mHttpApi.createHttpGet(URL_DOMAIN + URL_API_MAGAZINE,
                new BasicNameValuePair("act", "down"),
                new BasicNameValuePair("sid", String.valueOf(sid)));
        return mHttpApi.doHttpRequest(httpGet);
    }

    // find password
    public PostResult findPassword(String email) throws WH_DMException, UnKnownException,
            IOException {

        HttpPost httPost = mHttpApi.createHttpPost(URL_DOMAIN + URL_API_MEM,
                new BasicNameValuePair("act", "findpass"), new BasicNameValuePair("mail", email));
        String content = mHttpApi.doHttpRequest(httPost);
        PostResult result = gson.fromJson(content, PostResult.class);
        return result;
    }

}
