package com.efse.toucher;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.efse.toucher.App;
import com.efse.toucher.AppLoader;
import com.efse.toucher.R;
import com.efse.toucher.fragment.GridFragment;
import com.efse.toucher.utils.AppEntry;
import com.efse.viewpagerindicator.PageIndicator;

public class AppListActivity extends FragmentActivity{
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentManager fm = getSupportFragmentManager();
		if (fm.findFragmentById(android.R.id.content) == null) {
			AppListFragment list = new AppListFragment();
			fm.beginTransaction().add(android.R.id.content, list).commit();
		}
	}
	
	class AppListFragment  extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<AppEntry>>, OnClickListener{
		public PageIndicator mIndicator;
		private ViewPager awesomePager;
		private PagerAdapter pm;
		private ProgressBar progressBar;
		private List<AppEntry> objs;
		private ArrayList<AppEntry> selected_items;
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			getActivity().setTitle(R.string.choose_app);
			View title = getActivity().getWindow().findViewById(android.R.id.title);
			View titleBar = (View) title.getParent();
			titleBar.setBackgroundColor(getResources().getColor(R.color.aqua_deep));
			selected_items = new ArrayList<AppEntry>();
			
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.layout_choose_app, container, false);
			awesomePager = (ViewPager)view.findViewById(R.id.pager);
			mIndicator = (PageIndicator)view.findViewById(R.id.pagerIndicator);
			progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
			view.findViewById(R.id.btn_accept).setOnClickListener(this);
			view.findViewById(R.id.btn_cancel).setOnClickListener(this);
			getLoaderManager().initLoader(0, savedInstanceState, this);
			return view;
		}
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_accept:
				saveAppEntry();
				setResult(RESULT_OK);
				break;
				
			case R.id.btn_cancel:
				setResult(RESULT_CANCELED);
				break;
			}
			
			finish();
		}

		@Override
		public Loader<ArrayList<AppEntry>> onCreateLoader(int arg0, Bundle arg1) {
			progressBar.setVisibility(View.VISIBLE);
			return new AppLoader(getActivity());
		}

		@Override
		public void onLoadFinished(Loader<ArrayList<AppEntry>> arg0,
				ArrayList<AppEntry> arg1) {
			progressBar.setVisibility(View.GONE);
			objs = arg1;
			checkselection(objs);
			getPackageApplication(objs);
		}

		@Override
		public void onLoaderReset(Loader<ArrayList<AppEntry>> arg0) {

		}
		
		private void getPackageApplication(List<AppEntry> packageApplication) {
			
			Iterator<AppEntry> it = packageApplication.iterator();
			List<GridFragment> gridFragments = new ArrayList<GridFragment>();
			while (it.hasNext()) {
				ArrayList<AppEntry> itmLst = new ArrayList<AppEntry>();
				for ( int i = 0;i < 24 ; i++ ){
					setGridPage(itmLst, it);
				}
				
				gridFragments.add(new GridFragment(itmLst,selected_items,getActivity()));
			}
			
			pm = new PagerAdapter(getActivity().getSupportFragmentManager(), gridFragments);
			awesomePager.setAdapter(pm);
			mIndicator.setViewPager(awesomePager);
		}
		
		private void checkselection(List<AppEntry> packageApplication){
			Hashtable<String, String> list = App.adapterDB.getPackageNames();
			for (int i = 0 ; i < packageApplication.size() ; i ++) {
				if(list.containsKey(packageApplication.get(i).getPackageActivity())  ){
					packageApplication.get(i).isSelected = true;
					selected_items.add(packageApplication.get(i));
				}
				
			}
		}
		
		private void saveAppEntry(){
			App.adapterDB.clearEntryApps();
			for(AppEntry obj : selected_items){
				if (obj.isSelected) {
					App.adapterDB.addEntryApp(obj);
				}
			}
		}
		
		private void setGridPage(ArrayList<AppEntry> itmLst, Iterator<AppEntry> it) {
			if (it.hasNext()) {
					itmLst.add(it.next());
			}
		}

		private class PagerAdapter extends FragmentStatePagerAdapter {
			private List<GridFragment> fragments;

			public PagerAdapter(FragmentManager fm, List<GridFragment> fragments) {
				super(fm);
				this.fragments = fragments;
			}

			@Override
			public Fragment getItem(int position) {
				return this.fragments.get(position);
			}

			@Override
			public int getCount() {
				return this.fragments.size();
			}
		}

	}
}
 
