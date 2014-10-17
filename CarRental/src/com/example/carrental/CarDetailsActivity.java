package com.example.carrental;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.CarsData;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
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

	private UiLifecycleHelper uiHelper;
	private Activity activity = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_details);

		Intent selectedCarData = this.getIntent();
		car = selectedCarData.getExtras().getParcelable("SelectedCar");

		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);

		initializeObjects();

		new ImageDownloaderTask(carImage).execute(car.getImageUrl());
		model.append(car.getModel());
		consumption.append(String.valueOf(car.getConsumption()) + " litres/100km");
		year.append(String.valueOf(car.getYear()));
		price.append(String.valueOf(car.getPrice()) + " $/day");
		rentBtn = (Button) findViewById(R.id.btn_rent);
		shareBtn = (Button) findViewById(R.id.btn_share);

		rentBtn.setOnClickListener(this);
		shareBtn.setOnClickListener(this);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
			@Override
			public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
				Log.e("Activity", String.format("Error: %s", error.toString()));
			}

			@Override
			public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
				Log.i("Activity", "Success!");
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (uiHelper != null) {
			uiHelper.onResume();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (uiHelper != null) {
			uiHelper.onSaveInstanceState(outState);
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		if (uiHelper != null) {
			uiHelper.onPause();
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (uiHelper != null) {
			uiHelper.onDestroy();
		}

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
			Intent backToAllCars = new Intent(CarDetailsActivity.this, AvailableCarsActivity.class);
			startActivity(backToAllCars);
		}
		if (v.getId() == R.id.btn_share) {
			if (FacebookDialog.canPresentShareDialog(getApplicationContext(),
					FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
				FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(activity)
						.setApplicationName("CarRentalTLRK").setCaption("caption").setDescription(car.getModel())
						.setLink(car.getImageUrl()).build();
				uiHelper.trackPendingDialogCall(shareDialog.present());
			} else {
				Toast.makeText(getApplicationContext(), "No FB app installed", Toast.LENGTH_SHORT).show();
			}
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
