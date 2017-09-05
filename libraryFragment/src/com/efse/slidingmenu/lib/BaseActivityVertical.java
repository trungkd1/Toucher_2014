package com.efse.slidingmenu.lib;


import com.jeremyfeinstein.slidingmenu.lib.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class BaseActivityVertical extends FragmentActivity{

	private SlidingMenu mSlidingMenu;
	private View mLayout;
	private boolean mContentViewCalled = false;
	private boolean mBehindContentViewCalled = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.slidingmenumain);
		mSlidingMenu = (SlidingMenu) super.findViewById(R.id.slidingmenuLayout);
		mLayout = super.findViewById(R.id.slidingmenuLayout);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (!mContentViewCalled || !mBehindContentViewCalled) {
			throw new IllegalStateException("Both setContentView and " +
					"setBehindContentView must be called in onCreate.");
		}
		mSlidingMenu.setStatic(isStatic());
	}
	
	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		setContentView(getLayoutInflater().inflate(layoutResID, null));
	}
	

	public void setContentView(View v) {
		setContentView(v, null);
	}

	public void setContentView(View v, LayoutParams params) {
		if (!mContentViewCalled) {
			mContentViewCalled = true;
		}
		RelativeLayout layout = new RelativeLayout(this);
		layout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

			RelativeLayout.LayoutParams p2 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		p2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		layout.addView(v, p2);
		mSlidingMenu.setAboveContent(layout, params);
	}
	
	
	
	public void setBehindContentView(int id) {
		setBehindContentView(getLayoutInflater().inflate(id, null));
	}

	public void setBehindContentView(View v) {
		setBehindContentView(v, null);
	}

	public void setBehindContentView(View v, LayoutParams params) {
		if (!mBehindContentViewCalled) {
			mBehindContentViewCalled = true;
		}
		mSlidingMenu.setBehindContent(v);
	}

	
	private boolean isStatic() {
		return mLayout instanceof LinearLayout;
	}

	public int getBehindOffset() {
		// TODO
		return 0;
	}

	public void setBehindOffset(int i) {
		mSlidingMenu.setBehindOffset(i);
	}

	public float getBehindScrollScale() {
		// TODO
		return 0;
	}

	public void setBehindScrollScale(float f) {
		mSlidingMenu.setBehindScrollScale(f);
	}

	@Override
	public View findViewById(int id) {
		return mSlidingMenu.findViewById(id);
	}

	public boolean toggle() {
		//		if (isStatic()) return;
		if (mSlidingMenu.isBehindShowing()) {
			showAbove();
			return true;
		} else {
			showBehind();
			return false;
		}
	}

	public void showAbove() {
		//		if (isStatic()) return;
		mSlidingMenu.showAbove();
	}

	public void showBehind() {
		//		if (isStatic()) return;
		mSlidingMenu.showBehind();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && mSlidingMenu.isBehindShowing()) {
			showAbove();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
