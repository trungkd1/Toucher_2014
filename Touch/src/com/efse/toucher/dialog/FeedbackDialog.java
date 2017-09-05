package com.efse.toucher.dialog;

import com.efse.toucher.R;
import com.efse.toucher.adapter.FeedbackApdapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

public class FeedbackDialog extends Dialog {

	Context context;
	public FeedbackDialog(Context context) {
		super(context);
		this.context = context;
		setTitle("Feel about Quick Touch ?");
	}

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_feedback);
		String[] values = context.getResources().getStringArray(R.array.feedback);

		listView = (ListView) findViewById(R.id.list);
		FeedbackApdapter adapter = new FeedbackApdapter(context, values);
        listView.setAdapter(adapter); 
	}
	
	
	

}
