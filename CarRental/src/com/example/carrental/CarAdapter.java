package com.example.carrental;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CarAdapter extends BaseAdapter {

	private ArrayList listData;

	private LayoutInflater layoutInflater;

	public CarAdapter(Context context, ArrayList listData) {
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.list_row, null);
			holder = new ViewHolder();
			holder.model = (TextView) convertView.findViewById(R.id.model);
			holder.price = (TextView) convertView.findViewById(R.id.price);
			holder.listImage = (ImageView) convertView.findViewById(R.id.list_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		CarModel carItem = (CarModel) listData.get(position);

		holder.model.setText(carItem.getModel());
		holder.price.setText(Double.toString(carItem.getPrice()));

		if (holder.listImage != null) {
			new ImageDownloaderTask(holder.listImage).execute(carItem.getImageUrl());
		}

		return convertView;
	}

	static class ViewHolder {
		TextView model;
		TextView price;
		ImageView listImage;
	}
}