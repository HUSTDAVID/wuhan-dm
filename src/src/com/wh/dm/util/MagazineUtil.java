
package com.wh.dm.util;

import com.wh.dm.type.Magazine;
import com.wh.dm.type.TwoMagazine;

import java.util.ArrayList;

public class MagazineUtil {

    public static ArrayList<TwoMagazine> toTwoMagazine(ArrayList<Magazine> magazineList) {

        ArrayList<TwoMagazine> twoMagazine = new ArrayList<TwoMagazine>();
        for (int i = 0; i < magazineList.size(); i++) {
            TwoMagazine two = new TwoMagazine();
            two.setLeftMagazine(magazineList.get(i));
            i++;
            if (magazineList.size() <= i) {
                // two.setRightMagazine(magazineList.get(i - 1));
                Magazine mg = new Magazine();
                mg.setAddtime("END");
                two.setRightMagazine(mg);
                twoMagazine.add(two);

            } else {
                two.setRightMagazine(magazineList.get(i));
                twoMagazine.add(two);
            }

        }

        return twoMagazine;
    }

    public static ArrayList<Magazine> toOneMagazine(ArrayList<TwoMagazine> twoMagazine) {

        ArrayList<Magazine> oneMagazine = new ArrayList<Magazine>();
        for (int i = 0; i < twoMagazine.size(); i++) {
            Magazine left = new Magazine();
            Magazine right = new Magazine();
            left = twoMagazine.get(i).getLeftMagazine();
            right = twoMagazine.get(i).getRightMagazine();
            oneMagazine.add(left);
            oneMagazine.add(right);
        }

        return oneMagazine;
    }
}
