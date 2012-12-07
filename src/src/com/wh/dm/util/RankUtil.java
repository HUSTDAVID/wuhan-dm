
package com.wh.dm.util;

import com.wh.dm.type.Comment;

import java.util.ArrayList;

public class RankUtil {

    public static ArrayList<Comment> RankOrdering(ArrayList<Comment> comments) {

        Comment comment;
        int size = comments.size();
        int first;
        int flag;
        for (int i = 0; i < size; i++) {
            first = i;
            flag = i;
            comment = comments.get(i);
            int good = comments.get(first).getGood();
            for (int j = i; j < size; j++) {
                if (comments.get(j).getGood() > good) {
                    good = comments.get(j).getGood();
                    flag = j;
                }
            }
            comments.set(i, comments.get(flag));
            comments.set(flag, comment);
        }
        return comments;
    }
}
