package com.efse.toucher.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.efse.toucher.R;
import com.efse.toucher.adapter.GridAdapter;
import com.efse.toucher.utils.AppEntry;

public class GridFragment extends Fragment {

	private GridView mGridView;
	private GridAdapter mGridAdapter;
	ArrayList<AppEntry> gridItems;
	private Activity activity;
	private ArrayList<AppEntry> selected_items;

	public GridFragment(ArrayList<AppEntry> gridItems,ArrayList<AppEntry> selected_items, Activity activity) {
		this.gridItems = gridItems;
		this.activity = activity;
		this.selected_items = selected_items;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view;
		view = inflater.inflate(R.layout.layout_grid, container, false);
		mGridView = (GridView) view.findViewById(R.id.gridView);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (activity != null) {

			mGridAdapter = new GridAdapter(activity, gridItems);
			if (mGridView != null) {
				mGridView.setAdapter(mGridAdapter);
				mGridAdapter.setSelectedItem(selected_items);
			}
		}
	}

}