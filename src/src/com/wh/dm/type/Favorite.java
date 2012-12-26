package com.wh.dm.type;

public class Favorite {
	private int no;
	private String fid;  //favorite id
	private String uid; 
	private String nid;  //content id
	private String addtime;  //collect time
	private String ctype;     //type 0:news 1:photo
	private String title;     //title
	private String ext3;     
    
    public int getNo(){
		return no;
	}
	
	public void setNo(int _no){
		no=_no;
	}
	
	public int getFid(){
		return Integer.valueOf(fid);
	}
	
	public void setFid(int _id){
		fid=String.valueOf(_id);
	}
	
	public String getUid(){
		return uid;
	}
	
	public void setUid(String _id){
		uid=_id;
	}

	public int getNid(){
		return Integer.valueOf(nid);
	}
	
	public void setNid(int _id){
		nid=String.valueOf(_id);
	}
	
	public String getAddTime(){
		return addtime;
	}
	
	public void setAddTime(String _time){
		addtime=_time;
	}
	
	public int getType(){
		return Integer.valueOf(ctype);
	}
	
	public void setType(int _type){
		ctype=String.valueOf(_type);
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String _title){
		title=_title;
	}
	
	public String getExt3(){
		return ext3;
	}
	
	public void setExt3(String _ext3){
		ext3=_ext3;
	}
	
}
