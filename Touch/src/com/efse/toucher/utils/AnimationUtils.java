package com.efse.toucher.utils;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

public class AnimationUtils {

	static int duration = 1000;

	public static final int AnimationRotate1 = 1;
	public static final int AnimationRotate2 = 2;
	public static final int AnimationRotate3 = 3;
	
	public static void onAnimationRotate(Activity act, View view,boolean isAppear,int type,AnimationListener listener) {
		RotateAnimation rotate;
		
		if(AnimationRotate1 == type ){
			rotate = new RotateAnimation(0f, 720f,
					view.getWidth() / 200, view.getHeight() / 2);
		} else if (AnimationRotate2 == type) {
			rotate = new RotateAnimation(0f, 720f,
					view.getWidth() / 2, view.getHeight() / 2);
		} else {
			rotate = new RotateAnimation(0f, 45f,
					view.getWidth() / 2, view.getHeight() / 2);
		}

		rotate.setInterpolator(new LinearInterpolator());
		
		Display mdisp = act.getWindowManager().getDefaultDisplay();
		Point mdispSize = new Point();
		mdisp.getSize(mdispSize);
		int maxX = mdispSize.x;
		int maxY = mdispSize.y;
		float to_axis_x = maxX/2
				- (view.getMeasuredWidth() / 2);
		float to_axis_y = maxY/2
				- (view.getMeasuredHeight() / 2);
		int[] i = SaveData.getInstance(act).getAxis(view.getMeasuredWidth(),view.getMeasuredHeight());
		float from_axis_x = i[0];
		float from_axis_y = i[1];
		AnimationSet animationSet = new AnimationSet(false);
		AlphaAnimation alpha = new AlphaAnimation(isAppear ? 0.0f : 1.0f,
				isAppear ? 1.0f : 0.0f);
		ScaleAnimation scale = new ScaleAnimation(
				isAppear ? 0.0f : 1.0f, isAppear ? 1.0f : 0.0f, // Start and end values for the X axis scaling
				isAppear ? 0.0f : 1.0f, isAppear ? 1.0f : 0.0f, // Start and end values for the Y axis scaling
	            Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
	            Animation.RELATIVE_TO_SELF, 0.5f);
		scale.initialize(view.getMeasuredWidth(), view.getMeasuredHeight() / 2, view.getMeasuredHeight(), view.getMeasuredHeight());
		TranslateAnimation translate;
		if(isAppear){
			translate = new TranslateAnimation(from_axis_x, to_axis_x, from_axis_y, to_axis_y);
			
		}else {
			translate = new TranslateAnimation(to_axis_x,from_axis_x ,to_axis_y, from_axis_y );

		}
		
		animationSet.setFillAfter(true);
		animationSet.addAnimation(rotate);
		animationSet.addAnimation(alpha);
		animationSet.addAnimation(scale);
		animationSet.addAnimation(translate);
		animationSet.setDuration(duration);
		animationSet.setAnimationListener(listener);
		view.startAnimation(animationSet);
		
	}

	public static void onDisapperAnimation(View view) {
		Transformation t = new Transformation();
		AnimationSet animationSet = new AnimationSet(true);
		Matrix mReverse = new Matrix();
		mReverse.setScale(0, 0);
		Matrix matrix = new Matrix();
		int cx = view.getWidth() / 2;
		int cy = view.getHeight() / 2;
		matrix.preTranslate(-cx, -cy);
		matrix.postRotate(90);
		matrix.postTranslate(view.getWidth() / 2, view.getHeight() / 2);
	}

	public static void onZoomAnimation(View view, boolean isAppear,
			AnimationListener listener) {
		AnimationSet animationSet = new AnimationSet(true);
		animationSet.addAnimation(new ScaleAnimation(isAppear ? 0.0f : 1.0f,
				isAppear ? 1.0f : 0.0f, isAppear ? 0.0f : 1.0f, isAppear ? 1.0f
						: 0.0f, Animation.RELATIVE_TO_PARENT, 0.5f,
				Animation.RELATIVE_TO_PARENT, 0.5f));
		animationSet.addAnimation(new AlphaAnimation(isAppear ? 0.0f : 1.0f,
				isAppear ? 1.0f : 0.0f));
		animationSet.setDuration(duration);
		animationSet.setInterpolator(new DecelerateInterpolator());
		animationSet.setFillAfter(true);
		animationSet.setAnimationListener(listener);
		view.startAnimation(animationSet);
	}
	
	public static void moveViewToScreenCenter(Activity act, View view,boolean isAppear,AnimationListener listener) {
		float[] _to =toAxis(act, view);
		int[] _from = SaveData.getInstance(act).getAxis(view.getMeasuredWidth(),view.getMeasuredHeight());
		
		AnimationSet animationSet = new AnimationSet(false);
		AlphaAnimation alpha = new AlphaAnimation(isAppear ? 0.0f : 1.0f,
				isAppear ? 1.0f : 0.0f);
		ScaleAnimation scale = new ScaleAnimation(
				isAppear ? 0.0f : 1.0f, isAppear ? 1.0f : 0.0f, // Start and end values for the X axis scaling
				isAppear ? 0.0f : 1.0f, isAppear ? 1.0f : 0.0f, // Start and end values for the Y axis scaling
	            Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
	            Animation.RELATIVE_TO_SELF, 0.5f);
		scale.initialize(view.getMeasuredWidth(), view.getMeasuredHeight() / 2, view.getMeasuredHeight(), view.getMeasuredHeight());
		TranslateAnimation translate;
		if(isAppear){
			translate = new TranslateAnimation(_from[0], _to[0], _from[1], _to[1]);
		}else {
			translate = new TranslateAnimation(_to[0],_from[0] ,_to[1], _from[1]);
		}
		
		animationSet.setFillAfter(true);
		animationSet.addAnimation(alpha);
		animationSet.addAnimation(scale);
		animationSet.addAnimation(translate);
		animationSet.setDuration(duration);
		animationSet.setAnimationListener(listener);
		view.startAnimation(animationSet);
	}

	
	 public static void expandOrCollapse(final View v,boolean isAppear,AnimationListener listener) {
		    TranslateAnimation anim = null;
		    if(isAppear)
		    {
		        anim = new TranslateAnimation(0.0f, 0.0f, -v.getHeight(), 0.0f);
		        v.setVisibility(View.VISIBLE);  
		    }
		    else{
		        anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, -v.getHeight());
		        AnimationListener collapselistener= new AnimationListener() {
		            @Override
		            public void onAnimationStart(Animation animation) {
		            }

		            @Override
		            public void onAnimationRepeat(Animation animation) {
		            }

		            @Override
		            public void onAnimationEnd(Animation animation) {
		            v.setVisibility(View.GONE);
		            }
		        };

		        anim.setAnimationListener(collapselistener);
		    }
		    anim.setDuration(300);
		    anim.setInterpolator(new AccelerateInterpolator(0.5f));
		    anim.setAnimationListener(listener);
		    v.startAnimation(anim);
		}

	 
	 public static void onBounce(View view, boolean isAppear , AnimationListener listener){
		 ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1, isAppear ? 0.0f : 1.0f, isAppear ? 1.0f : 0.0f);
		 scaleAnimation.setDuration(500);
		 scaleAnimation.setAnimationListener(listener);
		 scaleAnimation.setFillAfter(true);
		 if(isAppear){
			 scaleAnimation.setInterpolator(new BounceInterpolator());
		 }
		 view.startAnimation(scaleAnimation);
	 }
	 
	 
	 public static void onSlide(Activity act,View view, boolean isAppear , AnimationListener listener) {
		 int[] i = SaveData.getInstance(act).getAxis(view.getMeasuredWidth(),view.getMeasuredHeight());
		 float axix_x = toAxis(act,view)[0];
		 float axix_y = toAxis(act,view)[1];
//		 float from_axis_x = i[0];
//		float from_axis_y = i[1];
		 Log.e("axix_x :"+axix_x);
		 Log.e("axix_y :"+axix_y);
		 AnimationSet animationSet = new AnimationSet(false);
			AlphaAnimation alpha = new AlphaAnimation(isAppear ? 0.0f : 1.0f,
					isAppear ? 1.0f : 0.0f);
			ScaleAnimation scale = new ScaleAnimation(
					isAppear ? 1.0f : 1.0f, isAppear ? 1.0f : 1.0f, // Start and end values for the X axis scaling
					isAppear ? 1.0f : 1.0f, isAppear ? 1.0f : 1.0f, // Start and end values for the Y axis scaling
		            Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
		            Animation.RELATIVE_TO_SELF, 0.5f);
			scale.initialize(view.getMeasuredWidth(), view.getMeasuredHeight() / 2, view.getMeasuredHeight(), view.getMeasuredHeight());
			TranslateAnimation translate = null;
			if(isAppear){
				translate = new TranslateAnimation(0, axix_x, 0, axix_y);
			}else {
//				translate = new TranslateAnimation(to_axis_x,from_axis_x ,to_axis_y, from_axis_y );
			}
			
			animationSet.setFillAfter(true);
//			animationSet.addAnimation(alpha);
			animationSet.addAnimation(scale);
			animationSet.addAnimation(translate);
			animationSet.setDuration(400);
			animationSet.setAnimationListener(listener);
			view.startAnimation(animationSet);
	 }
	 
	 
	 private  static float[] toAxis(Activity act, View view){
			Display mdisp = act.getWindowManager().getDefaultDisplay();
			Point mdispSize = new Point();
			mdisp.getSize(mdispSize);
			int maxX = mdispSize.x;
			int maxY = mdispSize.y;
			float to_axis_x = maxX/2
					- (view.getMeasuredWidth() / 2);
			float to_axis_y = maxY/2
					- (view.getMeasuredHeight() / 2);
//			float to_axis_x = maxX/2
//					;
//			float to_axis_y = maxY/2
//					;
			return new float[]{to_axis_x,to_axis_y};
		}
}
