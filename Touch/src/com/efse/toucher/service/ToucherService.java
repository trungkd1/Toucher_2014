package com.efse.toucher.service;


import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.efse.toucher.App;
import com.efse.toucher.ShowPannelActivity;
import com.efse.toucher.utils.Log;
import com.efse.toucher.utils.Mode;
import com.efse.toucher.utils.PointItem;
import com.efse.toucher.utils.SaveData;
import com.efse.toucher.utils.Utils;

public class ToucherService extends Service implements OnLongClickListener,
		OnTouchListener, OnClickListener {

	private WindowManager windowManager;
	private ImageView chatHead;
	private WindowManager.LayoutParams mParams;
	private int mPreX;
	private int mPreY;
	private int x;
	private int y;
	private int mTotalAnimDx;
	private int mTotalAnimDy;
	private float initialTouchX;
	private float initialTouchY;
	private static final int HANDLE_AXIS_X = 0;
	private static final int HANDLE_AXIS_Y = 1;
	private boolean isTouching = false;
	private GestureDetector mDetector;

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	IBinder mBinder = new LocalBinder();

	public class LocalBinder extends Binder {
		public ToucherService getServerInstance() {
			return ToucherService.this;
		}
	}

	int mDefault_Height;

	@Override
	public void onCreate() {
		super.onCreate();
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		chatHead = new ImageView(this);

		showImageFloating();
		
        startService(new Intent(this, MyIntentService.class));

		mDefault_Height = Utils.getWidthPoint(getApplicationContext());
		
		int[] axis = SaveData.getInstance(getBaseContext()).getAxis();
		mPreX = axis[0];
		mPreY = axis[1];
		
		this.mParams = getParam(mDefault_Height, mDefault_Height, mPreX, mPreY,
				Gravity.TOP | Gravity.LEFT);
		windowManager.addView(chatHead, mParams);
		
		chatHead.setOnLongClickListener(this);
		chatHead.setOnTouchListener(this);
		chatHead.setOnClickListener(this);
		mDetector = new GestureDetector(this, new MyGestureDetector());
		
		DisplayMetrics metrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(metrics);
		mTotalAnimDx = metrics.widthPixels - mDefault_Height;
		mTotalAnimDy = metrics.heightPixels - mDefault_Height;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (chatHead != null)
			windowManager.removeView(chatHead);
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		Log.i("onUnbind");
		 stopService(new Intent(this, MyIntentService.class));
		return super.onUnbind(intent);
	}


	@Override
	public void onClick(View v) {
		if (!isTouching) {
			
			onShowToucher();
//			if (SaveData.getInstance(getBaseContext()).getMODE() == Mode.HOME) {
//				Utils.actionHome(getBaseContext());
//				return;
//			}
//			
////			appearToucher();
//			pannel.setOnTouchListener(new OnTouchListener() {
//
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//					// TODO Auto-generated method stub
//					disappearToucher();
//					return false;
//				}
//			});
		}

	}

	@Override
	public boolean onLongClick(View v) {
		if(!isTouching){
			stopSelf();
			Utils.onNotification(getBaseContext());
		}
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		 mDetector.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			initialTouchX = event.getRawX() - this.mParams.x;
			initialTouchY = event.getRawY() - this.mParams.y;
			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_MASK:
			onAnimateMove(x, y, 1);
			break;

		case MotionEvent.ACTION_MOVE:
			isTouching = true;
			x = Math.round(event.getRawX() - initialTouchX);
			y = Math.round(event.getRawY() - initialTouchY);
			if ((x <= mTotalAnimDx && x > 0) && (y <= mTotalAnimDy && y > 0)) {
				this.mParams.x = x;
				this.mParams.y = y;
				windowManager.updateViewLayout(chatHead, this.mParams);
			}
			break;
		}
		
		mPreX = this.mParams.x;
		mPreY = this.mParams.y;
		return false;
	}

	public Handler mHandlerAnimation = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if ((mPreX > (mTotalAnimDx) || mPreX < 0) ||  (mPreY > (mTotalAnimDy) || mPreY < 0)){
				isTouching = false;
				ToucherService.this.mHandlerAnimation.removeCallbacksAndMessages(this);
				SaveData.getInstance(getBaseContext()).setAxis(mPreX, mPreY);
				return;

			} else {
				Message msg2 = new Message();
				msg2.arg1 = msg.arg1;
				msg2.what = msg.what;
				if (msg.what == HANDLE_AXIS_X) {
					updateLayoutParams(true);
				} else if (msg.what == HANDLE_AXIS_Y) {
					updateLayoutParams(false);
				}
				mHandlerAnimation.sendMessageDelayed(msg2, msg2.arg1);
			}
		}
	};

	
	class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			if (SaveData.getInstance(getBaseContext()).getMODE() == Mode.HOME) {
				Utils.actionLockScreen(getBaseContext());
			}
			return false;
		}
	}

	
	private void showImageFloating() {
		PointItem obj = SaveData.getInstance(getApplicationContext())
				.getPoint();
		changePoint(obj);
		int alpha = (int) SaveData.getInstance(getApplicationContext())
				.getAlphaPoint();
		Utils.setAlpha(chatHead, alpha);
	}
	
	
	private void updateLayoutParams(boolean isAxisX) {
		if (isAxisX) {
			if (mPreX <= mTotalAnimDx / 2) {
				mPreX = mPreX - 40;
				this.mParams.x = mPreX;
				this.mParams.y = mPreY;
			} else {
				mPreX = mPreX + 40;
				this.mParams.x = mPreX;
				this.mParams.y = mPreY;
			}
		} else {
			if (mPreY <= mTotalAnimDy / 2) {
				mPreY = mPreY - 40;
				this.mParams.x = mPreX;
				this.mParams.y = mPreY;
			} else {
				mPreY = mPreY + 40;
				this.mParams.x = mPreX;
				this.mParams.y = mPreY;
			}
		}
		this.windowManager.updateViewLayout(this.chatHead, this.mParams);
	}
	
	
	private int getAXIS(int paramInt1, int paramInt2) {

		float percentX = (paramInt1 * 100) / mTotalAnimDx;
		float percentY = (paramInt2 * 100) / mTotalAnimDy;
		int what;
		if (percentY <= 50 && percentX < 50) {
			if (paramInt1 < paramInt2) {
				what = HANDLE_AXIS_X;
			} else {
				what = HANDLE_AXIS_Y;
			}
		} else if (percentY <= 50 && percentX > 50) {
			if ((mTotalAnimDx - paramInt1) < paramInt2) {
				what = HANDLE_AXIS_X;
			} else {
				what = HANDLE_AXIS_Y;
			}
		} else if (percentY > 50 && percentX < 50) {
			if (paramInt1 < (mTotalAnimDy - paramInt2)) {
				what = HANDLE_AXIS_X;
			} else {
				what = HANDLE_AXIS_Y;
			}
		} else {
			if ((mTotalAnimDx - paramInt1) < (mTotalAnimDy - paramInt2)) {
				what = HANDLE_AXIS_X;
			} else {
				what = HANDLE_AXIS_Y;
			}
		}
		return what;
	}
	
	private void onAnimateMove(int paramInt1, int paramInt2, int timedelay) {
		this.mPreX = paramInt1;
		this.mPreY = paramInt2;
		Message msg = new Message();
		msg.arg1 = timedelay;
		msg.what = getAXIS(paramInt1, paramInt2);
		this.mHandlerAnimation.sendMessage(msg);
	}

	public void changePoint(PointItem obj) {
		if (obj.getIconBitmap() != null) {
			chatHead.setImageBitmap(App.adapterDB.getBitmapPointer(obj.getId()));
		} else {
			chatHead.setImageResource(obj.getIconRes());
		}
	}
	
	public void onShowToucher(){
		Log.e("onShowToucher");
		Intent intent  = new Intent(getBaseContext(), ShowPannelActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		if(!SaveData.isStarted)
			startActivity(intent);
	}

	public void setAlpha(int alpha) {
		Utils.setAlpha(chatHead, alpha);
	}

	public void setSize(int size) {
		mParams.height = size;
		mParams.width = size;
		mDefault_Height = size;
		DisplayMetrics metrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(metrics);
		mTotalAnimDx = metrics.widthPixels - mDefault_Height;
		mTotalAnimDy = metrics.heightPixels - mDefault_Height;
		windowManager.updateViewLayout(chatHead, this.mParams);
		onAnimateMove(mPreX, mPreY, 1);
	}

	public WindowManager.LayoutParams getParam(int w, int h, int x, int y,
			int gravity) {
		final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				w, h, WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
						| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.RGBA_8888);
		params.gravity = gravity;
		params.x = x;
		params.y = y;
		// params.windowAnimations = android.R.style.Animation_Translucent;
		return params;
	}

	public void toast(String str) {
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
	}
	
	public void disappearToucher() {
//		pannel.destroyDrawingCache();
//		pannel.setVisibility(View.GONE);
		chatHead.setVisibility(View.VISIBLE);
	}

//	public void appearToucher() {
//		
//		windowManager.removeView(pannel);
//		pannel.destroyDrawingCache();
//		chatHead.setVisibility(View.GONE);
//		DisplayMetrics metrics = new DisplayMetrics();
//		windowManager.getDefaultDisplay().getMetrics(metrics);
//		pannel = new Pannel(getBaseContext(), this);
//		windowManager.addView(
//				pannel,
//				getParam(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
//						0, 0, Gravity.CENTER));
//		setAlphaAnimation(pannel);
//	}
	
	
	@Override
	public void onTaskRemoved(Intent rootIntent) {
//		Log.i("onTaskRemoved");
		super.onTaskRemoved(rootIntent);
	}

	@Override
	public void onTrimMemory(int level) {
//		Log.i("onTrimMemory");
		super.onTrimMemory(level);
	}
	
	@Override
	public void onLowMemory() {
//		Log.i("onLowMemory");
		super.onLowMemory();
	}
	
	@Override
	public void onRebind(Intent intent) {
//		Log.i("onRebind");
		super.onRebind(intent);
	}
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		Log.i("onStartCommand");
		return super.onStartCommand(intent, flags, startId);
		
	}
}
