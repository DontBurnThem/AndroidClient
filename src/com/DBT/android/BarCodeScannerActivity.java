package com.DBT.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
public class BarCodeScannerActivity extends Activity{
	public static Context ctx;
	protected void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		ctx=this;
		if (isCameraAvailable()){
			Intent intent = new Intent(this, ZBarScannerActivity.class);
			startActivityForResult(intent, 0);
		}else{
			Toast.makeText(BarCodeScannerActivity.this, "No Camera Available", Toast.LENGTH_SHORT);
		}
		
	}
	
    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    //Toast.makeText(this, "Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT), Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "BarCode successfully readed" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent (BarCodeScannerActivity.this, AddOfferActivity.class);
                    intent.putExtra("ISBN", data.getStringExtra(ZBarConstants.SCAN_RESULT));
                    startActivity(intent);
                    finish();
                } else if(resultCode == RESULT_CANCELED && data != null) {
                    String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
                    if(!TextUtils.isEmpty(error)) {
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
    }
    }

}
