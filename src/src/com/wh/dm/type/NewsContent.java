package com.wh.dm.type;

public class NewsContent {

	private int no;
	private int id;
	private int typeid;
	private int sortrank;
	private String title;
	private String source;
	private String litpic;
	private String pubdate;
	private boolean isfirst;
	private boolean ishot;
	private String isurl;
	private String body;

	public void setNo(int _no){
		no = _no;
	}

	public int getNo(){
		return no;
	}
	public void setId(int _id){
		id = _id;
	}
	public int getId(){
		return id;
	}
	public void setTypeid(int _typeid){
		typeid = _typeid;
	}
	public int getTypeid(){
		return typeid;
	}
	public void setSortrank(int _sortrank){
		sortrank = _sortrank;
	}
	public int getSortrank(){
		return sortrank;
	}
	public void setTitle(String _title){
		title = _title;
	}
	public String getTitle(){
		return title;
	}
	public void setSource(String _source){
		source = _source;
	}
	public String getSource(){
		return source;
	}
	public void setLitpic(String _litpic){
		litpic = _litpic;
	}
	public String getLitpic(){
		return litpic;
	}
	public void setPubdate(String _pubdate){
		pubdate = _pubdate;
	}
	public String getPubdate(){
		return pubdate;
	}
	public void setIsfirst(boolean _isfirst){
		isfirst = _isfirst;
	}
	public boolean getIsfirst(){
		return isfirst;
	}
	public void setIshot(boolean _ishot){
		ishot = _ishot;
	}
	public boolean getIshot(){
		return ishot;
	}
	public void setIsurl(String _isurl){
		isurl = _isurl;
	}
	public String getIsurl(){
		return isurl;
	}
	public void setBody(String _body){
		body = _body;
	}
	public String getBody(){
		return body;
	}
}
