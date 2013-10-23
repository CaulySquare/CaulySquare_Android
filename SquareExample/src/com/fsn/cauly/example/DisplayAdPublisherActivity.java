package com.fsn.cauly.example;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulySquare;
import com.fsn.cauly.CaulySquareAd;
import com.fsn.cauly.CaulySquareDisplayAd;
import com.fsn.cauly.CaulySquareDisplayAdListener;
import com.fsn.cauly.CaulySquareListener;

public class DisplayAdPublisherActivity extends Activity implements CaulySquareListener, OnClickListener, CaulySquareDisplayAdListener {

	String APP_CODE="gatester";  // your app code which you are assigned.
    Button show_display;
    CaulySquare mCaulySquare;
    ArrayList<CaulySquareAd> mOfferList;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher2);
        setSpinner();
        show_display = (Button) findViewById(R.id.show_display);
        show_display.setOnClickListener(this);
        
        /////////////////////////////////////////////////////////////////////
        // 카울리 스퀘어 초기화 
        initCaulySquare();
    }
	 void setSpinner()
	    {
	    	 Spinner go_pub = (Spinner) findViewById(R.id.go_adv);
	         go_pub.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,	int pos, long arg3) {
					Intent i;
	 				switch(pos)
	 				{
	 				case 0:
	 					i = new Intent(DisplayAdPublisherActivity.this,PublisherActivity.class);
	 					startActivity(i);
	 					finish();
	 					break;
	 				case 1:
	 					i = new Intent(DisplayAdPublisherActivity.this,CustomOfferwallPublisherActivity.class);
	 					startActivity(i);
	 					finish();
	 					break;
	 				case 3:
	 					i = new Intent(DisplayAdPublisherActivity.this,AdvertiserActivity.class);
	 					startActivity(i);
	 					finish();
	 					break;
	 				case 4:
						i = new Intent(DisplayAdPublisherActivity.this,BannerActivity.class);
						startActivity(i);
						finish();
						break;
	 				}
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	         
	         String[] items = {"Go OfferWall Ad","Go Custom Offerwal Ad","Go DisplayAd","Go AdVertiser Report","Go Banner Ad"};
	         ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
	         aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	         go_pub.setAdapter(aa);
	         go_pub.setSelection(2);
	    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.show_display:
			show_DispayAd();
			break;
		}
	}
	

	// CaulySquare Initiation  
	void initCaulySquare()
	{
		// CaulyAdInfo creation : CaulyAdInfoBuilder with APP_CODE 
		CaulyAdInfo adInfo1 = new CaulyAdInfoBuilder(APP_CODE).build();
		
		 // CaulySquare Initiation 
		mCaulySquare = CaulySquare.initWithAdInfo(this, adInfo1);
		
		// kakaoid or user unique key in Game. ( optional ) 
		mCaulySquare.setCustomId( "kakaoid_of_the_game,안녕하세요" );
		
		// callback listener register.
		mCaulySquare.setListener(this);
	}
	
	// CaulySquareDisplayAd request 
	// Before you call show() , You must call requestDisplayAd() first. 
	// You see the result of requestDisplayAd at onReceiveDisplayAd and onFailedToReceiveDisplayAd 
	//which is implemented on CaulySquareDisplayAdListener
	private void show_DispayAd() {
		CaulyAdInfo adInfo = new CaulyAdInfoBuilder(APP_CODE).build();
		CaulySquareDisplayAd displayad = new CaulySquareDisplayAd();
		displayad.setAdInfo(adInfo);
		displayad.setDisplayAdListener(this);
		displayad.requestDisplayAd(this);		
	}
	
	//////////////////////////////
	// CaulySquareState Function  
	// You Should call this 4 function
	// onStart, onResume, onStop, onPause
	//////////////////////////////
	
	@Override
	protected void onPause() {
		super.onPause();
		mCaulySquare.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mCaulySquare.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
		mCaulySquare.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mCaulySquare.onStop();
	}
	
	//////////////////////////////
	// CaulySquare Callback Listener
	//////////////////////////////
	
	@Override
	public void onCloseOfferDetails(int arg0, String arg1) {
		
	}

	@Override
	public void onCloseOfferwall(int retCode, String retMsg) {
		
	}
	@Override
	public void onOfferListReceived(int retCode, String retMsg,	ArrayList<CaulySquareAd> arg2) {
	}

	@Override
	public void onOfferStatusReceived(int retCode, String retMsg) {
	}

	@Override
	public void onOpenOfferDetails() {
		
	}

	@Override
	public void onOpenOfferwall() {
		
	}

	@Override
	public void onClosedDisplayAd(CaulySquareDisplayAd arg0) {
		
	}

	@Override
	public void onFailedToReceiveDisplayAd(CaulySquareDisplayAd arg0, int arg1,	String arg2) {
		Log.i("CaulySample","onFailedToReceiveDisplayAd "+arg1+"  "+arg2 );
	}

	
	// DisplayAd is ready for showing 
	// 
	@Override
	public void onReceiveDisplayAd(CaulySquareDisplayAd ad, boolean arg1) {
		ad.show();
	}
}
