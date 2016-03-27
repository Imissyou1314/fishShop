package com.zhanjixun.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zhanjixun.R;
import com.zhanjixun.adapter.MyCommentAdapter;
import com.zhanjixun.base.BackActivity;
import com.zhanjixun.data.Constants;
import com.zhanjixun.data.DC;
import com.zhanjixun.data.IC;
import com.zhanjixun.data.TaskTag;
import com.zhanjixun.domain2.BaseResult;
import com.zhanjixun.domain2.Comment;
import com.zhanjixun.interfaces.OnDataReturnListener;
import com.zhanjixun.utils.MyGson;
import com.zhanjixun.utils.ScreenUtil;
import com.zhanjixun.views.LoadingDialog;
import com.zhanjixun.views.MessageDialog;
import com.zhanjixun.views.RoundImageView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MyCommentActivity extends BackActivity implements
		OnDataReturnListener, OnRefreshListener<ScrollView> {

	private RoundImageView faceImg;
	private TextView userNameTv;
	private ListView commentLv;
	private ImageView faceBg;
	private final int PAGE_SIZE = 5;
	private int pageIndex = 1;
	private List<Comment> comments = new ArrayList<Comment>();
	private MyCommentAdapter adapter;
	private LoadingDialog dialog;
	private PullToRefreshScrollView mScrollView;
	private FrameLayout title;
	private FrameLayout frameLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me_comment);
		initViews();
		initData();
	}

	private void initViews() {
		faceBg = (ImageView) findViewById(R.id.img_mycomment_bg);
		faceImg = (RoundImageView) findViewById(R.id.img_mycomment_face);
		
		
		
		userNameTv = (TextView) findViewById(R.id.text_mycomment_name);
		commentLv = (ListView) findViewById(R.id.list_mycomment_data);
		
		
		frameLayout = (FrameLayout) findViewById(R.id.frameLayout_face_mycomment);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				(int) ((ScreenUtil.getHeight(this) / 5.0) * 2));
		frameLayout.setLayoutParams(lp);
		mScrollView = (PullToRefreshScrollView) findViewById(R.id.scroll_mycomment);
		mScrollView.setMode(Mode.PULL_FROM_END);
		title = (FrameLayout) findViewById(R.id.title_mycomment);
		title.setAlpha(0);
		mScrollView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:

					break;
				case MotionEvent.ACTION_MOVE:
					int scrollY = view.getScrollY();
					int height = view.getHeight();
					title.setAlpha((float) (scrollY / 350.00));
					if (scrollY == 0) {// TOP
						title.setAlpha(0);
					}
					if ((scrollY + height) == mScrollView.getChildAt(0)
							.getMeasuredHeight()) {
						MyCommentActivity.this.onRefresh(null);
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
		adapter = new MyCommentAdapter(comments, this);
		commentLv.setAdapter(adapter);
		mScrollView.setOnRefreshListener(this);
	}

	private void initData() {
		IC.getInstance().setBlurForegound(this, Constants.user.getHeadImage(),
				faceBg);
		IC.getInstance().setForegound(Constants.user.getHeadImage(), faceImg);
		userNameTv.setText(Constants.user.getUserName());
		dialog = new LoadingDialog(this);
		dialog.show();
		DC.getInstance().getMyComment(this, Constants.user.getUserId(),
				pageIndex++, PAGE_SIZE);
	}

	@Override
	public void onDataReturn(String taskTag, BaseResult result, String json) {
		dialog.dismiss();
		if (result.getServiceResult()) {
			if (taskTag.equals(TaskTag.MY_COMMENT)) {
				List<Comment> c = MyGson.getInstance().fromJson(
						result.getResultParam().get("commentsArray"),
						new TypeToken<List<Comment>>() {
						}.getType());
				
				if (c.size() == 0) {
					pageIndex --;
				}
				
				comments.addAll(c);
				initListViewData();
			}
		} else {
			new MessageDialog(this, result.getResultInfo()).show();
		}
	}

	private void initListViewData() {
		adapter.notifyDataSetChanged();
		mScrollView.onRefreshComplete();
	}


	@Override
	public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
		// TODO Auto-generated method stub
		DC.getInstance().getMyComment(this, Constants.user.getUserId(),
				pageIndex++, PAGE_SIZE);
		
	}

}
