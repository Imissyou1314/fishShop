package com.zhanjixun.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

public class MissBitmapUtils {
	
	private final static int REQWdith = 100;
	private final static int REQHeight = 100;
	
	
	public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {
	             final int heightRatio = Math.round((float) height/ (float) reqHeight);
	             final int widthRatio = Math.round((float) width / (float) reqWidth);
	             inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }
	        return inSampleSize;
	}
	
	
	// 根据路径获得图片并压缩，返回bitmap用于显示
	public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, REQWdith, REQHeight);

        // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;

    return BitmapFactory.decodeFile(filePath, options);
    }
	
	/** 
     * 回收ImageView占用的图像内存; 
     * @param view 
     */  
    public static void recycleImageView(View view){  
        if(view==null) return;  
        if(view instanceof ImageView){  
            Drawable drawable=((ImageView) view).getDrawable();  
            if(drawable instanceof BitmapDrawable){  
                Bitmap bmp = ((BitmapDrawable)drawable).getBitmap();  
                if (bmp != null && !bmp.isRecycled()){  
                    ((ImageView) view).setImageBitmap(null);  
                    bmp.recycle();  
                    bmp=null;  
                }  
            }  
        }  
    }  
    
    
  //根据指定的bitmap路径加载，并定义好了目标的大小
    public static Bitmap decodeFile(String path, int dstWidth, int dstHeight) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth,
                dstHeight);
        Bitmap unscaledBitmap = BitmapFactory.decodeFile(path, options);
 
        return unscaledBitmap;
    }
 
    /**
     * 获得经过处理的bitmap
     */
    public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight) {
        Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(),
                dstWidth, dstHeight);
        Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(),
                dstWidth, dstHeight);
        Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(),
                Config.ARGB_8888);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }
 
    /**
     * 计算Option的inSampleSize属性
     */
    public static int calculateSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight) {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstHeight;
 
            if (srcAspect > dstAspect) {
                return srcWidth / dstWidth;
            } else {
                return srcHeight / dstHeight;
            }
    }
 
    /**
     * 计算源文件的Rect
     */
    public static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight) {
            return new Rect(0, 0, srcWidth, srcHeight);
    }
 
    /**
     * 计算目标bitmap的rect区域
     */
    public static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight) {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstHeight;
 
            if (srcAspect > dstAspect) {
                return new Rect(0, 0, dstWidth, (int) (dstWidth / srcAspect));
            } else {
                return new Rect(0, 0, (int) (dstHeight * srcAspect), dstHeight);
            }
    }  
    
    /**
     * 默认图片压缩
     * @param path
     */
    public static Bitmap decodeFileDefault(String path) {
    	return decodeFile(path, REQWdith, REQWdith);
    }
     
}
