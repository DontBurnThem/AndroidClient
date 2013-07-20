package com.DBT.android;

import java.io.InputStream;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class BookCoverRetreiver extends AsyncTask<String, Void, Bitmap>{
	Context ctx;
	ProgressDialog dialog;
	String photo_id;
	ImageView iv;
	public BookCoverRetreiver(Context ctx, String photo_id, ImageView iv){
		this.ctx = ctx;
		this.iv = iv;
		this.photo_id = photo_id;
		this.dialog = new ProgressDialog(this.ctx);
		this.dialog.setMessage("Loading..");
	}
	@Override
	protected void onPreExecute(){
       dialog.show();
	}
	
	@Override
	protected Bitmap doInBackground(String... params) {
		String urldisplay = ("http://covers.openlibrary.org/b/id/"+photo_id+"-L.jpg");
		System.out.println("URL:" + urldisplay);
        Bitmap mIcon11 = null;
        if(mIcon11==null){
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        }
        return mIcon11;
	}

	protected void onPostExecute(Bitmap result) {		
        dialog.dismiss();
        iv.setImageBitmap(result);
	}
	
}
