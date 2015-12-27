package com.zhanjixun.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.zhanjixun.R;
import com.zhanjixun.adapter.AddressManageAdapter;
import com.zhanjixun.base.BackActivity;
import com.zhanjixun.data.Constants;
import com.zhanjixun.data.DC;
import com.zhanjixun.data.TaskTag;
import com.zhanjixun.domain2.Address;
import com.zhanjixun.domain2.BaseResult;
import com.zhanjixun.interfaces.OnDataReturnListener;
import com.zhanjixun.utils.MyGson;
import com.zhanjixun.views.DoubleButtonMessageDialog;
import com.zhanjixun.views.LoadingDialog;
import com.zhanjixun.views.MessageDialog;
import com.zhanjixun.views.ReflashListView;
import com.zhanjixun.views.ReflashListView.OnRefreshListener;

public class AddressManageActivity extends BackActivity implements
		OnDataReturnListener, OnRefreshListener {
	private LoadingDialog dialog;
	private ReflashListView addressLv;
	private AddressManageAdapter adapter;
	private int postion;
	private DoubleButtonMessageDialog dbMsgDialog;
	private AdapterContextMenuInfo info;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me_address_manage);
	}

	@Override
	protected void onStart() {
		super.onStart();
		getAddress();
	}

	private void getAddress() {
		dialog = new LoadingDialog(this);
		dialog.show();
		DC.getInstance().getAddrees(this, Constants.user.getUserId());
	}

	public void onAdd(View v) {
		Intent intent = new Intent(this, AddAddressActivity.class);
		startActivity(intent);
	}

	private void loadListViewData(List<Address> addresses) {
		addressLv = (ReflashListView) findViewById(R.id.id_mp_admanage);
		adapter = new AddressManageAdapter(this, addresses);
		addressLv.setAdapter(adapter);
		addressLv.setOnRefreshListener(this);
		addressLv.setOnCreateContextMenuListener(this);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.add(0, 1, Menu.NONE, "��ΪĬ��");
		menu.add(0, 2, Menu.NONE, "�޸�");
		menu.add(0, 3, Menu.NONE, "ɾ��");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		info = (AdapterContextMenuInfo) item.getMenuInfo();
		postion = new Long(info.id).intValue();
		Address address = (Address) adapter.getItem(postion);
		switch (item.getItemId()) {
		// ��ΪĬ��
		case 1:
			dialog.show();
			DC.getInstance().defultaAdress(this, address.getGetAddressId(),
					Constants.user.getUserId());
			break;
		// �޸�
		case 2:
			Intent intent = new Intent(this, AddAddressActivity.class);
			intent.putExtra("address", address.toString());
			startActivity(intent);
			break;
		// ɾ��
		case 3:
			dbMsgDialog = new DoubleButtonMessageDialog(this, "ȷ��Ҫɾ�����ջ���ַ?");
			dbMsgDialog.setNegativeButton("����", null);
			dbMsgDialog.setPositiveButton("ɾ��", new OnClickListener() {
				@Override
				public void onClick(View v) {
					postion = new Long(info.id).intValue();
					Address address = (Address) adapter.getItem(postion);
					dialog.show();
					DC.getInstance().deleteAdress(AddressManageActivity.this,
							address.getGetAddressId());
				}
			});
			dbMsgDialog.show();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onDataReturn(String taskTag, BaseResult result, String json) {
		dialog.dismiss();

		if (result.getServiceResult()) {
			if (taskTag.equals(TaskTag.GET_ADDREES)) {
				List<Address> addresses = MyGson.getInstance().fromJson(result
						.getResultParam().get("getAddressList"),
						new TypeToken<List<Address>>() {
						}.getType());
				// ����Ĭ�ϵ�ַ
				String def = result.getResultParam().get("default");
				for (Address address : addresses) {
					address.setDefaultAddress(def == address.getGetAddressId());
				}
				loadListViewData(addresses);
			} else if (taskTag.equals(TaskTag.DELETE_ADDRESS)) {
				Toast.makeText(this, result.getResultInfo(), Toast.LENGTH_LONG)
						.show();
				getAddress();// ���µ�ַ����
			} else if (taskTag.equals(TaskTag.DEFULT_AADRESS)) {
				Toast.makeText(this, result.getResultInfo(), Toast.LENGTH_LONG)
						.show();
				getAddress();// ���µ�ַ����
			}

		} else {
			new MessageDialog(this, result.getResultInfo()).show();
		}

	}

	@Override
	public void onLoadingMore(View v) {
		addressLv.hideFooterView();
	}

}
