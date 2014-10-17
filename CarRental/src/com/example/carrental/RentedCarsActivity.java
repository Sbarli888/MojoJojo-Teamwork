package com.example.carrental;

import java.util.ArrayList;

import com.example.database.CarsData;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RentedCarsActivity extends ActionBarActivity implements OnItemClickListener {
	ArrayList<CarModel> carsInDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rented_cars);

		CarsData carsDb = new CarsData(getApplicationContext());
		carsInDb = (ArrayList<CarModel>) carsDb.getAllRentedCars();

		ListView carsList = (ListView) findViewById(R.id.rented_list);
		RentedCarsAdapter adapter = new RentedCarsAdapter(getApplicationContext(), carsInDb);

		carsList.setAdapter(adapter);
		carsList.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rented_cars, menu);
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(RentedCarsActivity.this, ReturnCarActivity.class);
		CarModel selectedCar = carsInDb.get(position);
		intent.putExtra("SelectedCar", selectedCar);
		this.startActivity(intent);
	}
}
