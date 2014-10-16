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
	
//	@ServerIgnore
//	private UUID carId;
	
	@ServerIgnore
	private Date modifiedAt;
	
	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		modifiedAt = modifiedAt;
	}

	@ServerProperty("CarModel")
	private String CarModel;
	
	@ServerProperty("Year")
	private Number Year;
		
	@ServerProperty("Price")
	private Number Price;
	
	@ServerProperty("Consumption")
	private Number Consumption;
	
	@ServerProperty("ImageURL")
	private String ImageURL;
	
	@ServerProperty("IsAvailable")
	private boolean IsAvailable;
	
	@ServerProperty("Location")
	private String Location;

	@ServerProperty("ReturnDate")
	private Date ReturnDate;
	

	public CarModel(UUID carId, String model, Number year, Number price, Number consumption, String imageUrl, boolean isAvailable, String location) {
		super();
		this.id = carId;
		this.CarModel = model;
		this.Year = year;
		this.Price = price;
		this.Consumption = consumption;
		this.ImageURL = imageUrl;
		this.IsAvailable = isAvailable;
		this.Location = location;
	}
	
	public Date getReturnDate() {
		return ReturnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.ReturnDate = returnDate;
	}
	
	public String getModel() {
		return CarModel;
	}

	public void setModel(String model) {
		this.CarModel = model;
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
		return Year;
	}

	public void setYear(Number year) {
		this.Year = year;
	}

	public Number getPrice() {
		return Price;
	}

	public void setPrice(Number price) {
		this.Price = price;
	}

	public Number getConsumption() {
		return Consumption;
	}

	public void setConsumption(Number consumption) {
		this.Consumption = consumption;
	}

	public String getImageUrl() {
		return ImageURL;
	}

	public void setImageUrl(String imageUrl) {
		this.ImageURL = imageUrl;
	}

	public boolean isAvailable() {
		return IsAvailable;
	}

	public CarModel setAvailable(boolean isAvailable) {
		this.IsAvailable = isAvailable;
		return this;
	}
	
	public UUID getCarId() {
		return id;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		this.Location = location;
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
		this.id = UUID.fromString(in.readString());
		this.CarModel = in.readString();
		this.Year = in.readInt();
		this.Price = in.readDouble();
		this.Consumption = in.readDouble();
		this.ImageURL = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id.toString());
		dest.writeString(CarModel);
		dest.writeInt(Year.intValue());
		dest.writeDouble(Price.doubleValue());
		dest.writeDouble(Consumption.doubleValue());
		dest.writeString(ImageURL);
	}
}
