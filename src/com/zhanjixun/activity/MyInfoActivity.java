package com.zhanjixun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.zhanjixun.R;
import com.zhanjixun.base.BackActivity;
import com.zhanjixun.data.Constants;
import com.zhanjixun.data.DC;
import com.zhanjixun.data.TaskTag;
import com.zhanjixun.domain2.BaseResult;
import com.zhanjixun.domain2.User;
import com.zhanjixun.interfaces.OnDataReturnListener;
import com.zhanjixun.utils.JsonToUserUtil;
import com.zhanjixun.utils.SPUtil;
import com.zhanjixun.utils.StringUtil;
import com.zhanjixun.views.ChangePasswdDialog;
import com.zhanjixun.views.DoubleButtonMessageDialog;

public class MyInfoActivity extends BackActivity implements OnDataReturnListener {
	private TextView phoneTv;
	private TextView usernameTv;
	private ChangePasswdDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me_myinfo);
		initViews();

	}

	@Override
	protected void onStart() {
		super.onStart();
		initData();
	}

	private void initData() {
		if (null != Constants.user.getPhoneNumber()) {
			phoneTv.setText(StringUtil.encryptPhoneNumber(Constants.user.getPhoneNumber()));
			usernameTv.setText(Constants.user.getUserName());
		}

	}

	private void initViews() {
		phoneTv = (TextView) findViewById(R.id.text_myinfo_phone);
		usernameTv = (TextView) findViewById(R.id.text_myinfo_username);
	}

	public void onClick(View v) {
		final String  tag = (String) v.getTag();
		if (!tag.equals("headImage") && !tag.equals("exit")) {
			if (tag.equals("name")) {
				dialog = new ChangePasswdDialog(this, R.layout.dialog_changename, "更改用户名");
				dialog.show();
			} else if (tag.equals("password")) {
				dialog = new ChangePasswdDialog(this, R.layout.dialog_changepassword, "更改用户密码");
				dialog.show();
			} else if (tag.equals("phone")) {
				dialog = new ChangePasswdDialog(this, R.layout.dialog_changephone, "更改用户手机号");
				dialog.show();
			}
			/* 更新 */
			dialog.setPositiveButton("确定", new OnClickListener() {
				public void onClick(View v) {
					
					if (tag.equals("password") && null != MyInfoActivity.this.dialog.getOldPassword()) {
						//更新密码
						if (MyInfoActivity.this.dialog.getSurepassword()
								.equals(MyInfoActivity.this.dialog.getPassword())) {
							DC.getInstance().changeUserPassword(MyInfoActivity.this, Constants.user.getUserId(),
									MyInfoActivity.this.dialog.getOldPassword(),
									MyInfoActivity.this.dialog.getPassword());
						}
					} else if ( tag.equals("name") && null != MyInfoActivity.this.dialog.getName()) {
						DC.getInstance().changeUserName(MyInfoActivity.this, Constants.user.getUserId(),
								MyInfoActivity.this.dialog.getName());
					} else if (tag.equals("phone") && null != MyInfoActivity.this.dialog.getPhone()) {
						DC.getInstance().changeUserName(MyInfoActivity.this, Constants.user.getPhoneNumber(),
								MyInfoActivity.this.dialog.getPhone());
					}
				}
			});

		} else if (tag.equals("headImage")) {
			Intent intent = new Intent(MyInfoActivity.this, ChangeUserHeadImageActivity.class);
			startActivity(intent);
		} else if (tag.equals("exit")) {
			DoubleButtonMessageDialog dialog = new DoubleButtonMessageDialog(this, "确定要退出?");

			dialog.setPositiveButton("退出", new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					SPUtil.deleteSP(MyInfoActivity.this, Constants.XML_USER);
					Constants.user = null;

					Intent intent = new Intent(MyInfoActivity.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					MyInfoActivity.this.startActivity(intent);
				}
			});
			dialog.setNegativeButton("返回", null);
			dialog.show();
		}
	}

	/**
	 * 更新返回状态
	 */
	public void onDataReturn(String taskTag, BaseResult result, String json) {

		if (result.getServiceResult()) {
			if (taskTag.equals(TaskTag.CHANGE_USER_NAME)) {
				Toast.makeText(this, "更新用户名成功....", Toast.LENGTH_LONG).show();
				//更新用户名
				if (null != result.getResultParam()) {
					User user = JsonToUserUtil.getuser(result);
					usernameTv.setText(user.getUserName());
				}
				
			} else if (taskTag.equals(TaskTag.CHANGE_USER_PASSWORD)) {
				Toast.makeText(this, "更新用户密码成功....", Toast.LENGTH_LONG).show();
			} else if (taskTag.equals(TaskTag.CHANGE_USER_PHONE)) {
				Toast.makeText(this, "更新用户手机号成功....", Toast.LENGTH_LONG).show();
				//更新手机号
				if (null != result.getResultParam()) {
					User user = JsonToUserUtil.getuser(result);
					phoneTv.setText(user.getPhoneNumber());
				}
			}
		} else {
			Toast.makeText(this, result.getResultInfo(), Toast.LENGTH_LONG).show();
		}

	}
}
