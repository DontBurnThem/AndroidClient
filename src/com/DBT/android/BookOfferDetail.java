package com.DBT.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BookOfferDetail extends Activity{
	@Override
	protected void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookoffereddetails);
		int index = getIntent().getExtras().getInt("index");
		String from = getIntent().getExtras().getString("from");
		TextView book_title = (TextView)findViewById(R.id.booktitle);
		TextView book_auth = (TextView)findViewById(R.id.bookauth);
		TextView book_edition = (TextView)findViewById(R.id.bookedition);
		TextView book_condition = (TextView)findViewById(R.id.book_condition);
		TextView book_price = (TextView)findViewById(R.id.price);
		TextView user_selling = (TextView)findViewById(R.id.userselling);
		String email = null;
		if(from.equals("maps")){
			book_title.setText("Title: "+SearchOffers_aroundme.offers.get(index).getBookbeam().getTitle());
			book_auth.setText("Authors: "+SearchOffers_aroundme.offers.get(index).getBookbeam().getAuthors());
			book_edition.setText("Edition: "+SearchOffers_aroundme.offers.get(index).getBookbeam().getEdition());
			book_price.setText("Prezzo: "+String.valueOf(SearchOffers_aroundme.offers.get(index).getPrice())+"€");
			int condition = SearchOffers_aroundme.offers.get(index).getStatus();
			String cond = null;
			if (condition==0){
				cond="Status: Imballato";
			}else if(condition==1){
				cond="Status: Letto";
			}else if(condition==2){
				cond="Status: Usurato";
			}else if(condition==3){
				cond="Status: Scritto";
			}else if(condition==4){
				cond="Status: Danneggiato";
			}
			book_condition.setText(cond);
			user_selling.setText(SearchOffers_aroundme.offers.get(index).getUser());
			email = SearchOffers_aroundme.offers.get(index).getEmail();
		}else if (from.equals("isbn")){
			book_title.setText("Title: "+SearchISBNOffers.offers_ISBN.get(index).getBookbeam().getTitle());
			book_auth.setText("Authors: "+SearchISBNOffers.offers_ISBN.get(index).getBookbeam().getAuthors());
			book_edition.setText("Edition: "+SearchISBNOffers.offers_ISBN.get(index).getBookbeam().getEdition());
			book_price.setText("Prezzo: "+String.valueOf(SearchISBNOffers.offers_ISBN.get(index).getPrice())+"€");
			int condition = SearchISBNOffers.offers_ISBN.get(index).getStatus();
			String cond = null;
			if (condition==0){
				cond="Status: Imballato";
			}else if(condition==1){
				cond="Status: Letto";
			}else if(condition==2){
				cond="Status: Usurato";
			}else if(condition==3){
				cond="Status: Scritto";
			}else if(condition==4){
				cond="Status: Danneggiato";
			}
			book_condition.setText(cond);
			user_selling.setText(SearchISBNOffers.offers_ISBN.get(index).getUser());
			email = SearchISBNOffers.offers_ISBN.get(index).getEmail();
		}
		final String fin_mail = email;
		Button btn_mail = (Button)findViewById(R.id.btn_contact);
		btn_mail.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{fin_mail});
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "DBT - Contact for your insertion");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");

				startActivity(Intent.createChooser(emailIntent, ""));;				
			}
			
		});
	}
}
