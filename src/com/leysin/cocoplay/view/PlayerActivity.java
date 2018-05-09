package com.leysin.cocoplay.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.leysin.cocoplay.R;
import com.leysin.cocoplay.controller.LogUtils;
import com.leysin.cocoplay.controller.MusicListViewAdapter;
import com.leysin.cocoplay.controller.MusicPlayer;
import com.leysin.cocoplay.controller.MusicPlayer.PlayModle;
import com.leysin.cocoplay.controller.MusicUtils;
import com.leysin.cocoplay.entity.Mp3Info;
import com.leysin.cocoplay.entity.MusicEntity;
import com.leysin.cocoplay.model.ITaskBinder;
import com.leysin.cocoplay.model.ITaskCallback;

public class PlayerActivity extends Activity implements OnClickListener{

	public static final String TAG = PlayerActivity.class.getSimpleName();
	private MusicServiceConnection conn;
	private boolean isPlay = false;
	private ITaskBinder musicService;
	private ImageView start,next,pre;
	private TextView title, totle ,current;
	private Context mContext = this;
	private InfoUpdate infoUpdata;
	private SeekBar mSeekBar;
	private ViewPager viewPager;
	private ImageView playMode ;
	private ImageView backHome;
	private ImageView musicList;
	private CircleImageView imageView;
	private RotateAnimation animation;
	private boolean animationIsRun = false;
	private int PLAY_MODE = MusicPlayer.PlayModle.ORDER;
	private boolean isFirstPlay = true;
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat format = new SimpleDateFormat("mm:ss");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//全屏显示
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_player);
		super.onCreate(savedInstanceState);
		LogUtils.showLogI(TAG,"Thread id = " + Thread.currentThread().getId());
		init();
	}
	private void init() {
		initService();
		initView();
		initAnimation();
		initListener();
	}
	private void initListener() {
		mSeekBar.setOnSeekBarChangeListener(seekbarListener);
		playMode.setOnClickListener(playmodeListener);

	}

	private OnClickListener playmodeListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch(PLAY_MODE){
			case PlayModle.ORDER:
				if(musicService!=null){
					try {
						musicService.setPlayModle(PlayModle.RANDOM);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case PlayModle.RANDOM:
				if(musicService!=null){
					try {
						musicService.setPlayModle(PlayModle.SINGE);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case PlayModle.SINGE:
				if(musicService!=null){
					try {
						musicService.setPlayModle(PlayModle.ORDER);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			}
		}
	};
	private OnSeekBarChangeListener seekbarListener = new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			if(musicService != null){
				try {
					musicService.seekTo(seekBar.getProgress());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub

		}
	};
	private void initService() {
		conn = new MusicServiceConnection();
		Intent intent = new Intent();
		intent.setAction("com.leysin.cocoplay.model.MusicPlayService");
		//intent.setPackage("com.leysin.cocoplay.model");
		//startService(intent);
		bindService(intent, conn,Service.BIND_AUTO_CREATE);
		LogUtils.showLogI(TAG, "initService");
	}
	private ObjectAnimator mObjectAnimator ;
	private MyAnimatorUpdateListener animationListener;
	private void initView() {
		playMode = (ImageView) findViewById(R.id.play_modle);
		imageView = (CircleImageView) findViewById(R.id.imgeView);
		//imageView.setAnimation(animation);
		
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		start = (ImageView) findViewById(R.id.play_activity_bt);
		pre = (ImageView) findViewById(R.id.pre_activity_bt);
		next = (ImageView) findViewById(R.id.next_activity_bt);
		title = (TextView) findViewById(R.id.play_title);
		totle = (TextView) findViewById(R.id.player_total);
		current = (TextView) findViewById(R.id.player_duration);
		mSeekBar = (SeekBar) findViewById(R.id.seekbar);
		musicList = (ImageView) findViewById(R.id.music_list);
		backHome = (ImageView) findViewById(R.id.back_home);
		infoUpdata = new InfoUpdate();
		infoUpdata.init();
		LogUtils.showLogI(TAG, "initView");
		start.setOnClickListener(this);
		pre.setOnClickListener(this);
		next.setOnClickListener(this);
		backHome.setOnClickListener(this);
		musicList.setOnClickListener(this);
		/*	if(musicService != null){
			try {
				int position = getIntent().getIntExtra("position", 1);
				LogUtils.showLogI(TAG, "position :"+ position);
				musicService.play(position);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}*/
	}

	private void initAnimation() {
		/*animation = new RotateAnimation(0f,360f,Animation.RELATIVE_TO_SELF, 
				0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		animation.setDuration(20000);
		animation.setRepeatCount(-1);//执行次数
		animation.setFillAfter(false);//动画执行完后是否停留在执行完的状态 
		animation.setStartOffset(0);//执行前的等待时间 
		animation.setInterpolator(new LinearInterpolator());*///线性插值器
		mObjectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f,360f);
		mObjectAnimator.setDuration(20000);
		mObjectAnimator.setInterpolator(new LinearInterpolator());
		mObjectAnimator.setRepeatMode(Animation.RESTART);
		mObjectAnimator.setRepeatCount(-1);
		mObjectAnimator.start();
		animationListener = new MyAnimatorUpdateListener(mObjectAnimator);
	}
	class MusicServiceConnection implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			musicService = ITaskBinder.Stub.asInterface(service);
			LogUtils.showLogI(TAG, "onServiceConnected");
			if(musicService != null){
				try {
					LogUtils.showLogI(TAG, "registerCallback");
					musicService.registerCallback(task);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
			musicService = null;
		}
	}
	private String getAlbumArt(int album_id) {  
        String mUriAlbums = "content://media/external/audio/albums";  
        String[] projection = new String[] { "album_art" };  
        Cursor cur = this.getContentResolver().query(  
                Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),  
                projection, null, null, null);  
        String album_art = null;  
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {  
            cur.moveToNext();  
            album_art = cur.getString(0);  
        }  
        cur.close();  
        cur = null;  
        return album_art;  
    }  
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
			overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//this.finish();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	
	//remote callback implement
	private ITaskCallback.Stub task = new ITaskCallback.Stub() {

		@Override
		public void OnStateChanged(String songName, String artistName,
				String totleTime, String currentTime, boolean isPlayer) 
						 {
			LogUtils.showLogI(TAG, "OnStateChanged");
			title.setText(songName);
			totle.setText(totleTime);
			current.setText(currentTime);
		}
	};
	@Override
	public void onClick(View v) {

		switch(v.getId()){

		case R.id.play_modle:

			break;
		case R.id.play_activity_bt:
			if(isPlay){
				pause();
			}else{
				start();
			}
			break;

		case R.id.next_activity_bt:
			try {
				musicService.next();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case R.id.pre_activity_bt:
			try {
				musicService.pre();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case R.id.back_home:
			Intent i = new Intent(PlayerActivity.this,MainActivity.class);
			PlayerActivity.this.startActivity(i);
			overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			break;
			
		case R.id.music_list:
			
			break;

		}
	}
	private void shwoPopupWindow(View view) {
		
	}
	private final class InfoUpdate extends BroadcastReceiver{

		private void init(){
			IntentFilter filter = new IntentFilter();
			filter.addAction("com.leysin.cocoplay.mp3content_change");
			mContext.registerReceiver(this, filter);
		}
		@Override
		public void onReceive(Context context, Intent intent) {
			LogUtils.showLogI(TAG, "MusicInfoUpdate");
			Mp3Info info = (Mp3Info) intent.getSerializableExtra("info");
			if(info!=null){
				title.setText("--" + info.getSongName().trim()+"--");
				String totleTime = format.format(new Date(info.getTotleTime()));
				String currentTime = format.format(new Date(info.getCurrentTime()));
				current.setText(currentTime);
				totle.setText(totleTime);
				mSeekBar.setMax((int)info.getTotleTime());
				mSeekBar.setProgress((int)info.getCurrentTime());
				isPlay = info.isPlay();
				PLAY_MODE = info.getPlayModle();
				switchPlayButtonImage();
				switchModeButtonImage();
			//	setImage(info.getAlbumId());
			}
		}
		@SuppressWarnings("deprecation")
		private void setImage(int id) {
				String imagePath = getAlbumArt(id);
				Bitmap bm = null;   
                bm = BitmapFactory.decodeFile(imagePath);
                if(bm != null){
                	imageView.setImageDrawable(new BitmapDrawable(bm));;
                }else{
                	imageView.setImageDrawable(getResources()
                			.getDrawable(R.drawable.default_album_gyl));
                }
		}
		@SuppressWarnings("deprecation")
		private void switchModeButtonImage() {
			switch(PLAY_MODE){
			case MusicPlayer.PlayModle.ORDER:
				playMode.setBackground(
						PlayerActivity.this.getResources()
						.getDrawable(R.drawable.playmode_repeate_all));
				break;
			case MusicPlayer.PlayModle.RANDOM:
				playMode.setBackground(
						PlayerActivity.this.getResources()
						.getDrawable(R.drawable.playmode_repeate_random));
				break;
			case MusicPlayer.PlayModle.SINGE:
				playMode.setBackground(
						PlayerActivity.this.getResources()
						.getDrawable(R.drawable.playmode_repeate_single));
				break;
			}
		}
		@SuppressWarnings("deprecation")
		private void switchPlayButtonImage() {
			/*if(isFirstPlay){
				mObjectAnimator.start();
				isFirstPlay = false;
			}*/
			if(isPlay){
				start.setBackground(getResources()
						.getDrawable(R.drawable.landscape_player_btn_pause_normal));
			}else{
				start.setBackground(getResources()
						.getDrawable(R.drawable.landscape_player_btn_play_normal));
			}
		}
	}
	private void start() {
		try {
			if(musicService != null){
				musicService.start();
				isPlay = true;
				animationListener.play();
				animationListener.onAnimationUpdate(mObjectAnimator);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void pause() {
		try {
			if(musicService != null){
				musicService.pause();
				isPlay = false;
				animationListener.pause();
				animationListener.onAnimationUpdate(mObjectAnimator);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.unbindService(conn);
		try {
			musicService.unregisterCallback(task);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.unregisterReceiver(infoUpdata);
	}

}
