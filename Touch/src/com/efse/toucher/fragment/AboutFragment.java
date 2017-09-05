package com.efse.toucher.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.efse.toucher.AdActivity;
import com.efse.toucher.R;
import com.efse.toucher.dialog.CreditsDialog;
import com.efse.toucher.utils.Config;
import com.efse.toucher.utils.Utils;

public class AboutFragment extends Fragment implements OnClickListener {

	private static AboutFragment f;

	public static AboutFragment newInstance(String text) {
		if (f == null) {
			f = new AboutFragment();
			f.setTargetFragment(f, Config.FRAGMENT_FOUR);
			Bundle b = new Bundle();
			b.putString("", text);
			f.setArguments(b);
		}
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_about, null);
		view.findViewById(R.id.btn_support).setOnClickListener(this);
		view.findViewById(R.id.btn_send_feedback).setOnClickListener(this);
		view.findViewById(R.id.btn_credits).setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_support:
			onSupport();
			break;
		case R.id.btn_send_feedback:
			onFeedback();
			break;
		case R.id.btn_credits:
			onCredits();
			break;

		default:
			break;
		}
		
	}
	
	private void onSupport(){
		startActivity(new Intent(getActivity(), AdActivity.class));
	}
	
	private void onFeedback(){
		if(Utils.hasConnection(getActivity())){
//			FeedbackDialog dialog = new FeedbackDialog(getActivity());
//			dialog.show();
			String url = "https://docs.google.com/forms/d/1m5SrfteBYWXWkIGi3yO9lQ-dpFfeX34X4-8Onxgd8aI/viewform?c=0&w=1";
//			String url = "http://app.appsflyer.com/jp.sapporo.beerapp?pid=bui@razona.jp&c=admob-text";
			Intent i = new Intent(Intent.ACTION_VIEW, 
				       Uri.parse(url));
				startActivity(i);

		}else{
			Toast.makeText(getActivity(), "Please connect internet", Toast.LENGTH_SHORT).show();
		}
		
		
		
	}
	
	private void onCredits(){
		CreditsDialog dialog = new CreditsDialog(getActivity());
		dialog.show();
	}
}
