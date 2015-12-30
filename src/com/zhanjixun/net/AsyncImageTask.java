package com.zhanjixun.net;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.zhanjixun.data.LoadImage;
import com.zhanjixun.utils.BitmapUtils;
import com.zhanjixun.utils.LogUtils;

public class AsyncImageTask extends AsyncTask<String, Integer, Bitmap> {

	private View view;
	private int type;
	public static final int SET_BITMAP_BACKGROUND = 1;
	public static final int SET_BITMAP_FOREGROUND = 2;

	@SuppressWarnings("deprecation")
	public AsyncImageTask(String url, View view, int type) {
		if (view == null)
			return;
		this.view = view;
		this.type = type;
		Bitmap bitmap = LoadImage.getInstance().getBitmapFromLruCache(url);

		if (null != bitmap) {
			LogUtils.v(bitmap.toString());
			bitmap = BitmapUtils.getBitmap(bitmap, view.getMeasuredWidth(),
					view.getMeasuredHeight());
			if (SET_BITMAP_BACKGROUND == type) {
				view.setBackgroundDrawable(new BitmapDrawable(bitmap));
			} else if (SET_BITMAP_FOREGROUND == type) {
				((ImageView) view).setImageBitmap(bitmap);
			}
		} else {
			this.execute(url);
		}
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		String urlStr = params[0];
		LogUtils.v(urlStr);
		Bitmap bitmap = LoadImage.getInstance().getBitmap(urlStr);
		return bitmap;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPostExecute(Bitmap result) {
		if (result != null) {
			result = BitmapUtils.getBitmap(result, view.getMeasuredWidth(),
					view.getMeasuredHeight());
			if (SET_BITMAP_BACKGROUND == type) {
				view.setBackgroundDrawable(new BitmapDrawable(result));
			} else if (SET_BITMAP_FOREGROUND == type) {
				result = BitmapUtils.getBitmap(result, view.getMeasuredWidth(),
						view.getMeasuredHeight());
				((ImageView) view).setImageBitmap(result);
			}
		}
	}

}
