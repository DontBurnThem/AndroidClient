package com.DBT.android;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class BookJSONParser extends AsyncTask<String, BookBeam, BookBeam> {
	private static final String TAG_ISBN = "ISBN";
	private static final String TAG_PHOTO_ID = "covers";
	private static final String TAG_NAME = "name";
	private static final String TAG_DETAILS = "details";
	private static final String TAG_EDITION = "edition_name";
	private static final String TAG_TITLE = "title";
	private static final String TAG_SUBTITLE = "subtitle";
	private static final String TAG_AUTHORS = "by_statement";
	private static final String TAG_PUBLISHERS = "publishers";
	static InputStream is = null;
	static String json = "";
	static JSONObject jObj = null;
	static JSONObject book_object=null;
	JSONArray book = null;
	ProgressDialog dialog;
	BookBeam book_readed;
	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(this.ctx, "", "Loading....");
	}
	String ISBN;
    private AsyncTaskCompleteListener<BookBeam> callback;
    Context ctx;
	public BookJSONParser(String ISBN, AsyncTaskCompleteListener<BookBeam> cb, Context ctx){
		//this.ISBN= ISBN;
		this.ISBN= "0201558025";
		this.callback = cb;
		this.ctx = ctx;
	}
	
	@Override
	protected BookBeam doInBackground(String... params) {
		
		//String url = "http://openlibrary.org/api/books?bibkeys=ISBN:"+this.ISBN+"&jscmd=details&format=json";
		String url = "http://openlibrary.org/api/books?bibkeys=ISBN:0201558025&jscmd=details&format=json";
		 StringBuilder builder = new StringBuilder();
		    HttpClient client = new DefaultHttpClient();
		    HttpGet httpGet = new HttpGet(url);
		    try {
		      HttpResponse response = client.execute(httpGet);
		      StatusLine statusLine = response.getStatusLine();
		      int statusCode = statusLine.getStatusCode();
		      if (statusCode == 200) {
		        HttpEntity entity = response.getEntity();
		        InputStream content = entity.getContent();
		        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
		        String line;
		        while ((line = reader.readLine()) != null) {
		          builder.append(line);
		        }
		      } else {
		        Log.e("PARSER", "Failed to download file");
		      }
		    } catch (ClientProtocolException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		    json = builder.toString();
		    try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
			dialog.dismiss();

		}
		try {
			//book = jObj.getJSONArray(TAG_ISBN+":"+this.ISBN);
			book_object = jObj.getJSONObject(TAG_ISBN+":"+this.ISBN);
			book_readed = new BookBeam();
			JSONObject details = book_object.getJSONObject(TAG_DETAILS);
			book_readed.setPhoto_ID(details.getString(TAG_PHOTO_ID));
			book_readed.setSubtitle(details.getString(TAG_SUBTITLE));
			book_readed.setEdition(details.getString(TAG_EDITION));
			book_readed.setTitle(details.getString(TAG_TITLE));
			book_readed.setAuthors(details.getString(TAG_AUTHORS));
			book_readed.setPublichser(details.getString(TAG_PUBLISHERS));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		dialog.dismiss();
		
		return book_readed;
	}
	
	  protected void onPostExecute(BookBeam params){
		    callback.onTaskComplete(params);
	        super.onPostExecute(params);

	    }
	    
}
