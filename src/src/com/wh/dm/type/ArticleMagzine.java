
package com.wh.dm.type;

public class ArticleMagzine {
    private int id;
    private String title;
    private String litpic;
    private String pubdate;
    private String author;
    private String writer;
    private String source;
    private int sid;
    private String body;
    private boolean ispic;

    public boolean isIspic() {

        return ispic;
    }

    public void setIspic(boolean ispic) {

        this.ispic = ispic;
    }

    public int getSid() {

        return sid;
    }

    public void setSid(int sid) {

        this.sid = sid;
    }

    public String getBody() {

        return body;
    }

    public void setBody(String body) {

        this.body = body;
    }

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

    public void setAuthor(String _author) {

        author = _author;
    }

    public String getAuthor() {

        return author;
    }

    public void setWriter(String _writer) {

        writer = _writer;
    }

    public String getWriter() {

        return writer;
    }

    public void setSource(String _source) {

        source = _source;
    }

    public String getSource() {

        return source;
    }
}
