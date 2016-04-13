package com.zhanjixun.utils;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

/**
 * 倒计时异步任务
 * 
 * @author 詹命天子
 *
 */
public class AsyncClockTask extends AsyncTask<Void, Integer, Void> {

	private TextView view;
	private boolean isRunning = true;
	private String text;

	public AsyncClockTask(TextView t) {
		this.view = t;
		view.setFocusable(false);
		text = (String) t.getText();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		view.setClickable(false);
	}

	@Override
	protected Void doInBackground(Void... params) {
		int second = 60;
		
		if (isCancelled()) {
			isRunning = false;
			return null;
		}
		
		while (second > -1 && isRunning) {
			publishProgress(second--);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		view.setText(values[0] + "秒后重试");
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		view.setText(text);
		view.setClickable(true);
	}
	
	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		isRunning = false;
		super.onCancelled();
		Log.d("AsyncClockTask", "退出计时器");
	}
	
	/**
	 * 设置标准为去停止线程
	 * @param isRunning
	 */
	public void onCancle(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
}
