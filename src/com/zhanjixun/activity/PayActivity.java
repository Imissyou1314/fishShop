/**
 * 
 */
package com.zhanjixun.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.pingplusplus.android.PaymentActivity;
import com.zhanjixun.R;
import com.zhanjixun.base.BackActivity;
import com.zhanjixun.data.DC;
import com.zhanjixun.domain2.BaseResult;
import com.zhanjixun.interfaces.OnDataReturnListener;

/**
 * @author Imissyou
 * @Time  2015��12��2��
 *  
 *  ֧������
 *  
 *  ����:PayActivity
 */
public class PayActivity extends BackActivity implements OnClickListener, OnDataReturnListener{
	
	private ImageView zhifubaoImage;
	private ImageView weixiImage;
	private String channel = "";
    private String OrdersId = "";
	
	private static final int REQUEST_CODE_PAYMENT = 1;
	
	 /**
     * ΢��֧������
     */
    private static final String CHANNEL_WECHAT = "wx";
    private static final String CHANNEL_ALIPAY = "alipay";
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		
		initViews();
	}

	private void initViews() {
		zhifubaoImage = (ImageView) findViewById(R.id.pay_zhifubao_image);
		weixiImage = (ImageView) findViewById(R.id.pay_weixi_image);
		zhifubaoImage.setOnClickListener(this);
		weixiImage.setOnClickListener(this);
		Intent intent = getIntent();
		OrdersId = intent.getStringExtra("order_id");
	}

	public void onClick(View v) {
		//�������֮��Ľ��ã���ֹ�ظ����
		weixiImage.setOnClickListener(null);
		zhifubaoImage.setOnClickListener(null);
		
		if (v.getId() == R.id.pay_zhifubao_image) {
			channel = CHANNEL_ALIPAY;
			
		} else if(v.getId() == R.id.pay_weixi_image) {
			channel = CHANNEL_WECHAT;
		} else {
			Toast.makeText(this, "��ѡ��֧����ʽ...", Toast.LENGTH_LONG).show();
			return ;
		}
		DC.getInstance().pay(this,channel,OrdersId);
	}

	/**
	 * ����Ping++ ��SDK
	 */
	public void onDataReturn(String taskTag, BaseResult result, String json) {
		if(null==json){
			Toast.makeText(this, "֧��ʧ��...", Toast.LENGTH_LONG).show();
    		showMsg("�������", "����URL", "URL�޷���ȡcharge");
    		return;
    	}
		 /**
         * ��÷���˵�charge ==>json������ping++ sdk��
         */
		int start = json.indexOf("charge");
		String charge =(String) json.subSequence(start + 8, json.length() -2);
		Log.v("test===>", charge);
		
		Intent intent = new Intent();
		String packageName = getPackageName();
		ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
		intent.setComponent(componentName);
		intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);
		startActivityForResult(intent, REQUEST_CODE_PAYMENT);
	}
	
	/**
	 * onActivityResult ���֧����������֧���ɹ������������յ�ping++ ���������͵��첽֪ͨ��
	 * ����֧���ɹ������첽֪ͨΪ׼
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		zhifubaoImage.setOnClickListener(PayActivity.this);
		weixiImage.setOnClickListener(PayActivity.this);
        //֧��ҳ�淵�ش���
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                Log.d("miss", result);
                /* ����ֵ����
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                String errorMsg = data.getExtras().getString("error_msg"); // ������Ϣ
                String extraMsg = data.getExtras().getString("extra_msg"); // ������Ϣ
                showMsg(result, errorMsg, extraMsg);
            }
        }
    }
	
	/**
     * ��ʾ���ص���Ϣ
     * 
     * @return void
     */
    public void showMsg(String title, String msg1, String msg2) {
    	String str = title;
    	if (null !=msg1 && msg1.length() != 0) {
    		str += "\n" + msg1;
    	}
    	if (null !=msg2 && msg2.length() != 0) {
    		str += "\n" + msg2;
    	}
    	AlertDialog.Builder builder = new Builder(this);
    	builder.setMessage(str);
    	builder.setTitle("��ʾ");
    	builder.setPositiveButton("OK", null);
    	builder.create().show();
    }

}
