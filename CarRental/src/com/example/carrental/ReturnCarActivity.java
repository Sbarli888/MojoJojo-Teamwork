package com.example.carrental;

import com.example.database.CarsData;
import com.telerik.everlive.sdk.core.EverliveApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ReturnCarActivity extends ActionBarActivity implements OnClickListener {
	CarModel car;
	Button returnBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_return_car);

		Intent selectedCarData = this.getIntent();
		car = selectedCarData.getExtras().getParcelable("SelectedCar");
		TextView carTitle = (TextView) findViewById(R.id.car_to_return);
		carTitle.append(car.getModel());
		returnBtn = (Button) findViewById(R.id.return_button);
		returnBtn.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.return_car, menu);
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
		if (v.getId() == R.id.return_button) {
			CarsData carsDb = new CarsData(getApplicationContext());
			carsDb.deleteCar(car);

			EverliveApp app = new EverliveApp("ZEW8xrnCpPDDsuQD");
			CarModel carToUpdate = new CarModel(car.getCarId(), car.getModel(), car.getYear(), car.getPrice(),
					car.getConsumption(), car.getImageUrl(), true, car.getLocation());

			app.workWith().data(CarModel.class).updateById(car.getCarId(), carToUpdate).executeAsync();

			Intent intent = new Intent(ReturnCarActivity.this, AvailableCarsActivity.class);
			this.startActivity(intent);
		}
	}
}
