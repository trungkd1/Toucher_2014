package com.efse.toucher;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.method.Touch;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.efse.toucher.adapter.FavoriteAppAdapter;
import com.efse.toucher.utils.AppEntry;
import com.efse.toucher.utils.Log;

public class FavoriteAppActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		FragmentManager fm = getSupportFragmentManager();
		if (fm.findFragmentById(android.R.id.content) == null) {
			FavorFragment frag = new FavorFragment();
			fm.beginTransaction().add(android.R.id.content, frag).commit();
		}
	}

	public class FavorFragment extends Fragment implements OnItemClickListener,OnItemLongClickListener{
		private GridView grid;
		private ArrayList<AppEntry> objs;
		private final static int REQUEST_APP_ENTRY = 1;
		private boolean isDelete;
		private FavoriteAppAdapter adapter;
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			getActivity().setTitle(R.string.favor_app);
			
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.layout_favourite_app, container, false);
			grid = (GridView)view.findViewById(R.id.gridview);
			grid.setOnItemClickListener(this);
			grid.setOnItemLongClickListener(this);
			init();
			return view;
		}
		
		private void init(){
			objs = App.adapterDB.getAllAppEntries();
			objs.add(new AppEntry());
			adapter = new FavoriteAppAdapter(getActivity(), objs,true);
			grid.setAdapter(adapter);
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
			int endPos = adapter.getCount() - 1 ;
			if(isDelete){
				if(endPos == position){
					isDelete = false;
					adapter.setModeRemove(false);
				}else{
					App.adapterDB.deleteAppEntry(objs.get(position).getId());
					adapter.removeItem(objs.get(position));
				}
				
				return;
			}else{
				
				if(adapter.isModeRemove()){
					isDelete = true;
					return;
				}
			}
				
			if(endPos == position){
				startActivityForResult(new Intent(getActivity(), AppListActivity.class),REQUEST_APP_ENTRY);
			}else{
				Intent LaunchIntent = getActivity().getPackageManager().getLaunchIntentForPackage(objs.get(position).getPackageName());
				LaunchIntent.setComponent(new ComponentName(objs.get(position).getPackageName(),  objs.get(position).getPackageActivity()));
				startActivity(LaunchIntent);		
			}
		}
		
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View view, int position,
				long id) {
			if(position == arg0.getCount() -1){
				return false;
			}
			isDelete = false;
			adapter.setModeRemove(true);
			return false;
		}
		
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if(requestCode == REQUEST_APP_ENTRY && resultCode == Activity.RESULT_OK){
				init();
			}
		}
	}

}
