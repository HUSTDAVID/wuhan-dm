
package com.wh.dm.util;

import com.wh.dm.type.ArticleMagzine;
import com.wh.dm.type.Magazine;
import com.wh.dm.type.PictureMagzine;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

public class LoadUtil {

    public static ArrayList<ArticleMagzine> loadMagazine(String json) {

        Magazine magazine = new Magazine();
        int sid;
        ArrayList<ArticleMagzine> articleMagzines = new ArrayList<ArticleMagzine>();
        try {
            JSONTokener jsonParser = new JSONTokener(json);
            JSONObject jsonObject = new JSONObject();
            jsonObject = (JSONObject) jsonParser.nextValue();

            JSONObject objMagazine = jsonObject.getJSONObject("magazine");
            sid = objMagazine.getInt("sid");
            magazine.setNo(objMagazine.getInt("no"));
            magazine.setSid(objMagazine.getInt("sid"));
            magazine.setCid(objMagazine.getString("cid"));
            magazine.setEditor(objMagazine.getString("editor"));
            magazine.setTemplate(objMagazine.getInt("template"));

            JSONArray arr = jsonObject.getJSONArray("magazinebody");
            for (int i = 0; i < arr.length(); i++) {
                ArticleMagzine article = new ArticleMagzine();
                JSONObject objBody = (JSONObject) arr.get(i);

                article.setId(objBody.getInt("id"));
                article.setTitle(objBody.getString("title"));
                article.setAuthor(objBody.getString("author"));
                article.setWriter(objBody.getString("writer"));
                article.setSource(objBody.getString("source"));
                article.setLitpic(objBody.getString("litpic"));
                article.setPubdate(objBody.getString("pubdate"));
                article.setBody(objBody.getString("body"));
                article.setSid(sid);
                article.setIspic(objBody.getBoolean("ispic"));

                articleMagzines.add(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articleMagzines;
    }

    public static int checkTemplate(String json) {

        int template = 0;
        try {
            JSONTokener jsonParser = new JSONTokener(json);
            JSONObject jsonObject = new JSONObject();
            jsonObject = (JSONObject) jsonParser.nextValue();

            JSONObject objMagazine = jsonObject.getJSONObject("magazine");
            template = objMagazine.getInt("template");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return template;
    }

    public static ArrayList<PictureMagzine> loadMagazinePic(String json) {

        int sid;
        ArrayList<PictureMagzine> magazinePics = new ArrayList<PictureMagzine>();
        try {
            JSONTokener jsonParser = new JSONTokener(json);
            JSONObject jsonObject = new JSONObject();
            jsonObject = (JSONObject) jsonParser.nextValue();
            JSONObject objMagazine = jsonObject.getJSONObject("magazine");
            sid = objMagazine.getInt("sid");

            JSONArray arr = jsonObject.getJSONArray("magazinebody");
            for (int i = 0; i < arr.length(); i++) {
                PictureMagzine mPic = new PictureMagzine();
                JSONObject objBody = (JSONObject) arr.get(i);
                mPic.setAddtime(objBody.getString("addtime"));
                mPic.setDescription(objBody.getString("description"));
                mPic.setId(objBody.getInt("id"));
                mPic.setPic(objBody.getString("pic"));
                mPic.setSid(sid);

                magazinePics.add(mPic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return magazinePics;
    }

}
