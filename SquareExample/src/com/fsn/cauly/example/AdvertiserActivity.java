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

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulySquare;
import com.fsn.cauly.CaulySquare.CaulySquareActionType;
import com.fsn.cauly.CaulySquareAd;
import com.fsn.cauly.CaulySquareListener;

public class AdvertiserActivity extends Activity implements  CaulySquareListener {

	String APP_CODE="gatester";  // your app code which you are assigned.
    Button actioncompleted, execcompleted, showOfferwall;
    CaulySquare mCaulySquare;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser);
        setSpinner();
        execcompleted = (Button) findViewById(R.id.send_exec);
        actioncompleted = (Button) findViewById(R.id.send_action);
        showOfferwall  = (Button) findViewById(R.id.show_offerwall);
        /////////////////////////////////////////////////////////////////////
        // Cauly Square Initiation  
        // You must call this function 
        initCaulySquare();
        // click event register and request each action. 
        
        giveRewardtoUserWhenAction();
        
        giveRewardtoUserWhenExecution();
        
        showOfferwallWhenClick();
        
    }
	
	void setSpinner()
	    {
	    	 Spinner go_pub = (Spinner) findViewById(R.id.go_adv);
	         go_pub.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,int pos, long arg3) {
					if(pos!=Util.CODE_AdvertiserActivity){
						Util.goActivity(pos, AdvertiserActivity.this);
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
	         go_pub.setSelection(3);
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
	}
	
	
	
	// Advertiser Side  
	void giveRewardtoUserWhenExecution( ) {
		execcompleted.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCaulySquare.reportAction(AdvertiserActivity.this, CaulySquareActionType.EXECUTION_COMPLETE, "exec completed");
			}
		});
		
		   // EXECUTION_COMPLETE : 실행 완료
		   // ACTION_COMPLETE : 액션 완료
	}
	
	

	// Advertiser Side  
	void giveRewardtoUserWhenAction( ) {
		actioncompleted.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCaulySquare.reportAction(AdvertiserActivity.this, CaulySquareActionType.ACTION_COMPLETE, "action completed");
			}
		});
		   // EXECUTION_COMPLETE : 실행 완료
		   // ACTION_COMPLETE : 액션 완료
	}

	 private void showOfferwallWhenClick() {
		 showOfferwall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CaulyAdInfo adInfo1 = new CaulyAdInfoBuilder(APP_CODE).testMode(true).build();
				mCaulySquare = CaulySquare.initWithAdInfo(AdvertiserActivity.this, adInfo1);
				mCaulySquare.setCustomId( "kakaoid_of_the_game" );
				mCaulySquare.showOfferwall(AdvertiserActivity.this, "Your own title");
			}
		});
	}
	
	@Override
	public void onCloseOfferDetails(int arg0, String arg1) {
		
	}

	@Override
	public void onCloseOfferwall(int arg0, String arg1) {
		
	}

	@Override
	public void onOfferListReceived(int arg0, String arg1,ArrayList<CaulySquareAd> arg2) {
		
	}

	@Override
	public void onOfferStatusReceived(int arg0, String arg1) {
		
	}

	@Override
	public void onOpenOfferDetails() {
		
	}

	@Override
	public void onOpenOfferwall() {
		
	}
	
}
