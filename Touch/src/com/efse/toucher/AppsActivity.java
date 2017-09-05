package com.efse.toucher;

import java.util.ArrayList;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.efse.toucher.adapter.FavoriteAppAdapter;
import com.efse.toucher.utils.AppEntry;

public class AppsActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentManager fm = getSupportFragmentManager();
		if (fm.findFragmentById(android.R.id.content) == null) {
			AppListFragment list = new AppListFragment();
			fm.beginTransaction().add(android.R.id.content, list).commit();
		}
	}

	
	class AppListFragment  extends Fragment implements OnItemClickListener,LoaderManager.LoaderCallbacks<ArrayList<AppEntry>>{
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			getActivity().setTitle(R.string.apps);
			View title = getActivity().getWindow().findViewById(android.R.id.title);
			View titleBar = (View) title.getParent();
			titleBar.setBackgroundColor(getResources().getColor(R.color.aqua_deep));
			
		}
		
		private GridView grid;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.layout_grid, container, false);
			grid = (GridView)view.findViewById(R.id.gridView);
			grid.setOnItemClickListener(this);
			grid.setSelector(R.color.aqua_lighter);
			grid.setBackgroundColor(getResources().getColor(R.color.white));
			getLoaderManager().initLoader(0, savedInstanceState, this);
			return view;
		}
		
		@Override
		public Loader<ArrayList<AppEntry>> onCreateLoader(int arg0, Bundle arg1) {
			// TODO Auto-generated method stub
			return new AppLoader(getActivity());
		}

		@Override
		public void onLoadFinished(Loader<ArrayList<AppEntry>> arg0, ArrayList<AppEntry> arg1) {
			// TODO Auto-generated method stub
			FavoriteAppAdapter adapter = new FavoriteAppAdapter(getActivity(), arg1,false);
			grid.setAdapter(adapter);
		}

		@Override
		public void onLoaderReset(Loader<ArrayList<AppEntry>> arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long id) {
			AppEntry obj = (AppEntry)arg0.getItemAtPosition(position);
			Intent LaunchIntent = getActivity().getPackageManager().getLaunchIntentForPackage(obj.getPackageName());
			LaunchIntent.setComponent(new ComponentName(obj.getPackageName(),  obj.getPackageActivity()));
			startActivity(LaunchIntent);	
			finish();
		}
	}
	
	
	
	
	
	
	
}
