package com.efse.toucher.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.camera.CropImageIntentBuilder;
import com.efse.toucher.App;
import com.efse.toucher.MainActivity;
import com.efse.toucher.R;
import com.efse.toucher.adapter.PointAdapter;
import com.efse.toucher.utils.Config;
import com.efse.toucher.utils.PointItem;
import com.efse.toucher.utils.SaveData;
import com.efse.toucher.utils.Utils;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.OpacityBar.OnOpacityChangedListener;

public class PointFragment extends Fragment implements OnItemClickListener,OnItemLongClickListener {
	
	private static PointFragment f;
	public static PointFragment newInstance(String text) {
		if (f == null) {
			f = new PointFragment();
			f.setTargetFragment(f, Config.FRAGMENT_SECONDS);
			Bundle b = new Bundle();
			b.putString("", text);
			f.setArguments(b);
		}
		return f;
	}
	
	private static final int REQUEST_GALLERY = 1;
	private static final int REQUEST_CAMERA = 2;
	private static final int REQUEST_CROP = 3;
	
	private MainActivity mainActivity;
	private ArrayList<PointItem> objs;
	private ImageView ic_preview;
	private OpacityBar alpha_opacityBar;
	private OpacityBar size_opacityBar;
	private GridView grid ;
	private PointAdapter adapter;
	private int width;
	private Uri uri;
	private boolean isDelete = false;
	private PointItem pointpref;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		objs = new ArrayList<PointItem>();
		objs.add(new PointItem(R.drawable.ic_launcher));
		objs.add(new PointItem(R.drawable.f_japan));
		objs.add(new PointItem(R.drawable.f_england));
		objs.add(new PointItem(R.drawable.f_germany));
		objs.add(new PointItem(R.drawable.f_united));
		
		for( PointItem item : App.adapterDB.getAllPointer()){
			objs.add(item);
		}
		objs.add(new PointItem(R.drawable.button_add));
		uri = Uri.fromFile(Utils.creatFile());
		 width = Utils.getDimenIcon(getActivity());
		 if(SaveData.getInstance(getActivity()).isFirstRunning()){
			 SaveData.getInstance(getActivity()).setPoint(objs.get(0));
		 }
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 super.onCreateView(inflater, container, savedInstanceState);
		 View view = inflater.inflate(R.layout.layout_point, container, false);
		 ic_preview = (ImageView)view.findViewById(R.id.ipreview);
		 grid = (GridView)view.findViewById(R.id.gridview);
		 grid.setOnItemClickListener(this);
		 grid.setOnItemLongClickListener(this);
		 alpha_opacityBar = (OpacityBar) view.findViewById(R.id.alpha_opacitybar);
		 size_opacityBar = (OpacityBar) view.findViewById(R.id.size_opacitybar);
		 init();
		return view;
	}
	
	private void init(){
		 adapter = new PointAdapter(getActivity(), objs);
		 grid.setAdapter(adapter);
		 
		 mainActivity = (MainActivity)getActivity();
		 alpha_opacityBar.setMaxValue(300);
		 int size = (int)(SaveData.getInstance(getActivity()).getWidthDisplay()/2.82f);
		 
		 size_opacityBar.setMaxValue(size);
		 
		 alpha_opacityBar.setColor(getResources().getColor(R.color.aqua_deep));
		 size_opacityBar.setColor(getResources().getColor(R.color.aqua_deep));
		 
		 int alpha = (int) (SaveData.getInstance(getActivity()).getAlphaPoint());
		
		 alpha_opacityBar.setOpacity(alpha);
		 size_opacityBar.setOpacity(SaveData.getInstance(getActivity()).getSizePoint());
		 Utils.setAlpha(ic_preview, alpha);
		 Utils.setPaddingView(ic_preview, Utils.getWidthPoint(getActivity()));
		 
		 alpha_opacityBar.setOnOpacityChangedListener(new OnOpacityChangedListener() {
			
			@Override
			public void onOpacityChanged(int alpha) {
				if (alpha > 50) {
					SaveData.getInstance(getActivity()).setALphaPoint(alpha);
					Utils.setAlpha(ic_preview, alpha);
					mainActivity.setAlphaPoint(alpha);
				}
			}
		 });
		 
		 size_opacityBar.setOnOpacityChangedListener(new OnOpacityChangedListener() {
			
			@Override
			public void onOpacityChanged(int size) {
				SaveData.getInstance(getActivity()).setSizePoint(size);
				int width = Utils.getWidthPoint(getActivity());
				Utils.setPaddingView(ic_preview, width);
				mainActivity.setSizePoint(width);
			}
		 });
		 
		pointpref =  SaveData.getInstance(getActivity()).getPoint();
		if(pointpref.getIconBitmap() != null){
			ic_preview.setImageBitmap(App.adapterDB.getBitmapPointer(pointpref.getId()));
		}else{
			ic_preview.setImageResource(pointpref.getIconRes());
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long id) {
		int endPos = objs.size() - 1;
		if(isDelete){
			if(adapter.isRemove()){
				if(pos == endPos){
					adapter.setModeRemove(false);
				}else{
					if(objs.get(pos).isPrivateIcon() && isDelete){
						App.adapterDB.deletePointer(objs.get(pos).getId());
						adapter.removeItem(objs.get(pos));
						if((pointpref.getId() == objs.get(pos).getId())){
							pointpref = objs.get(0);
							ic_preview.setImageResource(pointpref.getIconRes());
							mainActivity.changePoint(pointpref);
							 SaveData.getInstance(getActivity()).setPoint(pointpref);
						};
					}
				}
				return;
			}
		}else{
			isDelete = !isDelete;
		}
	
		
		if(pos == endPos){
			AlertDialog.Builder dialog = new Builder(getActivity());
			dialog.setItems(new String[]{"Take Picture","Choose Picture"}, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(which == 0){
						onTakePicture();
					}else{
						onChoosePicture();
					}
				}
			} );
			
			dialog.show();
		}else{
			if(objs.get(pos).getIconBitmap() == null){
				ic_preview.setImageResource(objs.get(pos).getIconRes());
			}else{
				ic_preview.setImageBitmap(objs.get(pos).getIconBitmap());
			}
			SaveData.getInstance(getActivity()).setPoint(objs.get(pos));
			mainActivity.changePoint(objs.get(pos));
			
		}
	}
	
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View view, int position,
			long id) {
		isDelete = false;
		if(objs.get(position).isPrivateIcon()){
			adapter.setModeRemove(true);
		}
		return false;
	}

	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		  if ( resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_GALLERY:
				performCrop(data);
				break;
			case REQUEST_CAMERA:
				performCrop(data);
				break;
			case REQUEST_CROP:
				Bitmap bmp = (Bitmap)data.getParcelableExtra("data");
				addList(bmp);
				break;
			}
		  }
	}
	
	private void onTakePicture(){
	    Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
	    startActivityForResult(captureIntent, REQUEST_CAMERA);
	}
	
	private void onChoosePicture(){
         startActivityForResult(Utils.getPickImageIntent(getActivity()), REQUEST_GALLERY);
	}
	
	private void performCrop(Intent data) {
         CropImageIntentBuilder cropImage = new CropImageIntentBuilder(200, 200, uri);
         if(data != null){
        	 cropImage.setSourceImage(data.getData());
         }else{
        	 cropImage.setSourceImage(uri);
         }
         Intent intent = cropImage.getIntent(getActivity());
         intent.putExtra("circleCrop", true);
         startActivityForResult(intent,REQUEST_CROP);
	}
	
	private void addList(Bitmap b){
		Bitmap bmp = Utils.getResizedBitmap(b, (width * 3) , (width * 3) );
		ic_preview.setImageBitmap(bmp);
		PointItem item = new PointItem(bmp);
		item.setPrivateIcon(true);
		objs.add(objs.size() - 1,item);
		long id = App.adapterDB.addPointer(item);
        adapter.notifyDataSetChanged();
        mainActivity.changePoint(item);
        item.setId((int) id);
        SaveData.getInstance(getActivity()).setPoint(item);
	}
}
