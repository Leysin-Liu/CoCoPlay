package com.leysin.cocoplay.view;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.leysin.cocoplay.R;
import com.leysin.cocoplay.Interface.BaseFragment;
import com.leysin.cocoplay.controller.LogUtils;
import com.leysin.cocoplay.controller.MusicListViewAdapter;
import com.leysin.cocoplay.controller.MusicUtils;
import com.leysin.cocoplay.entity.MusicEntity;

public class ListFragment extends BaseFragment{
	
	private static final String TAG = ListFragment.class.getSimpleName();
	private ClickListItemListener listener;
	private ArrayList<MusicEntity> musicList ;
	private ListView mListView;
	private View view;
	private ImageView back;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(getActivity(), R.layout.activity_list, null);
		initView();
		initListener();
		return view;
	}
	

	private void initView(){
		LogUtils.showLogI(TAG, "initView");
		musicList = (ArrayList<MusicEntity>) 
				MusicUtils.getInstance(getActivity()).getMusicList();
		/*musicList = (ArrayList<MusicEntity>) CocoPlay.getMusicList();*/
		MusicListViewAdapter adapter =  new MusicListViewAdapter(getActivity(),musicList);
		mListView = (ListView) view.findViewById(R.id.music_listView);
		back = (ImageView) view.findViewById(R.id.listfragment_back);
		back.setOnClickListener(backListener);
		mListView.setAdapter(adapter);

	}
	
	public void setOnClickListItemListener(ClickListItemListener listener){
		this.listener = listener;
	}
	
	interface ClickListItemListener{
		
		void onClickListItem(int position);
		
		void onBackButtonClick();
	}
	private void initListener() {
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LogUtils.showLogI(TAG, "onItemClick" + position);
				LogUtils.showLogI(TAG, "listener = null?" + String.valueOf(listener == null));
				if(listener != null){
					listener.onClickListItem(position);
				}
			}
		});
	}
	private OnClickListener backListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(listener != null){
				listener.onBackButtonClick();
			}
		}
	};
}
