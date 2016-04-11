package com.zhanjixun.views;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class selfAdapterViewPager extends ViewPager{
	
	private static int MAX_Height = 0;

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
	        //�����������child�ĸ߶�
	        for (int i = 0; i < getChildCount(); i++) {
	            View child = getChildAt(i);
	            child.measure(widthMeasureSpec,
	                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
	            int h = child.getMeasuredHeight();
	            if (h > height) //��������view�ĸ߶ȡ�
	                height = h;
	        }
	        /**
	         * �޸��˲���ȫ��������
	         */
	        if (heightMeasureSpec <= MeasureSpec.makeMeasureSpec(height,
	                MeasureSpec.EXACTLY)) {
	        	heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
		                MeasureSpec.EXACTLY);
			}
	        Log.d("Miss Adapter Viewpager", heightMeasureSpec + ":::::>>>after");
	        Log.d("Miss Adapter Viewpager", MAX_Height + ":::::>>>after  MAX_Height");
	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	        
	    }

}
