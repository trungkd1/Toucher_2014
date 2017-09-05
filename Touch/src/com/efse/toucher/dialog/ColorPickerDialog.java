package com.efse.toucher.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.efse.toucher.R;
import com.efse.toucher.utils.SaveData;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.ColorPicker.OnColorChangedListener;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;

public class ColorPickerDialog extends Dialog implements OnColorChangedListener,android.view.View.OnClickListener{

	public ColorPickerDialog(Context context, OnColorChange listener) {
		super(context);
		this.listener = listener;
	}

	OnColorChange listener;
	
	public interface OnColorChange{
		public void setOnColorChange(int color);
	}
	SVBar svBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.layout_picker_color_dialog);
		ColorPicker picker = (ColorPicker) findViewById(R.id.picker);
		svBar = (SVBar) findViewById(R.id.svbar);
		OpacityBar opacityBar = (OpacityBar) findViewById(R.id.opacitybar);
		findViewById(R.id.btn_change_color).setOnClickListener(this);
		opacityBar.setMaxValue(280);
		
		picker.addSVBar(svBar);
		picker.addOpacityBar(opacityBar);
		picker.setOnColorChangedListener(this);
		picker.setColor(SaveData.getInstance(getContext()).getColorPannel());
	}

	int color;
	@Override
	public void onColorChanged(int color) {
//		svBar.setColor(color);
		this.color = color;
	}


	@Override
	public void onClick(View v) {
		SaveData.getInstance(getContext()).setColorPannel(color);
		listener.setOnColorChange(color);
		dismiss();
	}

}
