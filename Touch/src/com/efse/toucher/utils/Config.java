package com.efse.toucher.utils;

import android.content.Context;

import com.efse.toucher.R;

public class Config {

	public static final int FRAGMENT_HOME = 0;

	public static int[] MenuIcons = new int[] { 0, 
			R.drawable.ic_home,
			R.drawable.ic_star, 
			R.drawable.ic_setting, 
			R.drawable.ic_power_down,
			R.drawable.ic_apps, 
			R.drawable.ic_clean_memory,
			R.drawable.ic_back_key, 
			R.drawable.ic_menu,
			R.drawable.ic_cache_clean, 
			R.drawable.ic_clock,
			R.drawable.ic_screenshot, 
			R.drawable.ic_battery,
			R.drawable.ic_volume_up,
			R.drawable.ic_volume_down,
			R.drawable.ic_auto_orientation_on,
			R.drawable.ic_flash_off,
			R.drawable.ic_ringer_normal,
			R.drawable.ic_gps_on,
			R.drawable.ic_screen_light};
	
	public static int FIRST_SCREEN = 0;
	
	public static int SECONDS_SCREEN = 1;

	public static int NONE = 0;

	public static int HOME = 1;

	public static int FAVOR = 2;

	public static int SETTING = 3;

	public static int LOCK_SCREEN = 4;

	public static int APPS = 5;

	public static int CLEAN_MEMORY = 6;

	public static int BACK = 7;

	public static int MENU = 8;

	public static int CLEAN_CACHE = 9;
	
	public static int TIME = 10;

	public static int SCREEN_SHOT = 11;

	public static int BATTERY_DISPLAY = 12;
	
	
	public static final int FRAGMENT_FIRST = 0;
	
	public static final int FRAGMENT_SECONDS = 1;
	
	public static final int FRAGMENT_THIRD = 2;
	
	public static final int FRAGMENT_FOUR = 3;
	
	public static int getWidthScreen(Context context){
		return context.getResources().getDisplayMetrics().widthPixels;
	}
	
	public static int getHeightScreen(Context context){
		return context.getResources().getDisplayMetrics().heightPixels;
	}
}
