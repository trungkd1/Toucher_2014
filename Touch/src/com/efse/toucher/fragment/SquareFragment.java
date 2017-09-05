package com.efse.toucher.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.efse.toucher.ViewPannel;
import com.efse.toucher.ViewPannel.ObjectView;
import com.efse.toucher.utils.Config;
import com.efse.toucher.utils.SaveData;
import com.efse.toucher.utils.Utils;

public class SquareFragment extends BaseFragment implements OnClickListener{

	private static SquareFragment f;
	public static SquareFragment newInstance(String text) {
		if(f == null){
			f = new SquareFragment();
			f .setTargetFragment(f, Config.FRAGMENT_SECONDS);
	        Bundle b = new Bundle();
	        b.putString("", text);
	        f.setArguments(b);
		}
        return f;
    }

	@Override
	protected void setFragment() {
		text.setText("Square");
		square_opacityBar.setVisibility(View.VISIBLE);
		pannel.setVisibility(View.VISIBLE);
		circle.setVisibility(View.GONE);
		pannel.setType(ViewPannel.TYPE_FLAT);
		pannel.setPannel(true);
	}

	@Override
	protected void setColorBackground(int color) {
		int square = SaveData.getInstance(activity).getSquarePannel();
		for(int i = 0 ; i < pannel.objectsView.size() ; i++){
			ObjectView obj = pannel.objectsView.get(i);
			Utils.setPaddingView(obj.getLayout(), Utils.getSquareStandard(getActivity(),square));
			obj.getLayout().setBackgroundColor(color);
		}
	}


}
