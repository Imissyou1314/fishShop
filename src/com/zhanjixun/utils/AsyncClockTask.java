package com.zhanjixun.utils;

import android.os.AsyncTask;
import android.widget.TextView;

/**
 * ����ʱ�첽����
 * 
 * @author ղ������
 *
 */
public class AsyncClockTask extends AsyncTask<Void, Integer, Void> {

	private TextView view;
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

		while (second > -1) {
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
		view.setText(values[0] + "�������");
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		view.setText(text);
		view.setClickable(true);
	}
}
