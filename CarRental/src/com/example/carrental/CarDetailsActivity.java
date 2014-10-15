package com.example.carrental;

import java.util.ArrayList;

import com.example.database.CarsData;
import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.interfaces.UpdatableItem;
import com.telerik.everlive.sdk.core.result.RequestResult;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CarDetailsActivity extends ActionBarActivity implements OnClickListener{

	TextView model, price, year, consumption;
	ImageView carImage;
	Button rentBtn, shareBtn;
	CarsData carsDb;
	CarModel car;
	EverliveApp app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_details);
		
		Intent selectedCarData = this.getIntent();
		  car = selectedCarData.getExtras()
				.getParcelable("SelectedCar");
		
		  initializeObjects();
		
		
		new ImageDownloaderTask(carImage).execute(car.getImageUrl());
		model.append(car.getModel());
		consumption.append(Double.toString(car.getConsumption()));
		year.append(Integer.toString(car.getYear()));
		price.append(Double.toString(car.getPrice()));
		rentBtn = (Button) findViewById(R.id.btn_rent);
		
		rentBtn.setOnClickListener(this);
//		Log.d("CAR", "UUID: "+car.getCarId().toString());
//		Log.d("CAR", "URL: "+car.getImageUrl());
	}

	private void initializeObjects(){
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
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.btn_rent) {
			rentCurrentCar();
		}
	}

	private void rentCurrentCar() {
		
//		RequestResult carToUpdateRequest = app.workWith().data(CarModel.class).getById(car.getCarId()).executeSync();
//		CarModel carToUpdate = (CarModel) carToUpdateRequest.getValue();
//		carToUpdate.setAvailable(false);
//
//		app.workWith().data(CarModel.class).update(carToUpdate).executeAsync();
//		app.workWith().data(CarModel.class).updateById(car.getCarId(), (UpdatableItem) car).executeAsync();
		carsDb = new CarsData(getApplicationContext());
		carsDb.addCar(car);
				
		
		//for test only
		ArrayList<CarModel> carsInDb = (ArrayList<CarModel>) carsDb.getAllRentedCars();
		for (CarModel car : carsInDb) {
			
			Log.d("CAR_IN_SQLITE", "ID="+car.getModel());
		}
	}
}
