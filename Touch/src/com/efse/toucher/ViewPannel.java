package com.efse.toucher;

import java.util.ArrayList;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efse.toucher.utils.MenuItem;
import com.efse.toucher.utils.OnViewPannelListener;
import com.efse.toucher.utils.SaveData;
import com.efse.toucher.utils.Utils;

public class ViewPannel extends LinearLayout {
	private Context context;
	private int padding;
	private int type = 0;
	private int width;
	private int tag = 0;
	public static final int TYPE_CLASSIC = 0;
	public static final int TYPE_FLAT = 1;

	private ArrayList<MenuItem> objects;
	public ArrayList<ObjectView> objectsView;

	int cellX = 3;
	int cellY = 3;

	public ViewPannel(Context context) {
		super(context);
		this.context = context;
	}

	public ViewPannel(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void setType(int type) {
		this.type = type;
		init();
	}

	private void init() {
		objectsView = new ArrayList<ObjectView>();
		padding = (int) getResources().getDimension(R.dimen.padding);
		// setBackgroundResource(R.drawable.border);
		width = SaveData.getInstance(context).getWidthPannel();
		setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		setGravity(Gravity.CENTER);
		setOrientation(LinearLayout.VERTICAL);
		createView(cellX, cellY);
	}

	public void setPannel(boolean isSetting) {
		objects = App.adapterDB.getAllMenu();
		if (objects.size() != 0) {
			for (int i = 0; i < objects.size(); i++) {
				MenuItem item = objects.get(i);
				ObjectView obj = objectsView.get(i);
				if (!isSetting) {
					if (item.getIconRes() > 0) {
						obj.setText(item.getTitle());
						obj.setIdObject(item.getId());
						
						switch (item.getIconRes()) {
						case R.drawable.ic_auto_orientation_on:
							int iconRes = Utils.isAutoOrientation(context)? R.drawable.ic_auto_orientation_on : R.drawable.ic_auto_orientation_on_press;
							obj.setImage(iconRes);
							break;

						case R.drawable.ic_ringer_normal:
							iconRes = Utils.geticonResRinger(context)[0];
							obj.setImage(iconRes);
							break;
						case R.drawable.ic_flash_off:
							if (Utils.getFlash().equals(Camera.Parameters.FLASH_MODE_TORCH)) {
								obj.setImage(R.drawable.ic_flash_on);
								break;
							} 
						default:
							obj.setImage(item.getIconRes());
							break;
						}
						
					} else {
						obj.setImage(0);
					}
				} else {
					if (item.getIconRes() > 0) {
						obj.setIdObject(item.getId());
						obj.setImage(item.getIconRes());
					}
				}
			}
		}
	}


	private void createView(int cellX, int cellY) {
		for (int i = 0; i < cellY; i++) {
			LinearLayout layout1 = new LinearLayout(context);
			layout1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			layout1.setOrientation(LinearLayout.HORIZONTAL);
			layout1.setGravity(Gravity.CENTER);
			for (int j = 0; j < cellX; j++) {
				layout1.addView(createItemview());
			}
			addView(layout1);
		}
	}

	private LinearLayout createItemview() {
		final LinearLayout layout = new LinearLayout(context);
		layout.setTag(tag);
		layout.setOrientation(LinearLayout.VERTICAL);
		final TextView text = new TextView(context);
		text.setVisibility(View.GONE);
		final ImageView image = new ImageView(context);
		image.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		image.setImageResource(R.drawable.ic_favor);
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int pos = (Integer) layout.getTag();
				listener.setOnViewPannelListener(objectsView.get(pos)
						.getImage(), objects.get(pos));
			}
		});

		if (type == TYPE_CLASSIC) {
			layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			layout.setPadding(padding, padding, padding, padding);
			layout.addView(image);
			layout.addView(text);
			objectsView.add(tag++, new ObjectView(layout, image, text));
		} else {
			layout.setLayoutParams(new LayoutParams(width / 3, width / 3));
			layout.setGravity(Gravity.CENTER);
			final LinearLayout layoutChild = new LinearLayout(context);
			layoutChild.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			layoutChild.setGravity(Gravity.CENTER);
			layoutChild.addView(image);
			layout.addView(text);
			layout.addView(layoutChild);
			objectsView.add(tag++, new ObjectView(layoutChild, image, text));
		}

		return layout;
	}

	private OnViewPannelListener listener;

	public void setOnListenerViewItem(OnViewPannelListener listener) {
		this.listener = listener;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

	public class ObjectView {
		ImageView image;
		TextView text;
		int idObject;
		LinearLayout layout;

		public LinearLayout getLayout() {
			return layout;
		}

		public void setLayout(LinearLayout layout) {
			this.layout = layout;
		}

		public int getIdObject() {
			return idObject;
		}

		public void setIdObject(int idObject) {
			this.idObject = idObject;
		}

		public ImageView getImage() {
			return image;
		}

		public void setImage(int resId) {
			this.image.setImageResource(resId);
		}

		public TextView getText() {
			return text;
		}

		public void setText(String title) {
			this.text.setText(title);
		}

		public ObjectView(LinearLayout layout, ImageView image, TextView text) {
			this.image = image;
			this.text = text;
			this.layout = layout;
		}
	}
}
