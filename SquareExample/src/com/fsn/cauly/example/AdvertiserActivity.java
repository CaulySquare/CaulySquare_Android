package com.fsn.cauly.example;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulySquare;
import com.fsn.cauly.CaulySquare.CaulySquareActionType;
import com.fsn.cauly.CaulySquareAd;
import com.fsn.cauly.CaulySquareListener;

public class AdvertiserActivity extends Activity implements CaulySquareListener {

	String APP_CODE="";  // your app code which you are assigned.
    Button show_offerwall;
    Button actioncompleted, execcompleted;
    CaulySquare mCaulySquare;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser);
        Button go_pub = (Button) findViewById(R.id.go_pub);
        go_pub.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
        show_offerwall = (Button) findViewById(R.id.show_adwall);
        actioncompleted = (Button) findViewById(R.id.send_exec);
        execcompleted = (Button) findViewById(R.id.send_action);
        
        /////////////////////////////////////////////////////////////////////
        // Cauly Square Initiation  
        // You must call this function 
        initCaulySquare();
        
        requestOfferWallScreen();
        
    }
	
	// CaulySquare Initiation  
	void initCaulySquare()
	{
		// CaulyAdInfo creation : CaulyAdInfoBuilder with APP_CODE 
		CaulyAdInfo adInfo1 = new CaulyAdInfoBuilder(APP_CODE).build();
		
		 // CaulySquare Initiation 
		mCaulySquare = CaulySquare.initWithAdInfo(this, adInfo1);
		
		// kakaoid or user unique key in Game. ( optional ) 
		mCaulySquare.setCustomId( "kakaoid_of_the_game" );
		
		// callback listener register.
		mCaulySquare.setListener(this);
	}
	
	
	// show adwall page. it calls back "onOpenOfferwall","onCloseOfferwall"  on CaulySquareListener
	void requestOfferWallScreen()
	{
		show_offerwall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCaulySquare.showOfferwall(AdvertiserActivity.this, "Your own title");
			}
		});
	
	}
	
	
	// Advertiser Side  
	void giveRewardtoUserWhenExecution( ) {
		mCaulySquare.reportAction(this, CaulySquareActionType.EXECUTION_COMPLETE, "exec completed");
		   // EXECUTION_COMPLETE : 실행 완료
		   // ACTION_COMPLETE : 액션 완료
	}
	
	

	// Advertiser Side  
	void giveRewardtoUserWhenAction( ) {
		mCaulySquare.reportAction(this, CaulySquareActionType.ACTION_COMPLETE, "action completed");
		   // EXECUTION_COMPLETE : 실행 완료
		   // ACTION_COMPLETE : 액션 완료
	}
	
	
	//////////////////////////////
	// CaulySquareState Function  
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
	// This is called when offer list received. 
	// CaulySquareAd contains offer Ad information.
	public void onOfferListReceived(int retCode, String retMsg,	ArrayList<CaulySquareAd> arg2) {
		if(retCode > 0 )  // success
		{
		}
		else			// failed
		{
			Toast.makeText(this, ""+retMsg, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	// This is called when offer status received. 
	// It tells us whether offerwall is available or not;
	public void onOfferStatusReceived(int retCode, String retMsg) {
		if(retCode >= 0) // offers is available. 
		{
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
}
