package com.leysin.cocoplay.view;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.leysin.api.ULog;
import com.leysin.api.UToast;
import com.leysin.cocoplay.R;
import com.leysin.cocoplay.Interface.BaseFragment;
import com.leysin.cocoplay.controller.GridViewAdapter;
import com.leysin.cocoplay.controller.ListViewAdapter;
import com.leysin.cocoplay.controller.MusicUtils;
import com.leysin.cocoplay.entity.GridViewItem;
import com.leysin.cocoplay.entity.ListViewItem;

public class MainMenuFragment extends BaseFragment{

	private GridViewAdapter mGridViewAdapter;
	private ListViewAdapter mListViewAdapter;
	private MyListView mListView;
	private MyGridView mGridView;
	private View mainView;
	private	View listViewHead;
	private MenuFragmentItemClickListener listener;
	private ArrayList<GridViewItem> gridViewList = new ArrayList<GridViewItem>();
	private ArrayList<ListViewItem> listViewList = new ArrayList<ListViewItem>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainView = View.inflate(getActivity(), R.layout.fragment_main_menu, null);
		initData();
		initView();
		initListener();
		return mainView;
	}

	private void initData() {
		initGridViewData();
		initListViewData();
	}

	private void initListViewData() {
		
		ListViewItem localItem = new ListViewItem();
		localItem.picId = R.drawable.lyric_poster_default_bg1_small;
		localItem.name = "个性电台";
		localItem.content = "来听跟The Show 一样好听的音乐吧";
		listViewList.add(localItem);

		ListViewItem downloadItem = new ListViewItem();
		downloadItem.picId = R.drawable.lyric_poster_default_bg2_small;
		downloadItem.name = "Nike跑步电台";
		downloadItem.content = "就是这个feel,倍爽！";
		listViewList.add(downloadItem);

		ListViewItem historyItem = new ListViewItem();
		historyItem.picId = R.drawable.lyric_poster_default_bg3_small;
		historyItem.name = "我最爱听";
		historyItem.content = 15 + "";
		listViewList.add(historyItem);

		ListViewItem favoriteItem = new ListViewItem();
		favoriteItem.picId = R.drawable.lyric_poster_default_bg4_small;
		favoriteItem.name = "QQ炫舞";
		favoriteItem.content = 9 +"";
		listViewList.add(favoriteItem);

		ListViewItem mvItem = new ListViewItem();
		mvItem.picId = R.drawable.lyric_poster_default_bg5_small;
		mvItem.name = "默认收藏";
		mvItem.content = 155 +"";
		listViewList.add(mvItem);

		ListViewItem paidItem = new ListViewItem();
		paidItem.picId = R.drawable.lyric_poster_default_bg6_small;
		paidItem.name = "我的歌单";
		paidItem.content = 10000 + "";
		listViewList.add(paidItem);
	}

	private void initGridViewData() {
		GridViewItem localItem = new GridViewItem();
		localItem.picId = R.drawable.mymusic_icon_allsongs_normal;
		localItem.name = "本地歌曲";
		localItem.number = MusicUtils.getInstance(getActivity()).getMusicList().size();
		gridViewList.add(localItem);

		GridViewItem downloadItem = new GridViewItem();
		downloadItem.picId = R.drawable.mymusic_icon_download_normal;
		downloadItem.name = "下载歌曲";
		downloadItem.number = 88;
		gridViewList.add(downloadItem);

		GridViewItem historyItem = new GridViewItem();
		historyItem.picId = R.drawable.mymusic_icon_history_normal;
		historyItem.name = "最近播放";
		historyItem.number = 45;
		gridViewList.add(historyItem);

		GridViewItem favoriteItem = new GridViewItem();
		favoriteItem.picId = R.drawable.mymusic_icon_favorite_normal;
		favoriteItem.name = "我喜欢";
		favoriteItem.number = 5;
		gridViewList.add(favoriteItem);

		GridViewItem mvItem = new GridViewItem();
		mvItem.picId = R.drawable.mymusic_icon_mv_normal;
		mvItem.name = "下载MV";
		mvItem.number = 0;
		gridViewList.add(mvItem);

		GridViewItem paidItem = new GridViewItem();
		paidItem.picId = R.drawable.mymusic_icon_paid_songs_normal;
		paidItem.name = "已购音乐";
		paidItem.number = 10000;
		gridViewList.add(paidItem);
	}
	interface MenuFragmentItemClickListener{
			void OnItemClick(int position);
	}
	public void setMenuFragmentItemClickListener(MenuFragmentItemClickListener listener){
		this.listener =  listener;
	}
	@SuppressLint("Recycle")
	private void initListener() {
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch(position){
				case 0:
					if(listener != null){
						listener.OnItemClick(position);
					}	
					break;
				case 1:
					ULog.toast(getActivity(), "功能尚未开发完成，敬请期待能");
					break;
				case 2:
					ULog.toast(getActivity(), "功能尚未开发完成，敬请期待能");
					break;
				case 3:
					ULog.toast(getActivity(), "功能尚未开发完成，敬请期待能");
					break;
				case 4:
					ULog.toast(getActivity(), "功能尚未开发完成，敬请期待能");
					break;
				case 5:
					ULog.toast(getActivity(), "功能尚未开发完成，敬请期待能");
					break;
				}
			}
		});
	}


	private void initView(){
		mListView = (MyListView) mainView.findViewById(R.id.listView);
		listViewHead = View.inflate(getActivity(), R.layout.listview_head, null);
		mGridView = (MyGridView) listViewHead.findViewById(R.id.gridView);
		mGridViewAdapter = new GridViewAdapter(getActivity(), gridViewList);
		mListViewAdapter = new ListViewAdapter(getActivity(), listViewList);
		mGridView.setAdapter(mGridViewAdapter);

		mListView.setAdapter(mListViewAdapter);
		mListView.removeHeaderView(listViewHead);
		mListView.addHeaderView(listViewHead);
	}

}
