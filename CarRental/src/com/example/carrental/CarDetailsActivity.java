package com.example.carrental;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.CarsData;
//import com.example.database.CarsData;
import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.result.RequestResult;

public class CarDetailsActivity extends ActionBarActivity implements OnClickListener {

	TextView model, price, year, consumption;
	ImageView carImage;
	Button rentBtn, shareBtn;
	CarsData carsDb;
	CarModel car;
	EverliveApp app;
	RequestResult<CarModel> tempCar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_details);

		Intent selectedCarData = this.getIntent();
		car = selectedCarData.getExtras().getParcelable("SelectedCar");

		initializeObjects();

		new ImageDownloaderTask(carImage).execute(car.getImageUrl());
		model.append(car.getModel());
		consumption.append(String.valueOf(car.getConsumption()) + " litres/100km");
		year.append(String.valueOf(car.getYear()));
		price.append(String.valueOf(car.getPrice()) + " $/day");
		rentBtn = (Button) findViewById(R.id.btn_rent);

		rentBtn.setOnClickListener(this);

	}

	private void initializeObjects() {
		model = (TextView) findViewById(R.id.tv_car_model);
		consumption = (TextView) findViewById(R.id.tv_car_consumption);
		year = (TextView) findViewById(R.id.tv_car_year);
		price = (TextView) findViewById(R.id.tv_car_price);
		carImage = (ImageView) findViewById(R.id.car_image);

		app = new EverliveApp("ZEW8xrnCpPDDsuQD");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.car_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_rent) {
			rentCurrentCar();
		}
	}

	private void rentCurrentCar() {

		CarModel carToUpdate = new CarModel(car.getCarId(), car.getModel(), car.getYear(), car.getPrice(),
				car.getConsumption(), car.getImageUrl(), false, car.getLocation());

		app.workWith().data(CarModel.class).updateById(car.getCarId(), carToUpdate).executeAsync();

		carsDb = new CarsData(getApplicationContext());
		carsDb.addCar(car);
		Toast.makeText(getApplicationContext(), "Rented Successful!", Toast.LENGTH_SHORT).show();

	}
	
	
}
