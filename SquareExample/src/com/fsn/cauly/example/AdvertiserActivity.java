package com.fsn.cauly.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
        Button go_pub = (Button) findViewById(R.id.go_pub);
        go_pub.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
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
