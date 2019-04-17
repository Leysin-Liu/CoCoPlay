package com.leysin.cocoplay.controller;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.leysin.cocoplay.entity.MusicEntity;

public class MusicUtils {

	public static MusicUtils instance;
	public static Context context;

	private MusicUtils() {
	}

	//double check lock
	public static MusicUtils getInstance(Context context){
		MusicUtils.context = context;
		if(instance == null){
			synchronized (MusicUtils.class) {
				if(instance == null){
					instance = new MusicUtils();
				}
			}
		}
		return instance;
	}

	public List<MusicEntity> getMusicList(){
		Cursor cursor = context.getContentResolver().query(  
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,  
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);  
		List<MusicEntity> MusicInfos = new ArrayList<MusicEntity>();  
		for (int i = 0; i < cursor.getCount(); i++) {  
			MusicEntity MusicInfo = new MusicEntity();  
			cursor.moveToNext();  
			long id = cursor.getLong(cursor  
					.getColumnIndex(MediaStore.Audio.Media._ID));   //音乐id  
			String title = cursor.getString((cursor   
					.getColumnIndex(MediaStore.Audio.Media.TITLE)));//音乐标题  
			String artist = cursor.getString(cursor  
					.getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家  
			long duration = cursor.getLong(cursor  
					.getColumnIndex(MediaStore.Audio.Media.DURATION));//时长  
			long size = cursor.getLong(cursor  
					.getColumnIndex(MediaStore.Audio.Media.SIZE));  //文件大小  
			String url = cursor.getString(cursor  
					.getColumnIndex(MediaStore.Audio.Media.DATA));   //文件路径
			int albumId = cursor.getInt(cursor
					.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)); //封面
			int isMusic = cursor.getInt(cursor  
					.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否为音乐  
			if (isMusic != 0) {     //只把音乐添加到集合当中 
				MusicInfo.setAlbumId(albumId);
				MusicInfo.setId(id);  
				MusicInfo.setTitle(title);  
				MusicInfo.setArtist(artist);  
				MusicInfo.setDuration(duration);  
				MusicInfo.setSize(size);  
				MusicInfo.setUrl(url);  
				MusicInfos.add(MusicInfo);  
			}  
		}	
		cursor.close();
		return  MusicInfos;
	}
}
