package com.example.carrental;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class CarDetailsActivity extends ActionBarActivity {

	TextView model, price, year, consumption;
	ImageView carImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_details);
		
		Intent selectedCarData = this.getIntent();
		CarModel car = selectedCarData.getExtras()
				.getParcelable("SelectedCar");
		
		model = (TextView) findViewById(R.id.tv_car_model);
		consumption = (TextView) findViewById(R.id.tv_car_consumption);
		year = (TextView) findViewById(R.id.tv_car_year);
		price = (TextView) findViewById(R.id.tv_car_price);
		carImage = (ImageView) findViewById(R.id.car_image);
		
		model.append(car.getModel());
		consumption.append(Double.toString(car.getConsumption()));
		year.append(Integer.toString(car.getYear()));
		price.append(Double.toString(car.getPrice()));
		new ImageDownloaderTask(carImage).execute(car.getImageUrl());
//		Log.d("CAR", "UUID: "+car.getCarId().toString());
//		Log.d("CAR", "URL: "+car.getImageUrl());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.car_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
