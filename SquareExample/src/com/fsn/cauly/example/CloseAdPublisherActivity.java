package com.fsn.cauly.example;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulySquare;
import com.fsn.cauly.CaulySquareAd;
import com.fsn.cauly.CaulySquareCloseAd;
import com.fsn.cauly.CaulySquareCloseAdListener;
import com.fsn.cauly.CaulySquareListener;

public class CloseAdPublisherActivity extends Activity implements CaulySquareListener, OnClickListener, CaulySquareCloseAdListener {

	String APP_CODE= Util.APPCODE;//"gatester";  // your app code which you are assigned.
    Button show_close, request_close;
    CaulySquare mCaulySquare;
    ArrayList<CaulySquareAd> mOfferList;
    CaulySquareCloseAd mCloseAd; 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_close);
        setSpinner();
        show_close = (Button) findViewById(R.id.show_close);
        show_close.setOnClickListener(this);
        
        request_close = (Button) findViewById(R.id.request_close);
        request_close.setOnClickListener(this);
        
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
					
					if(pos!=Util.CODE_CloseAdPublisherActivity)
					{
						Util.goActivity(pos, CloseAdPublisherActivity.this);
						finish();
					}
						
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	         
	         ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Util.items);
	         aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	         go_pub.setAdapter(aa);
	         go_pub.setSelection(5);
	    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.show_close:
			if(mCloseAd!=null){
				mCloseAd.show();
			}
			break;
		case R.id.request_close:
			request_CloseAd();
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
	
	// CaulySquareCloseAd request 
	// Before you call show() , You must call requestCloseAd() first. 
	// You see the result of requestCloseAd at onReceiveCloseAd and onFailedToReceiveCloseAd 
	//which is implemented on CaulySquareCloseAdListener
	private void request_CloseAd() {
		CaulyAdInfo adInfo = new CaulyAdInfoBuilder(APP_CODE).build();
		mCloseAd = new CaulySquareCloseAd();
		mCloseAd.setAdInfo(adInfo);
		mCloseAd.setCloseAdListener(this);
		mCloseAd.requestCloseAd(this);
		
		show_close.setEnabled(false);
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
	public void onClosedCloseAd(CaulySquareCloseAd arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onFailedToReceiveCloseAd(CaulySquareCloseAd arg0, int arg1,	String arg2) {
		Toast.makeText(this, "onFailedToReceiveCloseAd "+arg1+"  "+arg2, Toast.LENGTH_SHORT).show();
		show_close.setEnabled(true);
	}
	@Override
	public void onReceiveCloseAd(CaulySquareCloseAd ad, boolean arg1) {
		show_close.setEnabled(true);
	}
	@Override
	public void onIsNewOfferwall(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
}
