
package com.wh.dm.db;

import com.wh.dm.type.FavoriteNews;
import com.wh.dm.type.FavoritePhoto;
import com.wh.dm.type.LoadInfo;
import com.wh.dm.type.Magazine;
import com.wh.dm.type.MagazineBody;
import com.wh.dm.type.NewsContent;
import com.wh.dm.type.Photo;
import com.wh.dm.type.PhotoDetails;
import com.wh.dm.type.PicWithTxtNews;
import com.wh.dm.type.PostMessage;

import java.util.ArrayList;

public interface Database {

    // local news
    public void deleteAllData();

    public void deleteAllNews();

    public void addHeadNews(ArrayList<PicWithTxtNews> news);

    public void addHouseNews(ArrayList<PicWithTxtNews> news);

    public void addCarNews(ArrayList<PicWithTxtNews> news);

    public void addFashionNews(ArrayList<PicWithTxtNews> news);

    public void addLifeNews(ArrayList<PicWithTxtNews> news);

    public void addTravelNews(ArrayList<PicWithTxtNews> news);

    public void addNewsContent(NewsContent newsContent);

    public void deleteHeadNews();

    public void deleteHouseNews();

    public void deleteCarNews();

    public void deleteFashionNews();

    public void deleteLifeNews();

    public void deleteTravelNews();

    public void deleteNewsContent();

    public ArrayList<PicWithTxtNews> getHeadNews();

    public ArrayList<PicWithTxtNews> getHouseNews();

    public ArrayList<PicWithTxtNews> getCarNews();

    public ArrayList<PicWithTxtNews> getFashionNews();

    public ArrayList<PicWithTxtNews> getLifeNews();

    public ArrayList<PicWithTxtNews> getTravelNews();

    public NewsContent getNewsContent(int id);

    // photos
    public void addAllPhoto(ArrayList<Photo> photo);

    public void addHotPhoto(ArrayList<Photo> photo);

    public void addCarPhoto(ArrayList<Photo> photo);

    public void addGirlPhoto(ArrayList<Photo> photo);

    public void addPhotographPhoto(ArrayList<Photo> photo);

    public void addFunPhoto(ArrayList<Photo> photo);

    public void deleteAllPhoto();

    public void deleteHotPhoto();

    public void deleteCarPhoto();

    public void deleteGirlPhoto();

    public void deletePhotographPhoto();

    public void deleteFunPhoto();

    public ArrayList<Photo> getAllPhoto();

    public ArrayList<Photo> getHotPhoto();

    public ArrayList<Photo> getCarPhoto();

    public ArrayList<Photo> getGirlPhoto();

    public ArrayList<Photo> getPhotographPhoto();

    public ArrayList<Photo> getFunPhoto();

    // photo details
    public void addAllPhotoDet(ArrayList<PhotoDetails> photoDet);

    public void addHotPhotoDet(ArrayList<PhotoDetails> photoDet);

    public void addCarPhotoDet(ArrayList<PhotoDetails> photoDet);

    public void addGirlPhotoDet(ArrayList<PhotoDetails> photoDet);

    public void addPhotographPhotoDet(ArrayList<PhotoDetails> photoDet);

    public void addFunPhotoDet(ArrayList<PhotoDetails> photoDet);

    public void deleteAllPhotoDet();

    public void deleteHotPhotoDet();

    public void deleteCarPhotoDet();

    public void deleteGirlPhotoDet();

    public void deletePhotographPhotoDet();

    public void deleteFunPhotoDet();

    public ArrayList<PhotoDetails> getAllPhotoDet();

    public ArrayList<PhotoDetails> getHotPhotoDet();

    public ArrayList<PhotoDetails> getCarPhotoDet();

    public ArrayList<PhotoDetails> getGirlPhotoDet();

    public ArrayList<PhotoDetails> getPhotographPhotoDet();

    public ArrayList<PhotoDetails> getFunPhotoDet();

    // magazine
    public void deleteHotMagazine();

    public void deleteCarMagazine();

    public void deleteGirlMagazine();

    public void deletePhotographMagazine();

    public void deleteFunMagazine();

    public void addHotMagazine(ArrayList<Magazine> magazine);

    public void addCarMagazine(ArrayList<Magazine> magazine);

    public void addGirlMagazine(ArrayList<Magazine> magazine);

    public void addPhotographMagazine(ArrayList<Magazine> magazine);

    public void addFunMagazine(ArrayList<Magazine> magazine);

    public ArrayList<Magazine> getHotMagazine();

    public ArrayList<Magazine> getCarMagazine();

    public ArrayList<Magazine> getGirlMagazine();

    public ArrayList<Magazine> getPhotographMagazine();

    public ArrayList<Magazine> getFunMagazine();

    // favorite
    public void addNewsFavorite(ArrayList<FavoriteNews> favorite);

    public ArrayList<FavoriteNews> getNewsFavorite();

    public void deleteNewsFavorite();

    public boolean deleteOneNewsFavorite(int id);

    public void addPhotoFavorite(ArrayList<FavoritePhoto> favorite);

    public ArrayList<FavoritePhoto> getPhotoFavorite();

    public void deletePhotoFavorite();

    public boolean deleteOnePhotoFavorite(int aid);

    // subcribe
    public ArrayList<Magazine> getSubcribedMagazine();

    public void addMagazine(Magazine magazine);

    public void addMagazines(ArrayList<Magazine> magazines);

    public void delMagazine(int id);

    public void delMagazines();

    // magazine download
    public ArrayList<MagazineBody> getMagazineBody(int sid);

    public void deleteMagazineBody();

    public void addMagazineBody(ArrayList<MagazineBody> magazineList);

    public Magazine getMagazine(int sid);

    public ArrayList<LoadInfo> getLoadInfo();

    public MagazineBody getOneMagazineBody(int id);

    public boolean isLoad(int sid);

    public void deleteLoadInfo();

    public void addLoadInfo(ArrayList<LoadInfo> loadInfos);

    // post message
    public ArrayList<PostMessage> getPostMessage();

    public void deletePostMessage();

    public void addPostMessage(ArrayList<PostMessage> postMessages);

}
