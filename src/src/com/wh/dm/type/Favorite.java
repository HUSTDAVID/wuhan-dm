package com.wh.dm.type;

public class Favorite {
	
	private int id;
	private String title;
	private String litpic;
	private String pubdate;
		
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
	
}
