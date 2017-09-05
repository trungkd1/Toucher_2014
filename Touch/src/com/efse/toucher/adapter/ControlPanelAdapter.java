package com.efse.toucher.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.efse.toucher.App;
import com.efse.toucher.R;
import com.efse.toucher.utils.MenuItem;

public class ControlPanelAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<MenuItem> objects;
	
	
	public ControlPanelAdapter(Context context,ArrayList<MenuItem> objects){
		this.context = context;
		this.objects = objects;
	}
	
	public void setMenu(int pos,int area,MenuItem item){
		
		objects.get(pos).setType(item.getType());
		objects.get(pos).setTitle(item.getTitle());
		objects.get(pos).setIconRes(item.getIconRes());
		int result = App.adapterDB.updateMenu(objects.get(pos));
		if(result > 0)
			notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return objects.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(context);
	            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(8, 8, 8, 8);
	        } else {
	            imageView = (ImageView) convertView;
	        }

	        imageView.setImageResource(R.drawable.ic_favor);
	        
	        return imageView;
	}

	class ViewHolder{
		ImageView image;
		TextView title;
	}
}
