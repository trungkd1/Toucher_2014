package com.efse.toucher;

import android.app.Activity;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.efse.toucher.ViewPannel.ObjectView;
import com.efse.toucher.utils.AnimationUtils;
import com.efse.toucher.utils.Funtion;
import com.efse.toucher.utils.Log;
import com.efse.toucher.utils.MenuItem;
import com.efse.toucher.utils.Mode;
import com.efse.toucher.utils.OnViewPannelListener;
import com.efse.toucher.utils.SaveData;
import com.efse.toucher.utils.Utils;
import com.efse.toucher.utils.Funtion.Func;

public class Pannel extends LinearLayout implements OnViewPannelListener{

	public ViewPannel pannel;
	private Funtion func;
	private Activity context;
	private ImageView view;
	private MenuItem item;

	public Pannel(Activity context) {
		super(context);
		this.context = context;
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		setGravity(Gravity.CENTER);
		pannel = new ViewPannel(context);
		pannel.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		pannel.setType(ViewPannel.TYPE_FLAT);
		pannel.setPannel(false);
		pannel.setOnListenerViewItem(this);
		addView(pannel);
		func = new Funtion(context);
		int mode = SaveData.getInstance(context).getMODE();
		transaction(mode);
	}
	
	
	public void listenerAnimation(AnimationListener listener){
		this.listener = listener;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
	    int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
	    this.setMeasuredDimension(parentWidth, parentHeight);
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	android.view.animation.Animation.AnimationListener listener = new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationEnd(Animation animation) {
			func.process(view, item,context);
			context.finish();
		}
	};
	
	private void transaction(int mode) {
		int alpha = (int) (SaveData.getInstance(this.context).getAlphaPannel());
		int color = SaveData.getInstance(this.context).getColorPannel();
		Utils.setAlpha(pannel, Utils.getAlpha(alpha));
		switch (mode) {
		case Mode.CONNER:
			onConner(color);
			break;
		case Mode.ROUND:
			onRound(color);
			break;
		case Mode.SQUARE:
			onSquare(color);
			break;
		case Mode.HOME:
			onHome(color);
			break;
		}
	}

	
	private void onConner(int color) {
		int conner = SaveData.getInstance(this.context).getConnerPannel();
		Utils.setPannelBackGround(pannel, color, conner);
	}

	private void onRound(int color) {
		int round = SaveData.getInstance(this.context).getRoundPannel();
		for (int i = 0; i < pannel.objectsView.size(); i++) {
			ObjectView obj = pannel.objectsView.get(i);
			Utils.setPaddingView(obj.getLayout(),
					Utils.getRoundStandard(this.context, round));
			Utils.setPannelBackGround(obj.getLayout(), color, 150);
		}
	}

	private void onHome(int color) {

	}

	private void onSquare(int color) {
		int square = SaveData.getInstance(this.context).getSquarePannel();
		for (int i = 0; i < pannel.objectsView.size(); i++) {
			ObjectView obj = pannel.objectsView.get(i);
			Utils.setPaddingView(obj.getLayout(),
					Utils.getSquareStandard(this.context, square));
			obj.getLayout().setBackgroundColor(color);
		}
	}

	@Override
	public void setOnViewPannelListener(ImageView view, MenuItem item) {
		Log.e("setOnViewPannelListener");
		switch (Func.valueOf(item.getTitle())) {
		case Back:
		case SnapShot:
		case Volume_Up:
		case Volume_Down:
		case Auto_Orientation:
		case Light:
		case Ringer:
			func.process(view, item,context);
			break;
			
		case Home:
		case Favor:
		case Setting:
		case Lock_Screen:
		case Apps:
		case Clean_Memory:
		case Menu:
		case Clean_Cache:
		case Time:
		case Battery_Display:
			AnimationUtils.onZoomAnimation(this, false,listener);
			this.view = view;
			this.item = item;
			break;
		default:
			break;
		}
	}
	
}
