package com.leysin.cocoplay.model;
import com.leysin.cocoplay.controller.LogUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MusicDataHelp extends SQLiteOpenHelper{
	
	private static final String TAG = "MusicDataHelp";
	private static final String DATABASE_NAME = "cocoplay.db";
	private static final int DATABASE_version = 1;
	public static final String TABLE_NAME = "like_list";
	public MusicDataHelp(Context context) {
		//创建数据库
		super(context, DATABASE_NAME, null, DATABASE_version);
		LogUtils.showLogI(TAG, "database onCreate");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建表
		String liketable = "CREATE TABLE "+ TABLE_NAME + "(_id INTEGER PRIMARY KEY,"
				+ "name varchar(20),"
				+ "artist varchar(20),"
				+ "url varchar(40));";
		db.execSQL(liketable);
		LogUtils.showLogI(TAG, "database onCreate");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
