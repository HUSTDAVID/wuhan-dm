
package com.wh.dm.type;

public class Cover {

    private String name;
    private int drawableId;
    private int id;
    private String mgname;
    private String mgpic;
    private int template;

    public void setId(int _id) {

        id = _id;
    }

    public int getId() {

        return id;
    }

    public void setName(String _name) {

        name = _name;
    }

    public void setDrawableId(int _drawableId) {

        drawableId = _drawableId;
    }

    public String getName() {

        return name;
    }

    public int getDrawableId() {

        return drawableId;
    }

    public void setMagazineName(String _mgname) {

        mgname = _mgname;

    }

    public String getMagazineName() {

        return mgname;
    }

    public void setMagazinePic(String _mgpic) {

        mgpic = _mgpic;

    }

    public String getMagazinePic() {

        return mgpic;
    }

    public void setTemplate(int _template) {

        template = _template;
    }

    public int getTemplate() {

        return template;
    }
}
