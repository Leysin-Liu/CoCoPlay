package com.leysin.cocoplay.view;

import java.util.ArrayList;
import java.util.List;

import com.leysin.cocoplay.Interface.Init;
import com.leysin.cocoplay.controller.MusicUtils;
import com.leysin.cocoplay.entity.MusicEntity;

import android.app.Application;

public class CocoPlay extends Application{
	
	static MusicUtils musicUtils;
	public static ArrayList<MusicEntity> Musics;
	@Override
	public void onCreate() {
		super.onCreate();
	}

}
