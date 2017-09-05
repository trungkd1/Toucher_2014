package com.efse.toucher.service;

import com.efse.toucher.utils.Log;

import android.app.IntentService;
import android.content.Intent;

public class MyIntentService extends IntentService{

	public MyIntentService() {
		super("service2");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.e("haiz");
		  Intent broadcastIntent = new Intent();
	      broadcastIntent.setAction("FILE_DOWNLOADED_ACTION");
	      getBaseContext().sendBroadcast(broadcastIntent);

	}

}
