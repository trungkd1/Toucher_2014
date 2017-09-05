package com.efse.toucher.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.efse.toucher.fragment.AnimationFragment;
import com.efse.toucher.fragment.HomeFragment;
import com.efse.toucher.fragment.PointFragment;
import com.efse.toucher.fragment.AboutFragment;

public  class FragmentViewPagerAdapter extends
		android.support.v4.app.FragmentPagerAdapter {

	protected static final String[] CONTENT = new String[] { "Pannel", "Point","Animation",
			"About" };
	public FragmentViewPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
	}

	
	@Override
	public Fragment getItem(int pos) {
		switch(pos) {
          case 0:return HomeFragment.newInstance("HomeFragment, HomeFragment");
          case 1: return PointFragment.newInstance("PointFragment, PointFragment");
          case 2: return AnimationFragment.newInstance("AnimationFragment, AnimationFragment");
          case 3: return AboutFragment.newInstance("ThirdFragment, ThirdFragment");
          default: return HomeFragment.newInstance("HomeFragment, HomeFragment");
          }
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return CONTENT.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return CONTENT[position % CONTENT.length];
	}
	
	
}
