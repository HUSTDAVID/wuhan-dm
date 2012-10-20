package com.wh.dm.type;

import java.util.Date;

public class PicsNews {

	private int no;
	private int id;
	private int typeid;
	private String litpic;
	private String title;
	private int sortrank;
	private boolean isfirst;
	private boolean ishot;
	private boolean isflash;
	private String isurl;
	private String pubdate;

	public void setNo(int _no) {
		id = _no;
	}

	public int getNo() {
		return no;
	}

	public void setId(int _id) {
		id = _id;
	}

	public int getId() {
		return id;
	}

	public void setTypeId(int _typeid) {
		typeid = _typeid;
	}

	public int getTypeId() {
		return typeid;
	}

	public void setLitpic(String _litpic) {
		litpic = _litpic;
	}

	public String getLitpic() {
		return litpic;
	}

	public void setTite(String _title) {
		title = _title;
	}

	public String getTitle() {
		return title;
	}

	public void setIsFirst(boolean _isfirst) {
		isfirst = _isfirst;
	}

	public boolean getIsFirt() {
		return isfirst;
	}

	public void setIshot(boolean _ishot) {
		ishot = _ishot;
	}

	public boolean getIshot() {
		return ishot;
	}

	public void setIsurl(String _isurl) {
		isurl = _isurl;
	}

	public String getIsurl() {

		return isurl;
	}

}
