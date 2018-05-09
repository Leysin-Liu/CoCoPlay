package com.leysin.cocoplay.controller;

import java.util.ArrayList;

import com.leysin.cocoplay.R;
import com.leysin.cocoplay.entity.GridViewItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter{
	private ArrayList<GridViewItem> list;
	private Context mContext;
	public GridViewAdapter(Context mContext,ArrayList<GridViewItem> list) {
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
			convertView =  View.inflate(mContext, R.layout.gridview_item, null);
			holder.imageView =  (ImageView) convertView.findViewById(R.id.gridview_item_image);
			holder.titleTextView =  (TextView) convertView.findViewById(R.id.gridview_item_title);
			holder.numberTextView =  (TextView) convertView.findViewById(R.id.gridview_item_number);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.imageView.setImageResource(list.get(position).getPicId());
		holder.titleTextView.setText(list.get(position).getName());
		holder.numberTextView.setText(String.valueOf(list.get(position).getNumber()));
		return convertView;
	}
	
	class ViewHolder{
		
		ImageView imageView;
		
		TextView titleTextView;
		
		TextView numberTextView;
	}
}
