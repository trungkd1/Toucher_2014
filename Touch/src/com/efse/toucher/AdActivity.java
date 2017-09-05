package com.efse.toucher;

//import com.efse.admods.Admods;
//import com.efse.admods.Admods;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.efse.admods.Admods;
import com.efse.admods.Interstitial;

public class AdActivity extends Activity{

	private Admods admods;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		admods = new Admods(this);
		setContentView(R.layout.ad);
		final LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout);
		layout.addView(admods);
	    

		Interstitial interstitialAd = new Interstitial(this);
		interstitialAd.loadAd();
	}
	
	@Override
	  public void onPause() {
		admods.adView.pause();
	    super.onPause();
	  }

	  @Override
	  public void onResume() {
	    super.onResume();
	    admods.adView.resume();
	  }

	  @Override
	  public void onDestroy() {
		admods. adView.destroy();
	    super.onDestroy();
	  }
}
