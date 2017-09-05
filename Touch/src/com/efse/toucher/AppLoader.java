package com.efse.toucher;

import java.util.ArrayList;

import com.efse.toucher.utils.AppEntry;
import com.efse.toucher.utils.Utils;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class AppLoader extends AsyncTaskLoader<ArrayList<AppEntry>>{

	private Context context;
	boolean isLoading = false;
	public AppLoader(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public ArrayList<AppEntry> loadInBackground() {
		return Utils.getInstalledApps(this.context);
	}
	
	@Override
	protected void onStartLoading() {
		forceLoad();
	}

	@Override
	protected void onForceLoad() {
		super.onForceLoad();
	}


	@Override
	public void forceLoad() {
		if (!isLoading) {
			super.forceLoad();
			isLoading = !isLoading;
		}
	}
}
