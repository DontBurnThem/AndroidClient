package com.DBT.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.maps.model.LatLng;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SearchOffers_aroundme extends AsyncTask<String, Void, String>{
	static JSONArray jObj = null;
	static JSONObject book_object=null;
	static StringBuilder builder;
	public static ArrayList<OfferBeam> offers=null;
	private static final String TAG_ISBN = "ISBN";
	private static final String TAG_PHOTO_ID = "covers";
	private static final String TAG_URL = "info_url";
	private static final String TAG_NAME = "name";
	private static final String TAG_DETAILS = "details";
	private static final String TAG_EDITION = "edition_name";
	private static final String TAG_TITLE = "title";
	private static final String TAG_SUBTITLE = "subtitle";
	private static final String TAG_AUTHORS = "by_statement";
	private static final String TAG_PUBLISHERS = "publishers";
	private static void Get_Offers_aroundme() throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();
		String url ="http://dontburnthem.herokuapp.com/api/offers/search";
		HttpGet request = new HttpGet(url);
		HttpParams p = request.getParams();
		p.setParameter("radius", 20);
		String ll = lat+","+lon;
		p.setParameter("ll", ll);
		request.setParams(p);
		HttpResponse response  = client.execute(request);

		BufferedReader rd =  new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
				 System.out.println("Leggo:");
				 builder = new StringBuilder();
				 String  line;
					while ((line = rd.readLine()) != null) {
						builder.append(line);
					}
		try {
			Parse_Result(builder.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String username;
	private static String email;
	private static void Parse_Result(String readed) throws JSONException, ClientProtocolException, IOException{
		jObj = new JSONArray(readed);
		offers = new ArrayList<OfferBeam>(jObj.length());
		for (int i=0; i<jObj.length(); i++){
		//String url, status, price, lat, lon, user, book;
		JSONObject o = jObj.getJSONObject(i);
		OfferBeam ob = new OfferBeam();
		String ISBN = get_ISBN(o.getString("book"));
		String User = o.getString("user");
		get_User(User);
		BookBeam bb = Parse_Book (ISBN);
		//bb.setTitle("prova");
		//bb.setAuthors("ermanno");
		ob.setBookbeam(bb);
		LatLng ll = new LatLng(Double.parseDouble(o.getString("lat")), Double.parseDouble(o.getString("lon")));
		ob.setCoordinates(ll);
		ob.setEmail(email);
		ob.setUser(username);
		ob.setPrice(Float.parseFloat(o.getString("price")));
		ob.setStatus(Integer.parseInt(o.getString("status")));
		offers.add(ob);
		}
		
	}
	
	private static String get_User(String User) throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();
		String url ="http://dontburnthem.herokuapp.com"+ User;
		//System.out.println("URL:" + url);
		HttpGet request = new HttpGet(url);
		HttpResponse response  = client.execute(request);

		BufferedReader rd =  new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
				// System.out.println("Leggo:");
				 builder = new StringBuilder();
				 String  line;
					while ((line = rd.readLine()) != null) {
						builder.append(line);
					}
					try {
						String json3=null;
						json3 = builder.toString();
						JSONObject isbn = new JSONObject(json3);
						username= isbn.getString("username");
						email = isbn.getString("email");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
	}
	private static String get_ISBN(String book) throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();
		String url ="http://dontburnthem.herokuapp.com"+ book;
		//System.out.println("URL:" + url);
		HttpGet request = new HttpGet(url);
		HttpResponse response  = client.execute(request);

		BufferedReader rd =  new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
				// System.out.println("Leggo:");
				 builder = new StringBuilder();
				 String  line;
					while ((line = rd.readLine()) != null) {
						builder.append(line);
					}
		try {
			String json3=null;
			json3 = builder.toString();
			JSONObject isbn = new JSONObject(json3);
			return isbn.getString("isbn");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	static JSONObject book_parsed;
	private static BookBeam Parse_Book (String ISBN){
		BookBeam book_readed = null;
		String url = "http://openlibrary.org/api/books?bibkeys=ISBN:"+ISBN+"&jscmd=details&format=json";
		System.out.println("Url:" + url);
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
		    String json2 = null;
		    json2 = builder.toString();
		    try {
			book_parsed = new JSONObject(json2);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());

		}
		try {
			book_object = book_parsed.getJSONObject(TAG_ISBN+":"+ISBN);
			//book_object = jObj.getJSONObject(0);

			book_readed = new BookBeam();
			book_readed.setISBN(ISBN);
			book_readed.setBook_URL(book_object.getString(TAG_URL));
			JSONObject details = book_object.getJSONObject(TAG_DETAILS);
			String id = details.getString(TAG_PHOTO_ID);
			String right_id="";
			for (int i=0; i<id.length(); i++){
				if (id.charAt(i) == '['){
					
				}else if (id.charAt(i)==']'){
					
				}else{
					right_id=right_id+ id.charAt(i);
				}
			}
			book_readed.setPhoto_ID(right_id);
			book_readed.setSubtitle(details.getString(TAG_SUBTITLE));
			book_readed.setEdition(details.getString(TAG_EDITION));
			book_readed.setTitle(details.getString(TAG_TITLE));
			book_readed.setAuthors(details.getString(TAG_AUTHORS));
			String pub= details.getString(TAG_PUBLISHERS);
			String right_pub="";
			for (int i=0; i<pub.length(); i++){
				if (pub.charAt(i) == '['){
					
				}else if (pub.charAt(i)==']'){
					
				}else{
					right_pub=right_pub+ pub.charAt(i);
				}
			}
			book_readed.setPublichser(right_pub);
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return book_readed;
	}

	
	@Override
	protected String doInBackground(String... params) {
		try {
			Get_Offers_aroundme();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	Context ctx;
    private AsyncTaskCompleteListener<String> callback;
    ProgressDialog dialog;
    static Double lat;
	static Double lon;
	public SearchOffers_aroundme (Context ctx, AsyncTaskCompleteListener<String> cb, Double lat, Double lon){
		this.lat=lat;
		this.lon=lon;
		this.ctx = ctx;
		this.callback= cb;
	}
	protected void onPreExecute(){
		dialog = ProgressDialog.show(ctx, "", "Loading...");
	}
	
	protected void onPostExecute(String result){
		callback.onTaskComplete("Finished");
		dialog.dismiss();
	}

}
