package com.efse.toucher.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.efse.toucher.R;
import com.efse.toucher.utils.AppEntry;

public class FavoriteAppAdapter extends BaseAdapter {

	public class ViewHolder {
		public ImageView imageView;
		public TextView textTitle;
		public View view;
//		public LinearLayout linearLayout;
	}

	private ArrayList<AppEntry> items;
	private LayoutInflater mInflater;
	private boolean isRemove;
	private boolean isFavouriteList;
	private Context context;

	public FavoriteAppAdapter(Context context, ArrayList<AppEntry> locations,boolean isFavouriteList) {
		this.context = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		items = locations;
		this.isFavouriteList = isFavouriteList;
	}

	public void removeItem(AppEntry item){
		items.remove(item);
		notifyDataSetChanged();
	}
	
	public void setModeRemove(boolean isRemove){
		this.isRemove = isRemove;
		notifyDataSetChanged();
	}
	
	public boolean isModeRemove(){
		return this.isRemove ;
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


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View view = convertView;
		final ViewHolder viewHolder;

		if (view == null) {
			view = mInflater.inflate(R.layout.item_menu2, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
			viewHolder.view = (View) view.findViewById(R.id.view);
//			viewHolder.linearLayout = (LinearLayout) view.findViewById(R.id.row_layout);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		final AppEntry gridItem = items.get(position);
		
		if(isFavouriteList){
			if(position == getCount() -1){
				viewHolder.imageView.setImageResource(R.drawable.button_add);;
			}else{
				viewHolder.imageView.setImageDrawable(gridItem.getIcon(context.getPackageManager()));
			}
			
			if(isRemove){
				if(position == getCount() - 1){
					viewHolder.imageView.setImageResource(R.drawable.ic_done);
					viewHolder.view.setVisibility(View.GONE);
				}else{
					viewHolder.view.setVisibility(View.VISIBLE);	
				}
				
			}else{
				if(position == getCount() - 1){
					viewHolder.imageView.setImageResource(R.drawable.button_add);
				}else{
					viewHolder.view.setVisibility(View.GONE);
				}
				
			}
		}else{
			viewHolder.imageView.setImageDrawable(gridItem.getIcon(context.getPackageManager()));
		}
		
		
		return view;
	}

}
