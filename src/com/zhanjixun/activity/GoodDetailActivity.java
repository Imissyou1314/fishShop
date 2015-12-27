package com.zhanjixun.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.zhanjixun.R;
import com.zhanjixun.adapter.SellerListAdapter;
import com.zhanjixun.base.BackActivity;
import com.zhanjixun.data.DC;
import com.zhanjixun.data.IC;
import com.zhanjixun.data.TaskTag;
import com.zhanjixun.domain2.BaseResult;
import com.zhanjixun.domain2.Farmer;
import com.zhanjixun.domain2.Fisherman;
import com.zhanjixun.domain2.Seller;
import com.zhanjixun.interfaces.OnDataReturnListener;
import com.zhanjixun.utils.MyGson;
import com.zhanjixun.utils.LogUtils;
import com.zhanjixun.utils.StringUtil;
import com.zhanjixun.views.LoadingDialog;
import com.zhanjixun.views.MessageDialog;
import com.zhanjixun.views.ReflashListView;
import com.zhanjixun.views.ReflashListView.OnRefreshListener;
import com.zhanjixun.views.RoundImageView;

public class GoodDetailActivity extends BackActivity
		implements OnDataReturnListener, OnRefreshListener, OnItemClickListener {

	private String categoryId;
	private int pageIndex = 1;
	private final int PAGER_SIZE = 7;
	private TextView title;
	private LoadingDialog dialog;
	private RoundImageView image;
	private ArrayList<Seller> sellers = new ArrayList<Seller>();
	private ReflashListView dataLv;
	private SellerListAdapter adapter;
	private RelativeLayout breedWay_popMenu;
	private RelativeLayout sortWay_popMenu;
	private TextView breedWay_title;
	private TextView sortWay_title;
	private TextView simpleName;
	private TextView academicName;
	private TextView englishName;
	private ImageView imageBg;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_good_detail);
		initView();
		initData();
	}

	private void initView() {
		// 设置返回页的名字
		TextView backTv = (TextView) findViewById(R.id.back_good_detail);
		backTv.setText(getIntent().getStringExtra("back"));

		image = (RoundImageView) findViewById(R.id.id_category_good_detail_seafoodImage);
		imageBg = (ImageView) findViewById(R.id.id_category_good_detail_seafoodImage_bg);
		breedWay_popMenu = (RelativeLayout) findViewById(R.id.id_popup1);
		sortWay_popMenu = (RelativeLayout) findViewById(R.id.id_popup2);
		breedWay_title = (TextView) findViewById(R.id.id_breadWay_title);
		sortWay_title = (TextView) findViewById(R.id.id_sortWay_title);
		simpleName = (TextView) findViewById(R.id.id_category_good_detail_seafoodPopularName); // 俗名
		academicName = (TextView) findViewById(R.id.id_category_good_detail_seafoodScienticName); // 学名
		englishName = (TextView) findViewById(R.id.id_category_good_detail_seafoodEnglishName); // 英文名
		title = (TextView) findViewById(R.id.text_gooddetailAty_title);

		dataLv = (ReflashListView) findViewById(R.id.id_list_seller);
		adapter = new SellerListAdapter(this, sellers);
		dataLv.setAdapter(adapter);
		dataLv.setOnRefreshListener(this);
		dataLv.setOnItemClickListener(this);
		initPopupMenu();
	};

	public void initData() {
		title.setText(getIntent().getStringExtra("simpleName"));
		simpleName.setText(getIntent().getStringExtra("simpleName"));
		academicName.setText(getIntent().getStringExtra("academicName"));
		englishName.setText(getIntent().getStringExtra("EnglishName"));
		this.categoryId = getIntent().getStringExtra("categoryId");

		IC.getInstance().setForegound(getIntent().getStringExtra("iamgeURL"), image);
		IC.getInstance().setBlurForegound(this, getIntent().getStringExtra("iamgeURL"), imageBg);

		if (!StringUtil.isEmptyString(categoryId)) {
			dialog = new LoadingDialog(this);
			dialog.show();
			DC.getInstance().getAllGoodSellers(this, categoryId, pageIndex++, PAGER_SIZE);
		} else {
			LogUtils.w("categoryId为空");
		}
	}

	public void initListData() {
		adapter.notifyDataSetChanged();
		dataLv.hideFooterView();

	}

	/**
	 * 初始化养殖方式和排序选项菜单
	 */
	public void initPopupMenu() {
		breedWay_popMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PopupMenu popupMenu = new PopupMenu(GoodDetailActivity.this, v);
				popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						pageIndex = 1;
						switch (item.getItemId()) {
						case R.id.menu_all:
							breedWay_title.setText("全部");
							Toast.makeText(GoodDetailActivity.this, "你选择了全部", Toast.LENGTH_SHORT).show();
							DC.getInstance().getGoodDatailWild(GoodDetailActivity.this, TaskTag.GOOD_ALL,
									pageIndex++, PAGER_SIZE, 1, categoryId);
							return true;
						case R.id.menu_wild:
							breedWay_title.setText("野生");
							Toast.makeText(GoodDetailActivity.this, "你选择了野生", Toast.LENGTH_SHORT).show();
							DC.getInstance().getGoodDatailWild(GoodDetailActivity.this, TaskTag.GOOD_WILD, pageIndex++,
									PAGER_SIZE, 1, categoryId);
							return true;
						case R.id.menu_breed:
							breedWay_title.setText("养殖");
							Toast.makeText(GoodDetailActivity.this, "你选择了养殖", Toast.LENGTH_SHORT).show();
							DC.getInstance().getGoodDatailWild(GoodDetailActivity.this, TaskTag.GOOD_BREAD, pageIndex++,
									PAGER_SIZE, 1 ,categoryId);
							return true;
						default:
							return false;
						}
					}
				});
				MenuInflater mi = popupMenu.getMenuInflater();
				mi.inflate(R.menu.catch_way_menu, popupMenu.getMenu());
				popupMenu.show();
			}
		});

		sortWay_popMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PopupMenu popupMenu = new PopupMenu(GoodDetailActivity.this, v);
				popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						pageIndex = 1;
						switch (item.getItemId()) {
						case R.id.menu_comprehensive_ranking:
							sortWay_title.setText("综合排序");

							Toast.makeText(GoodDetailActivity.this, "你选择了综合排序", Toast.LENGTH_SHORT).show();
							DC.getInstance().getGoodDatailWild(GoodDetailActivity.this, TaskTag.COMPREHENSIVE_RANKING,
									pageIndex++, PAGER_SIZE, 1, categoryId);
							return true;
							
						case R.id.menu_comment_highest:
							sortWay_title.setText("评价最高");

							Toast.makeText(GoodDetailActivity.this, "你选择了评价最高", Toast.LENGTH_SHORT).show();
							DC.getInstance().getGoodDatailWild(GoodDetailActivity.this, TaskTag.COMMENT_HIGHEST,
									pageIndex++, PAGER_SIZE, 2, categoryId);
							return true;
							
						case R.id.menu_send_price:
							sortWay_title.setText("起送价最低");
							Toast.makeText(GoodDetailActivity.this, "你选择了起送价最低", Toast.LENGTH_SHORT).show();
							DC.getInstance().getGoodDatailWild(GoodDetailActivity.this, TaskTag.SEND_PRICE,
									pageIndex++, PAGER_SIZE, 3, categoryId);
							return true;
							
						case R.id.menu_sales_volume:
							sortWay_title.setText("销量最高");
							Toast.makeText(GoodDetailActivity.this, "你选择了销量最高", Toast.LENGTH_SHORT).show();
							DC.getInstance().getGoodDatailWild(GoodDetailActivity.this, TaskTag.SALES_VOLUME,
									pageIndex++, PAGER_SIZE, 4, categoryId);
							return true;
						default:
							return false;
						}
					}
				});
				MenuInflater mi = popupMenu.getMenuInflater();
				mi.inflate(R.menu.sort_way_menu, popupMenu.getMenu());
				popupMenu.show();
			}
		});
	}

	@Override
	public void onLoadingMore(View v) {
		DC.getInstance().getAllGoodSellers(this, categoryId, pageIndex++, PAGER_SIZE);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Seller seller = (Seller) parent.getAdapter().getItem(position);
		Intent intent = new Intent(this, ShopDetailActivity.class);
		intent.putExtra("shopId", seller.getShopId());
		if (seller instanceof Farmer) {
			intent.putExtra("sellerType", "farmer");
		} else {
			intent.putExtra("sellerType", "fishman");
		}
		intent.putExtra("back", title.getText());
		startActivity(intent);
	}

	/**
	 * 返回数据解析
	 */
	@Override
	public void onDataReturn(String taskTag, BaseResult result, String json) {
		dialog.dismiss();
		sellers.clear();
		List<Seller> ss = new ArrayList<Seller>();
		Map<String, String> resultParm = result.getResultParam();
		
		Log.v("TaskTag", taskTag);
		if (result.getServiceResult()) {
			//所以有的商品
			if (taskTag.equals(TaskTag.GOOD_SELLER) || taskTag.equals(TaskTag.GOOD_ALL)) {
				List<Fisherman> fisher = MyGson.getInstance().fromJson(resultParm.get("shopList"),
						new TypeToken<List<Fisherman>>() {
						}.getType());
				List<Farmer> farmer = MyGson.getInstance().fromJson(resultParm.get("shopList"),
						new TypeToken<List<Farmer>>() {
						}.getType());
				for (int i = 0; i < fisher.size(); i++) {
					if (fisher.get(i).getShopType() == Seller.TYPE_FARMER) {
						ss.add(farmer.get(i));
					} else {
						ss.add(fisher.get(i));
					}
				}
			} else if (taskTag.equals(TaskTag.GOOD_WILD)) { //野生的商品
				List<Farmer> farmers = MyGson.getInstance().fromJson(resultParm.get("shopList"),
						new TypeToken<List<Farmer>>() {
						}.getType());
				Log.v("farmers", farmers.toString());
				for (Farmer farmer : farmers) {
					ss.add(farmer);
				}
			} else if (taskTag.equals(TaskTag.GOOD_BREAD)) { //养殖的商品
				List<Fisherman> fishers = MyGson.getInstance().fromJson(resultParm.get("shopList"),
						new TypeToken<List<Fisherman>>() {
						}.getType());
				for (Fisherman fisher : fishers) {
					ss.add(fisher);
				}
			}
			Log.v("SS  Miss===>", ss.toString());
			sellers.addAll(ss);
			Log.v("fresh", "Miss----->");
			initListData();
		} else {
			new MessageDialog(this, result.getResultInfo()).show();
		}
	}
}
