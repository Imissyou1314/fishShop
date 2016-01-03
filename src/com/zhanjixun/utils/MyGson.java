package com.zhanjixun.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyGson {
	private static Gson gson;

	private MyGson() {
	}
	public static Gson getInstance() {
		if (gson == null) {
			gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
					.create();
		}
		return gson;
	}
}
