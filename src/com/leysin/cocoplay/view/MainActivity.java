package com.leysin.cocoplay.view;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.leysin.cocoplay.R;
import com.leysin.cocoplay.Interface.BaseActivity;
import com.leysin.cocoplay.controller.LogUtils;
import com.leysin.cocoplay.entity.Mp3Info;
import com.leysin.cocoplay.model.ITaskBinder;
import com.leysin.cocoplay.model.ITaskCallback;
import com.leysin.cocoplay.view.ListFragment.ClickListItemListener;
import com.leysin.cocoplay.view.MainMenuFragment.MenuFragmentItemClickListener;

public class MainActivity extends BaseActivity{

	private ITaskBinder musicService;
	public static final String TAG = MainActivity.class.getSimpleName();
	private boolean isPlay = false;
	private Button playButton;
	private Button listButton;
	private TextView songNameTextView;
	private TextView singerNameTextView;
	private ImageView toPlay;
	private ListFragment mListFragment;
	private MainMenuFragment mMainMenuFragment;
	private Context mContext = this;
	private MusicInfoUpdate infoUpdate;
	private boolean isFistLuncher =  true;
	private SharedPreferences sp;
	private String lastPlay = "lastPlay";
	private boolean isLastPlay = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initData() {
		bindService();
	}

	private void readState() {
			SharedPreferences sp = this.getSharedPreferences(lastPlay, Context.MODE_PRIVATE);
			String song = sp.getString("song", "default");
			String artist = sp.getString("artist", "default");
			long totle = sp.getLong("totle",0L);
			String url = sp.getString("url", "");
			long current = sp.getLong("current", 0L);
			if(!TextUtils.isEmpty(song)){
				songNameTextView.setText(song);
			}
			if(!TextUtils.isEmpty(artist)){
				singerNameTextView.setText(artist);
			}
	}

	private void bindService() {
		if(musicService == null){
			Intent intent = new Intent();
			intent.setAction("com.leysin.cocoplay.model.MusicPlayService");
			//intent.setPackage("com.leysin.cocoplay.model");
			//startService(intent);
			bindService(intent, conn,Service.BIND_AUTO_CREATE);
			infoUpdate = new MusicInfoUpdate();
			infoUpdate.init();
			LogUtils.showLogI(TAG, "initService");
		}		
	}
	@Override
	public void initView() {
		LogUtils.showLogI(TAG, "initView");
		playButton = findID(R.id.play);
		songNameTextView = findID(R.id.songName);
		singerNameTextView = findID(R.id.singerName);
		listButton = findID(R.id.list);
		toPlay = findID(R.id.to_play_activity);
		mListFragment = new ListFragment();
		mListFragment.setOnClickListItemListener(listFragmentListener);
		mMainMenuFragment = new MainMenuFragment();
		mMainMenuFragment.setMenuFragmentItemClickListener(mMenuFragmentItemClickListener);
		getSupportFragmentManager().beginTransaction().
		add(R.id.fragment_container,mMainMenuFragment).show(mMainMenuFragment).commit();
		readState();

	}

	private ClickListItemListener listFragmentListener = new ClickListItemListener() {
		@Override
		public void onClickListItem(int position) {
			LogUtils.showLogI(TAG, "onClickListItem = " + position );
			playMusic(position);
		}
		@Override
		public void onBackButtonClick() {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			if(!mMainMenuFragment.isAdded()){
				transaction.add(R.id.fragment_container,mMainMenuFragment);
			}
			transaction.hide(mListFragment);
			transaction.show(mMainMenuFragment).commit();
		}
	};
	private MenuFragmentItemClickListener mMenuFragmentItemClickListener = new MenuFragmentItemClickListener() {

		@Override
		public void OnItemClick(int position) {
			switch(position){
			case 0:
				showListFragment();
				break;
			}
		}

		private void showListFragment() {
			FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
			if(!mListFragment.isAdded()){
				transaction.add(R.id.fragment_container,mListFragment);
			}
			transaction.hide(mMainMenuFragment);
			transaction.show(mListFragment).commit();
		}
	};
	private ITaskCallback.Stub callback = new ITaskCallback.Stub() {
		@Override
		public void OnStateChanged(String songName, String artistName,
				String totleTime, String currentTime, boolean isPlayer)
						throws RemoteException {
			LogUtils.showLogI(TAG, "OnStateChanged");
		}
	};
	private ServiceConnection conn = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			LogUtils.showLogI(TAG, "onServiceConnected");
			musicService = ITaskBinder.Stub.asInterface(service);
			if(musicService != null){
				try {
					musicService.registerCallback(callback);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			musicService = null;
		}
	};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void initListener() {
		playButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switchCase();
			}

		});
		toPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity.this.startActivity(new Intent(MainActivity.this,PlayerActivity.class));
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			}
		});
	}

	private void switchCase() {
		int  position = MainActivity.this.getSharedPreferences(lastPlay, Context.MODE_PRIVATE).getInt("position", -1);
		if(!isPlay){
			if(musicService!=null){
				try {
					if(position != -1 && isLastPlay){
						musicService.play(position);
						isLastPlay = false;
						return;
					}
					musicService.start();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			isPlay = true;
		}else{
			if(musicService!=null){
				try {
					musicService.pause();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			isPlay = false;
		}
	}
	private Mp3Info info;
	private final class MusicInfoUpdate extends BroadcastReceiver{

		private void init(){
			IntentFilter filter = new IntentFilter();
			filter.addAction("com.leysin.cocoplay.mp3content_change");
			mContext.registerReceiver(this, filter);
		}
		@Override
		public void onReceive(Context context, Intent intent) {
			LogUtils.showLogI(TAG, "MusicInfoUpdate");
			info = (Mp3Info) intent.getSerializableExtra("info");
			if(info != null){
				songNameTextView.setText(info.getSongName());
				singerNameTextView.setText(info.getArtistName());
				isPlay =  info.isPlay();
				if(isPlay){
					playButton.setBackground(getResources().
							getDrawable(R.drawable.search_stop_btn));
				}else{
					playButton.setBackground(getResources().
							getDrawable(R.drawable.search_play_btn));
				}
			}
		}

	}
	protected void playMusic(int position) {
		try {
			if(musicService != null){
				musicService.play(position);
			}else{
				LogUtils.showLogI(TAG, "musicService == null");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onStop() {
		saveLastPlay();
		super.onStop();
	}
	@Override
	protected void onDestroy() {
		saveLastPlay();
		super.onDestroy();
	}

	private void saveLastPlay() {
		sp = this.getSharedPreferences(lastPlay, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("song", info.getSongName());
		editor.putString("artist", info.getArtistName());
		editor.putLong("totle", info.getTotleTime());
		editor.putString("url", info.getUrl());
		editor.putLong("current", info.getCurrentTime());
		editor.putInt("position", info.getPosition());
		editor.commit();
		LogUtils.showLogI(TAG, "sp save state");
		
	}

}
