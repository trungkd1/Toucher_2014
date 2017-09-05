package com.efse.toucher.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.efse.toucher.R;
import com.efse.toucher.utils.PointItem;

public class PointAdapter extends BaseAdapter{

	private ArrayList<PointItem> objects;
	private boolean isRemove;
	private LayoutInflater mInflater;
	
	public PointAdapter(Context context ,ArrayList<PointItem> objects){
		this.objects  = objects;
		isRemove = false;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return objects.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return objects.get(position).getId();
	}
	
	public void setModeRemove(boolean isRemove){
		this.isRemove = isRemove;
		notifyDataSetChanged();
	}
	
	public boolean isRemove(){
		return this.isRemove;
	}
	
	public void removeItem(PointItem item){
		objects.remove(item);
		notifyDataSetChanged();
	}

	public class ViewHolder {
		public ImageView imageView;
		public View view;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder viewHolder;

		if (view == null) {
			view = mInflater.inflate(R.layout.item_point, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
			viewHolder.view = (View) view.findViewById(R.id.view);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
	        if(objects.get(position).getIconRes() != 0){
	        	viewHolder.imageView.setImageResource(objects.get(position).getIconRes());
	        }else{
	        	viewHolder.imageView.setImageBitmap(objects.get(position).getIconBitmap());
	        }
	       
	        if(isRemove){
	        	if(objects.get(position).isPrivateIcon()){
	        		viewHolder.view.setBackgroundResource(R.drawable.frame_delete);
	 	        }else{
	 	        	viewHolder.view.setBackgroundResource(0);
	 	        }
	        	objects.get(getCount() -1).setIconRes(R.drawable.ic_done); 
	        	 
	        }else{
	        	 viewHolder.view.setBackgroundResource(0);
	        	 objects.get(getCount() -1 ).setIconRes(R.drawable.button_add); 
	        }
	        
	       return view;
	}

}
