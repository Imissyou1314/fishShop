package com.zhanjixun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zhanjixun.R;
import com.zhanjixun.activity.LoginActivity;
import com.zhanjixun.activity.SubmitOrder;
import com.zhanjixun.adapter.CarAdapter;
import com.zhanjixun.adapter.CarEditAdapter;
import com.zhanjixun.base.CheckCarFragment;
import com.zhanjixun.data.Constants;
import com.zhanjixun.domain2.CarOrder;
import com.zhanjixun.utils.LogUtils;
import com.zhanjixun.utils.UnitUtil;
import com.zhanjixun.views.DoubleButtonMessageDialog;
import com.zhanjixun.views.MessageDialog;

public class CarFragment extends CheckCarFragment implements OnClickListener {

	private TextView priceTv;
	private Button commitBtn;
	private ListView listView;
	private TextView sellerNameTv;
	private TextView editBtn;
	private CarOrder data;
	private MessageDialog msg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (hasData()) {
			return inflater.inflate(R.layout.fragment_car, container, false);
		} else {
			return inflater.inflate(R.layout.fragment_car_empty, container,
					false);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (hasData()) {
			initViews();
			initData();
		}
	}

	private void initViews() {
		msg = new MessageDialog(getActivity());
		sellerNameTv = (TextView) getView().findViewById(
				R.id.text_car_sellerName);
		priceTv = (TextView) getActivity().findViewById(R.id.text_car_price);
		commitBtn = (Button) getActivity().findViewById(R.id.btn_car_commit);
		listView = (ListView) getActivity().findViewById(R.id.list_car_goods);
		editBtn = (TextView) getActivity().findViewById(R.id.btn_car_edit);
		editBtn.setOnClickListener(this);
		commitBtn.setOnClickListener(this);
	}

	private void initData() {
		data = getData();
		sellerNameTv.setText(data.getShopKeeperName());
		listView.setAdapter(new CarAdapter(getActivity(), data));
		setListViewHeightBasedOnChildren(listView);
		setPrice();
	}

	@Override
	public void onClick(View v) {
		String tag = (String) v.getTag();
		if (tag.equals("EDIT")) {
			String text = (String) editBtn.getText();
			if (text.equals("�༭")) {
				data = getData();
				listView.setAdapter(new CarEditAdapter(getActivity(), data));
				commitBtn.setClickable(false);
				editBtn.setText("���");
			} else if (text.equals("���")) {
				data = getData();
				listView.setAdapter(new CarAdapter(getActivity(), data));
				editBtn.setText("�༭");
				commitBtn.setClickable(true);
			}
		} else if (tag.equals("COMMIT")) {
			if (getPrice() == 0f) {
				msg = new MessageDialog(getActivity());
				msg.setMessage("��ѡ��Ʒ����Ϊ0��");
				msg.show();
			} else if (Constants.user.getUserId() == null) {

				LogUtils.v("�û�û�е�¼");
				DoubleButtonMessageDialog doubleBtnMsg = new DoubleButtonMessageDialog(
						getActivity(), "�㻹û�е�¼��");
				doubleBtnMsg.setPositiveButton("��¼", new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								LoginActivity.class);
						getActivity().startActivity(intent);
					}
				});
				doubleBtnMsg.setNegativeButton("����", null);
				doubleBtnMsg.show();
			} else {
				Intent intent = new Intent(getActivity(), SubmitOrder.class);
				getActivity().startActivity(intent);
			}
		}
		setPrice();
	}

	private float getPrice() {
		try {
			String text = (String) priceTv.getText();
			return Float.parseFloat(text.substring(1));
		} catch (Exception e) {
			return 0;
		}
	}

	private void setPrice() {
		priceTv.setText(UnitUtil.toRMB(data.getPrice()));
	}

}
