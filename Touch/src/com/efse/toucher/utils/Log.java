package com.efse.toucher.utils;

public class Log {

	private static String TAG = "debug";
	
	public static void e(String log){
		android.util.Log.e(TAG, log);
	}
	
	public static void v(String log){
		android.util.Log.v(TAG, log);
	}
	
	public static void i(String log){
		android.util.Log.i(TAG, log);
	}
}
