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
import com.efse.toucher.utils.MenuItem;

public class ListMenuAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<MenuItem> objects;
	
	
	public ListMenuAdapter(Context context,ArrayList<MenuItem> objects){
		this.context = context;
		this.objects = objects;
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
		ViewHolder viewholder;
		if(convertView == null){
			convertView = LayoutInflater.from(this.context).inflate(R.layout.item_menu, parent,false);
			viewholder = new ViewHolder();
			viewholder.image = (ImageView)convertView.findViewById(R.id.row_icon);
			viewholder.title = (TextView)convertView.findViewById(R.id.row_title1);
			convertView.setTag(viewholder);
		}else{
			viewholder = (ViewHolder)convertView.getTag();
		}
		viewholder.title.setVisibility(View.VISIBLE);
		viewholder.image.setImageResource(objects.get(position).getIconRes());
		if(objects.get(position).getTitle() == null){
			viewholder.title.setText("none");
		}else{
			String title = objects.get(position).getTitle();
			if(objects.get(position).getIconRes() == R.drawable.ic_back_key || objects.get(position).getIconRes() == R.drawable.ic_screenshot || objects.get(position).getIconRes() == R.drawable.ic_menu){
				title = title+" **Need Root**";
			}
			viewholder.title.setText(title);
		}
		
		
		return convertView;
	}

	class ViewHolder{
		ImageView image;
		TextView title;
	}
}
