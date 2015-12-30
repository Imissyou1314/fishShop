package com.zhanjixun.views;

import com.zhanjixun.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 更改用户信息
 * 
 * @author Imissyou
 *
 */
public class ChangePasswdDialog {

	private ThemeDialog dialog;
	private RelativeLayout view;
	private int id;

	private EditText phoneEt;
	private EditText passwordEt;
	private EditText nameEt;
	/* 是否点击了提交 */
	private EditText passwordEt1;
	private EditText passwordEt2;

	public ChangePasswdDialog(Context context, int id, CharSequence title) {
		dialog = new ThemeDialog(context);
		this.id = id;
		this.view = (RelativeLayout) LayoutInflater.from(context).inflate(id, null);
		LinearLayout.LayoutParams lp = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);

		initViews();

		view.setLayoutParams(lp);
		view.setGravity(Gravity.CENTER);
		dialog.setPositiveButton("确认", null);
		dialog.setNegativeButton("返回", null);
		dialog.setView(view);
		dialog.setTitle(title);
		dialog.setDialogSize(0.8, 0.4);
	}

	/**
	 * 加载页面
	 * 
	 * @param id
	 */
	private void initViews() {
		switch (id) {
		case R.layout.dialog_changename:
			nameEt = (EditText) view.findViewById(R.id.dialog_changeName);
			break;
		case R.layout.dialog_changepassword:
			passwordEt = (EditText) view.findViewById(R.id.dialog_newPassword);
			passwordEt1 = (EditText) view.findViewById(R.id.dialog_newPassword2);
			passwordEt2 = (EditText) view.findViewById(R.id.dialog_oldPassword);

			break;
		case R.layout.dialog_changephone:
			phoneEt = (EditText) view.findViewById(R.id.dialog_changePhone);
			break;

		default:
			break;
		}
	}

	/**
	 * 展示
	 */
	public void show() {
		if (dialog != null && !dialog.isShowing()) {
			dialog.show();
		}
	}

	/**
	 * 关掉
	 */
	public void dissmiss() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	public String getName() {
		return nameEt.getText().toString();
	}

	public String getPassword() {
		return passwordEt.getText().toString();
	}

	public String getPhone() {
		return phoneEt.getText().toString();
	}

	public String getSurepassword() {
		return passwordEt1.getText().toString();
	}

	public String getOldPassword() {
		return passwordEt2.getText().toString();
	}

	/**
	 * 设置兼听事件
	 * 
	 * @param string
	 * @param onClickListener
	 */
	public void setPositiveButton(String string, OnClickListener onClick) {
		dialog.setPositiveButton(string, onClick);
	}
	
}
