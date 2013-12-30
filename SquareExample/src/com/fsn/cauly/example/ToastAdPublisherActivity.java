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
import com.fsn.cauly.CaulySquareListener;
import com.fsn.cauly.CaulySquareToastAd;
import com.fsn.cauly.CaulySquareToastAd.TOAST_DIRECTION;
import com.fsn.cauly.CaulySquareToastAdListener;

public class ToastAdPublisherActivity extends Activity implements CaulySquareListener, OnClickListener, CaulySquareToastAdListener {

	String APP_CODE="gatester";  // your app code which you are assigned.
    Button show_toast_top, show_toast_bottom, dismiss_toast;
    CaulySquare mCaulySquare;
    ArrayList<CaulySquareAd> mOfferList;
    CaulySquareToastAd mToastAd;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_toast);
        setSpinner();
        show_toast_top = (Button) findViewById(R.id.show_toast_top);
        show_toast_top.setOnClickListener(this);
        show_toast_bottom = (Button) findViewById(R.id.show_toast_bottom);
        show_toast_bottom.setOnClickListener(this);
        dismiss_toast = (Button) findViewById(R.id.cancel_toast);
        dismiss_toast.setOnClickListener(this);
        
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
					
					if(pos!=Util.CODE_ToastAdPublisherActivity)
					{
						Util.goActivity(pos, ToastAdPublisherActivity.this);
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
	         go_pub.setSelection(6);
	    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.show_toast_top:
			show_ToastAd(true);
			break;
		case R.id.show_toast_bottom:
			show_ToastAd(false);
			break;
		case R.id.cancel_toast:
//			if(mToastAd!=null)
//			{
//				mToastAd.cancel();
//				mToastAd = null;
//			}
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
	
	// CaulySquareToastAd request 
	// Before you call show() , You must call requestToastAd() first. 
	// You see the result of requestToastAd at onReceiveToastAd and onFailedToReceiveToastAd 
	//which is implemented on CaulySquareToastAdListener
	private void show_ToastAd(boolean isTop) {
		CaulyAdInfo adInfo = new CaulyAdInfoBuilder(APP_CODE).build();
		CaulySquareToastAd toastAd = new CaulySquareToastAd();
		toastAd.setAdInfo(adInfo);
		toastAd.setToastAdListener(this);
		if(isTop)
			toastAd.requestToastAd(this,TOAST_DIRECTION.TOP);		
		else
			toastAd.requestToastAd(this,TOAST_DIRECTION.BOTTOM);		
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
	public void onClosedToastAd(CaulySquareToastAd arg0) {
		
	}

	@Override
	public void onFailedToReceiveToastAd(CaulySquareToastAd arg0, int arg1,	String arg2) {
		Toast.makeText(this, "onFailedToReceiveToastAd "+arg1+"  "+arg2, Toast.LENGTH_SHORT).show();
	}

	
	// ToastAd is ready for showing 
	// 
	@Override
	public void onReceiveToastAd(CaulySquareToastAd ad, boolean arg1) {
		mToastAd = ad;
		ad.show();
	}
}
