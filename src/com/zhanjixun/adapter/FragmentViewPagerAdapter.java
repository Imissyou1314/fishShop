package com.zhanjixun.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * ΪViewPager��Ӳ��֣�Fragment�����󶨺ʹ���fragments��viewpager֮����߼���ϵ
 *
 */
public class FragmentViewPagerAdapter extends PagerAdapter implements
		ViewPager.OnPageChangeListener {
	private List<Fragment> fragments; // ÿ��Fragment��Ӧһ��Page
	private FragmentManager fragmentManager;
	private ViewPager viewPager; // viewPager����
	private int currentPageIndex = 0; // ��ǰpage�������л�֮ǰ��
	private int offset = 0;// ����ͼƬƫ����
	private int one,two;
	private ImageView cursor;
	
	private OnExtraPageChangeListener onExtraPageChangeListener; // ViewPager�л�ҳ��ʱ�Ķ��⹦����ӽӿ�

	public FragmentViewPagerAdapter(FragmentManager fragmentManager,
			ViewPager viewPager, List<Fragment> fragments,int offset,int bmpW,ImageView cursor) {
		this.fragments = fragments;
		this.fragmentManager = fragmentManager;
		this.viewPager = viewPager;
		this.viewPager.setAdapter(this);
		this.viewPager.setOnPageChangeListener(this);
		this.offset = offset;
		this.one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
		this.two = one * 2;// ҳ��1 -> ҳ��3 ƫ����
		this.cursor = cursor;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object o) {
		return view == o;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(fragments.get(position).getView()); // �Ƴ�viewpager����֮���page����
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Fragment fragment = fragments.get(position);
		if (!fragment.isAdded()) { // ���fragment��û��added
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.add(fragment, fragment.getClass().getSimpleName());
			ft.commit();
			/**
			 * ����FragmentTransaction.commit()�����ύFragmentTransaction�����
			 * ���ڽ��̵����߳��У����첽�ķ�ʽ��ִ�С� �����Ҫ����ִ������ȴ��еĲ�������Ҫ�������������ֻ�������߳��е��ã���
			 * Ҫע����ǣ����еĻص�����ص���Ϊ��������������б�ִ����ɣ����Ҫ��ϸȷ����������ĵ���λ�á�
			 */
			fragmentManager.executePendingTransactions();
		}

		if (fragment.getView().getParent() == null) {
			container.addView(fragment.getView()); // Ϊviewpager���Ӳ���
		}

		return fragment.getView();
	}

	/**
	 * ��ǰpage�������л�֮ǰ��
	 * 
	 * @return
	 */
	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public OnExtraPageChangeListener getOnExtraPageChangeListener() {
		return onExtraPageChangeListener;
	}

	/**
	 * ����ҳ���л����⹦�ܼ�����
	 * 
	 * @param onExtraPageChangeListener
	 */
	public void setOnExtraPageChangeListener(
			OnExtraPageChangeListener onExtraPageChangeListener) {
		this.onExtraPageChangeListener = onExtraPageChangeListener;
	}

	@Override
	public void onPageScrolled(int i, float v, int i2) {
		if (null != onExtraPageChangeListener) { // ��������˶��⹦�ܽӿ�
			onExtraPageChangeListener.onExtraPageScrolled(i, v, i2);
		}
	}

	@Override
	public void onPageSelected(int i) {
		Animation animation = null;
		switch (i) {
		case 0:
			animation = new TranslateAnimation(one * currentPageIndex, 0, 0, 0);
			break;
		case 1:
			if (currentPageIndex == 0) {
				animation = new TranslateAnimation(offset, one, 0, 0);
			} else {
				animation = new TranslateAnimation(one * currentPageIndex, one, 0, 0);
			}
			break;
		case 2:
			if (currentPageIndex == 0) {
				animation = new TranslateAnimation(offset, two, 0, 0);
			} else {
				animation = new TranslateAnimation(one * currentPageIndex, two, 0, 0);
			}
			break;
		}
		//currIndex = i;
		fragments.get(currentPageIndex).onPause(); // �����л�ǰFargment��onPause()
		// fragments.get(currentPageIndex).onStop(); // �����л�ǰFargment��onStop()
		if (fragments.get(i).isAdded()) {
			// fragments.get(i).onStart(); // �����л���Fargment��onStart()
			fragments.get(i).onResume(); // �����л���Fargment��onResume()
		}
		currentPageIndex = i;

		if (null != onExtraPageChangeListener) { // ��������˶��⹦�ܽӿ�
			onExtraPageChangeListener.onExtraPageSelected(i);
		}
		animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
		animation.setDuration(300);
		cursor.startAnimation(animation);
		
	}

	@Override
	public void onPageScrollStateChanged(int i) {
		if (null != onExtraPageChangeListener) { // ��������˶��⹦�ܽӿ�
			onExtraPageChangeListener.onExtraPageScrollStateChanged(i);
		}
	}

	/**
	 * page�л����⹦�ܽӿ�
	 */
	static class OnExtraPageChangeListener {
		public void onExtraPageScrolled(int i, float v, int i2) {
		}

		public void onExtraPageSelected(int i) {
		}

		public void onExtraPageScrollStateChanged(int i) {
		}
	}

}
