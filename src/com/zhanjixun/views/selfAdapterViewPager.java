package com.zhanjixun.views;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class selfAdapterViewPager extends ViewPager{

	public selfAdapterViewPager(Context context) {
		super(context);
	}
	
	public selfAdapterViewPager(Context context, AttributeSet arg1) {
		super(context, arg1);
	}
	
	 @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		 	
		 Log.d("Miss Adapter Viewpager", heightMeasureSpec + ":::::>>>now");
	        int height = 0;
	        //下面遍历所有child的高度
	        for (int i = 0; i < getChildCount(); i++) {
	            View child = getChildAt(i);
	            child.measure(widthMeasureSpec,
	                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
	            int h = child.getMeasuredHeight();
	            if (h > height) //采用最大的view的高度。
	                height = h;
	        }
	        /**
	         * 修复了不能全屏的问题
	         */
	        if (heightMeasureSpec <= MeasureSpec.makeMeasureSpec(height,
	                MeasureSpec.EXACTLY)) {
	        	heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
		                MeasureSpec.EXACTLY);
			}
	        Log.d("Miss Adapter Viewpager", heightMeasureSpec + ":::::>>>after");
	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    }

}
