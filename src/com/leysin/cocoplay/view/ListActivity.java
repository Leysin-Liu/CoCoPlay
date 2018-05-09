package com.leysin.cocoplay.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.leysin.cocoplay.R;
import com.leysin.cocoplay.Interface.BaseActivity;
import com.leysin.cocoplay.controller.MusicListViewAdapter;
import com.leysin.cocoplay.controller.MusicPlayer;
import com.leysin.cocoplay.controller.MusicUtils;
import com.leysin.cocoplay.entity.MusicEntity;

public class ListActivity extends BaseActivity {
	
	private MusicPlayer player;
	private ListView listView;
	private MusicUtils mMusicUtils;
	private ArrayList<MusicEntity> musicList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_list);
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initData() {
		//player = new MusicPlayer(this);
		mMusicUtils = MusicUtils.getInstance(this);
		musicList = (ArrayList<MusicEntity>) mMusicUtils.getMusicList();
	}

	@Override
	public void initView() {
		listView = findID(R.id.music_listView);
	}

	@Override
	public void initListener() {
		MusicListViewAdapter adapter = new MusicListViewAdapter(this, musicList);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ListActivity.this,PlayerActivity.class);
				intent.putExtra("position", position);
				ListActivity.this.startActivity(intent);
			}
		});
		//Toast.makeText(this, "list size +" + musicList.size(), 1).show();
	}

}
