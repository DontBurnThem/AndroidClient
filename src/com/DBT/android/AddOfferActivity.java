package com.DBT.android;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.DBT.android.R;

public class AddOfferActivity extends Activity implements AsyncTaskCompleteListener<BookBeam>{
	static Context ctx; 
	GPSTracker gps;
	double latitude;
	double longitude;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		ctx=this;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_offer_activity);
		gps = new GPSTracker (AddOfferActivity.this);
		if(gps.canGetLocation()){
             latitude = gps.getLatitude();
             longitude = gps.getLongitude();
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            System.out.println("LAT:"+latitude + " LONG:" + longitude);
        }else{
            gps.showSettingsAlert();
        }
         
		String ISBN = getIntent().getExtras().getString("ISBN");
		BookBeam book = null;
		try {
			new BookJSONParser(ISBN, AddOfferActivity.this, this).execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	public static int condition = 0;
	@Override
	public void onTaskComplete(final BookBeam result) {
		TextView tv_name = (TextView)findViewById(R.id.booktitle);
		tv_name.setText("Title: " + result.getTitle());
		TextView tv_auth = (TextView)findViewById(R.id.bookauth);
		tv_auth.setText("Authors: " + result.getAuthors());
		TextView tv_edition = (TextView)findViewById(R.id.bookedition);
		tv_edition.setText("Edition:"+result.getEdition() + " Editor:" + result.getPublichser());
		ImageView iv = (ImageView)findViewById(R.id.book_photo);
		Spinner book_cond = (Spinner)findViewById(R.id.spinner_state);
		book_cond.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				String choosen = arg0.getItemAtPosition(arg2).toString();
				if(choosen.charAt(0)=='I'){
					condition=0;
				}else if(choosen.charAt(0)=='L'){
					condition=1;
				}
				else if(choosen.charAt(0)=='U'){
					condition=2;
				}else if(choosen.charAt(0)=='S'){
					condition=3;
				}
				else if(choosen.charAt(0)=='D'){
					condition=4;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		});
		BookCoverRetreiver bcr = new BookCoverRetreiver(ctx,result.getPhoto_ID(), iv);
		bcr.execute();
		final EditText price = (EditText)findViewById(R.id.txt_price);
		Button btn_confirm = (Button)findViewById(R.id.btn_confirm_new_book);
		btn_confirm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				AlertDialog.Builder ad = new AlertDialog.Builder(AddOfferActivity.this);
				ad.setTitle("Confirm New Book");
				ad.setMessage("Do you want to confir that book?");
				ad.setCancelable(true);
				ad.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (price.getText().toString().equals("")){
									Toast.makeText(AddOfferActivity.this, "You must insert a price", Toast.LENGTH_LONG).show();
								}else{
								PostNewBookOffer pnbo = new PostNewBookOffer(AddOfferActivity.this);
								//System.out.println("ISBN:" + result.getISBN() + " condition:" + String.valueOf(condition) + " latitude:"+ String.valueOf(latitude) + " longitude:" + String.valueOf(longitude));
								pnbo.execute(new String[]{result.getISBN(),result.getAuthors(), result.getTitle(), result.getEdition(),String.valueOf(condition), String.valueOf(latitude), String.valueOf(longitude), result.getBook_URL(), price.getText().toString()});
							}
							}
						});
				ad.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
							}
						});
				AlertDialog alertDialog = ad.create();
				alertDialog.show();
			}
			
		});
	}
}
