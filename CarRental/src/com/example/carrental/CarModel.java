package com.example.carrental;


import java.util.UUID;

import android.util.Base64;

import com.telerik.everlive.sdk.core.handlers.FilesHandler;
import com.telerik.everlive.sdk.core.model.system.File;
import com.telerik.everlive.sdk.core.query.definition.FileField;
import com.telerik.everlive.sdk.core.serialization.ServerIgnore;
import com.telerik.everlive.sdk.core.serialization.ServerProperty;
import com.telerik.everlive.sdk.core.serialization.ServerType;

@ServerType("Cars")
public class CarModel{

	@ServerProperty("Id")
	private UUID carId;
	@ServerProperty("CarModel")
	private String model;
	@ServerProperty("Year")
	private int year;
	@ServerProperty("Price")
	private double price;
	@ServerProperty("Consumption")
	private double consumption;
	@ServerProperty("ImageURL")
	private String imageUrl;
	@ServerProperty("IsAvailable")
	private boolean isAvailable;
	@ServerIgnore
	private String location;

	public CarModel(UUID carId, String model, int year, double price, double consumption, String imageUrl, boolean isAvailable) {
		this.carId = carId;
		this.model = model;
		this.year = year;
		this.price = price;
		this.consumption = consumption;
		this.imageUrl = imageUrl;
		this.isAvailable = isAvailable;
	}
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getConsumption() {
		return consumption;
	}

	public void setConsumption(double consumption) {
		this.consumption = consumption;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	public UUID getCarId() {
		return carId;
	}


}
