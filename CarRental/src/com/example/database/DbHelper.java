package com.example.database;

import com.telerik.everlive.sdk.core.serialization.ServerProperty;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	static final String DB_NAME = "CarsDb";
	static final String TABLE_CARS = "Cars";
	static final String COLUMN_ID = "_id";	
	static final String COLUMN_BACKEND_ID = "UUID";
	static final String COLUMN_MODEL = "Model";
	static final String COLUMN_YEAR = "Year";
	static final String COLUMN_PRICE = "Price";
	static final String COLUMN_CONSUMPTION = "Consumption";
	static final String COLUMN_IMAGE_URL = "URL";
//	static final String COLUMN_ISAVAILABLE = "IsAvailable";
	static final String COLUMN_LOCATION = "Location";

	static final int VERSION = 1;
	static final String DB_CREATE_QUERY = "CREATE TABLE "+TABLE_CARS
			+"("+ COLUMN_ID + " integer primary key autoincrement, "+
			COLUMN_BACKEND_ID +" text not null"+
			COLUMN_MODEL + "text not null, "+
			COLUMN_YEAR + " integer not null, "+
			COLUMN_PRICE + " real, "+
			COLUMN_CONSUMPTION + " real, "+
			COLUMN_IMAGE_URL + " text, "+			
			COLUMN_LOCATION + " text)";
	
	public DbHelper(Context context) {
		super(context, DB_NAME, null, VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(DB_CREATE_QUERY);
	}

	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}


}
