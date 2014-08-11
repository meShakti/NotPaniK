package com.meshakti.notpanic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "Not_Panic_Contact_List";
	private static final String TABLE_NAME = "Contacts";
	private static final String TABLE_QUERY = "CREATE TABLE if not exists "
			+ TABLE_NAME
			+ " (id integer PRIMARY KEY AUTOINCREMENT,name varchar,telno varchar)";
	private static final int DB_VERSION = 7;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_QUERY);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (oldVersion < newVersion) {
			db.execSQL("drop table if exists " + TABLE_NAME);
			onCreate(db);
		}
	}

	public int insert(ContentValues values) {
		SQLiteDatabase db = getWritableDatabase();
		long ll = db.insert(TABLE_NAME, null, values);
		return (int) ll;
	}

	public Cursor getData(String query) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery(query, null);
		return c;
	}

	public int delete(String value) {
		SQLiteDatabase db = getWritableDatabase();
		long ll = db.delete(TABLE_NAME, "id=?", new String[] { value });
		return (int) ll;

	}

	public int update(ContentValues value, String id) {
		SQLiteDatabase db = getWritableDatabase();
		long ll = db.update(TABLE_NAME, value, "id=?", new String[] { id });
		return (int) ll;

	}

	public int clearAll() {
		SQLiteDatabase db = getWritableDatabase();
		long ll = db.delete(TABLE_NAME, null, null);
		return (int) ll;
	}

}
