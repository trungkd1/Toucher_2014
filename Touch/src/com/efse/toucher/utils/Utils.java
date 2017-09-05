package com.efse.toucher.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.efse.toucher.AppsActivity;
import com.efse.toucher.FavoriteAppActivity;
import com.efse.toucher.R;
import com.efse.toucher.service.AdminReceiver;
import com.efse.toucher.service.ToucherService;

public class Utils {

	public static void setPannelBackGround(View view, int color, int conner) {
		RoundRectShape rect = new RoundRectShape(new float[] { conner, conner,
				conner, conner, conner, conner, conner, conner }, null, null);
		ShapeDrawable bg = new ShapeDrawable(rect);
		bg.getPaint().setColor(color);
		view.setBackgroundDrawable(bg);
	}

	public static void setPaddingView(View view, int padding) {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				padding, padding);
		view.setLayoutParams(layoutParams);
	}

	public static float getAlpha(int opacity) {
		return (float) opacity / 255;
	}

	public static int darkerColor(int color) {
		float[] hsv = new float[3];
		Color.colorToHSV(color, hsv);
		hsv[2] *= 0.8f; // value component
		return color = Color.HSVToColor(hsv);
	}

	public static void setAlpha(View view, float alpha) {
		AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
		animation.setDuration(0);
		animation.setFillAfter(true);
		view.startAnimation(animation);
	}

	public static void setAlpha(ImageView view, int alpha) {
		view.setAlpha(alpha);
	}

	public static byte[] bitmap2Byte(Bitmap b) {
		ByteArrayOutputStream blob = new ByteArrayOutputStream();
		b.compress(CompressFormat.PNG, 0 /* ignored for PNG */, blob);
		byte[] bitmapdata = blob.toByteArray();
		return bitmapdata;
	}

	public static Bitmap byte2Bitmap(byte[] blob) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		Bitmap b = BitmapFactory.decodeByteArray(blob, 0, blob.length, options);
		return b;
	}

	public static Bitmap getBitmap(Context context, Intent data) {
		Uri selectedImage = data.getData();
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = context.getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();
		int width = SaveData.getInstance(context).getWidthPointer();
		return getResizedBitmap(BitmapFactory.decodeFile(picturePath), width,
				width);
	}

	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);
		// "RECREATE" THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
				matrix, false);
		return resizedBitmap;
	}

	public static Intent getPickImageIntent(final Context context) {
		final Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		// return Intent.createChooser(intent, "Select picture");
		return intent;
	}

	public static File creatFile() {
		return new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/android.jpg");
	}

	public static int getDimenIcon(Context context) {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inTargetDensity = DisplayMetrics.DENSITY_DEFAULT;
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_apps, o);
		int w = bmp.getWidth();
		return w;
	}

	public static ArrayList<AppEntry> getInstalledApps(Context context) {

		ArrayList<AppEntry> objs = new ArrayList<AppEntry>();
		PackageManager mPm = context.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> packs = mPm.queryIntentActivities(intent,
				PackageManager.PERMISSION_GRANTED);
		for (int i = 0; i < packs.size(); i++) {
			ResolveInfo packageInfo = packs.get(i);
			if (packageInfo.loadLabel(mPm) == null) {
				continue;
			}
			objs.add(new AppEntry(packageInfo, mPm));
		}
		return objs;
	}

	public static int getOffsetSquare(Context context) {
		return SaveData.getInstance(context).getWidthPannel() / 3
				+ (int) context.getResources().getDimension(R.dimen.padding);
	}

	public static int getOffsetRound(Context context) {
		return (int) (SaveData.getInstance(context).getWidthPannel() / 3.35 + (int) context
				.getResources().getDimension(R.dimen.padding));
	}

	public static int getOffsetConner(Context context) {
		return SaveData.getInstance(context).getWidthPannel() / 5
				+ (int) context.getResources().getDimension(R.dimen.padding);
	}

	public static int getWidthPoint(Context context) {
		int width = Utils.getDimenIcon(context);
		int size = SaveData.getInstance(context).getSizePoint();
		return width + size - 10;
	}

	public static int getRoundStandard(Context context, int round) {
		float cont = (float) (SaveData.getInstance(context).getWidthPannel() / 4.15);
		return (int) (cont + (round / 3));
	}

	public static int getSquareStandard(Context context, int square) {
		float cont = (float) (SaveData.getInstance(context).getWidthPannel() / 4.7);
		return (int) (cont + (square / 3));
	}

	public static void actionHome(Context context) {
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(startMain);
	}

	public static void actionSetting(Context context) {
		Intent startMain = new Intent(Settings.ACTION_SETTINGS);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(startMain);
	}
	
	public static void actionAdminDevice(Context activity){
		try{
				ComponentName compName = new ComponentName(activity,
						AdminReceiver.class);
			Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName);
			activity.startActivity(intent);
		}catch(Exception ex){
			
		}
		
	}
	
	public static void onFavourite(Context context) {
		Intent startMain = new Intent(context, FavoriteAppActivity.class);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(startMain);
	}

	public static void actionLockScreen(Context activity) {
		DevicePolicyManager deviceManger = (DevicePolicyManager) activity
				.getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName compName = new ComponentName(activity,
				AdminReceiver.class);

		boolean active = deviceManger.isAdminActive(compName);
		if (active) {
			deviceManger.lockNow();
		} else {
			actionAdminDevice(activity);
		}

	}
	
	public static boolean isActionLockScreen(Context activity){
		DevicePolicyManager deviceManger = (DevicePolicyManager) activity
				.getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName compName = new ComponentName(activity,
				AdminReceiver.class);

		return deviceManger.isAdminActive(compName);
	}

	public static void execute_as_root(String[] commands) {
		try {
			// Do the magic
			Process p = Runtime.getRuntime().exec("su");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			DataOutputStream os = new DataOutputStream(p.getOutputStream());

			for (String command : commands) {
				os.writeBytes(new StringBuilder(command).append("\n")
						.toString());
			}
			os.writeBytes("\n");
			os.flush();
			os.close();

			// p.waitFor();
		} catch (IOException e) {
			Log.e(e.getMessage());
		}
	}

	private static int MB_SIZE = 1024 * 1024;

	private static float mTotalMemory = 0;

	public static float getTotalMemory() {
		if (mTotalMemory == 0) {
			MemInfoReader reader = new MemInfoReader();
			reader.readMemInfo();
			mTotalMemory = (float) reader.getTotalSize() / MB_SIZE;
		}
		return mTotalMemory;
	}

	/**
	 * @param a
	 *            instance of AvtivityManager
	 * @return Current free memory of phone. The size of memory is MB.
	 */
	public static float getLastestFreeMemory(ActivityManager am) {
		float freeMemory = 0;
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi);
		freeMemory = (float) mi.availMem / MB_SIZE;
		return freeMemory;
	}

	private static boolean isSystemProcess(int uid) {
		if (uid < 10000) {
			return true;
		}
		return false;
	}

	public static String[] cleanMemory(Context activity) {String[] strs = new String[2];
		try{
			//com.efse.toucher
		ActivityManager manager = (ActivityManager) activity
				.getSystemService(Activity.ACTIVITY_SERVICE);
		float mTotalMemory = Utils.getTotalMemory();
		List<ActivityManager.RunningAppProcessInfo> runningProcesses = manager
				.getRunningAppProcesses();
		int count = runningProcesses.size();
		for (int i = 0; i < count; i++) {
			ActivityManager.RunningAppProcessInfo process = runningProcesses
					.get(i);
			
			if (!isSystemProcess(process.uid)
					&& process.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				ActivityManager am = (ActivityManager) activity
						.getSystemService(Activity.ACTIVITY_SERVICE);
				if(process.processName.equals(activity.getPackageName()))
					continue;
				am.killBackgroundProcesses(process.processName);
			}

		}
		
		float mAvailMemory = Utils.getLastestFreeMemory(manager);
		float percent = (mAvailMemory / mTotalMemory) * 100;
		strs[0] = "Total memory :"
				+ String.format("%d MB(%d%%)", (int) mTotalMemory, 100);
		strs[1] = "Avalable use :"
				+ String.format("%d MB(%d%%)", (int) mAvailMemory,
						(int) percent);
		}catch(Exception ex){
			Log.e("Exception :"+ex.getMessage());
		}
		
		
		
		return strs;
	}

	public static void onApps(Context activity){
		Intent intent = new Intent(activity, AppsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity.startActivity(intent);
	}
	
	public static void onBattery(Context activity){
		Intent intentBatteryUsage = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);        
	    activity.startActivity(intentBatteryUsage);
	}
	
	public static void onAlarm(Context activity){
		Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity.startActivity(intent);
	}
	
	public static String cleanCache(Context activity) {
		PackageManager pm = activity.getPackageManager();
		// Get all methods on the PackageManager
		Method[] methods = pm.getClass().getDeclaredMethods();
		for (Method m : methods) {
			if (m.getName().equals("freeStorage")) {
				// Found the method I want to use
				try {
					long desiredFreeStorage = 8 * 1024 * 1024 * 1024; // Request
																		// for
																		// 8GB
																		// of
																		// free
																		// space
					m.invoke(pm, desiredFreeStorage, null);
				} catch (Exception e) {
					// Method invocation failed. Could be a permission problem
					return "Clear cache failded :" + e.getMessage();
				}
				break;
			}
		}
		return activity.getResources().getString(R.string.clean_cache_success);
	}

	public static void onVolumeUp(Context activity) {
		AudioManager mAudioManager = (AudioManager) activity
				.getSystemService(Activity.AUDIO_SERVICE);
		int volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, ++volume,
				AudioManager.FLAG_SHOW_UI);
	}

	public static void onVolumeDown(Context activity) {
		AudioManager mAudioManager = (AudioManager) activity
				.getSystemService(Activity.AUDIO_SERVICE);
		int volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, --volume,
				AudioManager.FLAG_SHOW_UI);

	}

	public static boolean isAutoOrientation(Context activity) {
		if (android.provider.Settings.System.getInt(
				activity.getContentResolver(),
				Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
			return false;
		} else {
			return true;
		}
	}

	public static void setAutoOrientationEnabled(Context activity,
			boolean enabled) {
		android.provider.Settings.System.putInt(activity.getContentResolver(),
				Settings.System.ACCELEROMETER_ROTATION, enabled ? 0 : 1);
	}
	
	
	public static Camera cam = null;
	
	public static void flashLightOn(Context activity) {

	    try {
	        if (activity.getPackageManager().hasSystemFeature(
	                PackageManager.FEATURE_CAMERA_FLASH)) {
	        	try {
	    	        cam.setPreviewTexture(new SurfaceTexture(0));
	    	    } catch (IOException ex) {
	    	        // Ignore
	    	    }
	            Parameters p = cam.getParameters();
	            p.setFlashMode(Parameters.FLASH_MODE_TORCH);
	            cam.setParameters(p);
	            cam.startPreview();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public static void flashLightOff(Context activity) {
	    try {
	        if (activity.getPackageManager().hasSystemFeature(
	                PackageManager.FEATURE_CAMERA_FLASH)) {
	            cam.stopPreview();
	            cam.release();
	            cam = null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static String getFlash(){
		if(cam == null)
		 cam = Camera.open();
         Parameters p = cam.getParameters();
         return p.getFlashMode();
	}
	
	public static int[] geticonResRinger(Context activity){
		int[] mode = new int[2];
		AudioManager am = (AudioManager)activity.getSystemService(Context.AUDIO_SERVICE);
		switch (am.getRingerMode()) {
		    case AudioManager.RINGER_MODE_SILENT:
		    	mode[0] = R.drawable.ic_ringer_silent_nor;
		    	mode[1] = 0;
		    	break;
		    	
		    case AudioManager.RINGER_MODE_VIBRATE:
		    	mode[0] = R.drawable.ic_ringer_vibration_nor;
		    	mode[1] = 1;
		    	break;
		    	
		    case AudioManager.RINGER_MODE_NORMAL:
		    	mode[0] = R.drawable.ic_ringer_normal;
		    	mode[1] = 2;
		}
		return mode;
	}
	
	public static int seticonResRinger(Context activity,int mode){
		AudioManager am = (AudioManager)activity.getSystemService(Context.AUDIO_SERVICE);
		am.setRingerMode(mode);
		switch (mode) {
		    case AudioManager.RINGER_MODE_SILENT:
		    	 return R.drawable.ic_ringer_silent_nor;
		    case AudioManager.RINGER_MODE_VIBRATE:
		       return R.drawable.ic_ringer_vibration_nor;
		    case AudioManager.RINGER_MODE_NORMAL:
		    	 return R.drawable.ic_ringer_normal;
		}
		return 0;
	}
	
	public static String getPathQuickTouch(){
		String directory = Environment.getExternalStorageDirectory().getAbsolutePath()+"/QuickTouch";
		File file = new File(directory);
		file.mkdirs();
		return file.getAbsolutePath();
	}
	
	public static void refreshSDcard(Context activity){
		IntentFilter intentfilter = new IntentFilter(Intent.ACTION_MEDIA_SCANNER_STARTED);
		intentfilter.addDataScheme("file");
		activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory().getAbsolutePath()))); 
	}
	
	public static String getTime(){
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("H : mm a");
        String formattedDate = df.format(cal.getTime());
        return formattedDate;
	}
	
	public static Intent startService(Context activity){
		Intent intent = new Intent(activity, ToucherService.class);
		intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
		
//		Intent intent = new Intent(activity, TouchService.class);
//		intent.setAction("android.intent.action.MAIN");
//        intent.addCategory("android.intent.category.LAUNCHER");
		return intent;
	}
	
	public static boolean isServiceRunning(Context activity,String serviceClassName){
        final ActivityManager activityManager = (ActivityManager)activity.getSystemService(Context.ACTIVITY_SERVICE);
        final List<RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(serviceClassName)){
                return true;
            }
        }
        return false;
     }

	public static void onNotification(Context activity){
		Intent intent = new Intent(activity, ToucherService.class);
		intent.setAction(Long.toString(System.currentTimeMillis()));
		PendingIntent pendingIntent = PendingIntent.getService(activity, 0, intent, 0);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(activity);
		mBuilder.setContentTitle(activity.getString(R.string.app_name));
		mBuilder.setContentText(activity.getString(R.string.click_here));
		mBuilder.setTicker(activity.getString(R.string.service_stop));
		mBuilder.setSmallIcon(R.drawable.ic_launcher);
		mBuilder.setOngoing(true);
		mBuilder.setAutoCancel(true);
		mBuilder.setContentIntent(pendingIntent);
			     
		NotificationManager mNotificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(6,
				mBuilder.build());
	}
	
	public static void onCancelNotification(Context ctx) {
	    String ns = Context.NOTIFICATION_SERVICE;
	    NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
	    nMgr.cancel(6);
	}
	
	public static boolean hasConnection(Context context) {
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
	        Context.CONNECTIVITY_SERVICE);

	    NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    if (wifiNetwork != null && wifiNetwork.isConnected()) {
	      return true;
	    }

	    NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    if (mobileNetwork != null && mobileNetwork.isConnected()) {
	      return true;
	    }

	    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	    if (activeNetwork != null && activeNetwork.isConnected()) {
	      return true;
	    }

	    return false;
	  }
}
