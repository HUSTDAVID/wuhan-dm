
package com.wh.dm;

import com.wh.dm.error.UnKnownException;
import com.wh.dm.error.WH_DMException;
import com.wh.dm.type.Comment;
import com.wh.dm.type.NewsContent;
import com.wh.dm.type.PicWithTxtNews;
import com.wh.dm.type.PicsNews;
import com.wh.dm.type.Reply;

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

    public ArrayList<PicsNews> getPicsNews() throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.getPicsNews();
    }

    public ArrayList<PicWithTxtNews> getHeadNews() throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getHeadNews();
    }

    public ArrayList<PicWithTxtNews> getHouseNews() throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getHouseNews();
    }

    public ArrayList<PicWithTxtNews> getCarNews() throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getCarNews();
    }

    public ArrayList<PicWithTxtNews> getFashionNews() throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getFashionNews();
    }

    public ArrayList<PicWithTxtNews> getLifeNews() throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getLifeNews();
    }

    public ArrayList<PicWithTxtNews> getTravelNews() throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getTravelNews();
    }

    public NewsContent[] getNewsContent(int id) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getNewsContent(id);
    }

    public ArrayList<Comment> getComment(int id) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.getComment(id);
    }

    public ArrayList<Reply> getReply(int fid) throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.getReply(fid);
    }

    public boolean addTop(int fid) throws WH_DMException, UnKnownException, IOException {

        return mWH_DMHttpApiV1.addTop(fid);
    }

    public boolean addReply(String rconent, int id, int fid) throws WH_DMException,
            UnKnownException, IOException {

        return mWH_DMHttpApiV1.addReply(rconent, id, fid);
    }

    public boolean addReview(String content, int id) throws WH_DMException, UnKnownException,
            IOException {

        return mWH_DMHttpApiV1.addReview(content, id);
    }

}
