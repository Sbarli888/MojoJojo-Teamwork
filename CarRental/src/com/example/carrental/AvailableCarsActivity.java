package com.example.carrental;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.telerik.everlive.sdk.core.EverliveApp;

@SuppressLint("NewApi")
public class AvailableCarsActivity extends Activity implements OnItemClickListener, OnClickListener {
	
	ListView list;
	private Context mContext = this;
	CarAdapter adapter;
	private ArrayList<CarModel> cars;
	ListView carsListView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EverliveApp app = new EverliveApp("ZEW8xrnCpPDDsuQD");
		setContentView(R.layout.activity_available_cars);
		
		// action bar 
		ActionBar bar = getActionBar();		
		bar.setTitle("Available Cars");
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setDisplayShowHomeEnabled(false);

        //initializeElements();
		
		
		CarModel sampleCar = new CarModel("Lexus",2000,2000,10,"http://media.caranddriver.com/images/media/51/dissected-lotus-based-infiniti-emerg-e-sports-car-concept-top-image-photo-451994-s-original.jpg");
		CarModel sampleCar2 = new CarModel("Lexus",2000,2000,10,"http://media.caranddriver.com/images/media/51/dissected-lotus-based-infiniti-emerg-e-sports-car-concept-top-image-photo-451994-s-original.jpg");
		CarModel sampleCar3 = new CarModel("Lexus",2000,2000,10,"http://media.caranddriver.com/images/media/51/dissected-lotus-based-infiniti-emerg-e-sports-car-concept-top-image-photo-451994-s-original.jpg");
		CarModel sampleCar4 = new CarModel("Lexus",2000,2000,10,"http://media.caranddriver.com/images/media/51/dissected-lotus-based-infiniti-emerg-e-sports-car-concept-top-image-photo-451994-s-original.jpg");


		cars = new ArrayList<CarModel>();
		cars.add(sampleCar);
		cars.add(sampleCar2);
		cars.add(sampleCar3);
		cars.add(sampleCar4);
		
		
//		ArrayList image_details = cars;
		carsListView = (ListView) findViewById(R.id.listView1);
		carsListView.setAdapter(new CarAdapter(this, cars));
		carsListView.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Object o = carsListView.getItemAtPosition(position);
		CarModel car = (CarModel) o;
		Toast.makeText(AvailableCarsActivity.this, "Selected :" + " " + car,
				Toast.LENGTH_LONG).show();
		
	}
}
