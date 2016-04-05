package com.zhanjixun.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.zhanjixun.R;
import com.zhanjixun.base.BackActivity;
import com.zhanjixun.data.Constants;
import com.zhanjixun.data.DC;
import com.zhanjixun.data.TaskTag;
import com.zhanjixun.domain.Province;
import com.zhanjixun.domain2.Address;
import com.zhanjixun.domain2.BaseResult;
import com.zhanjixun.interfaces.OnDataReturnListener;
import com.zhanjixun.utils.MyGson;
import com.zhanjixun.utils.StringUtil;
import com.zhanjixun.views.LoadingDialog;
import com.zhanjixun.views.MessageDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddAddressActivity extends BackActivity implements OnDataReturnListener {

	private EditText edit_userName;
	private EditText edit_userPhone;
	private EditText edit_userEmail;
	private EditText edit_userAddress;
	private Spinner select_provinces;
	private String userName;
	private String userPhone;
	private String userEmail;
	private String userAddress;
	private long provinceId = 0;
	private LoadingDialog dialog;
	private MessageDialog messageDialog;
	private boolean Add = true;
	private Address address;

	private List<String> provinces = new ArrayList<String>();
	private ArrayAdapter<String> spinnerAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_add_address);
		initViews();
		initData();
	}

	/* 加载省份 */
	private void initData() {
		DC.getInstance().getProvinces(this);
	}

	private void initViews() {
		dialog = new LoadingDialog(this);
		edit_userName = (EditText) findViewById(R.id.shopping_car_add_address_userName);
		edit_userPhone = (EditText) findViewById(R.id.shopping_car_add_address_phone);
		edit_userEmail = (EditText) findViewById(R.id.shopping_car_add_address_email);
		edit_userAddress = (EditText) findViewById(R.id.shopping_car_add_address_AddressEdit);
		select_provinces = (Spinner) findViewById(R.id.shopping_car_add_address_shenfen);

		try {
			String stringExtra = getIntent().getStringExtra("address");
			address = MyGson.getInstance().fromJson(stringExtra, Address.class);
			edit_userName.setText(address.getUserName());
			edit_userPhone.setText(address.getPhone());
			edit_userEmail.setText(address.getPostcode());
			edit_userAddress.setText(address.getAddress());
			select_provinces.setVisibility(View.VISIBLE);
			Add = false;
		} catch (Exception e) {
		}
	}

	public void onFinish(View v) {
		getEdit();
		if (!StringUtil.isEmptyString(userName) && !StringUtil.isEmptyString(userPhone)
				&& !StringUtil.isEmptyString(userEmail) && !StringUtil.isEmptyString(userAddress)) {

			dialog.show();
			if (Add) {
				DC.getInstance().addAddress(this, Constants.user.getUserId(), userName, userPhone, userAddress,
						userEmail, provinceId);
			} else {
				DC.getInstance().updataAddress(this, address.getGetAddressId(), Constants.user.getUserId(), userName,
						userPhone, userAddress, userEmail, provinceId);
			}
		} else {
			new MessageDialog(this, "请输入完整信息！").show();
		}
	}

	private void getEdit() {
		userName = edit_userName.getText().toString();
		userPhone = edit_userPhone.getText().toString();
		userEmail = edit_userEmail.getText().toString();
		userAddress = edit_userAddress.getText().toString();
	}

	@Override
	public void onDataReturn(String taskTag, BaseResult result, String json) {
		dialog.dismiss();

		if (taskTag.equals(TaskTag.ADD_ADDRESS)) {
			if (result.getServiceResult()) {
				Toast.makeText(this, result.getResultInfo(), Toast.LENGTH_SHORT).show();
				onBack(null);
			} else {
				messageDialog = new MessageDialog(this);
				messageDialog.setMessage(result.getResultInfo());
				messageDialog.show();
			}
		} else if (taskTag.equals(TaskTag.UPDATA_ADDRESS)) {
			messageDialog = new MessageDialog(this);
			messageDialog.setMessage(result.getResultInfo());
			messageDialog.show();
		} else if (taskTag.equals(TaskTag.GET_SHENFEN)) {
			
			
			loadData(result.getResultParam().get("regionList"));
		}
	}

	/**
	 * 加载省份
	 * 
	 * @param list
	 */
	private void loadData(String list) {

		List<Province> provinceList = new ArrayList<Province>();
		provinceList = MyGson.getInstance().fromJson(list, new TypeToken<List<Province>>() {
		}.getType());

		for (Province item : provinceList)
			provinces.add(item.getName());

		spinnerAdapter = new ArrayAdapter<>(this, R.layout.select_provinces_item,provinces);
		spinnerAdapter.setDropDownViewResource(R.layout.select_provinces_dropdown_item);
		select_provinces.setAdapter(spinnerAdapter);
		
		select_provinces.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				provinceId = id + 1;
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

}
