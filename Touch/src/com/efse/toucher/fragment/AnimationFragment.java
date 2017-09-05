package com.efse.toucher.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.efse.toucher.R;
import com.efse.toucher.ShowPannelActivity;
import com.efse.toucher.utils.Config;
import com.efse.toucher.utils.SaveData;

public class AnimationFragment extends Fragment implements OnClickListener,
		OnItemClickListener {

	private static AnimationFragment f;

	public static AnimationFragment newInstance(String text) {
		if (f == null) {
			f = new AnimationFragment();
			f.setTargetFragment(f, Config.FRAGMENT_THIRD);
			Bundle b = new Bundle();
			b.putString("", text);
			f.setArguments(b);
		}
		return f;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	private Adapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_animation, null);
		ListView list = (ListView) view.findViewById(R.id.list);
		String[] values = getResources().getStringArray(R.array.array_animation);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		adapter = new Adapter(getActivity(), values);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		adapter.selection(SaveData.getInstance(getActivity()).getAnimationType());
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case 0:
			// mainActivity.animation(R.id.btn_test);
			
			break;

		default:
			break;
		}
	}

	class Adapter extends BaseAdapter {

		private String[] values;
		private Context context;

		public Adapter(Context context, String[] values) {
			this.values = values;
			this.context = context;
		}

		@Override
		public int getCount() {
			return this.values.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView;
			if (convertView == null) {
				textView = new TextView(getActivity());
				textView.setLayoutParams(new ListView.LayoutParams(
						ListView.LayoutParams.MATCH_PARENT, SaveData.getInstance(getActivity()).getWidthPannel()/4));
				textView.setGravity(Gravity.CENTER_VERTICAL);
				textView.setTextAppearance(context,
						android.R.style.TextAppearance_DeviceDefault_Medium);
				textView.setTextColor(R.color.gray);

			} else {
				textView = (TextView) convertView;
			}
			if (position_select == position) {
				textView.setBackgroundColor(getResources().getColor(R.color.aqua_lighter));
			}else{
				textView.setBackgroundColor(0);
			}
			textView.setText(values[position]);
			return textView;
		}

		int position_select;

		public void selection(int position) {
			position_select = position;
			notifyDataSetChanged();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapters, View view, int postion,
			long id) {
		adapter.selection(postion);
		SaveData.getInstance(getActivity()).setAnimationType(postion);
		Intent intent = new Intent(getActivity(), ShowPannelActivity.class);
		 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
