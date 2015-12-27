package com.zhanjixun.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.zhanjixun.domain2.Location;

public class MyUtil {
	/**
	 * ��ȡָ�����ڵ�Ŀǰ��������
	 * 
	 * @param d
	 * @return
	 */
	public static int getDays(Date d) {
		if (d == null) {
			return 0;
		}
		long end = System.currentTimeMillis();
		long result = end - d.getTime();
		Date date = new Date();
		date.setTime(result);
		String format = new SimpleDateFormat("d").format(date);
		return Integer.parseInt(format);
	}

	/**
	 * �����������������(��γ��)����
	 * 
	 * @param long1
	 *            ��һ�㾭��
	 * @param lat1
	 *            ��һ��γ��
	 * @param long2
	 *            �ڶ��㾭��
	 * @param lat2
	 *            �ڶ���γ��
	 * @return ���ؾ��� ��λ��ǧ��
	 */
	public static String distance(Location l1, Location l2) {
		if (l1 == null || l2 == null) {
			return null;
		}
		double long1 = l1.getLongitude();
		double lat1 = l1.getLatitude();
		double long2 = l2.getLongitude();
		double lat2 = l2.getLatitude();
		double R = 6378137; // ����뾶
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		double a = lat1 - lat2;
		double b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2
				* R
				* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
						* Math.cos(lat2) * sb2 * sb2));
		String result = null;
		double e = d / 1000.00;
		if (e > 2d) {
			result = String.valueOf((int) e);
		} else {
			result = String.valueOf(UnitUtil.NumberFormat((float) e, 2));
		}
		return result;
	}
}
