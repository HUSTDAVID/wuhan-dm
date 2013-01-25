
package com.wh.dm.type;

public class PictureMagzine {

    private int sid;
    private int id;
    private String description;
    private String pic;
    private int aid;
    private String addtime;

    public int getAid() {

        return aid;
    }

    public void setAid(int aid) {

        this.aid = aid;
    }

    public int getSid() {

        return sid;
    }

    public void setSid(int sid) {

        this.sid = sid;
    }

    public void setId(int _id) {

        id = _id;
    }

    public int getId() {

        return id;
    }

    public void setDescription(String _description) {

        description = _description;
    }

    public String getDescription() {

        return description;
    }

    public void setPic(String _pic) {

        pic = _pic;
    }

    public String getPic() {

        return pic;
    }

    public void setAddtime(String _addtime) {

        addtime = _addtime;
    }

    public String getAddtime() {

        return addtime;
    }

}
