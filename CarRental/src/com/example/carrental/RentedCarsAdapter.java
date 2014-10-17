package com.example.carrental;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RentedCarsAdapter extends BaseAdapter {

	private ArrayList listData;

	public ArrayList getListData() {
		return listData;
	}

	public void setListData(ArrayList listData) {
		this.listData.clear();
		this.listData = listData;
	}

	private LayoutInflater layoutInflater;

	public RentedCarsAdapter(Context context, ArrayList listData) {
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
			convertView = layoutInflater.inflate(R.layout.rented_cars_row, null);
			holder = new ViewHolder();
			holder.model = (TextView) convertView.findViewById(R.id.rented_model);
			holder.price = (TextView) convertView.findViewById(R.id.rented_price);
			holder.listImage = (ImageView) convertView.findViewById(R.id.rented_list_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		CarModel carItem = (CarModel) listData.get(position);

		holder.model.setText(carItem.getModel());
		holder.price.setText(String.valueOf(carItem.getPrice()) + " $/day");
		
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
