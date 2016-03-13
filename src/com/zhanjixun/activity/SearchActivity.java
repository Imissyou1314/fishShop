package com.zhanjixun.activity;


import com.zhanjixun.R;
import com.zhanjixun.base.BackActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;

/**
 * 搜索页面
 * @author Imissyou
 *
 */
public class SearchActivity extends BackActivity{
	
	private EditText searchET;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initViews();
		intent = new Intent(this,GoodListActivity.class);
		intent.putExtra("kind", GoodListActivity.SEARCH);
	}

	private void initViews() {
		searchET = (EditText) findViewById(R.id.searche_ET);
		
		/*设置焦点变化触发事件*/
		searchET.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ( keyCode == KeyEvent.KEYCODE_ENTER && 
						event.getAction() == KeyEvent.ACTION_DOWN) {
					initData();
				}
				return false;
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		searchET.setText("");
	}
	
	/*加载数据*/
	private void initData() {
		String search = searchET.getText().toString().toString();
		intent.putExtra("search", search);	
		this.startActivity(intent);
	}
}
