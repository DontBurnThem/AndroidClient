package com.DBT.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.DBT.android.R;
public class SplashFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.splash, 
	            container, false);
	    BitmapDrawable bd = (BitmapDrawable)getResources().getDrawable(R.drawable.logobig);
		   Bitmap bit = bd.getBitmap();
		   ImageView iv = (ImageView)view.findViewById(R.id.splash_icon);
		   iv.setImageBitmap(Tools.getResizedBitmap(bit, 2048,2048));
	    return view;
	}
	

}
