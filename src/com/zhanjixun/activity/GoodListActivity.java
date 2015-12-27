package com.zhanjixun.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zhanjixun.R;
import com.zhanjixun.adapter.GoodListAdapter;
import com.zhanjixun.base.BackActivity;
import com.zhanjixun.data.DC;
import com.zhanjixun.data.TaskTag;
import com.zhanjixun.domain2.BaseResult;
import com.zhanjixun.domain2.GoodListItem;
import com.zhanjixun.interfaces.OnDataReturnListener;
import com.zhanjixun.utils.MyGson;
import com.zhanjixun.utils.LogUtils;
import com.zhanjixun.views.LoadingDialog;
import com.zhanjixun.views.MessageDialog;
import com.zhanjixun.views.ReflashListView;
import com.zhanjixun.views.ReflashListView.OnRefreshListener;

public class GoodListActivity extends BackActivity implements
		OnDataReturnListener, OnRefreshListener, OnItemClickListener {

	private int pageIndex = 1;
	private final int PAGE_SIZE = 7;

	public static final int FISH = 1;
	public static final int SHRIMP = 2;
	public static final int CRAD = 3;
	public static final int SHELLFISH = 4;
	public static final int SQUID = 5;
	public static final int GINSENG = 6;
	public static final int OTHERS = 7;

	private TextView titleTv;
	private ReflashListView goodLv;
	private GoodListAdapter adapter;
	private LoadingDialog dialog;
	private String categoryId;
	private List<GoodListItem> goods = new ArrayList<GoodListItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_good_list);
		initViews();
		initData();
	}

	private void initViews() {
		titleTv = (TextView) findViewById(R.id.text_activity_goodlist_title);
		goodLv = (ReflashListView) findViewById(R.id.text_activity_goodlist_list);
		adapter = new GoodListAdapter(this, goods);
		goodLv.setAdapter(adapter);
		goodLv.setOnRefreshListener(this);
		goodLv.setOnItemClickListener(this);
		Integer kind = (Integer) getIntent().getExtras().get("kind");
		switch (kind) {
		case FISH:
			titleTv.setText("����");
			categoryId = "1";
			break;
		case SHRIMP:
			titleTv.setText("Ϻ��");
			categoryId = "2";
			break;
		case CRAD:
			titleTv.setText("з��");
			categoryId = "3";
			break;
		case SHELLFISH:
			titleTv.setText("����");
			categoryId = "4";
			break;
		case SQUID:
			titleTv.setText("������");
			categoryId = "5";
			break;
		case GINSENG:
			titleTv.setText("����");
			categoryId = "6";
			break;
		case OTHERS:
			titleTv.setText("����");
			break;
		default:
			break;
		}
	}

	private void initData() {
		if (categoryId != null) {
			dialog = new LoadingDialog(this);
			dialog.show();
			DC.getInstance().getGoodList(this, categoryId, pageIndex++,
					PAGE_SIZE);
		} else {
			LogUtils.w("categoryId=null");
		}
	}

	private void initListViewData() {
		adapter.notifyDataSetChanged();
		goodLv.hideFooterView();
	}

	@Override
	public void onDataReturn(String taskTag, BaseResult result, String json) {
		dialog.dismiss();
		if (result.getServiceResult()) {
			if (taskTag.equals(TaskTag.GOOD_LIST)) {
				List<GoodListItem> items = MyGson.getInstance().fromJson(result
						.getResultParam().get("categoryList"),
						new TypeToken<List<GoodListItem>>() {
						}.getType());
				if (items.size() != 0) {
					goods.addAll(items);
				}
				initListViewData();
				// Toast.makeText(this, items.size() == 0 ? "û�и���������" : "���سɹ�",
				// Toast.LENGTH_LONG).show();
			}
		} else {
			new MessageDialog(this, result.getResultInfo()).show();
		}

	}

	@Override
	public void onLoadingMore(View v) {
		DC.getInstance().getGoodList(this, categoryId, pageIndex++, PAGE_SIZE);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		GoodListItem item = (GoodListItem) parent.getAdapter()
				.getItem(position);
		Intent intent = new Intent(this, GoodDetailActivity.class);
		intent.putExtra("back", titleTv.getText());
		intent.putExtra("simpleName", item.getCategorySimpleName());
		intent.putExtra("academicName", item.getCategoryAcademicName());
		intent.putExtra("EnglishName", item.getCategoryEnglishName());
		intent.putExtra("categoryId", item.getCategoryId());
		intent.putExtra("iamgeURL", item.getFishPhoto());
		startActivity(intent);
	}

}
