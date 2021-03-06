package com.zhanjixun.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhanjixun.R;
import com.zhanjixun.adapter.OrderListAdapter;
import com.zhanjixun.data.Constants;
import com.zhanjixun.data.DC;
import com.zhanjixun.data.TaskTag;
import com.zhanjixun.domain2.BaseResult;
import com.zhanjixun.domain2.Order;
import com.zhanjixun.interfaces.OnDataReturnListener;
import com.zhanjixun.utils.MyGson;
import com.zhanjixun.utils.LogUtils;
import com.zhanjixun.utils.ScreenUtil;
import com.zhanjixun.utils.StringUtil;
import com.zhanjixun.utils.UnitUtil;
import com.zhanjixun.views.LoadingDialog;
import com.zhanjixun.views.MessageDialog;


public class OrderFragment extends Fragment implements OnDataReturnListener,
		OnRefreshListener<ListView> {
	private int indexPage[] = new int[5];
	private final int PAGE_SIZE = 7;

	private TextView t1;
	private TextView t2;
	private TextView t3;
	private TextView t4;
	private TextView t5;

	private ImageView cursor;
	private ViewPager pager;
	private int offset = 0;
	private int currIndex = 0;
	private int bmpW;

	private LoadingDialog dialog;
	private ListViewKeeper[] lvKeeper = new ListViewKeeper[5];
	private String id;
	private MessageDialog messageDialog;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_order, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//		messageDialog = new MessageDialog(getActivity());
		initViews();
		
	}
	
	/**
	 * 删除后的数据进行刷新
	 */
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		initData();
	}

	private void initViews() {
		messageDialog = new MessageDialog(getActivity());
		initTextView();
		initImageView();
		initViewPage();
	}
	
	/**
	 * 加载页面数据
	 */
	private void initData() {
		for (int i = 0; i < indexPage.length; i++) {
			indexPage[i] = 1;
		}
		id = com.zhanjixun.data.Constants.user.getUserId();
		if (!StringUtil.isEmptyString(id)) {
			dialog = new LoadingDialog(getActivity());
			dialog.show();
			DC.getInstance().getAllOrder(this, id, indexPage[0]++, PAGE_SIZE);
			DC.getInstance().getUnPayOrder(this, id, indexPage[1]++, PAGE_SIZE);
			DC.getInstance().getUnSentOrder(this, id, indexPage[2]++, PAGE_SIZE);
			DC.getInstance().getUngetOrder(this, id, indexPage[3]++, PAGE_SIZE);
			DC.getInstance().getUnCommentOrder(this, id, indexPage[4]++, PAGE_SIZE);
		} else {
			LogUtils.v("用户没有登录");
		}
	}

	private void initViewPage() {
		pager = (ViewPager) getActivity().findViewById(
				R.id.order_home_viewPager);
		
		ArrayList<View> list = new ArrayList<View>();
		for (int i = 0; i < 5; i++) {
			//TODO
			PullToRefreshListView lv = (PullToRefreshListView) View.inflate(
					getActivity(), R.layout.listview_order, null);
			lv.setMode(Mode.PULL_FROM_END);
			
			List<Order> dataList = new ArrayList<Order>();
			OrderListAdapter adapter = new OrderListAdapter(getActivity(),
					dataList);
			lv.setAdapter(adapter);
			lv.setTag(i);
			
			lv.setOnRefreshListener(this);
			
			lvKeeper[i] = new ListViewKeeper();
			lvKeeper[i].setListView(lv);
			lvKeeper[i].setAdapter(adapter);
			lvKeeper[i].setDataList(dataList);
			list.add(lv);
		}
		pager.setAdapter(new MyPagerAdapter(list));
		pager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void initImageView() {
		cursor = (ImageView) getActivity()
				.findViewById(R.id.image_order_cursor);
		int screenW = ScreenUtil.getWidth(getActivity());
		// 设置游标自适应长度
		int dipToPixels = UnitUtil.DipToPixels(getActivity(), 2);
		LinearLayout.LayoutParams lp = new LayoutParams((int) (screenW / 5.0),
				dipToPixels);
		cursor.setLayoutParams(lp);

		bmpW = cursor.getHeight();
		offset = (screenW / 5 - bmpW) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);
	}

	private void initTextView() {
		// TextView
		t1 = (TextView) getActivity().findViewById(R.id.fragment_order_all);
		t2 = (TextView) getActivity().findViewById(R.id.fragment_order_waitPay);
		t3 = (TextView) getActivity()
				.findViewById(R.id.fragment_order_waitSend);
		t4 = (TextView) getActivity().findViewById(
				R.id.fragment_order_waitCargo);
		t5 = (TextView) getActivity().findViewById(
				R.id.fragment_order_waitAppraise);

		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));
		t4.setOnClickListener(new MyOnClickListener(3));
		t5.setOnClickListener(new MyOnClickListener(4));
	}

	private void setTextViewBg(int index) {
		t1.setTextColor(0xaa000000);
		t2.setTextColor(0xaa000000);
		t3.setTextColor(0xaa000000);
		t4.setTextColor(0xaa000000);
		t5.setTextColor(0xaa000000);
		switch (index) {
		case 0:
			t1.setTextColor(getActivity().getResources()
					.getColor(R.color.theme));
			break;
		case 1:
			t2.setTextColor(getActivity().getResources()
					.getColor(R.color.theme));
			break;
		case 2:
			t3.setTextColor(getActivity().getResources()
					.getColor(R.color.theme));
			break;
		case 3:
			t4.setTextColor(getActivity().getResources()
					.getColor(R.color.theme));
			break;
		case 4:
			t5.setTextColor(getActivity().getResources()
					.getColor(R.color.theme));
			break;
		default:
			break;
		}
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		int third = one * 3;
		int four = one * 4;

		@Override
		public void onPageSelected(int i) {
			Animation animation = null;
			switch (i) {
			case 0:
				animation = new TranslateAnimation(one * currIndex, 0, 0, 0);
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else {
					animation = new TranslateAnimation(one * currIndex, one, 0,
							0);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else {
					animation = new TranslateAnimation(one * currIndex, two, 0,
							0);
				}
				break;
			case 3:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, third, 0, 0);
				} else {
					animation = new TranslateAnimation(one * currIndex, third,
							0, 0);
				}
				break;
			case 4:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, four, 0, 0);
				} else {
					animation = new TranslateAnimation(one * currIndex, four,
							0, 0);
				}
				break;
			}
			currIndex = i;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
			setTextViewBg(i);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

	}

	public class MyPagerAdapter extends PagerAdapter {
		List<View> list = new ArrayList<View>();

		public MyPagerAdapter(ArrayList<View> list2) {
			this.list = list2;
		}

		// 移动当前的Viewpage
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ViewPager pViewPager = ((ViewPager) container);
			pViewPager.removeView(list.get(position));
		}

		// 判断是否由对象产生页面
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		// 返回当前分页数
		@Override
		public int getCount() {
			return list.size();
		}

		// 返回一个对象该对象表明PagerAapter选择哪个对象放在当前的ViewPager
		@Override
		public Object instantiateItem(View arg0, int arg1) {
			ViewPager pViewPager = ((ViewPager) arg0);
			pViewPager.addView(list.get(arg1));
			return list.get(arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			pager.setCurrentItem(index);
			setTextViewBg(index);
		}
	}


	private class ListViewKeeper {

		private PullToRefreshListView listView;

		private Adapter adapter;

		private List<Order> dataList;

		/**
		 * @return listView
		 */
		public PullToRefreshListView getListView() {
			return listView;
		}

		/**
		 * @param lv
		 *            要设置的 listView
		 */
		public void setListView(PullToRefreshListView lv) {
			this.listView = lv;
		}

		/**
		 * @return adapter
		 */
		public Adapter getAdapter() {
			return adapter;
		}

		/**
		 * @param adapter
		 *            要设置的 adapter
		 */
		public void setAdapter(Adapter adapter) {
			this.adapter = adapter;
		}

		/**
		 * @return dataList
		 */
		public List<Order> getDataList() {
			return dataList;
		}

		/**
		 * @param dataList2
		 *            要设置的 dataList
		 */
		public void setDataList(List<Order> dataList2) {
			this.dataList = dataList2;
		}

	}

	@Override
	public void onDataReturn(String taskTag, BaseResult result, String json) {
		dialog.dismiss();
		if (result.getServiceResult()) {
			int index = 0;
			if (taskTag.equals(TaskTag.ORDER_ALL)) {
				index = 0;
			}
			if (taskTag.equals(TaskTag.ORDER_UN_PAY)) {
				index = 1;
			}
			if (taskTag.equals(TaskTag.ORDER_UN_SENT)) {
				index = 2;
			}
			if (taskTag.equals(TaskTag.ORDER_UN_GET)) {
				index = 3;
			}
			if (taskTag.equals(TaskTag.ORDER_UN_COMMENT)) {
				index = 4;
			}
			List<Order> orders = new ArrayList<Order>();
			orders = MyGson.getInstance().fromJson(
					result.getResultParam().get("ordersList"),
					new TypeToken<List<Order>>() {
					}.getType());
			
			if (orders.size() == 0)
				indexPage[index] = indexPage[index] - 1 ;
				
			//修改这里
			if(orders.size() == 0) {
				return;
			}		
			if (lvKeeper[index].getDataList().size() != 0) {
				
				//TODO  4.19 修改订单消除的BUG
//				lvKeeper[index].getDataList().clear();
				lvKeeper[index].getDataList().addAll(orders);
//				refresh = false;
			} else {
				lvKeeper[index].getDataList().addAll(orders);
			}
			
			updataListViewData(index);
		} else {
			messageDialog.setMessage(result.getResultInfo());
			messageDialog.show();
		}
	}

	private void updataListViewData(int i) {
		((OrderListAdapter) lvKeeper[i].getAdapter()).notifyDataSetChanged();
		lvKeeper[i].getListView().onRefreshComplete();
		
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		int tag = (int) refreshView.getTag();
		//TODO
//		refresh = true;
		LogUtils.v(tag + "");
		if (!StringUtil.isEmptyString(Constants.user.getUserId())) {
			if (tag == 0) {
				DC.getInstance().getAllOrder(this, id, indexPage[0]++, PAGE_SIZE);
			}
			if (tag == 1) {
				DC.getInstance().getUnPayOrder(this, id, indexPage[1]++, PAGE_SIZE);
			}
			if (tag == 2) {
				DC.getInstance()
						.getUnSentOrder(this, id, indexPage[2]++, PAGE_SIZE);
			}
			if (tag == 3) {
				DC.getInstance().getUngetOrder(this, id, indexPage[3]++, PAGE_SIZE);
			}
			if (tag == 4) {
				DC.getInstance().getUnCommentOrder(this, id, indexPage[4]++,
						PAGE_SIZE);
			}
		} else {
			LogUtils.v("用户没有登录");
		}
		
	}

}