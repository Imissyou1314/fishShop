package com.zhanjixun.utils;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class StringUtil {
	public static boolean isPhoneNumber(String phoneNumber) {
		if (phoneNumber == null) {
			return false;
		}
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(phoneNumber);
		return m.matches();
	}

	public static String encryptPhoneNumber(String phoneNum) {
		if (phoneNum == null || phoneNum.length() != 11) {
			throw new IllegalArgumentException("加密手机号码失败，号码格式不对");
		} else {
			return phoneNum.substring(0, 3) + "******"
					+ phoneNum.substring(7, 11);
		}
	}

	public static void main(String args) {
		System.out.println(encryptPhoneNumber("18312687412"));
	}

	public static boolean isEmptyString(String str) {
		if (str == null || str.equals("") || str.equals("null"))
			return true;
		return false;
	}

	/**
	 * 获取字符串md5
	 * 
	 * @param str
	 * @return
	 */
	public static String MD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digs = md.digest(str.getBytes());
			StringBuffer sb = new StringBuffer();
			for (byte b : digs) {
				int a = b & 0xff;
				if (a < 16)
					sb.append("0");
				sb.append(Integer.toHexString(a));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}

	/**
	 * 转换成md5文件名
	 * 
	 * @param dir
	 * @param name
	 * @return
	 */
	public static String toMDFileName(String dir, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append(dir).append(File.separator).append(MD5(name))
				.append(name.substring(name.indexOf(".")));
		return sb.toString();
	}
	
	/**
	 * 转换成 。。。。 1,2,3,4,4,4 ====》 1,2,3.....
	 * @param str 原来的字符串
	 * @param leng 转出的长度
	 * @param flag 识别标志
	 * @param replace 代替的字符串（....)
	 * @return 转换好的
	 */
	public static String splistString( String str, int leng, String flag, String replace ) {
		String[] strList = str.split(flag);
		String result = null;
		if (strList.length <= 2) {
			return str;
		}
		for (int i = 0; i < leng; i ++) {
			result = strList[i] + flag;
		}
		return result.substring(0, result.length() -1) + replace;
	}
	
	/**
	 * 判断是否可以装Integer类型
	 * @param str   待判断的字符串
	 * @return boolean
	 */
	public static boolean isNumber(String str) {
		if (str == null || str.isEmpty() || str.equals("null")) {
			return false;
		}
		try {
			Integer.parseInt(str);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * 检测是否是手机号
	 * @param mobiles
	 * @return
	 *
	 */
	//TODO
	public static boolean isMobileNO(String mobiles) {   
        Pattern p = Pattern   
                .compile("^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])$");   
        Matcher m = p.matcher(mobiles);   
        Log.d("mobiles", "shi ::" + mobiles);  
        return m.matches();   
    }   
	
	/**
	 * 检测是否是邮箱
	 * @param strEmail
	 * @return
	 */
	public static boolean isEmail(String strEmail) {    
	    String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";   
	    Pattern p = Pattern.compile(strPattern);   
	    Matcher m = p.matcher(strEmail);   
	    return m.matches();   
	}  
}
