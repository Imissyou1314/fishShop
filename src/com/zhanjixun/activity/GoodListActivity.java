package com.zhanjixun.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhanjixun.R;
import com.zhanjixun.adapter.GoodListAdapter;
import com.zhanjixun.adapter.TopGoodsAdapter;
import com.zhanjixun.base.BackActivity;
import com.zhanjixun.data.DC;
import com.zhanjixun.data.TaskTag;
import com.zhanjixun.domain2.BaseResult;
import com.zhanjixun.domain2.GoodListItem;
import com.zhanjixun.interfaces.OnDataReturnListener;
import com.zhanjixun.utils.LogUtils;
import com.zhanjixun.utils.MyGson;
import com.zhanjixun.views.LoadingDialog;
import com.zhanjixun.views.MessageDialog;

public class GoodListActivity extends BackActivity implements
		OnDataReturnListener, OnItemClickListener, OnRefreshListener<ListView> {

	private int pageIndex = 1;
	private final int PAGE_SIZE = 7;

	public static final int FISH = 1;
	public static final int SHRIMP = 2;
	public static final int CRAD = 3;
	public static final int SHELLFISH = 4;
	public static final int SQUID = 5;
	public static final int GINSENG = 6;
	public static final int OTHERS = 7;
	public static final int SEARCH = 8;

	private TextView titleTv;
	private PullToRefreshListView goodLv;
	private GoodListAdapter adapter;

	private TopGoodsAdapter topAdapter;
	private LoadingDialog dialog;
	private String categoryId;
	private List<GoodListItem> goods = new ArrayList<GoodListItem>();
	private String search = null;
	private Integer kind;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_good_list);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		initViews();
		initData();
	}

	private void initViews() {
		kind = (Integer) getIntent().getExtras().get("kind");
		titleTv = (TextView) findViewById(R.id.text_activity_goodlist_title);
		goodLv = (PullToRefreshListView) findViewById(R.id.text_activity_goodlist_list);
		goodLv.setMode(Mode.PULL_FROM_END);
		seletTitle(kind);

	}

	/*
	 * 自定义加载数据
	 */
	private void seletTitle(int kind) {
		adapter = new GoodListAdapter(this, goods);
		goodLv.setAdapter(adapter);
		switch (kind) {
		case FISH:
			titleTv.setText("海水鱼类");
			categoryId = "1";
			break;
		case SHRIMP:
			titleTv.setText("虾类");
			categoryId = "2";
			break;
		case CRAD:
			titleTv.setText("蟹类");
			categoryId = "3";
			break;
		case SHELLFISH:
			titleTv.setText("贝类");
			categoryId = "4";
			break;
		case SQUID:
			titleTv.setText("头足类");
			categoryId = "5";
			break;
		case GINSENG:
			titleTv.setText("海参类");
			categoryId = "6";
			break;
		case OTHERS:
			titleTv.setText("其他");
			categoryId = "7";
			//TODO
			topAdapter = new TopGoodsAdapter(this, goods);
			goodLv.setAdapter(topAdapter);
			break;
		case SEARCH:
			titleTv.setText("搜索结果");
			// categoryId = SEARCH + "";
			search = getIntent().getStringExtra("search");
			break;
		default:
			break;
		}
		goodLv.setOnRefreshListener(this);
		goodLv.setOnItemClickListener(this);
	}

	/* 请求服务器数据 */
	private void initData() {
		if (categoryId != null && !categoryId.equals("")) {
			dialog = new LoadingDialog(this);
			dialog.show();
			if (categoryId.equals("7")) {
				DC.getInstance().getTopCategory(this);
			} else {
				DC.getInstance().getGoodList(this, categoryId, pageIndex++,
						PAGE_SIZE);
			}
		} else if (null != search && !search.equals("")) {
			dialog = new LoadingDialog(this);
			dialog.show();
			DC.getInstance().searchGoods(this, search, pageIndex++, PAGE_SIZE);
		} else {
			LogUtils.w("categoryId=null");
		}
	}

	/**
	 * 刷新页面
	 */
	private void initListViewData() {
		adapter.notifyDataSetChanged();
		if (topAdapter != null) {
			topAdapter.notifyDataSetInvalidated();
		}
		goodLv.onRefreshComplete();
	}

	@Override
	public void onDataReturn(String taskTag, BaseResult result, String json) {
		dialog.dismiss();
		if (result.getServiceResult()) {

			if (taskTag.equals(TaskTag.GOOD_LIST)
					|| taskTag.equals(TaskTag.SEARCH_GOOD)
					|| taskTag.equals(TaskTag.GET_TOPCATWFROY)) {
				List<GoodListItem> items = MyGson.getInstance().fromJson(
						result.getResultParam().get("categoryList"),
						new TypeToken<List<GoodListItem>>() {
						}.getType());
				if (items.size() != 0) {
					goods.addAll(items);
				} else {
					pageIndex --;
				}
				initListViewData();
			}
		} else {
			new MessageDialog(this, result.getResultInfo()).show();
		}

	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		GoodListItem item = (GoodListItem) parent.getAdapter()
				.getItem(position);
		/**
		 * Imissyou  添从其他类别转到主类别
		 */
		if (kind == GoodListActivity.OTHERS) {
			//刷新页面
			categoryId = String.valueOf(position);
			kind = position;
			
			goods.clear();
			topAdapter = null;
			goodLv.setAdapter(adapter);
			fefreshView(item, position);
			
			return;
		}
		
		Intent intent = new Intent(this, GoodDetailActivity.class);
		intent.putExtra("back", titleTv.getText());
		intent.putExtra("simpleName", item.getCategorySimpleName());
		intent.putExtra("academicName", item.getCategoryAcademicName());
		intent.putExtra("EnglishName", item.getCategoryEnglishName());
		intent.putExtra("categoryId", item.getCategoryId());
		intent.putExtra("iamgeURL", item.getFishPhoto());
		startActivity(intent);
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		DC.getInstance().getGoodList(this, categoryId, pageIndex++, PAGE_SIZE);
	}
	
	/**
	 * 
	 * 刷新页面
	 * @param item
	 */
	public void fefreshView(GoodListItem item,int postion) {
		titleTv.setText(item.getCategorySimpleName());
		DC.getInstance().getGoodList(this, item.getCategoryId(), pageIndex,
				PAGE_SIZE);
	}

}
