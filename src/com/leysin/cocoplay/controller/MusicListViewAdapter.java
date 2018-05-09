package com.leysin.cocoplay.controller;

import java.util.ArrayList;

import com.leysin.cocoplay.R;
import com.leysin.cocoplay.entity.GridViewItem;
import com.leysin.cocoplay.entity.MusicEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MusicListViewAdapter extends BaseAdapter{
	
	private ArrayList<MusicEntity> list;
	private Context mContext;
	public MusicListViewAdapter(Context mContext,ArrayList<MusicEntity> list) {
		this.mContext = mContext;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView =  View.inflate(mContext, R.layout.music_list_item, null);
			holder.imageView =  (ImageView) convertView.findViewById(R.id.music_list_image);
			holder.titleTextView =  (TextView) convertView.findViewById(R.id.music_list_item_song);
			holder.contentTextView =  (TextView) convertView.findViewById(R.id.music_list_item_singe);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		//holder.imageView.setImageResource(list.get(position).get());
		holder.titleTextView.setText(list.get(position).getTitle());
		holder.contentTextView.setText(String.valueOf(list.get(position).getArtist()));
		return convertView;
	}
	
	class ViewHolder{
		
		ImageView imageView;
		
		TextView titleTextView;
		
		TextView contentTextView;
	}
}
