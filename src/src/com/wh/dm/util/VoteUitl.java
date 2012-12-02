
package com.wh.dm.util;

import com.wh.dm.type.Vote;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VoteUitl {

    public static ArrayList<Vote> parseVote(String json) {

        ArrayList<Vote> votes = new ArrayList<Vote>();
        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                Vote vote = new Vote();
                JSONObject obj = (JSONObject) arr.get(i);
                vote.setNo(obj.getInt("no"));
                vote.setAid(obj.getInt("aid"));
                vote.setVotename(obj.getString("votename"));
                vote.setStarttime(obj.getString("starttime"));
                vote.setEndtime(obj.getString("endtime"));
                vote.setIsmore(obj.getBoolean("ismore"));
                vote.setIsenable(obj.getBoolean("isenable"));
                vote.setDes(obj.getString("des"));
                vote.setPic(obj.getString("pic"));
                vote.setCode(obj.getString("code"));
                vote.setClick(obj.getInt("click"));
                vote.setInterval(obj.getInt("interval"));
                String votenote = obj.getString("votenote");
                votenote = "[" + votenote + "]";
                JSONArray notesArr = new JSONArray(votenote);
                String[] notes = new String[notesArr.length()];
                for (int j = 0; j < notesArr.length(); j++) {
                    notes[j] = notesArr.getString(j);
                }
                vote.setVotenote(notes);
                votes.add(vote);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return votes;
    }
}
