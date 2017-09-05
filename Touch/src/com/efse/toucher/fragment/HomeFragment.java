package com.efse.toucher.fragment;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.efse.toucher.R;
import com.efse.toucher.utils.Config;
import com.efse.toucher.utils.Log;
import com.efse.toucher.utils.Mode;
import com.efse.toucher.utils.SaveData;
import com.efse.toucher.utils.Utils;

 public class HomeFragment extends Fragment implements OnClickListener {
	ImageView btn_conner;
	ImageView btn_round;
	ImageView btn_square;
	ImageView btn_home;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.main, null);
		btn_conner = (ImageView) view.findViewById(R.id.btn_conner);
		btn_round = (ImageView) view.findViewById(R.id.btn_round);
		btn_square = (ImageView) view.findViewById(R.id.btn_square);
		btn_home = (ImageView) view.findViewById(R.id.btn_home);
		btn_conner.setOnClickListener(this);
		btn_round.setOnClickListener(this);
		btn_square.setOnClickListener(this);
		btn_home.setOnClickListener(this);

		init();
		return view;
	}

	@Override
	public void onClick(View v) {
		setAlphaButton(0.5f);
		Utils.setAlpha(v, 1);
		int mode = 0;
		switch (v.getId()) {
		case R.id.btn_conner:
			mode = Mode.CONNER;
			break;
		case R.id.btn_round:
			mode = Mode.ROUND;
			break;
		case R.id.btn_square:
			mode = Mode.SQUARE;
			break;
		case R.id.btn_home:
			mode = Mode.HOME;
			break;
		}
		SaveData.getInstance(getActivity()).setMODE(mode);
		transaction(mode);
	}

	void setAlphaButton(float alpha) {
		Utils.setAlpha(btn_conner, alpha);
		Utils.setAlpha(btn_round, alpha);
		Utils.setAlpha(btn_square, alpha);
		Utils.setAlpha(btn_home, alpha);
	}

	protected void init() {
		setAlphaButton(0.5f);
		int mode = SaveData.getInstance(getActivity()).getMODE();
		transaction(mode);
		
	}
	
	private  void transaction(int mode) {
		Fragment fragment = null ;
		switch (mode) {
		case Mode.CONNER:
			Utils.setAlpha(btn_conner, 1f);
			fragment = ConnerFragment.newInstance("ConnerFragment");
			break;
		case Mode.ROUND:
			Utils.setAlpha(btn_round, 1f);
			fragment = RoundFragment.newInstance("RoundFragment");
			break;
		case Mode.SQUARE:
			Utils.setAlpha(btn_square, 1f);
			fragment = SquareFragment.newInstance("SquareFragment");
			break;
		case Mode.HOME:
			Utils.setAlpha(btn_home, 1f);
			fragment = CircleFragment.newInstance("CircleFragment");
			break;

		}
		
		
		FragmentTransaction trans = getChildFragmentManager()
				.beginTransaction();
//		trans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
		trans.replace(R.id.content_main,
				fragment);
		trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		trans.commit();
	}

	static HomeFragment f;

	public static HomeFragment newInstance(String text) {
		if (f == null) {
			f = new HomeFragment();
			f.setTargetFragment(f, Config.FRAGMENT_FIRST);
			Bundle b = new Bundle();
			b.putString("", text);
			f.setArguments(b);
		}
		return f;
	}

	@Override
    public void onDetach() {
        super.onDetach();

        if (sChildFragmentManagerField != null) {
            try {
                sChildFragmentManagerField.set(this, null);
            } catch (Exception e) {
                Log.e( "Error setting mChildFragmentManager field "+ e.getMessage());
            }
        }
    }
	
	
	 private static final Field sChildFragmentManagerField;

	    static {
	        Field f = null;
	        try {
	            f = Fragment.class.getDeclaredField("mChildFragmentManager");
	            f.setAccessible(true);
	        } catch (NoSuchFieldException e) {
	        	 Log.e( "Error setting mChildFragmentManager field "+ e.getMessage());
	        }
	        sChildFragmentManagerField = f;
	    }

}
