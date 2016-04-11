package com.zhanjixun.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * ÊÊÓÃÓÚScrollViewµÄListView
 * @author Imissyou
 *
 */
public class ListViewForScrollView extends ListView{

	public ListViewForScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
