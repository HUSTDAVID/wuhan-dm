
package com.wh.dm.db;

import com.wh.dm.type.NewsContent;
import com.wh.dm.type.Photo;
import com.wh.dm.type.PhotoDetails;
import com.wh.dm.type.PicWithTxtNews;

import java.util.ArrayList;

public interface Database {

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

}
