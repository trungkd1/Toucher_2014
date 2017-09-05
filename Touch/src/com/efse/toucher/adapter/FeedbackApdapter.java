package com.efse.toucher.adapter;

import com.efse.toucher.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedbackApdapter extends BaseAdapter {

	private String[] resources;
	private Context context;

	public FeedbackApdapter(Context context, String[] resources) {
		this.context = context;
		this.resources = resources;
	}

	@Override
	public int getCount() {
		return resources.length;
	}

	@Override
	public Object getItem(int position) {
		return resources[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder;
		if (convertView == null) {
			convertView = LayoutInflater.from(this.context).inflate(
					R.layout.item_menu, parent, false);
			viewholder = new ViewHolder();
			viewholder.image = (ImageView) convertView
					.findViewById(R.id.row_icon);
			viewholder.image.setBackgroundResource(0);
			viewholder.title = (TextView) convertView
					.findViewById(R.id.row_title1);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		
		viewholder.title.setVisibility(View.VISIBLE);
		viewholder.image.setImageResource(imageRes[position]);
		viewholder.title.setText(resources[position]);
		return convertView;
	}

	class ViewHolder {
		ImageView image;
		TextView title;
	}

	int[] imageRes = new int[] { R.drawable.emotion_happy,
			R.drawable.emotion_confuse, R.drawable.emotion_sad,
			R.drawable.emotion_idea };
}
