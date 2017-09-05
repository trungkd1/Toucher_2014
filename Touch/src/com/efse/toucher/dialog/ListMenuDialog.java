package com.efse.toucher.dialog;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.efse.toucher.adapter.ListMenuAdapter;
import com.efse.toucher.utils.Config;
import com.efse.toucher.utils.ListenerDialog;
import com.efse.toucher.utils.MenuItem;
import com.efse.toucher.R;

public class ListMenuDialog extends Dialog implements OnItemClickListener{

	private String[] menuTitle;
	private ListenerDialog listen;
	private ArrayList<MenuItem> objects;
	
	public ListMenuDialog(Context context,ListenerDialog listen) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		menuTitle = getContext().getResources().getStringArray(R.array.pannel);
		this.listen = listen;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_choice_item);
		ListView list = (ListView)findViewById(R.id.menu_listview);
		objects = new ArrayList<MenuItem>();
		for(int i = 0 ;i < Config.MenuIcons.length ;i++){
			objects.add(new MenuItem(menuTitle[i],i,Config.MenuIcons[i]));
		}
		ListMenuAdapter adapter = new ListMenuAdapter(getContext(), objects);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
	}

	
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		this.listen.onListener(objects.get(position).getIconRes(),objects.get(position).getTitle());
		dismiss();
	}

	
}
