package com.leysin.cocoplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyListView extends ListView{
	
	//listView 可拉升高度
	private int mMaxOverDistance = 75;
	
	public MyListView(Context context,AttributeSet attr) {
		super(context,attr);
	}
	
	//ListView变为有弹性的
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
				scrollRangeY, maxOverScrollX, mMaxOverDistance, isTouchEvent);
	}
}
