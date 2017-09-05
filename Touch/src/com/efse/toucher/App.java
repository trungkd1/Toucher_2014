package com.efse.toucher;

import com.efse.toucher.data.SQLiteAdapter;
import com.efse.toucher.utils.MenuItem;
import com.efse.toucher.utils.SaveData;

import android.app.Application;

public class App extends Application{

	
	public static SQLiteAdapter adapterDB ;
	
	@Override
	public void onCreate() {
		super.onCreate();
		initPannel();
	}
	
	private void initPannel(){
		adapterDB = new SQLiteAdapter(getApplicationContext());
		if(SaveData.getInstance(getApplicationContext()).isFirstRunning()){
			for (int i = 0; i < 9; i++) {
				adapterDB.insertMenu(new MenuItem());
			}
			SaveData.getInstance(getApplicationContext()).setFirstRunning(false);
		}
		
	}
	
}
