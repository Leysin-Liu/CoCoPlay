package com.leysin.cocoplay.controller;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class MusicPlayer {
	
	private static final String TAG = MusicPlayer.class.getSimpleName();
	private static MusicPlayer mMusicPlayer;
	public static MediaPlayer mMediaPlayer;
	private static Context context;
	public int PLAY_MODLE = PlayModle.ORDER;
	private MusicPlayer(Context context) {
		this.context = context;
		
	}

	public void pause(){
		if(mMediaPlayer != null){
			mMediaPlayer.pause();
		}
	}
	
	public static MusicPlayer getInstanse(Context context){
		
		if(mMediaPlayer == null){
			mMediaPlayer = new MediaPlayer();
		}
		if(mMusicPlayer == null){
			synchronized (MusicPlayer.class) {
				if(mMusicPlayer == null){
					mMusicPlayer = new MusicPlayer(context);
				}
			}
		}
		return mMusicPlayer;
	}
	public void start(){
		if(mMediaPlayer != null){
				mMediaPlayer.start();
		}
	}
	public  void setPlayModle(int modle){
		LogUtils.showLogI(TAG, "Set Mode = " + modle);
		PLAY_MODLE = modle;
	}
	public  int getPlayModle(){
		return PLAY_MODLE;
	}
	public void play(String path){
		
		if(mMediaPlayer != null){
			try {
				if(mMediaPlayer.isPlaying()){
					//mMediaPlayer.stop();
				}
				mMediaPlayer.reset();
				mMediaPlayer.setDataSource(path);
				mMediaPlayer.prepare();
				mMediaPlayer.start();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void stop(){
		if(mMediaPlayer != null){
			mMediaPlayer.stop();
		}
	}
	public void seekTo(int positon){
		if(mMediaPlayer != null){
			mMediaPlayer.seekTo(positon);
		}
	}
	
	public void exit(){
		if(mMediaPlayer.isPlaying()){
			mMediaPlayer.stop();
		}
		mMediaPlayer.release();
		
	}
	
	public static class PlayModle{
		public static final int ORDER  = 0;
		public static final int SINGE = 1;
		public static final int RANDOM = 2;
	}
	

}
