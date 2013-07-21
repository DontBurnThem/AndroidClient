package com.DBT.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SearchHome extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_home);
		Button btn_map = (Button)findViewById(R.id.btn_around_me);
		btn_map.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent (SearchHome.this, MapFragment_around.class);
				startActivity(intent);
				
			}
			
		});
		Button btn_isbn = (Button)findViewById(R.id.btn_ISBN);
		btn_isbn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent (SearchHome.this, SearchOffers_isbn.class);
				startActivity(intent);
				
			}
			
		});
	}
}
