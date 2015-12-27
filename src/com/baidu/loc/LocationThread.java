package com.baidu.loc;

import android.app.Application;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class LocationThread extends Thread {
	TextView locTv;
	Application application;

	public LocationThread(TextView locTv, Application application) {
		this.locTv = locTv;
		this.application = application;
	}

	public void run() {
		locTv.setText("��λ��...");
		LocationApplication locationApplication = (LocationApplication) application;
		LocationClient locClient = locationApplication.mLocationClient;
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// ��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("gcj02");// ��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ��
		option.setScanSpan(1000);// ��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(true);// ��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);// ��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setLocationNotify(true);// ��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		option.setIgnoreKillProcess(false);// ��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��
		locClient.setLocOption(option);
		locationApplication.mLocationResult = locTv;
		locClient.start();
	}
}
