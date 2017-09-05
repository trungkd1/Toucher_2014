package com.efse.toucher.data;

import java.util.ArrayList;
import java.util.Hashtable;

import com.efse.toucher.utils.AppEntry;
import com.efse.toucher.utils.MenuItem;
import com.efse.toucher.utils.PointItem;
import com.efse.toucher.utils.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

public class SQLiteAdapter {

	/** The Constant VERSION_DB. */
	private static final int VERSION_DB = 1;

	/** The Constant MYDATABASE_NAME. */
	public static final String MYDATABASE_NAME = "EPSE";

	/** The Constant TABLE_HIDDEN_FILE. */
	public static final String TABLE_TOUCHER = "TABLE_TOUCHER";

	public static final String KEY_FILE_ID = "_id";

	public static final String KEY_FILE_NAME = "_name";

	public static final String KEY_FILE_TYPE = "_type";

	public static final String KEY_FILE_ICONRES = "_iconRes";

	public static final String KEY_FILE_POS = "_pos";
	
	public static final String TABLE_POINTER = "TABLE_POINTER";

	public static final String KEY_BITMAP = "_bitmap";
	
	public static final String TABLE_APP = "TABLE_APP";
	
	public static final String KEY_PACKAGE_NAME = "_packageName";
	
	public static final String KEY_PACKAGE_ACTIVITY = "_packageActivity";
	
	/** The Constant SCRIPT_CREATE_HIDDEN_FILE. */
	private static final String SCRIPT_CREATE_TABLE_TOUCHER = "create table if not exists "
			+ TABLE_TOUCHER
			+ " ("
			+ KEY_FILE_ID
			+ " integer primary key autoincrement, "
			+ KEY_FILE_NAME
			+ " text, "
			+ KEY_FILE_TYPE
			+ " integer, "
			+ KEY_FILE_ICONRES
			+ " integer, "
			+ KEY_FILE_POS +" integer )";

	private static final String SCRIPT_CREATE_TABLE_POINTER = "create table if not exists "
			+ TABLE_POINTER 
			+ " ("
			+ KEY_FILE_ID
			+ " integer primary key autoincrement, "
			+ KEY_BITMAP +" BLOB )";
	
	private static final String SCRIPT_CREATE_TABLE_APPLICATION = "create table if not exists "
			+ TABLE_APP 
			+ " ("
			+ KEY_FILE_ID
			+ " integer primary key autoincrement, "
			+ KEY_PACKAGE_NAME
			+ " text, "
			+ KEY_PACKAGE_ACTIVITY +" text )";
	
	
	private static final String[] columnToucher = { KEY_FILE_ID,
		KEY_FILE_NAME, KEY_FILE_TYPE, KEY_FILE_ICONRES,
		KEY_FILE_POS};
	
	private static final String[] columnPointer = { KEY_FILE_ID,
		KEY_BITMAP};
	
	private static final String[] columnAppEntry = { KEY_FILE_ID,
		KEY_PACKAGE_NAME,KEY_PACKAGE_ACTIVITY};
	
	private Context context;

	private SQLiteHelper sqLiteHelper;

	private SQLiteDatabase sqLiteDatabase;

	public SQLiteAdapter(Context context) {
		this.context = context;
		open();
	}

	private SQLiteAdapter open() {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null,
				VERSION_DB);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}
	
	
	public boolean isReady() {
		if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
			return true;
		} else {
			return false;
		}
	}
	
	//===========================pointer============================

	public long addPointer(PointItem item){
		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_BITMAP,Utils.bitmap2Byte(item.getIconBitmap()) );
		return sqLiteDatabase.insert(TABLE_POINTER, null, contentValues);
	}
	
	public int deletePointer(int id){
		return sqLiteDatabase.delete(TABLE_POINTER,
				KEY_FILE_ID + " = " + id, null);
	}
	
	public ArrayList<PointItem> getAllPointer(){
		ArrayList<PointItem> list = new ArrayList<PointItem>();
		Cursor cursor = sqLiteDatabase.query(TABLE_POINTER,
				columnPointer, null, null, null, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					PointItem obj = new PointItem();
					obj.setId(cursor.getInt(cursor.getColumnIndex(KEY_FILE_ID)));
					Bitmap b1 = Utils.byte2Bitmap(cursor.getBlob(cursor.getColumnIndex(KEY_BITMAP)));
					obj.setIconBitmap(b1);
					obj.setPrivateIcon(true);
					list.add(obj);
				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		return list;
	}
	
	public Bitmap getBitmapPointer(int id){
		Bitmap bmp = null;
		Cursor cursor = sqLiteDatabase.query(TABLE_POINTER,
				columnPointer, KEY_FILE_ID+ " = ?", new String[]{String.valueOf(id)}, null, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					PointItem obj = new PointItem();
					obj.setId(cursor.getInt(cursor.getColumnIndex(KEY_FILE_ID)));
					bmp = Utils.byte2Bitmap(cursor.getBlob(cursor.getColumnIndex(KEY_BITMAP)));
				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		return bmp;
	}
	
	//===========================app entry============================
	
	public long addEntryApp(AppEntry entry){
		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_PACKAGE_NAME, entry.getPackageName());
		contentValues.put(KEY_PACKAGE_ACTIVITY, entry.getPackageActivity());
		return sqLiteDatabase.insert(TABLE_APP, null, contentValues);
	}
	
	public Hashtable<String, String> getPackageNames(){
		Hashtable<String, String> list = new Hashtable<String, String>();
		Cursor cursor = sqLiteDatabase.query(TABLE_APP,
				columnAppEntry, null, null, null, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					String packageName = cursor.getString(cursor.getColumnIndex(KEY_PACKAGE_NAME));
					String packageActivity = cursor.getString(cursor.getColumnIndex(KEY_PACKAGE_ACTIVITY));
					
					list.put(packageActivity, packageName);
				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		return list;
	}
	
	public ArrayList<AppEntry> getAllAppEntries(){
		ArrayList<AppEntry> objs = new ArrayList<AppEntry>();
		Cursor cursor = sqLiteDatabase.query(TABLE_APP,
				columnAppEntry, null, null, null, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					int id = cursor.getInt(cursor.getColumnIndex(KEY_FILE_ID));
					String packageName = cursor.getString(cursor.getColumnIndex(KEY_PACKAGE_NAME));
					String packageActivity = cursor.getString(cursor.getColumnIndex(KEY_PACKAGE_ACTIVITY));
					AppEntry obj = new AppEntry();
					obj.setId(id);
					obj.setPackageName(packageName);
					obj.setPackageActivity(packageActivity);
					objs.add(obj);
				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		return objs;
	}
	
	public int deleteAppEntry(int id){
		return sqLiteDatabase.delete(TABLE_APP,
				KEY_FILE_ID + " = " + id, null);
	}
	
	
	public int clearEntryApps(){
		return sqLiteDatabase.delete(TABLE_APP,
				null, null);
	}
	
	
	//===========================menu============================
	
	
	public long addMenu(MenuItem item){
		if(item.getIconRes() == 0){
			deleteMenu(item.getId());
			return 0;
		}else{
			if(item.getId() > 0){
				deleteMenu(item.getId());
			}
			return insertMenu(item);
		}
	}
	
	public long insertMenu(MenuItem item){
		return sqLiteDatabase.insert(TABLE_TOUCHER, null, getFileValues(item));
	}
	
	private int deleteMenu(int id){
		return sqLiteDatabase.delete(TABLE_TOUCHER,
				KEY_FILE_ID + " = " + id, null);
	}
	
	public ArrayList<MenuItem> getAllMenu(){
		ArrayList<MenuItem> list = new ArrayList<MenuItem>();
		Cursor cursor = sqLiteDatabase.query(TABLE_TOUCHER,
				columnToucher, null, null, null, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					MenuItem obj = getFileItem(cursor);
					list.add(obj);
				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		return list;
	}
	
	public int updateMenu(MenuItem item){
		return sqLiteDatabase.update(TABLE_TOUCHER, getFileValues(item), KEY_FILE_ID +" = ?", new String[]{String.valueOf(item.getId())});
	}
	
	
	private MenuItem getFileItem(Cursor cursor) {
		MenuItem obj = new MenuItem();
		obj.setId(cursor.getInt(cursor.getColumnIndex(KEY_FILE_ID)));
		obj.setTitle(cursor.getString(cursor.getColumnIndex(KEY_FILE_NAME)));
		obj.setIconRes(cursor.getInt(cursor.getColumnIndex(KEY_FILE_ICONRES)));
		obj.setType(cursor.getInt(cursor.getColumnIndex(KEY_FILE_TYPE)));
		obj.setPos(cursor.getInt(cursor.getColumnIndex(KEY_FILE_POS)));
		return obj;
	}
	
	private ContentValues getFileValues(MenuItem obj) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_FILE_NAME, obj.getTitle());
		contentValues.put(KEY_FILE_ICONRES, obj.getIconRes());
		contentValues.put(KEY_FILE_TYPE, obj.getType());
		contentValues.put(KEY_FILE_POS, obj.getPos());
		return contentValues;
	}

	private class SQLiteHelper extends SQLiteOpenHelper {

		public SQLiteHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SCRIPT_CREATE_TABLE_TOUCHER);
			db.execSQL(SCRIPT_CREATE_TABLE_APPLICATION);
			db.execSQL(SCRIPT_CREATE_TABLE_POINTER);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}
}
