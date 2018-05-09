package com.leysin.cocoplay.view;

import com.leysin.cocoplay.R;
import com.leysin.cocoplay.Interface.BaseFragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ILikeFragment extends BaseFragment{
	
	private ListView mListView;
	private ImageView backButton;
	private View mainView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		init();
		mainView = View.inflate(getActivity(), R.layout.fragment_ilike, null);
		return mainView;
	}
	private void init() {
		SQLiteDatabase database = getActivity().openOrCreateDatabase("cocoplay.db", Context.MODE_PRIVATE,null);
		mListView = (ListView) mainView.findViewById(R.id.ilike_music_listView);
		backButton =  (ImageView) mainView.findViewById(R.id.ilikefragment_back);
	}
}
