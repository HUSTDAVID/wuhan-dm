package com.wh.dm.type;

public class FavoriteNews {
	private int no;
	private int id;
	private String title;
	private String litpic;
	private String pubdate;
    private String sid;
    
    public int getNo(){
		return no;
	}
	
	public void setNo(int _no){
		no=_no;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int _id){
		id=_id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String _title){
		title=_title;
	}
	
	public String getLitpic(){
		return litpic;
	}
	
	public void setLitpic(String _litpic){
		litpic=_litpic;
	}
	
	public String getPubdate(){
		return pubdate;
	}
	
	public void setPubdate(String _pubdate){
		pubdate=_pubdate;
	}
	
	public String getSid(){
		return sid;
	}
	
	public void setSid(String _sid){
		sid=_sid;
	}
}
