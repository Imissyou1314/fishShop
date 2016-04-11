package com.zhanjixun.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.zhanjixun.R;
import com.zhanjixun.activity.ShopDetailActivity;
import com.zhanjixun.adapter.SellerGoodsListAdapter;
import com.zhanjixun.data.DC;
import com.zhanjixun.data.TaskTag;
import com.zhanjixun.domain2.BaseResult;
import com.zhanjixun.domain2.Good;
import com.zhanjixun.interfaces.OnDataReturnListener;
import com.zhanjixun.utils.MyGson;
import com.zhanjixun.utils.LogUtils;
import com.zhanjixun.views.MessageDialog;



/**
 * 卖家 商品的页面
 * @author Imissyou
 *
 */
public class SellerDetailGoodFragment extends Fragment implements
		OnDataReturnListener, OnRefreshListener<ListView> {

	private int pageIndex = 1;
	private final int PAGE_SIZE = 7;
	private PullToRefreshListView listGoods;
	private SellerGoodsListAdapter adapter;
	private String shopId;
	private List<Good> goods = new ArrayList<Good>();
	
	
	private ShopDetailActivity mMainActivity;
	protected boolean isShowTitle = true;
	private boolean isPause = false;
	
	//TODO

	public SellerDetailGoodFragment(ShopDetailActivity shopDetailActivity) {
		this.mMainActivity = shopDetailActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_seller_detail_goods,
				container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initData();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		Log.d("Miss GOOD onPause", "显示");
		isPause  = true;
		//TODO 可能去掉
		mMainActivity.visibleView();
		super.onPause();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		isShowTitle = true;
		isPause = false;
		super.onResume();
	}
	
	private void initView() {
		listGoods = (PullToRefreshListView) getView().findViewById(
				R.id.listview_seller_detail_goods);
		listGoods.setMode(Mode.PULL_FROM_END);
		adapter = new SellerGoodsListAdapter(getActivity(), goods);
		listGoods.setAdapter(adapter);
		listGoods.setOnRefreshListener(this);
		listGoods.setOnScrollListener(new OnScrollListener() {
			
			int mFirstVisibleItem = 0;
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				 if (view instanceof ListView &&
						 scrollState == 0 &&
						 mFirstVisibleItem == 0) {
					 Log.d("滑动ListView  ", scrollState + "::::Miss");
					 mMainActivity.visibleView();
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if ((isShowTitle || isPause) && mFirstVisibleItem > 6) {
					mMainActivity.visibleView();
					return;
				}
				 if (view instanceof ListView && 
						 ((firstVisibleItem + visibleItemCount) == (totalItemCount -1))) {
					 Log.d("Goods 滑动ListView  ", firstVisibleItem + "::::Miss");
					 mMainActivity.goneView();
					 //刷新页面
					 adapter.notifyDataSetChanged();
				}
				Log.d("滑动", firstVisibleItem + "::::Miss");
				mFirstVisibleItem = firstVisibleItem;
			}
		});
	}

	private void initData() {
		shopId = ((ShopDetailActivity) getActivity()).getShop().getShopId();
		if (shopId != null) {
			DC.getInstance().getSellerGoods(this, shopId, pageIndex++,
					PAGE_SIZE);
		} else {
			LogUtils.v("shopId=null");
		}
	}

	@Override
	public void onDataReturn(String taskTag, BaseResult result, String json) {
		
		if (result.getServiceResult()) {
			
			if (taskTag.equals(TaskTag.SELLER_GOODS)) {
				List<Good> g = MyGson.getInstance().fromJson(
						result.getResultParam().get("goodsList"),
						new TypeToken<List<Good>>() {
						}.getType());
				if (g.size() != 0) {
					goods.addAll(g);
				} else {
					pageIndex --;
				}
				initListViewData();
			}
		} else {
			new MessageDialog(getActivity(), result.getResultInfo()).show();
		}

	}

	private void initListViewData() {
		adapter.setGoods(goods);
		adapter.notifyDataSetInvalidated();
		listGoods.onRefreshComplete();
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		isShowTitle = false;
		DC.getInstance().getSellerGoods(this, shopId, pageIndex++, PAGE_SIZE);
	}
}
