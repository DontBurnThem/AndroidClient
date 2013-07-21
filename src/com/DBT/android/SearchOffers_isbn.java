package com.DBT.android;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SearchOffers_isbn extends Activity implements AsyncTaskCompleteListener<String>{
	static Context ctx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ctx = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_isbn);
		final EditText isbn_searched = (EditText)findViewById(R.id.txt_booksearched);
		Button btn_search = (Button)findViewById(R.id.btn_searchbook_isbn);
		btn_search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				SearchISBNOffers sibo = new SearchISBNOffers(ctx, SearchOffers_isbn.this, isbn_searched.getText().toString());
				sibo.execute();
			}
			
		});
	}
	@Override
	public void onTaskComplete(String result) {
		String[] values= new String[SearchISBNOffers.offers_ISBN.size()];
		ListView lv = (ListView)findViewById(R.id.list_found);
		if (result.equals("Completed")){
			for (int i=0; i<SearchISBNOffers.offers_ISBN.size(); i++){
				values[i]=SearchISBNOffers.offers_ISBN.get(i).getBookbeam().getTitle()+", "+ SearchISBNOffers.offers_ISBN.get(i).getBookbeam().getAuthors();
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
					String selected = (String) arg0.getItemAtPosition(arg2);
					System.out.println("Selected:" + selected);
					if(selected.equals(SearchISBNOffers.offers_ISBN.get(arg2).getBookbeam().getTitle()+", "+ SearchISBNOffers.offers_ISBN.get(arg2).getBookbeam().getAuthors())){
						Intent intent = new Intent(SearchOffers_isbn.this, BookOfferDetail.class);
						intent.putExtra("from", "isbn");
						intent.putExtra("index", arg2);
						startActivity(intent);
						finish();
					}
			}
			
		});
		
	}
}
