package com.example.carrental;


import java.sql.Date;
import java.util.UUID;

import android.os.Parcel;
import android.os.Parcelable;

import com.telerik.everlive.sdk.core.model.base.DataItem;
import com.telerik.everlive.sdk.core.serialization.ServerIgnore;
import com.telerik.everlive.sdk.core.serialization.ServerProperty;
import com.telerik.everlive.sdk.core.serialization.ServerType;

@ServerType("Cars")
public class CarModel extends DataItem implements Parcelable{

	@ServerProperty("Id")
	private UUID id;
	
	@ServerIgnore
	private UUID carId;

	@ServerProperty("CarModel")
	private String model;
	
	@ServerProperty("Year")
	private Number year;
		
	@ServerProperty("Price")
	private Number price;
	
	@ServerProperty("Consumption")
	private Number consumption;
	
	@ServerProperty("ImageURL")
	private String imageUrl;
	
	@ServerProperty("IsAvailable")
	private boolean isAvailable;
	
	@ServerProperty("Location")
	private String location;

	@ServerProperty("ReturnDate")
	private Date returnDate;
	

	public CarModel(UUID carId, String model, Number year, Number price, Number consumption, String imageUrl, boolean isAvailable, String location) {
		super();
		this.carId = carId;
		this.model = model;
		this.year = year;
		this.price = price;
		this.consumption = consumption;
		this.imageUrl = imageUrl;
		this.isAvailable = isAvailable;
		this.location = location;
	}
	
	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

//	public int getYear() {
//		return year;
//	}
//
//	public void setYear(int year) {
//		this.year = year;
//	}
//
//	public double getPrice() {
//		return price;
//	}
//
//	public void setPrice(double price) {
//		this.price = price;
//	}
//
//	public double getConsumption() {
//		return consumption;
//	}
//
//	public void setConsumption(double consumption) {
//		this.consumption = consumption;
//	}
	public Number getYear() {
		return year;
	}

	public void setYear(Number year) {
		this.year = year;
	}

	public Number getPrice() {
		return price;
	}

	public void setPrice(Number price) {
		this.price = price;
	}

	public Number getConsumption() {
		return consumption;
	}

	public void setConsumption(Number consumption) {
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

//Make parcelable
	public static final Parcelable.Creator<CarModel> CREATOR = new Parcelable.Creator<CarModel>() {
		public CarModel createFromParcel(Parcel in) {
			return new CarModel(in);
		}

		public CarModel[] newArray(int size) {
			return new CarModel[size];
		}
	};
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	public CarModel(Parcel in) {
		readFromParcel(in);
	}

	private void readFromParcel(Parcel in) {		
		this.carId = UUID.fromString(in.readString());
		this.model = in.readString();
		this.year = in.readInt();
		this.price = in.readDouble();
		this.consumption = in.readDouble();
		this.imageUrl = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(carId.toString());
		dest.writeString(model);
		dest.writeInt(year.intValue());
		dest.writeDouble(price.doubleValue());
		dest.writeDouble(consumption.doubleValue());
		dest.writeString(imageUrl);
	}
}
