
package com.wh.dm.util;

import com.wh.dm.type.Magazine;
import com.wh.dm.type.MagazineBody;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

public class LoadUtil {

    public static ArrayList<MagazineBody> loadMagazine(String json) {

        Magazine magazine = new Magazine();
        int sid;
        ArrayList<MagazineBody> magazineBodys = new ArrayList<MagazineBody>();
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

            // String body = jsonObject.getString("magazinebody");
            JSONArray arr = jsonObject.getJSONArray("magazinebody");
            for (int i = 0; i < arr.length(); i++) {
                MagazineBody mbody = new MagazineBody();
                JSONObject objBody = (JSONObject) arr.get(i);
                mbody.setNo(objBody.getInt("no"));
                mbody.setId(objBody.getInt("id"));
                mbody.setTitle(objBody.getString("title"));
                mbody.setAuthor(objBody.getString("author"));
                mbody.setWriter(objBody.getString("writer"));
                mbody.setSource(objBody.getString("source"));
                mbody.setLitpic(objBody.getString("litpic"));
                mbody.setPubdate(objBody.getString("pubdate"));
                mbody.setBody(objBody.getString("body"));
                mbody.setSid(sid);

                magazineBodys.add(mbody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return magazineBodys;
    }
}
