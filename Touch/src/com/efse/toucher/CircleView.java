package com.efse.toucher;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.efse.toucher.utils.OnViewPannelListener;
import com.efse.toucher.utils.SaveData;

public class CircleView extends LinearLayout{

	private Context context;
	public CircleView(Context context) {
		super(context);
		this.context = context;
		int padding = (int) getResources().getDimension(R.dimen.padding);
		int width = SaveData.getInstance(context).getWidthPannel()/2;
//		setLayoutParams(new LayoutParams(200, 200));
//		setPadding(padding, padding, padding , padding);
//		day phai khong Ngoai
		
	}

	
	public CircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		int padding = (int) getResources().getDimension(R.dimen.padding);
//		setPadding(padding, padding, padding , padding);
		createView();
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	}
	
	private void createView(){
		int width = SaveData.getInstance(context).getWidthPannel()/2;
		LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(new LayoutParams(width, width));
		layout.setBackgroundResource(R.drawable.border_circle);
		layout.setGravity(Gravity.CENTER);
		final ImageView image = new ImageView(context);
		image.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		image.setImageResource(R.drawable.ic_favor);
		layout.addView(image);
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				listener.setOnViewPannelListener(-1,image,null,0);
			}
		});
		addView(layout);
	}
	
	private OnViewPannelListener listener;
	
	
	public void setOnListenerViewItem(OnViewPannelListener listener){
			this.listener = listener ;
	}
}
