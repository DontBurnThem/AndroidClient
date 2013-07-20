package com.DBT.android;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.DBT.android.R;
public class AddOfferActivity extends Activity implements AsyncTaskCompleteListener<BookBeam>{
	static Context ctx; 
	@Override
	protected void onCreate(Bundle savedInstanceState){
		ctx=this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_offer_activity);
		String ISBN = getIntent().getExtras().getString("ISBN");
		System.out.println("ISBN:" + ISBN);
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

	@Override
	public void onTaskComplete(BookBeam result) {

		TextView tv_name = (TextView)findViewById(R.id.booktitle);
		tv_name.setText("Title: " + result.getTitle());
		TextView tv_auth = (TextView)findViewById(R.id.bookauth);
		tv_auth.setText("Authors: " + result.getAuthors());
		TextView tv_edition = (TextView)findViewById(R.id.bookedition);
		String pub = result.getPublichser();
		String right_pub = "";
		for (int i=0; i<pub.length(); i++){
			if (pub.charAt(i) == '['){
				
			}else if (pub.charAt(i)==']'){
				
			}else{
				right_pub=right_pub+ pub.charAt(i);
			}
		}
		tv_edition.setText("Edition:"+result.getEdition() + " Editor:" + right_pub);
		ImageView iv = (ImageView)findViewById(R.id.book_photo);
		String id = result.getPhoto_ID();
		System.out.println("ID captured:" + id);
		String right_id = "";
		for (int i=0; i<id.length(); i++){
			if (id.charAt(i) == '['){
				
			}else if (id.charAt(i)==']'){
				
			}else{
				right_id=right_id+ id.charAt(i);
			}
		}
		System.out.println("right_id:" + right_id);
		BookCoverRetreiver bcr = new BookCoverRetreiver(ctx,right_id, iv);
		bcr.execute();

	}
}
