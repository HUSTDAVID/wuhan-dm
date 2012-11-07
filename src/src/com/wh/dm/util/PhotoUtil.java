
package com.wh.dm.util;

import com.wh.dm.type.Photo;
import com.wh.dm.type.TwoPhotos;

import java.util.ArrayList;

public class PhotoUtil {

    public static ArrayList<TwoPhotos> chagePhoto(ArrayList<Photo> photo) {

        ArrayList<TwoPhotos> twoPhotos = new ArrayList<TwoPhotos>();
        for (int i = 0; i < photo.size(); i++) {
            TwoPhotos twoP = new TwoPhotos();
            twoP.setLeftPhoto(photo.get(i));
            i++;
            twoP.setRightPhoto(photo.get(i));

            twoPhotos.add(twoP);
        }

        return twoPhotos;
    }

    public static ArrayList<Photo> chageOnePhoto(ArrayList<TwoPhotos> photos) {

        ArrayList<Photo> photo = new ArrayList<Photo>();
        for (int i = 0; i < photos.size(); i++) {
            photo.add(photos.get(i).getLeftPhoto());
            photo.add(photos.get(i).getRightPhoto());
        }
        return photo;
    }

}
