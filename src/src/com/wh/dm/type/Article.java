
package com.wh.dm.type;

public class Article {
    private int id;
    private String title;
    private String litpic;
    private String pubdate;
    private int sid;
    private String body;

    public void setId(int _id) {

        id = _id;
    }

    public int getId() {

        return id;
    }

    public void setTitle(String _title) {

        title = _title;
    }

    public String getTitle() {

        return title;
    }

    public void setLitpic(String _litpic) {

        litpic = _litpic;
    }

    public String getLitpic() {

        return litpic;
    }

    public void setPubdate(String _pubdate) {

        pubdate = _pubdate;
    }

    public String getPubdate() {

        return pubdate;
    }

    public void setSid(int _sid) {

        sid = _sid;
    }

    public int getSid() {

        return sid;
    }

    public void setBody(String _body) {

        body = _body;
    }

    public String getBody() {

        return body;
    }
}
