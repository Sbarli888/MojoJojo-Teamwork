package com.example.database;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.carrental.CarModel;

public class CarsData {
	private SQLiteDatabase database;
	private DbHelper dbHelper;
	private final String[] allColumns = new String[] { DbHelper.COLUMN_BACKEND_ID, DbHelper.COLUMN_MODEL,
			DbHelper.COLUMN_YEAR, DbHelper.COLUMN_PRICE, DbHelper.COLUMN_CONSUMPTION, DbHelper.COLUMN_IMAGE_URL,
			DbHelper.COLUMN_LOCATION };

	public CarsData(Context context) {
		dbHelper = new DbHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
	}

	public void addCar(CarModel car) {
		open();
		ContentValues dataRow = new ContentValues();
		dataRow.put(DbHelper.COLUMN_BACKEND_ID, car.getCarId().toString());
		dataRow.put(DbHelper.COLUMN_MODEL, car.getModel());
		dataRow.put(DbHelper.COLUMN_YEAR, car.getYear().intValue());
		dataRow.put(DbHelper.COLUMN_PRICE, car.getPrice().doubleValue());
		dataRow.put(DbHelper.COLUMN_CONSUMPTION, car.getConsumption().doubleValue());
		dataRow.put(DbHelper.COLUMN_IMAGE_URL, car.getImageUrl());
		dataRow.put(DbHelper.COLUMN_LOCATION, car.getLocation());

		this.database.insert(DbHelper.TABLE_CARS, null, dataRow);
		close();
	}

	public List<CarModel> getAllRentedCars() {
		open();

		List<CarModel> cars = new ArrayList<CarModel>();

		Cursor result = this.database.query(DbHelper.TABLE_CARS, allColumns, null, null, null, null, null);

		result.moveToFirst();
		while (!result.isAfterLast()) {

			UUID uuId = UUID.fromString(result.getString(result.getColumnIndex(DbHelper.COLUMN_BACKEND_ID)));
			String model = result.getString(result.getColumnIndex(DbHelper.COLUMN_MODEL));
			Number year = Integer.parseInt(result.getString(result.getColumnIndex(DbHelper.COLUMN_YEAR)));
			Number price = Double.parseDouble(result.getString(result.getColumnIndex(DbHelper.COLUMN_PRICE)));
			Number consumption = Double.parseDouble(result.getString(result.getColumnIndex(DbHelper.COLUMN_CONSUMPTION)));
			String url = result.getString(result.getColumnIndex(DbHelper.COLUMN_IMAGE_URL));
			String location = result.getString(result.getColumnIndex(DbHelper.COLUMN_LOCATION));

			CarModel newCar = new CarModel(uuId, model, year, price, consumption, url, false, location);

			cars.add(newCar);
			result.moveToNext();
		}

		result.close();
		close();
		return cars;
	}

	public void deleteCar(CarModel car) {
		open();
		String backendId = car.getCarId().toString();
		database.delete(DbHelper.TABLE_CARS, DbHelper.COLUMN_BACKEND_ID + " = " + backendId, null);
		close();
	}
}
