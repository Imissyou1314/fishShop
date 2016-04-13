package com.zhanjixun.activity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zhanjixun.R;
import com.zhanjixun.base.BackActivity;
import com.zhanjixun.data.Constants;
import com.zhanjixun.data.DC;
import com.zhanjixun.data.TaskTag;
import com.zhanjixun.domain2.BaseResult;
import com.zhanjixun.interfaces.OnDataReturnListener;
import com.zhanjixun.utils.AsyncClockTask;
import com.zhanjixun.utils.StringUtil;
import com.zhanjixun.views.LoadingDialog;
import com.zhanjixun.views.MessageDialog;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * �����û��ֻ�����
 * @author Imissyou
 *
 */
public class ChangeUserPhone extends BackActivity implements OnDataReturnListener{
	
	private Button getCodeBtn;
	private MessageDialog msg;
	private LoadingDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_userphone);
		msg = new MessageDialog(this);
		dialog = new LoadingDialog(this);
		
		getCodeBtn = (Button) findViewById(R.id.change_get_codeBtn);
		getCodeBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onGetCode();
				
			}
		});
		
		findViewById(R.id.change_userPhone_CommitBtn).setOnClickListener(new OnClickListener() {
			
			@Override public void onClick(View v) {
				getCodeBtn.setFocusable(true);
				onCommit();
				
			}
		});
	}

	/**
	 * ��ȡ������ֻ���
	 * @return
	 */
	private String getPhone() {
		return ((EditText) findViewById(R.id.change_userphone_input)).getText().toString();
	}
	
	/**
	 * ��ȡ��֤������
	 * @return
	 */
	private String getCode() {
		return ((EditText) findViewById(R.id.change_inputcode)).getText().toString();
	}

	private void onGetCode() {
		if (StringUtil.isEmptyString(getPhone())) {
			msg.setMessage("�������µ��ֻ�����");
			msg.show();
		} else if (getPhone().equals(Constants.user.getPhoneNumber())) {
			msg.setMessage("������ֻ��뵱ǰ���ֻ���ͬ");
			msg.show();
		} else {
			dialog.show();
			//��������֤��
			DC.getInstance().requestCodeForRegister(this, getPhone());
		}
	}

	/**
	 * �ύ����
	 * @param v
	 */
	private void onCommit() {
		if (StringUtil.isEmptyString(getPhone())) {
			msg.setMessage("�������µ��ֻ�����");
			msg.show();
		} else if (StringUtil.isEmptyString(getCode())) {
			msg.setMessage("�����������֤��");
			msg.show();
		} else {
			dialog.show();
			DC.getInstance().checkMsgCode(this, getPhone(), getCode());
		}
	}
	
	
	@Override
	public void onDataReturn(String taskTag, BaseResult result, String json) {
		dialog.dismiss();
		if (result.getServiceResult()) {
			if (taskTag.equals(TaskTag.REGISTER_CODE)) {
				Toast.makeText(this, result.getResultInfo() + "��Чʱ��Ϊ60��", Toast.LENGTH_LONG)
						.show();
				new AsyncClockTask((TextView) findViewById(R.id.change_get_codeBtn))
						.executeOnExecutor((ExecutorService)Executors.newCachedThreadPool());
			}
			if (taskTag.endsWith(TaskTag.CHECK_MSG_CODE)) {
				DC.getInstance().changeUserPhoneNumber(this, Constants.user.getUserId(),
						getPhone());
			}
			if (taskTag.equals(TaskTag.CHANGE_USER_PHONE)) {
				Toast.makeText(this, result.getResultInfo(), Toast.LENGTH_LONG)
						.show();
			}
		} else {
			msg.setMessage(result.getResultInfo());
			msg.show();
		}
	}

}
