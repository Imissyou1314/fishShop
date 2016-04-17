package com.zhanjixun.fragment;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {
	
	/** 
	 * 延时加载Fragment 
	 */  
	      
	    protected boolean isVisible;  
	      
	    @Override  
	    public void setUserVisibleHint(boolean isVisibleToUser) {  
	        super.setUserVisibleHint(isVisibleToUser);  
	          
	        if(getUserVisibleHint()) {            // 如果可见  
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
	     * 具体的延时加载任务交给子类具体实现 
	     */  
	    protected abstract void lazyLoad();  

}
