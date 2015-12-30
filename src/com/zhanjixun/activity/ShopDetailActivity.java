package com.zhanjixun.activity;

import java.util.ArrayList;
import java.util.Date;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.zhanjixun.R;
import com.zhanjixun.adapter.FragmentViewPagerAdapter;
import com.zhanjixun.data.Constants;
import com.zhanjixun.data.DC;
import com.zhanjixun.data.IC;
import com.zhanjixun.domain2.BaseResult;
import com.zhanjixun.domain2.Farmer;
import com.zhanjixun.domain2.Fisherman;
import com.zhanjixun.domain2.Location;
import com.zhanjixun.domain2.Seller;
import com.zhanjixun.fragment.SellerDetailCommentFragment;
import com.zhanjixun.fragment.SellerDetailGoodFragment;
import com.zhanjixun.fragment.SellerDetailSellerFragment;
import com.zhanjixun.interfaces.OnDataReturnListener;
import com.zhanjixun.utils.MyGson;
import com.zhanjixun.utils.LogUtils;
import com.zhanjixun.utils.MyUtil;
import com.zhanjixun.utils.ScreenUtil;
import com.zhanjixun.utils.UnitUtil;
import com.zhanjixun.views.LoadingDialog;
import com.zhanjixun.views.MessageDialog;

public class ShopDetailActivity extends FragmentActivity implements
		OnDataReturnListener {
	private Seller seller = new Seller();
	private TextView tv_goods;
	private TextView tv_comments;
	private TextView tv_sellers;

	private ImageView cursor;
	private ViewPager pager;
	private int offset = 0;
	private int bmpW;

	private ArrayList<Fragment> views;
	private SellerDetailGoodFragment goodFragment;
	private SellerDetailCommentFragment commentFragment;
	private SellerDetailSellerFragment sellerFragment;
	private FragmentViewPagerAdapter myPagerAdapter;

	private TextView shopName;
	private TextView msg_item1;
	private TextView msg_item2;
	private TextView msg_item3;
	private ImageView faceImg;
	private ImageView faceImgBg;
	private LoadingDialog dialog;
	private MessageDialog messageDialog;
	private TextView backTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seller_detail);
		initView();
		initData();
	}

	private void initView() {
		backTv = (TextView) findViewById(R.id.shop_back);
		faceImg = (ImageView) findViewById(R.id.id_seller_detail_sellerImage);
		faceImgBg = (ImageView) findViewById(R.id.id_seller_detail_sellerImage_bg);
		tv_goods = (TextView) findViewById(R.id.id_seller_detail_goods);
		tv_comments = (TextView) findViewById(R.id.id_seller_detail_comment);
		tv_sellers = (TextView) findViewById(R.id.id_seller_detail_seller);
		cursor = (ImageView) findViewById(R.id.image_seller_detail_cursor);
		pager = (ViewPager) findViewById(R.id.id_seller_detail_viewpager);

		shopName = (TextView) findViewById(R.id.id_seller_detail_sellerName); // �̼���
		msg_item1 = (TextView) findViewById(R.id.id_seller_detail_shipPort); // ������
		msg_item2 = (TextView) findViewById(R.id.id_seller_detail_returnTime); // ����ʱ��
		msg_item3 = (TextView) findViewById(R.id.id_seller_detail_creditValue); // ����ֵ

		tv_goods.setOnClickListener(new MyClickListener(0));
		tv_comments.setOnClickListener(new MyClickListener(1));
		tv_sellers.setOnClickListener(new MyClickListener(2));

		initViewpager();
	}

	private void initData() {
		seller.setShopId(getIntent().getStringExtra("shopId"));
		backTv.setText(getIntent().getStringExtra("back"));
		dialog = new LoadingDialog(this);
		dialog.show();
		DC.getInstance().getSellerBaseInfo(this, seller.getShopId());
	}

	@Override
	public void onDataReturn(String taskTag, BaseResult result, String json) {
		dialog.dismiss();
		if (result.getServiceResult()) {
			Farmer farmer = new Farmer();
			Fisherman fisherman = new Fisherman();
			farmer = MyGson.getInstance().fromJson(
					result.getResultParam().get("shop"), Farmer.class);
			fisherman = MyGson.getInstance().fromJson(
					result.getResultParam().get("shop"), Fisherman.class);

			seller = fisherman.getShopType() == Seller.TYPE_FARMER ? farmer
					: fisherman;
			Constants.seller = seller;
			LogUtils.v(MyGson.getInstance().toJson(seller));
			setDataOnReturn();
		} else {
			messageDialog = new MessageDialog(this, result.getResultInfo());
			messageDialog.show();
		}
	}

	private void setDataOnReturn() {
		shopName.setText(seller.getShopName());
		IC.getInstance().setForegound(seller.getShopPhoto(), faceImg);
		IC.getInstance().setBlurForegound(this, seller.getShopPhoto(),
				faceImgBg);
		if (seller instanceof Farmer) {
			Farmer farmer = (Farmer) seller;
			msg_item1.setText("��ֳ����ַ��" + farmer.getAddress());
			Location l2 = new Location();
			l2.setLongitude(seller.getLongitude());
			l2.setLatitude(seller.getLatitude());
			msg_item2.setText("���룺" + MyUtil.distance(Constants.location, l2)
					+ "ǧ��");
			msg_item3.setText("����ָ����" + farmer.getGrade());
		} else if (seller instanceof Fisherman) {
			Fisherman fisherman = (Fisherman) seller;
			msg_item1.setText("���������ڣ�" + fisherman.getShipPort());
			Date portTime = fisherman.getPortTime();
			if (fisherman.getSeaRecordId() == null) {
				msg_item2.setText("Ԥ������ʱ�䣺" + "δ����");
			} else {
				msg_item2.setText("Ԥ������ʱ�䣺" + MyUtil.getDays(portTime) + "���");
			}
			msg_item3.setText("����ָ����" + fisherman.getGrade());
		}
	}
	
	private void initViewpager() {
		int screenW = ScreenUtil.getWidth(this);
		// �����α�����Ӧ����
		int dipToPixels = UnitUtil.DipToPixels(this, 2);
		LinearLayout.LayoutParams lp = new LayoutParams((int) (screenW / 3.0),
				dipToPixels);
		//TODO
		cursor.setLayoutParams(lp);

		bmpW = cursor.getHeight();
		offset = (screenW / 3 - bmpW) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);

		views = new ArrayList<Fragment>();
		goodFragment = new SellerDetailGoodFragment();
		commentFragment = new SellerDetailCommentFragment();
		sellerFragment = new SellerDetailSellerFragment();
		views.add(goodFragment);
		views.add(commentFragment);
		views.add(sellerFragment);

		myPagerAdapter = new FragmentViewPagerAdapter(
				getSupportFragmentManager(), pager, views, offset, bmpW, cursor);
		pager.setAdapter(myPagerAdapter);
		pager.setCurrentItem(0);
	}
	
	class MyClickListener implements View.OnClickListener {
		int index = 0;

		public MyClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			pager.setCurrentItem(index);
		}
	}

	public Seller getShop() {
		return seller;
	}
	
	public void onBack(View v) {
		this.finish();
	}


}
