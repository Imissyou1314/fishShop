package com.zhanjixun.utils;

import com.zhanjixun.domain2.BaseResult;
import com.zhanjixun.domain2.User;

public class JsonToUserUtil {

	/**
	 * Json ת user
	 * @param result
	 * @return
	 */
	
	private JsonToUserUtil(){};
	
	public static User getuser(BaseResult result) {
		if (null != result ) {
			 User user = MyGson.getInstance().fromJson(result.getResultParam()
						.get("user"), User.class);
			 return user;
		}
		return null;
	}
}
