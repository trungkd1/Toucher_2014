package com.efse.toucher;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Window;
import android.widget.ImageView;

import com.efse.toucher.adapter.FragmentViewPagerAdapter;
import com.efse.toucher.service.ToucherService;
import com.efse.toucher.service.ToucherService.LocalBinder;
import com.efse.toucher.utils.Log;
import com.efse.toucher.utils.MenuItem;
import com.efse.toucher.utils.OnViewPannelListener;
import com.efse.toucher.utils.PointItem;
import com.efse.toucher.utils.SaveData;
import com.efse.toucher.utils.Utils;
import com.efse.viewpagerindicator.TitlePageIndicator;

public class MainActivity extends FragmentActivity implements OnPageChangeListener, OnViewPannelListener {
//	private ToucherService toucherService;
	
	protected ToucherService toucherService;
	private FragmentViewPagerAdapter adapter;
	private ViewPager pager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		// setBehindContentView(R.layout.main);
		init();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void init() {
		adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(),
				this);
		// setBehindOffset(450);
		// setBehindScrollScale(0.5f);
		pager = (ViewPager) findViewById(R.id.pagerTitle);
		pager.setAdapter(adapter);
		TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.indicatorTitle);
		indicator.setViewPager(pager);
		indicator.setOnPageChangeListener(this);
		 
		int dimen_Panel = getWindowManager().getDefaultDisplay().getWidth();
		SaveData.getInstance(this).setWidthDisplay(dimen_Panel);
		
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("FILE_DOWNLOADED_ACTION");
		registerReceiver(intentReceiver, intentFilter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Utils.onCancelNotification(this);
		onStartService();
	}

	@Override
	public void onPageScrollStateChanged(int position) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}
	
	
	@Override
	public void onPageSelected(int position) {
	}



	public void setAlphaPoint(int alpha) {
		if(Utils.isServiceRunning(this, ToucherService.class.getName())){
			toucherService.setAlpha(alpha);
		}
			
	}

	public void setSizePoint(int size) {
		if(Utils.isServiceRunning(this, ToucherService.class.getName())){
			toucherService.setSize(size);
		}
			
	}

	public void changePoint(PointItem item) {
		if(Utils.isServiceRunning(this, ToucherService.class.getName())){
			toucherService.changePoint(item);
		}
	}

	
	
	@Override
	public void setOnViewPannelListener(ImageView view,MenuItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(Utils.isServiceRunning(this, ToucherService.class.getName()))
			try{
				unbindService(mConnection);
			}catch(Exception ex){
				
			}
		
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(intentReceiver != null)
			this.unregisterReceiver(intentReceiver);
	}
	
	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			toucherService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			LocalBinder mLocalBinder = (LocalBinder) service;
			toucherService = mLocalBinder.getServerInstance();
			
		}
	};
	
	private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			onStartService();
		}
	};
	
	protected void onStartService(){
		Log.e("onStartService");
		Intent intent = Utils.startService(MainActivity.this);
		ComponentName myService = startService(intent);
		intent.setComponent(myService);
		bindService(intent, mConnection, Service.BIND_IMPORTANT);
	}
}
