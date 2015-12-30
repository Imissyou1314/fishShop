package com.zhanjixun.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.zhanjixun.data.Constants;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

public class BitmapUtils {

	/**
	 * 按指定宽高截取图片
	 * 
	 * @param bitmap
	 * @param width
	 */
	public static Bitmap getBitmap(Bitmap bitmap, int width, int height) {
		double scale1 = width * 1.0 / height;
		double scale2 = bitmap.getWidth() * 1.0 / bitmap.getHeight();
		int x = 0;
		int y = 0;
		if (scale1 > scale2) {
			height = (int) (height * (bitmap.getWidth() * 1.0 / width));
			y = (bitmap.getHeight() - height) / 2;
			width = bitmap.getWidth();
		} else if (scale1 < scale2) {
			width = (int) (width * (bitmap.getHeight() * 1.0 / height));
			x = (bitmap.getWidth() - width) / 2;
			height = bitmap.getHeight();
		} else {
			return bitmap;
		}

		Bitmap result = Bitmap.createBitmap(bitmap, x, y, width, height);
		return result;
	}
	
	/**
	 * Bitmap 装 file 
	 * @param bitmap 传入的bitmap一定要
	 * @param filename 保存的文件名
	 * @param path     保存地址
	 * @return
	 */
	public static boolean BitmapToFile(Bitmap bitmap, String path, String filename) {
		CompressFormat format = Bitmap.CompressFormat.JPEG;
		Boolean result = false;
		int quality = 100;//还原度
		OutputStream stream = null;
		
		Log.v("555miss=====Path5555", path);
		
		if (null == path && "".equals(path))
			path = Constants.CACHE_DIR;
		
		try {
			stream = new FileOutputStream(path +"/"+ filename);
			result = bitmap.compress(format, quality, stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (null != stream) {
				try {
					stream.flush();
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
