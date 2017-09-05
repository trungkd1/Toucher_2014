package com.efse.toucher.utils;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

public class AppEntry {
	private int id;
	private String packageName;
	private String packageActivity;
	private ResolveInfo pkgInfo;
	private PackageManager pkgManager;
	public boolean isSelected = false;
	
	public AppEntry() {

	}

	public AppEntry(ResolveInfo pkgInfo, PackageManager pkgManager) {
		this.pkgInfo = pkgInfo;
		this.pkgManager = pkgManager;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Drawable getIcon() {
		if(!TextUtils.isEmpty(packageName)){
			try {
				return pkgManager.getActivityIcon(new ComponentName(packageName, packageActivity));
			} catch (NameNotFoundException e) {
				return null;
			}
		}else{
			return pkgInfo.loadIcon(pkgManager);
		}
		
	}
	
	public Drawable getIcon(PackageManager pkgManager){
		this.pkgManager = pkgManager;
		return getIcon();
	}

	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageName() {
		if(TextUtils.isEmpty(packageName)){
			return pkgInfo.activityInfo.applicationInfo.packageName;
			
		}else{
			return packageName;
		}
		
	}
	
	public String getPackageActivity() {
		if(TextUtils.isEmpty(packageActivity)){
			return pkgInfo.activityInfo.name;
		}else{
			return packageActivity;
		}
	}

	public void setPackageActivity(String packageActivity) {
		this.packageActivity = packageActivity;
	}



	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
