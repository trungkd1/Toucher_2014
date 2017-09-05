package com.efse.toucher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.efse.toucher.utils.Config;
import com.efse.toucher.utils.Log;
import com.efse.toucher.utils.Utils;

public class CircleFragment extends BaseFragment{

	private static CircleFragment f;
	public static CircleFragment newInstance(String text) {
		if(f == null){
			f = new CircleFragment();
			f .setTargetFragment(f, Config.FRAGMENT_HOME);
	        Bundle b = new Bundle();
	        b.putString("", text);
	        f.setArguments(b);
		}
        return f;
    }
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("haiz");
	}
	
	@Override
	protected void setFragment() {
		// TODO Auto-generated method stub
		alpha_opacityBar.setEnabled(false);
		pannel.setVisibility(View.GONE);
		circle.setVisibility(View.GONE);
		if(!Utils.isActionLockScreen(getActivity())){
			Utils.actionAdminDevice(getActivity());
		}
	}

	@Override
	protected void setColorBackground(int color) {
		// TODO Auto-generated method stub
		
	}

}
