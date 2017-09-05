package com.efse.toucher.utils;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import com.efse.toucher.R;

public class Funtion {

	// Method : [input keyevent KEYCODE]

	// 0 --> "KEYCODE_UNKNOWN"
	// 1 --> "KEYCODE_MENU"
	// 2 --> "KEYCODE_SOFT_RIGHT"
	// 3 --> "KEYCODE_HOME"
	// 4 --> "KEYCODE_BACK"
	// 5 --> "KEYCODE_CALL"
	// 6 --> "KEYCODE_ENDCALL"
	// 7 --> "KEYCODE_0"
	// 8 --> "KEYCODE_1"
	// 9 --> "KEYCODE_2"
	// 10 --> "KEYCODE_3"
	// 11 --> "KEYCODE_4"
	// 12 --> "KEYCODE_5"
	// 13 --> "KEYCODE_6"
	// 14 --> "KEYCODE_7"
	// 15 --> "KEYCODE_8"
	// 16 --> "KEYCODE_9"
	// 17 --> "KEYCODE_STAR"
	// 18 --> "KEYCODE_POUND"
	// 19 --> "KEYCODE_DPAD_UP"
	// 20 --> "KEYCODE_DPAD_DOWN"
	// 21 --> "KEYCODE_DPAD_LEFT"
	// 22 --> "KEYCODE_DPAD_RIGHT"
	// 23 --> "KEYCODE_DPAD_CENTER"
	// 24 --> "KEYCODE_VOLUME_UP"
	// 25 --> "KEYCODE_VOLUME_DOWN"
	// 26 --> "KEYCODE_POWER"
	// 27 --> "KEYCODE_CAMERA"
	// 28 --> "KEYCODE_CLEAR"
	// 29 --> "KEYCODE_A"
	// 30 --> "KEYCODE_B"
	// 31 --> "KEYCODE_C"
	// 32 --> "KEYCODE_D"
	// 33 --> "KEYCODE_E"
	// 34 --> "KEYCODE_F"
	// 35 --> "KEYCODE_G"
	// 36 --> "KEYCODE_H"
	// 37 --> "KEYCODE_I"
	// 38 --> "KEYCODE_J"
	// 39 --> "KEYCODE_K"
	// 40 --> "KEYCODE_L"
	// 41 --> "KEYCODE_M"
	// 42 --> "KEYCODE_N"
	// 43 --> "KEYCODE_O"
	// 44 --> "KEYCODE_P"
	// 45 --> "KEYCODE_Q"
	// 46 --> "KEYCODE_R"
	// 47 --> "KEYCODE_S"
	// 48 --> "KEYCODE_T"
	// 49 --> "KEYCODE_U"
	// 50 --> "KEYCODE_V"
	// 51 --> "KEYCODE_W"
	// 52 --> "KEYCODE_X"
	// 53 --> "KEYCODE_Y"
	// 54 --> "KEYCODE_Z"
	// 55 --> "KEYCODE_COMMA"
	// 56 --> "KEYCODE_PERIOD"
	// 57 --> "KEYCODE_ALT_LEFT"
	// 58 --> "KEYCODE_ALT_RIGHT"
	// 59 --> "KEYCODE_SHIFT_LEFT"
	// 60 --> "KEYCODE_SHIFT_RIGHT"
	// 61 --> "KEYCODE_TAB"
	// 62 --> "KEYCODE_SPACE"
	// 63 --> "KEYCODE_SYM"
	// 64 --> "KEYCODE_EXPLORER"
	// 65 --> "KEYCODE_ENVELOPE"
	// 66 --> "KEYCODE_ENTER"
	// 67 --> "KEYCODE_DEL"
	// 68 --> "KEYCODE_GRAVE"
	// 69 --> "KEYCODE_MINUS"
	// 70 --> "KEYCODE_EQUALS"
	// 71 --> "KEYCODE_LEFT_BRACKET"
	// 72 --> "KEYCODE_RIGHT_BRACKET"
	// 73 --> "KEYCODE_BACKSLASH"
	// 74 --> "KEYCODE_SEMICOLON"
	// 75 --> "KEYCODE_APOSTROPHE"
	// 76 --> "KEYCODE_SLASH"
	// 77 --> "KEYCODE_AT"
	// 78 --> "KEYCODE_NUM"
	// 79 --> "KEYCODE_HEADSETHOOK"
	// 80 --> "KEYCODE_FOCUS"
	// 81 --> "KEYCODE_PLUS"
	// 82 --> "KEYCODE_MENU"
	// 83 --> "KEYCODE_NOTIFICATION"
	// 84 --> "KEYCODE_SEARCH"
	// 85 --> "TAG_LAST_KEYCODE"

	public enum Func {
		Home, Favor, Setting, Lock_Screen, Apps, Clean_Memory, Back, Menu, Clean_Cache, Time, SnapShot, Battery_Display, Volume_Up, Volume_Down, Auto_Orientation, Light, Ringer, GPS;
	}

	public Funtion(Activity act) {
		activity = act;
	}

	private Activity activity;
	public Handler mHandler;
	
	
	public void process(ImageView view, MenuItem item, Activity act) {
		switch (Func.valueOf(item.getTitle())) {
		case Home:
			onHome();
			break;

		case Favor:
			onFavor();
			break;

		case Setting:
			onSetting();
			break;

		case Lock_Screen:
			onLockScreens();
			break;

		case Apps:
			onApps();
			break;

		case Clean_Memory:
//			onCleanMemory(service);
			onCleanMemory(act);
			break;

		case Back:
			onBack();
			break;

		case Menu:
			onMenu();
			break;

		case Clean_Cache:
//			onClean(service);
			onClean(act);
			break;

		case Time:
			onAlarm();
			break;

		case SnapShot:
//			onSnapshot(service);
			onSnapshot(act);
			break;

		case Battery_Display:
			onBattery();
			break;
		case Volume_Up:
			onVolumeUp();
			break;

		case Volume_Down:
			onVolumeDown();
			break;

		case Auto_Orientation:
			onAutoOrientation(view);
			break;

		case Light:
			onLight(view);
			break;

		case Ringer:
			onRinger(view);
			break;
		case GPS:
			
			break;

		default:
			break;
		}
//		service.disappearToucher();
	}

	
	private void onHome() {
		Utils.actionHome(activity);
	}

	private void onFavor() {
		Utils.onFavourite(activity);
	}

	private void onSetting() {
		Utils.actionSetting(activity);
	}

	private void onLockScreens() {
		Utils.actionLockScreen(activity);
	}

	private void onMenu() {
		Utils.execute_as_root(new String[] { "input keyevent 82" });
	}

	private void onBack() {
		Utils.execute_as_root(new String[] { "input keyevent 4" });
	}

//	private void onClean(ToucherService service) {
//		String result = Utils.cleanCache(activity);
//		service.toast(result);
//	}

	private void onClean(Activity act) {
		String result = Utils.cleanCache(activity);
//		service.toast(result);
		Toast.makeText(act, result, Toast.LENGTH_SHORT).show();
	}
	
//	private void onCleanMemory(final ToucherService service) {
//		final String[] str = Utils.cleanMemory(activity);
//		service.toast(str[0] + "\n" + str[1]);
//	}

	private void onCleanMemory(Activity act) {
		final String[] str = Utils.cleanMemory(activity);
//		service.toast(str[0] + "\n" + str[1]);
		Toast.makeText(act, str[0] + "\n" + str[1], Toast.LENGTH_SHORT).show();
	}


	private void onBattery() {
		Utils.onBattery(activity);
	}

	private void onVolumeUp() {
		Utils.onVolumeUp(activity);
	}

	private void onVolumeDown() {
		Utils.onVolumeDown(activity);
	}

	private void onAutoOrientation(ImageView view) {
		boolean isAutoOrientation = Utils.isAutoOrientation(activity);
		isAutoOrientation = !isAutoOrientation;
		Utils.setAutoOrientationEnabled(activity, isAutoOrientation);
		view.setImageResource(isAutoOrientation ? R.drawable.ic_auto_orientation_on
				: R.drawable.ic_auto_orientation_on_press);

	}

	private void onLight(ImageView view) {
		if (Utils.getFlash().equals(Camera.Parameters.FLASH_MODE_TORCH)) {
			Utils.flashLightOff(activity);
			view.setImageResource(R.drawable.ic_flash_off);
		} else {
			Utils.flashLightOn(activity);
			view.setImageResource(R.drawable.ic_flash_on);
		}
	}

	private void onRinger(ImageView view) {
		int ringer = Utils.geticonResRinger(activity)[1];
		if (ringer == 2) {
			view.setImageResource(Utils.seticonResRinger(activity, 0));
		} else {
			view.setImageResource(Utils.seticonResRinger(activity, ++ringer));
		}
	}

	String path = null;
	String file = null;
//	private void onSnapshot(final ToucherService service) {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				Looper.prepare();
//				try {
//				mHandler = new Handler() {
//					public void handleMessage(Message msg) {
//						service.toast("Snapshot with path :" + path + file);
//						path = null;
//						file = null;
//					}
//				};
//				Thread.sleep(200);
//				path = Utils.getPathQuickTouch() + "/";
//				file = System.currentTimeMillis() + ".png";
//				Utils.execute_as_root(new String[] { "screencap -p " + path
//						+ file });
//				mHandler.sendEmptyMessage(0);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			Utils.refreshSDcard(activity);
//
//			Looper.loop();
//				
//			}
//		}).start();
//
//	}

	private void onSnapshot(final Activity act) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				try {
				mHandler = new Handler() {
					public void handleMessage(Message msg) {
//						service.toast("Snapshot with path :" + path + file);
						Toast.makeText(act, "Snapshot with path :" + path + file, Toast.LENGTH_SHORT).show();
						path = null;
						file = null;
					}
				};
				Thread.sleep(200);
				path = Utils.getPathQuickTouch() + "/";
				file = System.currentTimeMillis() + ".png";
				Utils.execute_as_root(new String[] { "screencap -p " + path
						+ file });
				mHandler.sendEmptyMessage(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Utils.refreshSDcard(activity);

			Looper.loop();
				
			}
		}).start();

	}


	private void onApps() {
		Utils.onApps(activity);
	}

	private void onAlarm() {
		Utils.onAlarm(activity);
	}
}
