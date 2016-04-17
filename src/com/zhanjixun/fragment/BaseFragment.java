package com.zhanjixun.fragment;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {
	
	/** 
	 * ��ʱ����Fragment 
	 */  
	      
	    protected boolean isVisible;  
	      
	    @Override  
	    public void setUserVisibleHint(boolean isVisibleToUser) {  
	        super.setUserVisibleHint(isVisibleToUser);  
	          
	        if(getUserVisibleHint()) {            // ����ɼ�  
	            isVisible = true;  
	            onVisible();  
	        }else {  
	            isVisible = false;  
	            onInvisible();  
	        }  
	    }  
	  
	    private void onInvisible() {  
	          
	    }  
	  
	    private void onVisible() {  
	        lazyLoad();  
	    }  
	      
	    /** 
	     * �������ʱ�������񽻸��������ʵ�� 
	     */  
	    protected abstract void lazyLoad();  

}
