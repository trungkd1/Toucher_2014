package com.efse.toucher.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.efse.toucher.App;
import com.efse.toucher.CircleView;
import com.efse.toucher.MainActivity;
import com.efse.toucher.R;
import com.efse.toucher.ViewPannel;
import com.efse.toucher.ViewPannel.ObjectView;
import com.efse.toucher.dialog.ColorPickerDialog;
import com.efse.toucher.dialog.ColorPickerDialog.OnColorChange;
import com.efse.toucher.dialog.ListMenuDialog;
import com.efse.toucher.utils.ListenerDialog;
import com.efse.toucher.utils.MenuItem;
import com.efse.toucher.utils.OnViewPannelListener;
import com.efse.toucher.utils.SaveData;
import com.efse.toucher.utils.Utils;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.OpacityBar.OnOpacityChangedListener;

public abstract class BaseFragment extends Fragment implements
		OnViewPannelListener, OnClickListener {

	protected MainActivity activity;
	protected OpacityBar alpha_opacityBar;
	protected OpacityBar conner_opacityBar;
	protected OpacityBar square_opacityBar;
	protected OpacityBar round_opacityBar;
	protected ViewPannel pannel;
	protected CircleView circle;
	protected TextView text;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		activity = (MainActivity) getActivity();
		View view = inflater.inflate(R.layout.layout_panel, null);
		pannel = (ViewPannel) view.findViewById(R.id.pannel1);
		circle = (CircleView) view.findViewById(R.id.circleView);
		alpha_opacityBar = (OpacityBar) view
				.findViewById(R.id.alpha_opacitybar);
		conner_opacityBar = (OpacityBar) view
				.findViewById(R.id.conner_opacitybar);
		square_opacityBar = (OpacityBar) view
				.findViewById(R.id.square_opacityBar);
		round_opacityBar = (OpacityBar) view
				.findViewById(R.id.round_opacityBar);
		text = (TextView)view.findViewById(R.id.text1);
		view.findViewById(R.id.image_color_picker).setOnClickListener(this);
		setFragment();
		init();
		return view;
	}
	
	@Override
	public void setOnViewPannelListener(final ImageView view,final MenuItem item) {
		ListMenuDialog dialog = new ListMenuDialog(getActivity(),
				new ListenerDialog() {

					@Override
					public void onListener(int iconRes,String title) {
						if(iconRes == R.drawable.ic_power_down){
							if(!Utils.isActionLockScreen(getActivity())){
								Utils.actionAdminDevice(getActivity());
								return;
							}
						}
						
						if(iconRes > 0){
//							title.setText(item.getTitle().toString());
							item.setTitle(title);
							item.setIconRes(iconRes);
							view.setImageResource(item.getIconRes());
						}else{
							item.setTitle(null);;
							item.setIconRes(0);
							view.setImageResource(R.drawable.ic_favor);
						}
						App.adapterDB.updateMenu(item);
					}
				});
		dialog.show();

	}

	private void init() {
		// Values layout every SquareItem
		int co = Utils.getOffsetConner(activity);
		conner_opacityBar.setMaxValue(co);
		alpha_opacityBar.setMaxValue(300);
		square_opacityBar.setMaxValue(Utils.getOffsetSquare(activity));
		round_opacityBar.setMaxValue(Utils.getOffsetRound(activity));
		

		int conner = SaveData.getInstance(activity).getConnerPannel();
		int alpha = (int) (SaveData.getInstance(activity).getAlphaPannel());
		int color = getResources().getColor(R.color.aqua_deep);
		int square = SaveData.getInstance(activity).getSquarePannel();
		int round = SaveData.getInstance(activity).getRoundPannel();

		conner_opacityBar.setColor(color);
		square_opacityBar.setColor(color);
		round_opacityBar.setColor(color);
		alpha_opacityBar.setColor(color);
		
		conner_opacityBar.setOpacity(conner);
		alpha_opacityBar.setOpacity(alpha);
		square_opacityBar.setOpacity(square);
		round_opacityBar.setOpacity(round);
		
		Utils.setAlpha(pannel, Utils.getAlpha(alpha));
		setColorBackground(SaveData.getInstance(activity).getColorPannel());
		
		alpha_opacityBar
				.setOnOpacityChangedListener(new OnOpacityChangedListener() {

					@Override
					public void onOpacityChanged(int opacity) {
						if (opacity > 50) {
							SaveData.getInstance(activity).setALphaPannel(
									opacity);
							Utils.setAlpha(pannel, Utils.getAlpha(opacity));
						}
					}
				});

		conner_opacityBar
				.setOnOpacityChangedListener(new OnOpacityChangedListener() {

					@Override
					public void onOpacityChanged(int conner) {
						Utils.setPannelBackGround(
								pannel,
								SaveData.getInstance(activity).getColorPannel(),
								conner);
						SaveData.getInstance(activity).setConnerPannel(conner);
					}
				});

		square_opacityBar
				.setOnOpacityChangedListener(new OnOpacityChangedListener() {

					@Override
					public void onOpacityChanged(int square) {
						int padding = Utils.getSquareStandard(getActivity(),square);
						SaveData.getInstance(activity)
								.setSquarePannel(square);
						for (int i = 0; i < pannel.objectsView.size(); i++) {
							ObjectView obj = pannel.objectsView.get(i);
							Utils.setPaddingView(obj.getLayout(), padding);
						}
					}
				});
		
		round_opacityBar 
				.setOnOpacityChangedListener(new OnOpacityChangedListener() {

					@Override
					public void onOpacityChanged(int round) {
						int padding = Utils.getRoundStandard(getActivity(),round);
						int color = SaveData.getInstance(activity).getColorPannel();
						SaveData.getInstance(activity).setRoundPannel(round);
						for (int i = 0; i < pannel.objectsView.size(); i++) {
							ObjectView obj = pannel.objectsView.get(i);
							Utils.setPaddingView(obj.getLayout(), padding);
							Utils.setPannelBackGround(obj.getLayout(), color,150);
						}
					}
				});
		
		circle.setOnListenerViewItem(this);
		pannel.setOnListenerViewItem(this);
	};
	
	
	protected abstract void setFragment();
	protected abstract void setColorBackground(int color);
	
	@Override
	public void onClick(View v) {
		ColorPickerDialog dialog = new ColorPickerDialog(activity,
				new OnColorChange() {

					@Override
					public void setOnColorChange(int color) {
//						alpha_opacityBar.setColor(color);
						setColorBackground(color);

					}
				});
		dialog.show();
	}

}
