package com.efse.toucher.fragment;

import android.os.Bundle;
import android.view.View;

import com.efse.toucher.ViewPannel;
import com.efse.toucher.ViewPannel.ObjectView;
import com.efse.toucher.utils.Config;
import com.efse.toucher.utils.SaveData;
import com.efse.toucher.utils.Utils;

public class RoundFragment extends BaseFragment{


	private static RoundFragment f;
	public static RoundFragment newInstance(String text) {
		if(f == null){
			f = new RoundFragment();
			f .setTargetFragment(f, Config.FRAGMENT_THIRD);
	        Bundle b = new Bundle();
	        b.putString("", text);
	        f.setArguments(b);
		}
        return f;
    }
	
	@Override
	protected void setFragment() {
		text.setText("Round");
		round_opacityBar.setVisibility(View.VISIBLE);
		pannel.setVisibility(View.VISIBLE);
		circle.setVisibility(View.GONE);
		pannel.setType(ViewPannel.TYPE_FLAT);
		pannel.setPannel(true);		
	}

	@Override
	protected void setColorBackground(int color) {
		int round = SaveData.getInstance(activity).getRoundPannel();
		for(int i = 0 ; i < pannel.objectsView.size() ; i++){
			ObjectView obj = pannel.objectsView.get(i);
			Utils.setPaddingView(obj.getLayout(), Utils.getRoundStandard(getActivity(),round));
			Utils.setPannelBackGround(obj.getLayout(), color,150);
		}
	}

}
