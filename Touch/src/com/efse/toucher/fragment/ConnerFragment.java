package com.efse.toucher.fragment;

import android.os.Bundle;
import android.view.View;

import com.efse.toucher.ViewPannel;
import com.efse.toucher.utils.Config;
import com.efse.toucher.utils.SaveData;
import com.efse.toucher.utils.Utils;

public class ConnerFragment extends BaseFragment  {
	
	private static ConnerFragment f;
	
	public static ConnerFragment newInstance(String text) {
		if (f == null) {
			f = new ConnerFragment();
			f.setTargetFragment(f, Config.FRAGMENT_FIRST);
			Bundle b = new Bundle();
			b.putString("", text);
			f.setArguments(b);
		}
		return f;
	}

	@Override
	protected void setFragment() {
		text.setText("Conner");
		conner_opacityBar.setVisibility(View.VISIBLE);
		pannel.setVisibility(View.VISIBLE);
		circle.setVisibility(View.GONE);
		pannel.setType(ViewPannel.TYPE_CLASSIC);
		pannel.setPannel(true);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				SaveData.getInstance(activity).setWidthPannel(pannel.getWidth());
			}
		}).start();
	}


	@Override
	protected void setColorBackground(int color) {
		int conner = SaveData.getInstance(activity).getConnerPannel();
		Utils.setPannelBackGround(pannel, color, conner);
	}

}
