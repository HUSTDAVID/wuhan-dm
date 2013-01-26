
package com.wh.dm.util;

import com.wh.dm.type.Photo;
import com.wh.dm.type.TwoPhotos;

import java.util.ArrayList;

public class PhotoUtil {

    public static ArrayList<TwoPhotos> chagePhoto(ArrayList<Photo> photo) {

        if (photo != null) {
            int size = photo.size();
            ArrayList<TwoPhotos> twoPhotos = new ArrayList<TwoPhotos>();
            for (int i = 0; i < size; i++) {
                TwoPhotos twoP = new TwoPhotos();
                twoP.setLeftPhoto(photo.get(i));
                i++;
                if (i < size) {
                    twoP.setRightPhoto(photo.get(i));
                    twoPhotos.add(twoP);
                } else {
                    twoPhotos.add(twoP);
                }
            }

            return twoPhotos;
        } else {
            return null;
        }
    }

    public static ArrayList<Photo> chageOnePhoto(ArrayList<TwoPhotos> photos) {

        if (photos != null) {
            ArrayList<Photo> photo = new ArrayList<Photo>();
            for (int i = 0; i < photos.size(); i++) {
                photo.add(photos.get(i).getLeftPhoto());
                if (photos.get(i).getRightPhoto() != null) {
                    photo.add(photos.get(i).getRightPhoto());
                }
            }
            return photo;
        } else
            return null;
    }

}
