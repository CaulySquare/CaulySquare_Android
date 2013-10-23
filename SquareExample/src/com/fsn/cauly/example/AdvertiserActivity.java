package com.fsn.cauly.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulySquare;
import com.fsn.cauly.CaulySquare.CaulySquareActionType;

public class AdvertiserActivity extends Activity {

	String APP_CODE="gatester";  // your app code which you are assigned.
    Button actioncompleted, execcompleted;
    CaulySquare mCaulySquare;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser);
        setSpinner();
        execcompleted = (Button) findViewById(R.id.send_exec);
        actioncompleted = (Button) findViewById(R.id.send_action);
        
        /////////////////////////////////////////////////////////////////////
        // Cauly Square Initiation  
        // You must call this function 
        initCaulySquare();
        
        // click event register and request each action. 
        
        giveRewardtoUserWhenAction();
        
        giveRewardtoUserWhenExecution();
        
    }
	 void setSpinner()
	    {
	    	 Spinner go_pub = (Spinner) findViewById(R.id.go_adv);
	         go_pub.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,int pos, long arg3) {
					Intent i;
					switch(pos)
					{
					case 0:
						i = new Intent(AdvertiserActivity.this,PublisherActivity.class);
						startActivity(i);
						finish();
						break;
					case 1:
						i = new Intent(AdvertiserActivity.this,CustomOfferwallPublisherActivity.class);
						startActivity(i);
						finish();
						break;
					case 2:
						i = new Intent(AdvertiserActivity.this,DisplayAdPublisherActivity.class);
						startActivity(i);
						finish();
						break;
					case 4:
						i = new Intent(AdvertiserActivity.this,BannerActivity.class);
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
	
}
