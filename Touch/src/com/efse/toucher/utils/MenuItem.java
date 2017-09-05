package com.efse.toucher.utils;

public class MenuItem {

	private String title;
	private int iconRes;
	private int type;
	private int id;
	private int pos;

	public MenuItem(String title, int iconRes) {
		this.title = title;
		this.iconRes = iconRes;
	}
	
	public MenuItem(String title,int type, int iconRes) {
		this.title = title;
		this.iconRes = iconRes;
	}

	public MenuItem(int id,String title,int type,int iconRes,int pos) {
		this.id = id;
		this.title = title;
		this.type = type;
		this.iconRes = iconRes;
		this.pos = pos;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIconRes() {
		return iconRes;
	}

	public void setIconRes(int iconRes) {
		this.iconRes = iconRes;
	}

	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public MenuItem(){
		
	}
	
}
