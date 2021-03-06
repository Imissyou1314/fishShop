package com.zhanjixun.activity;

import java.util.Timer;
import java.util.TimerTask;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.zhanjixun.R;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.setContentView(R.layout.activity_splash);
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				mHandler.sendEmptyMessage(1);
			}
		}, 3000);
	}

	@SuppressLint("HandlerLeak")
	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				startActivity(new Intent(SplashActivity.this,
						MainActivity.class));
				finish();
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.finish();
	}
}
