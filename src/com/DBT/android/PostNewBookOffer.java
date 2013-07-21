package com.DBT.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class PostNewBookOffer extends AsyncTask<String, Void, String>{
	Context ctx;
	ProgressDialog dialog;
	public PostNewBookOffer(Context context){
		this.ctx = context;
	}
	private boolean Check_Book_Exists (String ISBN) throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();
		String url ="http://dontburnthem.herokuapp.com/api/books/"+ISBN+"/";
		System.out.println("url=" + url);
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		System.out.println("RETURN FROM GET:" + response.getStatusLine().getStatusCode());
		if(response.getStatusLine().getStatusCode() ==404){
			return false;
		}else if(response.getStatusLine().getStatusCode() ==200){
			return true;
		}else{
			return false;
		}
		
	}
	
	private boolean Insert_New_Book(String ISBN, String Author, String edition, String Title){
		{
		    HttpClient client = new DefaultHttpClient();
		    HttpPost post = new HttpPost("http://dontburnthem.herokuapp.com/api/books/");
		    try {
		      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		      System.out.println("isbn="+ ISBN);
		      nameValuePairs.add(new BasicNameValuePair("isbn",ISBN));
		      //nameValuePairs.add(new BasicNameValuePair("author",Author));
		      //nameValuePairs.add(new BasicNameValuePair("edition",edition));
		      //nameValuePairs.add(new BasicNameValuePair("title",Title));
		      post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		      HttpResponse response = client.execute(post);
		      if(response.getStatusLine().getStatusCode()==404){
		    	  System.out.println("RITORNO 404");
		    	  return false;
		      }else if(response.getStatusLine().getStatusCode()==201){
		    	  System.out.println("RITORNO 200");
		    	  return true;
		      }
		      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		      String line = "";
		      while ((line = rd.readLine()) != null) {
		        //System.out.println(line);
		      }
		      System.out.println("Status Code:" + response.getStatusLine().getStatusCode());
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
		return false;
	}
	
	private static boolean Post_New_Offer(String status, Double Lat, Double Lon, String url, String price){
	    HttpClient client = new DefaultHttpClient();
	    HttpPost post = new HttpPost("http://dontburnthem.herokuapp.com/api/offers/");
	    try {
	      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	      nameValuePairs.add(new BasicNameValuePair("status",status));
	      nameValuePairs.add(new BasicNameValuePair("lat",String.valueOf(Lat)));
	      nameValuePairs.add(new BasicNameValuePair("lon",String.valueOf(Lon)));
	      nameValuePairs.add(new BasicNameValuePair("user", Home.userd_prova));
	      nameValuePairs.add(new BasicNameValuePair("price", price));
	      nameValuePairs.add(new BasicNameValuePair("book",url));
	      post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	      HttpResponse response = client.execute(post);
	      if(response.getStatusLine().getStatusCode()==404){
	    	  System.out.println("RITORNO 404");
	    	  return false;
	      }else if(response.getStatusLine().getStatusCode()==201){
	    	  System.out.println("RITORNO 200");
	    	  return true;
	      }
	      
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  
	return false;
	
}
	@Override
	protected String doInBackground(String... params) {
		String ISBN = params[0];
		String Author = params[1];
		String Title = params[2];
		String Edition = params[3];
		String Condition = params[4];
		//Float Price = "";
		Double Lat = Double.parseDouble(params[5]);
		Double Long = Double.parseDouble(params[6]);
		String price = params[8];
		String url_old = params[7];
		String url = "http://dontburnthem.herokuapp.com/api/books/"+ISBN+"/";
		try {
			if(!Check_Book_Exists(ISBN)){
				Log.i("DBT"," Il libro non e' presente nel DB!");
				System.out.println("ISBN:" + ISBN + " Author:" + Author + " Edition:" + Edition + " Title:" + Title); 
				if (Insert_New_Book(ISBN, Author, Edition, Title)){
					Post_New_Offer(Condition, Lat, Long, url, price);
				}
			}else{
				Log.i("DBT", "Il libro e' nel db!!");
				Post_New_Offer(Condition, Lat, Long, url, price);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		return null;
	}
	@Override
	protected void onPreExecute(){
		dialog=ProgressDialog.show(ctx, "", "Uploading Information...");
	}
	@Override
	protected void onPostExecute(String result){
		dialog.dismiss();
	}
	

}
