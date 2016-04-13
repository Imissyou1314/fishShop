package com.zhanjixun.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhanjixun.R;
import com.zhanjixun.activity.ShopDetailActivity;
import com.zhanjixun.adapter.SellerCommentsAdapter;
import com.zhanjixun.data.DC;
import com.zhanjixun.data.TaskTag;
import com.zhanjixun.domain2.BaseResult;
import com.zhanjixun.domain2.Comment;
import com.zhanjixun.domain2.CommentsSummary;
import com.zhanjixun.interfaces.OnDataReturnListener;
import com.zhanjixun.utils.MyGson;
import com.zhanjixun.utils.LogUtils;
import com.zhanjixun.utils.UnitUtil;
import com.zhanjixun.views.MessageDialog;

public class SellerDetailCommentFragment extends Fragment
		implements OnDataReturnListener, OnRefreshListener<ListView>, OnClickListener {

	private int allPageIndex = 1;
	private int goodPageIndex = 1;
	private int midPageIndex = 1;
	private int badPageIndex = 1;

	private final int PAGE_SIZE = 7;
	private String shopId;
	
	private PullToRefreshListView mListview;
	private SellerCommentsAdapter adapter;
	private List<Comment> comments = new ArrayList<Comment>();
	private CommentsSummary commentsSummary = new CommentsSummary();
	private RatingBar freshRatingBar;
	private RatingBar weightRatingBar;
	private RatingBar speedRatingBar;
	private Button allComment;
	private Button midComment;
	private Button goodComment;
	private Button badComment;
	private TextView weightQuality;
	private TextView freshQuality;
	private TextView speedQuality;
	private int commentType = 0;

	// TODO 是否显示Title评论那一块
	private ShopDetailActivity mMainactivity;
	private boolean isPause = false;
	private boolean isClick = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_seller_detail_comments, container, false);
		return view;
	}

	// TODO
	public SellerDetailCommentFragment(ShopDetailActivity activity) {
		this.mMainactivity = (ShopDetailActivity) activity;
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
		isPause = true;
		Log.d("MIss comment onPause", "Commnet Pause");
		Log.d("Miss onPause", this.getClass().getSimpleName());
		mMainactivity.goneView();
		super.onPause();
	}
	
	@Override
	public void onResume() {
		isPause = false;
		// TODO Auto-generated method stub
		Log.d("MIss comment onPause", "Commnet onResume");
		Log.d("Miss onResume", this.getClass().getSimpleName());
		mMainactivity.goneView();
		super.onResume();
	}

	public void initView() {
        
		freshRatingBar = (RatingBar) getView().findViewById(R.id.id_ratingBar_freshQuality);
		weightRatingBar = (RatingBar) getView().findViewById(R.id.id_ratingBar_weightQuality);
		speedRatingBar = (RatingBar) getView().findViewById(R.id.id_ratingBar_speedQuality);

		mListview = (PullToRefreshListView) getView().findViewById(R.id.id_seller_detail_comments_listview);
		mListview.setMode(Mode.PULL_FROM_END);
		allComment = (Button) getView().findViewById(R.id.id_commentButton_all);
		goodComment = (Button) getView().findViewById(R.id.id_commentButton_good);
		midComment = (Button) getView().findViewById(R.id.id_commentButton_mid);
		badComment = (Button) getView().findViewById(R.id.id_commentButton_bad);
		weightQuality = (TextView) getView().findViewById(R.id.id_text_weightQuality);
		freshQuality = (TextView) getView().findViewById(R.id.id_text_freshQuality);
		speedQuality = (TextView) getView().findViewById(R.id.id_text_speedQuality);

		adapter = new SellerCommentsAdapter(getActivity(), comments);
		mListview.setAdapter(adapter);

		// TODO
		mListview.setOnScrollListener(new OnScrollListener() {
			
			int mfirstVisibleItem = 0;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				/**
				 * 下拉显示
				 * 1.滑动的View是ListView
				 * 2.在第一条的时候才行
				 */
				if (scrollState == 0 &&
						view instanceof ListView &&
						mfirstVisibleItem == 0) {
					Log.d("Comment stae  VISIBLE", "显示");
					
					mMainactivity.visibleView();
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				
				if(isPause){
					return;
				} else if(isClick) {
					isClick = false;
					return;
				}

				/**
				 * 1.该变布局的是ListView
				 * 2.滑动到最后一项
				 * 3.显示的第一条数据大于六时
				 */
				if (comments.size() > 0 && view instanceof ListView && 
						((visibleItemCount + firstVisibleItem) == totalItemCount)
						|| mfirstVisibleItem > 6) {
					Log.d("MIss comment onPause", "不显示ppppp");
					mMainactivity.goneView();
				}
				mfirstVisibleItem = firstVisibleItem;
			}
		});
		mListview.setOnRefreshListener(this);

		allComment.setBackground(getResources().getDrawable(R.drawable.orange_button));

		allComment.setOnClickListener(this);
		goodComment.setOnClickListener(this);
		midComment.setOnClickListener(this);
		badComment.setOnClickListener(this);
	}

	private void initListViewData() {
		adapter.notifyDataSetChanged();
		mListview.onRefreshComplete();
	}

	private void initData() {
		shopId = ((ShopDetailActivity) getActivity()).getShop().getShopId();
		if (shopId != null) {
			DC.getInstance().getCommentsSummary(this, shopId);
			DC.getInstance().getAllComments(this, shopId, allPageIndex++, PAGE_SIZE);
		} else {
			LogUtils.v("shopId=null");
		}
	}

	private void initCommentButton() {
		allComment.setText("全部" + "(" + commentsSummary.getCommentSize() + ")");
		goodComment.setText("好评" + "(" + commentsSummary.getGoodComment() + ")");
		midComment.setText("中评" + "(" + commentsSummary.getMidComment() + ")");
		badComment.setText("差评" + "(" + commentsSummary.getBedComment() + ")");
	}

	private void initRatingBar() {
		Float freshf = commentsSummary.getShop().getFreshQuality();
		Float weightf = commentsSummary.getShop().getWeightQuality();
		Float speedf = commentsSummary.getShop().getSpeedQuality();
		freshRatingBar.setRating(freshf);
		weightRatingBar.setRating(weightf);
		speedRatingBar.setRating(speedf);

		freshQuality.setText(UnitUtil.NumberFormat(freshf.floatValue(), 1));
		weightQuality.setText(UnitUtil.NumberFormat(weightf.floatValue(), 1));
		speedQuality.setText(UnitUtil.NumberFormat(speedf.floatValue(), 1));
	}

	public void setCommentBtnClick(Button b) {
		allComment.setBackground(getResources().getDrawable(R.drawable.gray_button));
		goodComment.setBackground(getResources().getDrawable(R.drawable.gray_button));
		midComment.setBackground(getResources().getDrawable(R.drawable.gray_button));
		badComment.setBackground(getResources().getDrawable(R.drawable.gray_button));
		b.setBackground(getResources().getDrawable(R.drawable.orange_button));
	}

	@Override
	public void onDataReturn(String taskTag, BaseResult result, String json) {
		if (result.getServiceResult()) {

			// 评论概要
			if (taskTag == TaskTag.COMMENT_SUMMARY) {
				commentsSummary = MyGson.getInstance().fromJson(result.getResultParam().toString(),
						CommentsSummary.class);
				initRatingBar();
				initCommentButton();
			}
			// 评论详情
			if (taskTag == TaskTag.COMMENT_DETAIL) {
				List<Comment> c = MyGson.getInstance().fromJson(result.getResultParam().get("comment"),
						new TypeToken<List<Comment>>() {
						}.getType());
				if (c.size() != 0) {
					comments.addAll(c);
				} else {
					if (allPageIndex > 1) {
						allPageIndex--;
					}
				}
				initListViewData();
				Toast.makeText(getActivity(), c.size() == 0 ? "没有更多数据了" : "加载成功", Toast.LENGTH_LONG).show();
			}
		} else {
			new MessageDialog(getActivity(), result.getResultInfo()).show();
		}
	}

	@Override
	public void onClick(View v) {
		
		isClick = true;
		
		switch (v.getId()) {
		case R.id.id_commentButton_all:
			setCommentBtnClick(allComment);
			comments.clear();
			allPageIndex = 1;
			DC.getInstance().getAllComments(this, shopId, allPageIndex++, PAGE_SIZE);
			commentType = 0;
			break;
		case R.id.id_commentButton_good:
			setCommentBtnClick(goodComment);
			comments.clear();
			goodPageIndex = 1;
			DC.getInstance().getGoodComments(this, shopId, goodPageIndex++, PAGE_SIZE);
			commentType = 1;
			break;
		case R.id.id_commentButton_mid:
			setCommentBtnClick(midComment);
			comments.clear();
			midPageIndex = 1;
			DC.getInstance().getMidComments(this, shopId, midPageIndex++, PAGE_SIZE);
			commentType = 2;
			break;
		case R.id.id_commentButton_bad:
			setCommentBtnClick(badComment);
			comments.clear();
			badPageIndex = 1;
			DC.getInstance().getBadComments(this, shopId, badPageIndex++, PAGE_SIZE);
			commentType = 3;
			break;
		default:
			break;
		}
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		switch (commentType) {
		case 0:
			DC.getInstance().getAllComments(this, shopId, allPageIndex++, PAGE_SIZE);
			break;
		case 1:
			DC.getInstance().getGoodComments(this, shopId, goodPageIndex++, PAGE_SIZE);
			break;
		case 2:
			DC.getInstance().getMidComments(this, shopId, midPageIndex++, PAGE_SIZE);
			break;
		case 3:
			DC.getInstance().getBadComments(this, shopId, badPageIndex++, PAGE_SIZE);
			break;
		default:
			break;
		}
	}
}
