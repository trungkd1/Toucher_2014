package com.efse.toucher.utils;

import android.graphics.Bitmap;

public class PointItem {
	
	
	private Bitmap iconBitmap;
	private int iconRes;
	private int id;
	private boolean isPrivateIcon = false;

	public PointItem(){
		
	}
	
	public PointItem(Bitmap iconBitmap){
		this.iconBitmap = iconBitmap;
	}
	
	public PointItem(int iconRes){
		this.iconRes = iconRes;
	}
	
	public int getIconRes() {
		return iconRes;
	}
	public void setIconRes(int iconRes) {
		this.iconRes = iconRes;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Bitmap getIconBitmap() {
		return iconBitmap;
	}

	public void setIconBitmap(Bitmap iconBitmap) {
		this.iconBitmap = iconBitmap;
	}
	
	public boolean isPrivateIcon() {
		return isPrivateIcon;
	}

	public void setPrivateIcon(boolean isPrivateIcon) {
		this.isPrivateIcon = isPrivateIcon;
	}
}
