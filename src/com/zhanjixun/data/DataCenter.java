package com.zhanjixun.data;

import java.util.Map;

import com.zhanjixun.interfaces.OnDataReturnListener;
import com.zhanjixun.net.AsyncHttpTask;

import android.util.Log;

public class DataCenter {
	public void getDatasFromServer(String taskTag, String url,
			Map<String, String> params, OnDataReturnListener dataReturnListener) {
		
		//TODO 打印请求数据
		if (null != params ) {
			Log.d(taskTag, params.toString() + "Miss");
		} else {
			Log.d(taskTag,  "Miss the 请求数据为空");
		}
		
		
		new AsyncHttpTask(taskTag, dataReturnListener).execute(Constants.HOST
				+ "/fishshop/" + url, params, AsyncHttpTask.POST);

	}

	public void getLogistics(String taskTag, Map<String, String> params,
			OnDataReturnListener dataReturnListener) {
		new AsyncHttpTask(taskTag, dataReturnListener).execute(
				"http://www.kuaidi100.com/query", params, AsyncHttpTask.GET);
	}
}
