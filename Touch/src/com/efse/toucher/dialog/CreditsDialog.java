package com.efse.toucher.dialog;


import com.efse.toucher.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreditsDialog extends Dialog{

	Context context;
	public CreditsDialog(Context context) {
		super(context);
		setTitle(R.string.credits);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView text = new TextView(context);
		text.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		text.setText(R.string.credits_content);
		LinearLayout linear = new LinearLayout(context);
		linear.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		linear.setOrientation(LinearLayout.VERTICAL);
		linear.setGravity(Gravity.CENTER);
		linear.addView(text);
		setContentView(linear);
	}

}
