
package com.wh.dm;

import com.wh.dm.error.UnKnownException;
import com.wh.dm.error.WH_DMException;
import com.wh.dm.type.Article;
import com.wh.dm.type.ArticleMagzine;
import com.wh.dm.type.Comment;
import com.wh.dm.type.Magazine;
import com.wh.dm.type.NewsContent;
import com.wh.dm.type.NewsType;
import com.wh.dm.type.PhotoDetails;
import com.wh.dm.type.PhotoSort;
import com.wh.dm.type.PicWithTxtNews;
import com.wh.dm.type.PicsNews;
import com.wh.dm.type.PictureMagzine;
import com.wh.dm.type.Reply;
import com.wh.dm.type.TwoPhotos;
import com.wh.dm.type.Vote;
import com.wh.dm.type.VoteItem;

import java.io.IOException;
import java.util.ArrayList;

public class WH_DMApi {

    public static final boolean DEBUG = true;
    private final WH_DMHttpApiV1 mWH_DMHttpApiV1;

    public WH_DMApi(WH_DMHttpApiV1 httpApi) {

        mWH_DMHttpApiV1 = httpApi;
    }

    public WH_DMApi() {

        mWH_DMHttpApiV1 = new WH_DMHttpApiV1();
    }

    public ArrayList<NewsType> getNewsType() throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.getNewsType();
    }

    public ArrayList<PicsNews> getPicsNews() throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.getPicsNews();
    }

    public ArrayList<PicWithTxtNews> getHeadNews(int page) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getHeadNews(page);
    }

    public ArrayList<PicWithTxtNews> getHouseNews(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getHouseNews(page, id);
    }

    public ArrayList<PicWithTxtNews> getCarNews(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getCarNews(page, id);
    }

    public ArrayList<PicWithTxtNews> getFashionNews(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getFashionNews(page, id);
    }

    public ArrayList<PicWithTxtNews> getLifeNews(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getLifeNews(page, id);
    }

    public ArrayList<PicWithTxtNews> getTravelNews(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getTravelNews(page, id);
    }

    public NewsContent[] getNewsContent(int id) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getNewsContent(id);
    }

    public ArrayList<Comment> getComment(int id, int page) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getComment(id, page);
    }

    public ArrayList<Reply> getReply(int fid) throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.getReply(fid);
    }

    public boolean addTop(String fid) throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.addTop(fid);
    }

    public boolean addReply(String rconent, String fid) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.addReply(rconent, fid);
    }

    public boolean addReview(String content, int id) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.addReview(content, id);
    }

    public ArrayList<PhotoSort> getPhotoSort() throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.getPhotoSort();
    }

    public ArrayList<TwoPhotos> getPhotos(int page) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getPhotos(page);
    }

    public ArrayList<TwoPhotos> getHotPhotos(int page) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getHotPhotos(page);
    }

    public ArrayList<TwoPhotos> getCarPhotos(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getCarPhotos(page, id);
    }

    public ArrayList<TwoPhotos> getGirdPhotos(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getGirlPhotos(page, id);
    }

    public ArrayList<TwoPhotos> getPhotographPhotos(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getPhotographPhotos(page, id);
    }

    public ArrayList<TwoPhotos> getFunPhotos(int page, int id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getFunPhotos(page, id);
    }

    public ArrayList<PhotoDetails> getPhotoDetails(int aid) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getPhotoDetails(aid);
    }

    // vote
    public ArrayList<Vote> getVote() throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.getVote();
    }

    public ArrayList<VoteItem> getVoteItems(int vid) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getVoteItems(vid);
    }

    public boolean register(String regemail, String regepass) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.register(regemail, regepass);
    }

    public boolean login(String logemail, String logpassword) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.login(logemail, logpassword);
    }

    public ArrayList<Magazine> getMagazine() throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.getMagazine();
    }

    public ArrayList<ArticleMagzine> getArticleMagzine(int sid, int pid) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getArticleMagzine(sid, pid);
    }

    public ArrayList<PictureMagzine> getContentMagzine(int sid, int pid) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getPictureMagzine(sid, pid);
    }

    public Article getArticle(int sid) throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.getArticle(sid);
    }

}
