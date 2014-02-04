package com.fsn.cauly.example;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.AdapterView.OnItemClickListener;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulySquare;
import com.fsn.cauly.CaulySquareAd;
import com.fsn.cauly.CaulySquareDisplayAd;
import com.fsn.cauly.CaulySquareDisplayAdListener;
import com.fsn.cauly.CaulySquareListener;

public class PublisherActivity extends Activity implements CaulySquareListener, OnClickListener, CaulySquareDisplayAdListener {

	String APP_CODE=Util.APPCODE;//"gatester";  // your app code which you are assigned.
    Button show_offerwall,request_adwall_status, requestIsNewAdwall;
    CaulySquare mCaulySquare;
    ArrayList<CaulySquareAd> mOfferList;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);
        setSpinner();
        request_adwall_status = (Button) findViewById(R.id.request_adwall_status);
        show_offerwall = (Button) findViewById(R.id.show_adwall);
        requestIsNewAdwall = (Button) findViewById(R.id.request_isnew_adwall);
        requestIsNewAdwall.setOnClickListener(this);
        request_adwall_status.setOnClickListener(this);
        show_offerwall.setOnClickListener(this);
        show_offerwall.setEnabled(false);
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
					
					if(pos!=Util.CODE_PublisherActivity){
						Util.goActivity(pos, PublisherActivity.this);
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
	    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.request_adwall_status: 
			mCaulySquare.requestOfferStatus(this);  
			break;
		case R.id.show_adwall:					
			showOfferWallScreen();
			break;
		case R.id.request_isnew_adwall:					
			requestIsNewOfferWallCheck();
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
	
	
	// show adwall page. it calls back "onOpenOfferwall","onCloseOfferwall"  on CaulySquareListener
	void showOfferWallScreen()
	{
		mCaulySquare.showOfferwall(this, "Your own title");
	}
	
	
	// show adwall page. it calls back "onOpenOfferwall","onCloseOfferwall"  on CaulySquareListener
	void requestIsNewOfferWallCheck()
	{
		mCaulySquare.requestIsNewOfferWall(this);
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
	// This is called when Offer Detail Dialogue closed.
	public void onCloseOfferDetails(int arg0, String arg1) {
		
	}

	@Override
	// This is called when offerWall closed.
	public void onCloseOfferwall(int retCode, String retMsg) {
		
	}

	@Override
	public void onOfferListReceived(int retCode, String retMsg,	ArrayList<CaulySquareAd> arg2) {
	}

	@Override
	// This is called when offer status received. 
	// It tells us whether offerwall is available or not;
	public void onOfferStatusReceived(int retCode, String retMsg) {
		if(retCode > 0) // offers is available. 
		{
			show_offerwall.setEnabled(true);
		}
		else			// offers is not available at this moment for some reason. 
		{
			Toast.makeText(this, ""+retMsg, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	// This is called when Offer Detail Dialog shows.
	public void onOpenOfferDetails() {
		
	}

	@Override
	// This is called when offerwall shows.
	public void onOpenOfferwall() {
		
	}

	@Override
	public void onClosedDisplayAd(CaulySquareDisplayAd arg0) {
		
	}

	@Override
	public void onFailedToReceiveDisplayAd(CaulySquareDisplayAd arg0, int arg1,	String arg2) {
	}

	
	@Override
	public void onReceiveDisplayAd(CaulySquareDisplayAd ad, boolean arg1) {
	}
	@Override
	public void onIsNewOfferwall(boolean isNew) {
		if(isNew)
			Toast.makeText(this, "새로운 광고가 있습니다.", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(this, "기존의 광고 입니다.", Toast.LENGTH_SHORT).show();
	}
}
