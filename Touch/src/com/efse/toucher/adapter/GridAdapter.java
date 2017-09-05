package com.efse.toucher.adapter;

import java.util.ArrayList;
import java.util.Hashtable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efse.toucher.R;
import com.efse.toucher.utils.AppEntry;

public class GridAdapter extends BaseAdapter {

	public class ViewHolder {
		public ImageView imageView;
		public TextView textTitle;
		public LinearLayout layoutView;
	}

	private ArrayList<AppEntry> selected_items;
	private ArrayList<AppEntry> items;
	private LayoutInflater mInflater;
	private Context context;

	public GridAdapter(Context context, ArrayList<AppEntry> locations) {
		this.context = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		items = locations;

	}

	public void setSelectedItem(ArrayList<AppEntry> selected_items) {

		this.selected_items = selected_items;
	}

	public ArrayList<AppEntry> getItems() {
		return items;
	}

	public void setItems(ArrayList<AppEntry> items) {
		this.items = items;
	}

	@Override
	public int getCount() {
		if (items != null) {
			return items.size();
		}
		return 0;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		if (items != null && position >= 0 && position < getCount()) {
			return items.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (items != null && position >= 0 && position < getCount()) {
			return items.get(position).getId();
		}
		return 0;
	}

	public void setItemsList(ArrayList<AppEntry> locations) {
		this.items = locations;
	}

	Hashtable<String, String> hash = new Hashtable<String, String>();

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View view = convertView;
		final ViewHolder viewHolder;

		if (view == null) {
			view = mInflater.inflate(R.layout.item_menu2, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
			viewHolder.layoutView = (LinearLayout) view
					.findViewById(R.id.row_layout);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		final AppEntry gridItem = items.get(position);

		viewHolder.imageView.setImageDrawable(gridItem.getIcon());
		viewHolder.imageView.setAlpha(125);
		viewHolder.imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
				if (!gridItem.isSelected) {
					gridItem.isSelected = true;
					viewHolder.imageView.setAlpha(255);
					viewHolder.layoutView.setBackgroundColor(context
							.getResources().getColor(R.color.aqua_lighter));
					viewHolder.layoutView.getBackground().setAlpha(125);
				} else {
					gridItem.isSelected = false;
					viewHolder.imageView.setAlpha(125);
					viewHolder.layoutView.setBackgroundColor(context
							.getResources().getColor(R.color.white));
				}

				if (selected_items.contains(gridItem)) {
					selected_items.remove(gridItem);
				} else {
					selected_items.add(gridItem);
				}
			}
		});

		if (gridItem.isSelected) {
			viewHolder.imageView.setAlpha(255);
			viewHolder.layoutView.setBackgroundColor(context.getResources()
					.getColor(R.color.aqua_lighter));
			viewHolder.layoutView.getBackground().setAlpha(125);
		} else {
			viewHolder.imageView.setAlpha(125);
			viewHolder.layoutView.setBackgroundColor(context.getResources()
					.getColor(R.color.white));
		}
		return view;
	}

}
