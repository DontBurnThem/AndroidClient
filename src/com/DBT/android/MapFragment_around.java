package com.DBT.android;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment_around extends Activity{
	GPSTracker gpt;
	Double latitude;
	Double longitude;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gpt = new GPSTracker (MapFragment_around.this);
		if(gpt.canGetLocation()){
            latitude = gpt.getLatitude();
            longitude = gpt.getLongitude();
           //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
           System.out.println("LAT:"+latitude + " LONG:" + longitude);
       }else{
           gpt.showSettingsAlert();
       }
		setContentView(R.layout.mapfragment_aroundme);
		MapFragment mapFragment =  (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        GoogleMap map = mapFragment.getMap();
        //map.setMyLocationEnabled(true);
		LatLng my_position = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(my_position).title("You are here"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(my_position, 17));
		
	}
}
