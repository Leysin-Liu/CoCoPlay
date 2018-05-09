package com.leysin.cocoplay.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.leysin.cocoplay.R;
import com.leysin.cocoplay.controller.LogUtils;
import com.leysin.cocoplay.controller.MusicPlayer;
import com.leysin.cocoplay.controller.MusicPlayer.PlayModle;
import com.leysin.cocoplay.controller.MusicUtils;
import com.leysin.cocoplay.entity.Mp3Info;
import com.leysin.cocoplay.entity.MusicEntity;
import com.leysin.cocoplay.view.PlayerActivity;

public class MusicPlayService extends Service{

	private Context mContext = this;
	public static final String  TAG = MusicPlayService.class.getSimpleName();
	private Timer timer;
	private int musicPosition = 0;
	private MusicPlayer player;
	private TaskBinder binder = new TaskBinder();
	private ArrayList<MusicEntity> musicList;
	private MusicThread mMusicThread ;
	private boolean isSleep = false;
	private MusicPlayer.PlayModle MODLE = new MusicPlayer.PlayModle();
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat format = new SimpleDateFormat("mm:ss");

	@Override
	public void onStart(Intent intent, int startId) {
		LogUtils.showLogI(TAG, "onStart");
		//init();
		super.onStart(intent, startId);
	}
	@Override
	public IBinder onBind(Intent intent) {
		LogUtils.showLogI(TAG, "onBind");
		int position = intent.getIntExtra("position", 0);
		init();
		return binder;
	}
	@Override
	public void onDestroy() {
		//callbackList.finishBroadcast();
		if(mMusicThread!=null){
			mMusicThread.interrupt();
			mMusicThread = null;
		}
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		LogUtils.showLogI(TAG, "onUnbind");
		return super.onUnbind(intent);
	}
	@Override
	public void onCreate() {
		LogUtils.showLogI(TAG, "onCreate");
		super.onCreate();
	}
	private void init() {
		if(player == null){
			player = MusicPlayer.getInstanse(mContext);
			initListener();
			musicList = new ArrayList<MusicEntity>();
			musicList = (ArrayList<MusicEntity>) MusicUtils.getInstance(this).getMusicList();
		}
	}

	private void initListener() {
		player.mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				try {
					binder.next();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	class MusicThread extends Thread{
		@Override
		public void run() {

			while(!interrupted()){
				
				Intent intent = new Intent("com.leysin.cocoplay.mp3content_change");
				Mp3Info info = new Mp3Info();
				info.songName = musicList.get(musicPosition).getTitle();
				info.artistName = musicList.get(musicPosition).getArtist();
				info.currentTime = player.mMediaPlayer.getCurrentPosition();
				info.totleTime = musicList.get(musicPosition).getDuration();
				info.isPlay = player.mMediaPlayer.isPlaying();
				info.playModle = player.getPlayModle();
				info.setAlbumId(musicList.get(musicPosition).getAlbumId());
				info.setUrl(musicList.get(musicPosition).getUrl());
				info.setPosition(musicPosition);
				intent.putExtra("info", info);
				/*String songName = musicList.get(musicPosition).getTitle();
					String artist = musicList.get(musicPosition).getArtist();
					long totleTime = musicList.get(musicPosition).getDuration();
					long currentTime = player.mMediaPlayer.getCurrentPosition();
					//boolean isPlayer = player.mMediaPlayer.isPlaying();
					String totle = format.format(new Date(totleTime));
					String current = format.format(new Date(currentTime));
					Intent intent = new Intent("com.leysin.cocoplay");
					intent.putExtra("songName", songName);
					intent.putExtra("artist", artist);
					intent.putExtra("totle", totle);
					intent.putExtra("current", current);*/
				mContext.sendBroadcast(intent);
				/*try {	
						if(callbackList != null){
							ITaskCallback call =  callbackList.getBroadcastItem(0);
						}
						if(call != null){
							call.OnStateChanged("test1", "test2", "test3", "test4", false);
						}
					} catch (RemoteException e1) {
						e1.printStackTrace();
						LogUtils.showLogE(TAG, "CallBack ERR");
					}*/
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private final RemoteCallbackList<ITaskCallback> callbackList = new RemoteCallbackList<ITaskCallback>();
	public ITaskCallback call ;
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "MusicPlayService onStartCommand");
		init();
		return super.onStartCommand(intent, flags, startId);
	}

	class TaskBinder extends ITaskBinder.Stub{

		@Override
		public boolean isCallbackRunning() throws RemoteException {

			return true;
		}

		@Override
		public void stopRunningTask() throws RemoteException {

		}

		@Override
		public void registerCallback(ITaskCallback cb) throws RemoteException {
			if(cb != null){
				LogUtils.showLogI(TAG, "registerCallback");
				callbackList.register(cb);
			}
		}

		@Override
		public void unregisterCallback(ITaskCallback cb) throws RemoteException {
			if(cb != null){
				LogUtils.showLogI(TAG, "unregisterCallback");
				callbackList.unregister(cb);
			}
		}

		@Override
		public void start() throws RemoteException {
			player.start();
		}

		@Override
		public void stop() throws RemoteException {
			player.stop();
		}

		@Override
		public void pause() throws RemoteException {
			player.pause();
		}

		@Override
		public void pre() throws RemoteException {
			playPreMusic();
		}

		private void playPreMusic() throws RemoteException {
			switch(player.PLAY_MODLE){
			case PlayModle.ORDER:
				musicPosition = (musicPosition == 0 ? musicList.size()-1:musicPosition -1);
				play(musicPosition);
				break;
			case PlayModle.SINGE:
				play(musicPosition);
				break;
			case PlayModle.RANDOM:
				Random random = new Random();
				musicPosition = random.nextInt(musicList.size());
				play(musicPosition);
				break;
			}
		}

		@Override
		public void next() throws RemoteException {
			playNextMusic();
		}
		private void playNextMusic() throws RemoteException {
			switch(player.PLAY_MODLE){
			case PlayModle.ORDER:
				musicPosition = musicPosition == (musicList.size()-1) ? 0:musicPosition + 1;
				play(musicPosition);
				break;
			case PlayModle.SINGE:
				play(musicPosition);
				break;
			case PlayModle.RANDOM:
				Random random = new Random();
				musicPosition = random.nextInt(musicList.size());
				play(musicPosition);
				break;
			}
		}

		@Override
		public void seekTo(int position) throws RemoteException {
			player.seekTo(position);
			player.start();
		}

		@Override
		public void play(int position) throws RemoteException {
			
			initNotification();
			if(player != null){
				player.play(musicList.get(position).getUrl());
				musicPosition = position;
			}
			if(mMusicThread == null){
				mMusicThread = new MusicThread();
				mMusicThread.start();
				LogUtils.showLogI(TAG, "Notification run"); 
			}
		}

		@Override
		public void setPlayModle(int modle) throws RemoteException {
			if(player != null){
				player.setPlayModle(modle);
			} 
		}

	}
	private void initNotification() {
		/**
		 * 发送一个点击跳转到MainActivity的消息
		 */
		NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		//获取PendingIntent
		Intent mainIntent = new Intent(mContext, PlayerActivity.class);
		PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		//创建 Notification.Builder 对象
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.default_album_gyl)
		//点击通知后自动清除
		// .setAutoCancel(true)
		.setContentTitle(musicList.get(musicPosition).getTitle())
		.setContentText(musicList.get(musicPosition).getArtist())
		.setContentIntent(mainPendingIntent);
		//发送通知
		mNotifyManager.notify(3, builder.build());
	}
}
