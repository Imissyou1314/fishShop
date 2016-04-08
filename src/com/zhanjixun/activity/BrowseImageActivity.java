package com.zhanjixun.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.zhanjixun.R;
import com.zhanjixun.adapter.BrowseViewPagerAdapter;
import com.zhanjixun.data.IC;

public class BrowseImageActivity extends Activity implements OnClickListener {

	private ViewPager viewPager;
	private BrowseViewPagerAdapter adapter;
	private List<ImageView> images = new ArrayList<ImageView>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_image);
		viewPager = (ViewPager) findViewById(R.id.id_browse_viewpager);
		String[] urls = getIntent().getStringArrayExtra("urls");
		for (int i = 0; i < urls.length; i++) {
			ImageView defaultImage = new ImageView(this);
			defaultImage.setImageResource(R.drawable.default_bg);
			images.add(defaultImage);
		}
		adapter = new BrowseViewPagerAdapter(images);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(getIntent().getIntExtra("index", 0));
		images.clear();
		//¼ÓÔØÍ¼Æ¬
		for (int i = 0; i < urls.length; i++) {
			ImageView iv = new ImageView(this);
			IC.getInstance().setForegoundOrigin(urls[i], iv);
			iv.setOnClickListener(this);
			images.add(iv);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		this.finish();
	}

}
