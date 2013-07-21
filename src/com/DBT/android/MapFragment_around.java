package com.DBT.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment_around extends Activity implements AsyncTaskCompleteListener<String>{
	GPSTracker gpt;
	Double latitude;
	Double longitude;
	static GoogleMap map;
	Context ctx;
	protected void onCreate(Bundle savedInstanceState) {
		ctx = this;
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
         map = mapFragment.getMap();
        //map.setMyLocationEnabled(true);
		LatLng my_position = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(my_position).title("You are here"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(my_position, 14));
        SearchOffers_aroundme sea = new SearchOffers_aroundme(ctx,this, latitude,longitude);
		sea.execute();
		map.setOnMarkerClickListener(new OnMarkerClickListener(){

			@Override
			public boolean onMarkerClick(Marker marker) {
				for (int i=0; i<SearchOffers_aroundme.offers.size(); i++){
					if (marker.getPosition().latitude == SearchOffers_aroundme.offers.get(i).getCoordinates().latitude){
						if (marker.getPosition().longitude == SearchOffers_aroundme.offers.get(i).getCoordinates().longitude){
							if(marker.getTitle()== SearchOffers_aroundme.offers.get(i).getBookbeam().getTitle()+", "+ SearchOffers_aroundme.offers.get(i).getBookbeam().getAuthors()){
								Intent intent = new Intent(MapFragment_around.this, BookOfferDetail.class);
								intent.putExtra("from", "map");
								intent.putExtra("index", i);
								startActivity(intent);
								finish();
							}
						}
					}
				}
				return false;
			}
			
		});
		
	}
	

	@Override
	public void onTaskComplete(String result) {
		if (result.equals("Finished")){
			for (int i=0; i<SearchOffers_aroundme.offers.size(); i++){
				LatLng coordinates = SearchOffers_aroundme.offers.get(i).getCoordinates();
				String book_title = SearchOffers_aroundme.offers.get(i).getBookbeam().getTitle()+", "+ SearchOffers_aroundme.offers.get(i).getBookbeam().getAuthors();
				map.addMarker(new MarkerOptions().position(coordinates).title(book_title));
			}
		}
		
	}
}
