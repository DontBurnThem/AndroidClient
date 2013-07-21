package com.DBT.android;

import com.google.android.gms.maps.model.LatLng;

public class OfferBeam {
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float price;
	public LatLng getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(LatLng coordinates) {
		this.coordinates = coordinates;
	}
	private LatLng coordinates;
	private BookBeam bookbeam;
	public BookBeam getBookbeam() {
		return bookbeam;
	}
	public void setBookbeam(BookBeam bookbeam) {
		this.bookbeam = bookbeam;
	}
	public String getUser() {
		return User;
	}
	public void setUser(String user) {
		User = user;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	private String User;
	private String email;
	private int status;
	private Double lat;
	private Double lon;
}
