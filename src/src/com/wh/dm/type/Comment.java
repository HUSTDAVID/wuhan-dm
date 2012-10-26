package com.wh.dm.type;

public class Comment {
	private int no;
	private int id;
	private int aid;
	private int typeid;
	private int pid;
	private String username;
	private String artctitle;
	private String ip;
	private int ischeck;
	private String dtime;
	private int mid;
	private int bad;
	private int good;
	private int ftype;
	private String msg;

	public void setNo(int _no){
		no= _no;
	}

	public void setId(int _id){
		id = _id;
	}
	public void setAid(int _aid){
		aid = _aid;
	}
	public void setTypeid(int _typeid){
		typeid = _typeid;
	}
	public void setPid(int _pid){
		pid = _pid;
	}

	public void setUsrname(String _username){
		username = _username;
	}
	public void setArtctitle(String _artctitle){
		artctitle = _artctitle;
	}
	public void setIp(String _ip){
		ip =_ip;
	}
	public void setIschek(int _ischeck){
	  ischeck = _ischeck;
	}
	public void setDtime(String _dtime){
		dtime = _dtime;
	}
	public void setMid(int _mid){
		mid = _mid;
	}
	public void setBad(int _bad){
		bad = _bad;

	}
	public void setGood(int _good){
		good = _good;
	}
	public void setFtype(int _ftype){
		ftype = _ftype;
	}
	public void setMsg(String _msg){
		msg = _msg;
	}

}
