package com.zhanjixun.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhanjixun.R;
import com.zhanjixun.data.Constants;
import com.zhanjixun.data.IC;
import com.zhanjixun.domain2.CarOrder;
import com.zhanjixun.domain2.Fisherman;
import com.zhanjixun.domain2.Good;
import com.zhanjixun.domain2.Seller;
import com.zhanjixun.utils.StringUtil;
import com.zhanjixun.views.InputDialog;
import com.zhanjixun.views.InputDialog.OnPositiveButtonClickListener;

@SuppressLint("InflateParams")
public class SellerGoodsListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Good> goods;
	private Integer number = 1;
	
	public void setGoods(List<Good> goods) {
		this.goods = goods;
	}

	private Context context;

	public SellerGoodsListAdapter(Context context, List<Good> beans) {
		this.inflater = LayoutInflater.from(context);
		this.goods = beans;
		this.context = context;
	}

	@Override
	public int getCount() {
		return goods.size();
	}

	@Override
	public Object getItem(int position) {
		return goods.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.seller_detail_goods_listview_item, null);
			vh = new ViewHolder();
			vh.image = (ImageView) convertView
					.findViewById(R.id.id_seller_detail_goods_listview_item_image);
			vh.goodName = (TextView) convertView
					.findViewById(R.id.id_seller_detail_goods_listview_item_seaFoodName);
			vh.specification = (TextView) convertView
					.findViewById(R.id.seller_detail_goods_listview_item_specification);
			vh.sellNumber = (TextView) convertView
					.findViewById(R.id.id_seller_detail_goods_listview_item_salesValue);
			vh.moneyPer = (TextView) convertView
					.findViewById(R.id.seller_detail_goods_listview_item_perWeight);
			vh.addToCarBtn = (Button) convertView
					.findViewById(R.id.id_seller_detail_goods_listview_item_add_to_car);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		Good g = goods.get(position);

		IC.getInstance().setForegound(g.getGoodsPhoto(), vh.image);
		vh.goodName.setText(g.getGoodsName());
		vh.specification.setText(g.getSkuString());
		vh.sellNumber.setText(g.getSellNumber() + "");
		vh.moneyPer.setText(g.getPrice() + "/" + g.getUnit());

		vh.addToCarBtn.setOnClickListener(new MyOnClickListener(g));
		return convertView;
	}

	private class ViewHolder {
		public TextView goodName; // ��Ʒ��
		public TextView specification; // ���
		public TextView sellNumber; // ����
		public TextView moneyPer; // ÿֻ��ÿ�����Ǯ
		public ImageView image;
		public Button addToCarBtn;
	}

	class MyOnClickListener implements View.OnClickListener {
		Good goodItemBean;

		public MyOnClickListener(Good goodItemBean) {
			this.goodItemBean = goodItemBean;
		}

		@Override
		public void onClick(View v) {
			
			
			InputDialog input = new InputDialog(context);
			input.addInputItem(goodItemBean.getGoodsId(),
					"Ĭ������Ϊһ");
			number = 1;
			input.setPositiveButton("ȷ��", new OnPositiveButtonClickListener() {
						@Override public void onPositiveButtonClick(Map<String, String> result) {
							if (StringUtil.isNumber(result.get(goodItemBean.getGoodsId()))){
								number = Integer.valueOf(result.get(goodItemBean.getGoodsId()));
							}	
							//TODO
							if(number <= goodItemBean.getNowNumber()) {
								
								addToCarOrder(goodItemBean);
								goodItemBean.setNowNumber(goodItemBean.getNowNumber() - number);
								Toast.makeText(context, "���" + number + "����Ʒ�ɹ�!", Toast.LENGTH_LONG).show();
							} else {
								Toast.makeText(context, "���ֻ�����" + goodItemBean.getNowNumber() + "����Ʒ!", Toast.LENGTH_LONG).show();
							}
							
							
						}
					});
			input.show();
			v.setClickable(true);
		}
	}

	private void addToCarOrder(Good g) {
		CarOrder o = CarOrder.getCarOrder(context);
		String shopId = Constants.seller.getShopId();
		try {
			if (!shopId.equals(o.getShopId())) {
				o = new CarOrder();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		o.setBuyerName(Constants.seller.getShopName());
		o.setShopId(shopId);
		o.setBuyerName(Constants.user.getUserName());
		o.setShopKeeperName(Constants.seller.getShopName());
		o.setUserId(Constants.user.getUserId());
		Integer shopType = Constants.seller.getShopType();
		if (shopType != null) {
			o.setSeaRecordId(shopType.intValue() == Seller.TYPE_FARMER ? null
					: ((Fisherman) Constants.seller).getSeaRecordId());
		}
		List<Good> goods = o.getOrdersDetail();
		if (goods == null) {
			goods = new ArrayList<Good>();
		}
		
		//TODO ������Ʒ����(��������������)
		Log.d("��Ʒ����", number + "��");
		g.setNumber(number);
		goods.add(g);
		o.setOrdersDetail(goods);
		CarOrder.saveCarOrder(o, context);
	}

}
