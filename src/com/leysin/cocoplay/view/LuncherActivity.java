package com.leysin.cocoplay.view;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import com.leysin.cocoplay.R;
import com.leysin.cocoplay.controller.LogUtils;
import com.leysin.cocoplay.model.MusicDataHelp;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class LuncherActivity extends Activity {
	
	public static final String TAG = "LuncherActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_luncher);
		//initSystemBar();
		//initDatabase();
		init();
	}

	private void initDatabase() {
		MusicDataHelp dataHelp = new MusicDataHelp(this);
		SQLiteDatabase database = dataHelp.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("name", "真的爱你");
		values.put("artist", "黄家驹");
		values.put("url", "wwwwwwww");
		LogUtils.showLogI(TAG, "insert data");
		database.insert(MusicDataHelp.TABLE_NAME, null, values);
	}

	public void init() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent();
				intent.setClass(LuncherActivity.this, MainActivity.class);
				LuncherActivity.this.startActivity(intent);
				LuncherActivity.this.finish();
			}
		}, 3000);
	}
	private void initSystemBar() {
		// 4.4及以上版本开启
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}

		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setNavigationBarTintEnabled(true);
		// 自定义颜色
		//tintManager.setTintColor(Color.parseColor("#24b7a4"));
	}
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	

}
