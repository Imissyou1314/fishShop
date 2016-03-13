package com.zhanjixun.adapter;

import java.util.List;

import com.zhanjixun.R;
import com.zhanjixun.data.IC;
import com.zhanjixun.domain2.GoodListItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 一级类别
 * @author Imissyou
 *
 */
public class TopGoodsAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private List<GoodListItem> goods;

	public TopGoodsAdapter(Context context, List<GoodListItem> goods) {
		inflater = LayoutInflater.from(context);
		this.goods = goods;
	}

	@Override
	public int getCount() {
		return goods.size();
	}

	@Override
	public Object getItem(int position) {
		return goods.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = inflater.inflate(R.layout.top_category_listview_item,
					null);
			vh.academicName = (TextView) convertView
					.findViewById(R.id.id_top_category_listview_item_seafoodScientificName);
			vh.simpleName = (TextView) convertView
					.findViewById(R.id.id_top_category_listview_item_seafoodPopularName);
			vh.englishName = (TextView) convertView
					.findViewById(R.id.id_top_category_listview_item_seafoodEnglishName);
			vh.image = (ImageView) convertView
					.findViewById(R.id.id_top_category_listview_item_image);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		GoodListItem itemBean = goods.get(position);
		IC.getInstance().setForegound(itemBean.getFishPhoto(), vh.image);
		vh.academicName.setText(itemBean.getCategoryAcademicName());
		vh.simpleName.setText(itemBean.getCategorySimpleName());
		vh.englishName.setText(itemBean.getCategoryEnglishName());
		return convertView;
	}
	
	class ViewHolder {
		public TextView academicName;
		public TextView simpleName;
		public TextView englishName;
		public ImageView image;
	}
}
