package com.efse.toucher.utils;



import android.content.Context;
import android.content.SharedPreferences;

import com.efse.toucher.R;
import com.google.gson.Gson;

public class SaveData {

	private static final String COLOR_PANNEL = "COLOR_PANNEL";
	
	private static final String CONNER_PANNEL = "CONNER_PANNEL";
	
	private static final String ALPHA_PANNEL = "ALPHA_PANNEL";
	
	private static final String SQUARE_PANNEL = "SQUARE_PANNEL";
	
	private static final String ROUND_PANNEL = "ROUND_PANNEL";
	
	private static final String WIDTH_DISPLAY = "WIDTH_DISPLAY";
	
	private static final String WIDTH_PANNEL = "WIDTH_PANNEL";
	
	private static final String WIDTH_POINTER = "WIDTH_POINTER";
	
	private static final String ALPHA_POINT = "ALPHA_POINT";
	
	private static final String SIZE_POINT = "SIZE_POINT";
	
	private static final String MODE = "MODE";
	
	private static final String ANIMATION_TYPE = "ANIMATION_TYPE";
	
	private static final String MY_POINT = "MY_POINT";
	
	private static final String _AXIS_X = "_AXIS_X";
	
	private static final String _AXIS_Y = "_AXIS_Y";
	
	private static final String RUNNING = "RUNNING";
	
	public static boolean isStarted = false;
	
	/** The save data. */
	private static SaveData saveData = null;
	
	/** The share preference. */
	private SharedPreferences sharePreference;

	public static SaveData getInstance(Context context) {
		if (saveData == null) {
			saveData = new SaveData(context);
		}
		return saveData;
	}

	private SaveData(Context context) {
		sharePreference = context.getSharedPreferences("toucher",
				Context.MODE_PRIVATE);
	}
	
	public void setFirstRunning(boolean isFirst) {
		sharePreference.edit().putBoolean(RUNNING, isFirst).commit();
	}
	
	public boolean isFirstRunning() {
		return sharePreference.getBoolean(RUNNING, true);
	}
	
	public void setMODE(int mode) {
		sharePreference.edit().putInt(MODE, mode).commit();
	}
	
	public int getMODE() {
		return sharePreference.getInt(MODE, 0);
	}

	public void setALphaPannel(float alpha) {
		sharePreference.edit().putFloat(ALPHA_PANNEL, alpha).commit();
	}
	
	public float getAlphaPannel() {
		return sharePreference.getFloat(ALPHA_PANNEL, 245);
	}
	
	
	public void setALphaPoint(float alpha) {
		sharePreference.edit().putFloat(ALPHA_POINT, alpha).commit();
	}
	
	public float getAlphaPoint() {
		return sharePreference.getFloat(ALPHA_POINT, 226);
	}
	
	public void setSizePoint(int size) {
		sharePreference.edit().putInt(SIZE_POINT, size).commit();
	}
	
	public int getSizePoint() {
		return sharePreference.getInt(SIZE_POINT, getWidthDisplay()/6);
	}
	
	public int getColorPannel() {
		return sharePreference.getInt(COLOR_PANNEL, -13211136);
	}
	
	public void setColorPannel(int color) {
		sharePreference.edit().putInt(COLOR_PANNEL, color).commit();
	}
	
	public int getConnerPannel() {
		return sharePreference.getInt(CONNER_PANNEL, 0);
	}

	public void setConnerPannel(int conner) {
		sharePreference.edit().putInt(CONNER_PANNEL, conner).commit();
	}
	
	public int getRoundPannel() {
		return sharePreference.getInt(ROUND_PANNEL, getWidthPannel()/6);
	}

	public void setRoundPannel(int round) {
		sharePreference.edit().putInt(ROUND_PANNEL, round).commit();
	}
	
	public int getSquarePannel() {
		return sharePreference.getInt(SQUARE_PANNEL, getWidthPannel()/3-5);
	}

	public void setSquarePannel(int conner) {
		sharePreference.edit().putInt(SQUARE_PANNEL, conner).commit();
	}
	
	
	public int getWidthPannel() {
		return sharePreference.getInt(WIDTH_PANNEL, (int)(getWidthDisplay()/1.578947));
	}

	public void setWidthPannel(int width) {
		sharePreference.edit().putInt(WIDTH_PANNEL, width).commit();

	}
	
	public int getWidthDisplay() {
		return sharePreference.getInt(WIDTH_DISPLAY, 0);
	}

	public void setWidthDisplay(int width) {
		sharePreference.edit().putInt(WIDTH_DISPLAY, width).commit();

	}
	
	public int getWidthPointer() {
		return sharePreference.getInt(WIDTH_POINTER, 0);
	}

	public void setWidthPointer(int width) {
		sharePreference.edit().putInt(WIDTH_POINTER, width).commit();
	}
	
	public void setPoint(PointItem item){
	    Gson gson = new Gson();
	    String json = gson.toJson(item);
		sharePreference.edit().putString(MY_POINT, json).commit();
	}
	
	public PointItem getPoint(){
		 Gson gson = new Gson();
		 PointItem item = new PointItem(R.drawable.f_japan);
		 String json = sharePreference.getString(MY_POINT, gson.toJson(item));
		 PointItem obj = gson.fromJson(json, PointItem.class);
		 return obj;
	}
	
	public void setAxis(int x,int y){
		sharePreference.edit().putInt(_AXIS_X, x).commit();
		sharePreference.edit().putInt(_AXIS_Y, y).commit();
	}
	
	public int[] getAxis(int width,int height){
		Log.e("getWidthPointer :"+getSizePoint());
		int[] i = new int[2];
		i[0] = sharePreference.getInt(_AXIS_X, 0) + getSizePoint()/2 ;
		i[1] = sharePreference.getInt(_AXIS_Y, 100) - height/2 + getSizePoint()/2;
		return i;
	}
	
	public int[] getAxis(){
		int[] i = new int[2];
		i[0] = sharePreference.getInt(_AXIS_X, 0);
		i[1] = sharePreference.getInt(_AXIS_Y, 100);
		return i;
	}
	
	public int getAnimationType() {
		return sharePreference.getInt(ANIMATION_TYPE, 0);
	}

	public void setAnimationType(int type) {
		sharePreference.edit().putInt(ANIMATION_TYPE, type).commit();
	}
}
