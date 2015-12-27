package com.zhanjixun.views;

import android.content.Context;
import android.view.View.OnClickListener;

/**
 * ˫��ť��ʾ��Ϣ�Ի���
 * 
 * @author ղ������
 *
 */
public class DoubleButtonMessageDialog extends MessageDialog {

	public DoubleButtonMessageDialog(Context context, String msg) {
		super(context, msg);
	}

	public void setPositiveButton(String text, OnClickListener l) {
		dialog.setPositiveButton(text, l);
	}

	@Override
	public void setCancelable(boolean flag) {
		super.setCancelable(flag);
	}

	public void setNegativeButton(String text, OnClickListener l) {
		dialog.setNegativeButton(text, l);
	}
}
