
package com.wh.dm;

import com.wh.dm.error.UnKnownException;
import com.wh.dm.error.WH_DMException;
import com.wh.dm.type.Article;
import com.wh.dm.type.ArticleMagzine;
import com.wh.dm.type.Comment;
import com.wh.dm.type.Cover;
import com.wh.dm.type.Favorite;
import com.wh.dm.type.Magazine;
import com.wh.dm.type.MagazineSort;
import com.wh.dm.type.NewsContent;
import com.wh.dm.type.NewsType;
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

    public ArrayList<PicWithTxtNews> getHouseNews(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getHouseNews(page, id);
    }

    public ArrayList<PicWithTxtNews> getCarNews(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getCarNews(page, id);
    }

    public ArrayList<PicWithTxtNews> getFashionNews(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getFashionNews(page, id);
    }

    public ArrayList<PicWithTxtNews> getLifeNews(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getLifeNews(page, id);
    }

    public ArrayList<PicWithTxtNews> getTravelNews(int page, String id) throws WH_DMException,
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

    public ArrayList<Comment> getPhotoComment(int id, int page) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getPhotoComment(id, page);
    }

    public ArrayList<Reply> getReply(int fid) throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.getReply(fid);
    }

    public PostResult addTop(String fid, String machine) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.addTop(fid, machine);
    }

    public boolean addReply(String rconent, String fid) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.addReply(rconent, fid);
    }

    public boolean addReview(String content, int id) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.addReview(content, id);
    }

    public boolean addPhotoReview(String _content, int id) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.addPhotoReview(_content, id);
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

    public ArrayList<TwoPhotos> getCarPhotos(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getCarPhotos(page, id);
    }

    public ArrayList<TwoPhotos> getGirdPhotos(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getGirlPhotos(page, id);
    }

    public ArrayList<TwoPhotos> getPhotographPhotos(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getPhotographPhotos(page, id);
    }

    public ArrayList<TwoPhotos> getFunPhotos(int page, String id) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getFunPhotos(page, id);
    }

    public ArrayList<PhotoDetails> getPhotoDetails(int aid) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getPhotoDetails(aid);
    }

    // vote
    public ArrayList<Vote> getVote(int sid, String machine) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getVote(sid, machine);
    }

    public VoteResult postVote(int aid, String vtitle, String machine) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.postVote(aid, vtitle, machine);
    }

    public String getVoteNum(int vid) throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.getVoteNum(vid);
    }

    public ArrayList<VoteResultPercent> getVoteResultPercent(int vid) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getVoteResultPercent(vid);
    }

    public PostResult register(String regemail, String regepass, String machineId)
            throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.register(regemail, regepass, machineId);
    }

    public boolean login(String logemail, String logpassword, String machine)
            throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.login(logemail, logpassword, machine);
    }

    public PostResult login2(String logemail, String logpassword, String machine)
            throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.login2(logemail, logpassword, machine);
    }

    public boolean loginById(String machine) throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.loginById(machine);
    }

    public ArrayList<Magazine> getMagazine(String cid, int page) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getMagazine(cid, page);
    }

    public ArrayList<Magazine> getSearchMagazine(String key) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getSearchMagazine(key);
    }

    public ArrayList<MagazineSort> getMagazineSort() throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getMagazineSort();
    }

    public ArrayList<ArticleMagzine> getArticleMagzine(int sid) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getArticleMagzine(sid);
    }

    public ArrayList<PictureMagzine> getPictureMagzine(int sid) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getPictureMagzine(sid);
    }

    public ArrayList<PictureMagzine> getContentMagzine(int sid) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.getPictureMagzine(sid);
    }

    public Article getArticle(int sid) throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.getArticle(sid);
    }

    public PostResult commitFeedback(String contactways, String fcontent) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.commitFeedBack(contactways, fcontent);
    }

    public Cover subcribe(int id) throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.subcribe(id);
    }

    public boolean unsubcribe(int id) throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.unsubcribe(id);
    }

    public PostResult addFav(int nid, int type) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.addFav(nid, type);
    }

    public ArrayList<Favorite> getFav(int pz, int pi) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getFav(pz, pi);
    }

    public boolean delFav(int nid, int type) throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.delFav(nid, type);
    }

    public ArrayList<Magazine> getSubcribedMagazines() throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getSubcribedMagazines();
    }

    public PostResult getLoadPic() throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.getLoadPic();
    }

    public ArrayList<PostMessage> getMessage() throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.getMessages();
    }

    public String loadMagzine(int sid) throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.loadMagzine(sid);
    }

    public ArrayList<Magazine> getDefaultMagazine() throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getDefaultMagazine();
    }

    public PostResult findPassword(String email) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.findPassword(email);
    }

}
