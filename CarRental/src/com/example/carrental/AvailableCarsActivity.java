package com.example.carrental;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.model.base.DataItem;
import com.telerik.everlive.sdk.core.model.base.ItemBase;
import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;

@SuppressLint("NewApi")
public class AvailableCarsActivity extends Activity implements OnItemLongClickListener {

	private ListView list;
	private Context mContext;
	private CarAdapter adapter;
	private ArrayList<CarModel> cars;
	private ArrayList<String> carsIds;
	private ListView carsListView;
	private UserDataPreference userPrefs;
	private EverliveApp app;
	private ListviewUpdater updater;
	private boolean toBeNotified;
	private int carsCount;

	Handler handler;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = new EverliveApp("ZEW8xrnCpPDDsuQD");
		setContentView(R.layout.activity_available_cars);
		initializeElements();
		setActionBar();

		adapter = new CarAdapter(mContext, cars);

		carsListView.setAdapter(adapter);
		carsListView.setOnItemLongClickListener(this);
		callAsynchronousTask();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_logout, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void initializeElements() {
		mContext = this;
		cars = new ArrayList<CarModel>();
		carsListView = (ListView) findViewById(R.id.listView1);
		userPrefs = new UserDataPreference(getApplicationContext());
		updater = new ListviewUpdater();
		toBeNotified = true;
		carsIds = new ArrayList<String>();
		carsCount = 0;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		Object o = carsListView.getItemAtPosition(position);
		CarModel car = (CarModel) o;

		Intent intent = new Intent(AvailableCarsActivity.this, CarDetailsActivity.class);
		CarModel selectedCar = cars.get(position);
		intent.putExtra("SelectedCar", selectedCar);
		this.startActivity(intent);

		Log.d("CAR_IN_SQLITE", "URL = " + car.getImageUrl());
		// callAsynchronousTask();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_logout) {
			Toast.makeText(mContext, "Logging out", Toast.LENGTH_LONG).show();
			userPrefs.forget();
			Intent intent = new Intent(AvailableCarsActivity.this, HomeActivity.class);
			startActivity(intent);
			return true;
		}
		if (item.getItemId() == R.id.action_refresh) {
			adapter.notifyDataSetChanged();
			callAsynchronousTask();
			return true;
		}
		return false;
	}

	private void setActionBar() {
		// action bar
		ActionBar bar = getActionBar();
		bar.setTitle("Available Cars");
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setDisplayShowHomeEnabled(false);
		bar.show();
	}

	public void callAsynchronousTask() {
		final Handler handler = new Handler();
		Timer timer = new Timer();
		TimerTask doAsynchronousTask = new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						try {
							ListviewUpdater performBackgroundTask = new ListviewUpdater();
							// PerformBackgroundTask this class is the class
							// that extends AsynchTask
							performBackgroundTask.execute();
						} catch (Exception e) {
							// TODO Auto-generated catch block
						}
					}
				});
			}
		};
		timer.schedule(doAsynchronousTask, 0, 5000); // execute in every 50000
														// ms
	}

	private class ListviewUpdater extends AsyncTask<Void, Void, ArrayList<CarModel>> {
		EverliveApp app = new EverliveApp("ZEW8xrnCpPDDsuQD");

		@Override
		protected ArrayList<CarModel> doInBackground(Void... params) {
			RequestResult<ArrayList<CarModel>> result = this.app.workWith().data(CarModel.class).getAll().executeSync();
			if (result.getSuccess()) {
				Log.d("SSDSDAS", "OK");
				cars.clear();
				for (CarModel car : result.getValue()) {	
					
					UUID carId =UUID.fromString(car.getServerId().toString());
					CarModel tempCar = new CarModel(carId, car.getModel(), car.getYear(), car.getPrice(),
							car.getConsumption(), car.getImageUrl(), car.isAvailable(), car.getLocation());
					
					Log.d("CARID", tempCar.getCarId().toString());
					if (tempCar.isAvailable()) {
						cars.add(tempCar);
					} else {
						if (carsIds.contains(tempCar.getCarId().toString())) {
							carsIds.remove(tempCar.getCarId().toString());
							toBeNotified = true;
						}
					}
					if (cars.size() != carsCount) {
						carsCount = cars.size();
						toBeNotified = true;
					}
					// if(!(carsIds.contains(tempCar.getCarId().toString()))){
					// carsIds.add(tempCar.getCarId().toString());
					// toBeNotified = true;
					// }
					// if(carsIds.size() != carsCount){
					// carsCount = carsIds.size();
					// toBeNotified = true;
					// }
				}
				return cars;
			} else {
				Log.d("SSDSDAS", "fail");
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<CarModel> cars) {

			ArrayList<String> tempIds = new ArrayList<String>();
			for (CarModel car : cars) {
				if (!(carsIds.contains(car.getCarId().toString()))) {
					toBeNotified = true;
				}
				tempIds.add(car.getCarId().toString());
			}

			carsIds = new ArrayList<String>(tempIds);

			if (toBeNotified) {
				adapter.notifyDataSetChanged();
			}
			toBeNotified = false;

		}
	}
}
