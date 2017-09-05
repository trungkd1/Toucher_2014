package com.efse.toucher;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;

import com.efse.toucher.utils.AnimationUtils;
import com.efse.toucher.utils.Log;
import com.efse.toucher.utils.SaveData;

public class ShowPannelActivity extends Activity {

	private Pannel pannel;
	boolean isTouching = false;
	private int animationType;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		animationType = SaveData.getInstance(getBaseContext())
				.getAnimationType();
		pannel = new Pannel(this);
		pannel.setVisibility(View.INVISIBLE);
		SaveData.isStarted = true;
		setContentView(pannel);
	}

	@Override
	public void onResume() {
		super.onResume();
		onStartAnimation();
	}
	
	@Override
	protected void onStop() {
		SaveData.isStarted = false;
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		SaveData.isStarted = false;
		super.onDestroy();
		Log.e("onDestroy 1123232");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (isTouching)
				return true;
			switch (SaveData.getInstance(getBaseContext()).getAnimationType()) {
			case 0:
				AnimationUtils.onZoomAnimation(pannel, false,
						listenerAnimationEnd);
				break;

			case 1:
				AnimationUtils.expandOrCollapse(pannel, false,
						listenerAnimationEnd);

				break;

			case 2:
				AnimationUtils.onAnimationRotate(ShowPannelActivity.this,
						pannel ,false,1, listenerAnimationEnd);
				break;

			case 3:
				AnimationUtils.onAnimationRotate(ShowPannelActivity.this,
						pannel, false,2 ,listenerAnimationEnd);
				break;
			case 4:
				AnimationUtils.onBounce(
						pannel, false,listenerAnimationEnd);
				break;
			case 5:
				AnimationUtils.moveViewToScreenCenter(ShowPannelActivity.this,
						pannel, false, listenerAnimationEnd);
			default:
				break;
			}
		}
		return false;
	}

	private AnimationListener listenerAnimationEnd = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
			isTouching = true;
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			isTouching = false;
			finish();
			overridePendingTransition(0, 0);
		}
	};
	
	
	private void onStartAnimation() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				switch (animationType) {
				case 0:
					AnimationUtils.onZoomAnimation(pannel, true, null);
					break;

				case 1:
					AnimationUtils.expandOrCollapse(pannel, true, null);
					break;

				case 2:
					AnimationUtils.onAnimationRotate(ShowPannelActivity.this,
							pannel, true,1, null);
					break;
					
				case 3:
					AnimationUtils.onAnimationRotate(ShowPannelActivity.this,
							pannel, true,2, null);
					break;
					
				case 4:
					AnimationUtils.onBounce(
							pannel, true, null);
					break;

				case 5:
					AnimationUtils.moveViewToScreenCenter(
							ShowPannelActivity.this, pannel, true,
							new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {
  									pannel.setVisibility(View.VISIBLE);
								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationEnd(Animation animation) {
									// TODO Auto-generated method stub

								}
							});
					pannel.setVisibility(View.VISIBLE);
					break;
				case 6:
					AnimationUtils.onSlide(
							ShowPannelActivity.this, pannel.pannel, true,
							new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {
  									pannel.setVisibility(View.VISIBLE);
								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationEnd(Animation animation) {
									// TODO Auto-generated method stub

								}
							});
					pannel.setVisibility(View.VISIBLE);
					break;
				default:
					break;
				}

			}
		}, 200);

	}
	
}
