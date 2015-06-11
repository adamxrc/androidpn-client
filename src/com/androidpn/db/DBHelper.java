package com.androidpn.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "androidpn.db";
	
	private static final int DB_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table if not exists users" + 
			"(_id integer primary key autoincrement, userid varchar, username varchar, " + 
			"userstatus varchar, createdate varchar, updatedate varchar)");
		db.execSQL("create table if not exists messages" +
			"(_id integer primary key autoincrement, msg_id varchar, msg_type varchar, from_user varchar, " + 
			"to_user varchar, sent_date varchar, subject text, body text, is_come_msg integer, " + 
			"is_sent integer default 0, is_read integer default 0)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		onCreate(db);
	}

}
