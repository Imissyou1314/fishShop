package com.zhanjixun.data;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.zhanjixun.net.AsyncBlurImageTask;
import com.zhanjixun.net.AsyncImageTask;
import com.zhanjixun.utils.LogUtils;

public class IC {
	private static IC imageCenter;

	private IC() {
	}

	public static IC getInstance() {
		return imageCenter == null ? new IC() : imageCenter;
	}

	public void setBlurForegound(Context context, String url, View view) {
		if (url == null) {
			LogUtils.w("url=null");
			return;
		}
		String imgURL = Constants.HOST + "/fishshop/" + url;
		LogUtils.v(imgURL);
		new AsyncBlurImageTask(view, context, imgURL);
	}

	public void setForegound(String url, ImageView view) {
		if (url == null) {
			LogUtils.w("url=null");
			return;
		}
		String imgURL = Constants.HOST + "/fishshop/" + url;
		LogUtils.v(imgURL);
		new AsyncImageTask(imgURL, view, AsyncImageTask.SET_BITMAP_FOREGROUND);
	}

	public void setBackground(String url, View view) {
		if (url == null) {
			LogUtils.w("url=null");
			return;
		}
		String imgURL = Constants.HOST + "/fishshop/" + url;
		LogUtils.v(imgURL);
		new AsyncImageTask(imgURL, view, AsyncImageTask.SET_BITMAP_BACKGROUND);
	}
	
	
	/**
	 * 
	 * 加载大图片
	 * @param url
	 * @param view
	 */
	public void setForegoundOrigin(String url, ImageView view) {
		Log.d("miss:::>>> ", url);
		int lastIndexOf = url.lastIndexOf(".");
		StringBuilder builder = new StringBuilder();
		builder.append(url.substring(0, lastIndexOf));
		builder.append("_300");
		builder.append(url.substring(lastIndexOf, url.length()));
		String url2 = builder.toString();
		Log.d("miss:::after>>>", url2);
		setForegound(url2, view);
//		new AsyncImageTask(url2, view, AsyncImageTask.FOREGROUND);
		//TODO 后面的没有了
//		getImage(url2, view, AsyncImageTask.FOREGROUND);
	}

}
