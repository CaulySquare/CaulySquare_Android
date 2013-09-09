package com.fsn.cauly.example;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulySquare;
import com.fsn.cauly.CaulySquare.CaulySquareActionType;
import com.fsn.cauly.CaulySquareAd;
import com.fsn.cauly.CaulySquareListener;
import com.fsn.cauly.Logger;
import com.fsn.cauly.Logger.LogLevel;

public class ExampleActivity extends Activity implements CaulySquareListener, OnClickListener {

	String APP_CODE="SQUARE_TEST_3";
    Button show_offerwall, request_offerlist, show_offerdetail,request_adwall_status;
    Button actioncompleted, execcompleted;
    CaulySquare mCaulySquare;
    ArrayList<CaulySquareAd> mOfferList;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request_adwall_status = (Button) findViewById(R.id.request_adwall_status);
        show_offerwall = (Button) findViewById(R.id.show_adwall);
        request_offerlist = (Button) findViewById(R.id.request_ad_data);
        show_offerdetail = (Button) findViewById(R.id.show_ad_detail);
        actioncompleted = (Button) findViewById(R.id.send_exec);
        execcompleted = (Button) findViewById(R.id.send_action);
        show_offerwall.setOnClickListener(this);
        request_offerlist.setOnClickListener(this);
        show_offerdetail.setOnClickListener(this);
        actioncompleted.setOnClickListener(this);
        execcompleted.setOnClickListener(this);
        request_adwall_status.setOnClickListener(this);
        
        show_offerwall.setEnabled(false);
        show_offerdetail.setEnabled(false);
        
        // Cauly 로그 수준 지정 : 로그의 상세함 순서는 다음과 같다.
        //	LogLevel.Info > LogLevel.Warn > LogLevel.Error > LogLevel.None
        Logger.setLogLevel(LogLevel.None);
       
        // CaulyAdInfo 생성 : CaulyAdInfoBuilder 사용. APP_CODE 
		CaulyAdInfo adInfo1 = new CaulyAdInfoBuilder(APP_CODE).build();
		
		 // CaulySquare Initiation 
		mCaulySquare = CaulySquare.initWithAdInfo(this, adInfo1);
		// kakaoid나 게임에서 쓰는 id를 전달합니다. ( optional ) 
		mCaulySquare.setCustomId( "kakaoid_of_the_game" );
		// callback listener register.
		mCaulySquare.setListener(this);
    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.request_adwall_status:
			mCaulySquare.requestOfferStatus(this);  
			break;
		case R.id.show_adwall:
			mCaulySquare.showOfferwall(this, "타이틀 입니다.");
			break;
		case R.id.request_ad_data:
			mCaulySquare.requestOfferList(this);
			break;
		case R.id.show_ad_detail:
			if(mOfferList.size()>0)
				mCaulySquare.showOfferDetailDialog(this, mOfferList.get(0));
			break;
		case R.id.send_exec:
			mCaulySquare.reportAction(this, CaulySquareActionType.EXECUTION_COMPLETE, "실행 완료");
			break;
		case R.id.send_action:
			mCaulySquare.reportAction(this, CaulySquareActionType.ACTION_COMPLETE, "액션 완료");
			break;
		}
		
	}
	//////////////////////////////
	// CaulySquareState Function 호출
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
	// CaulySquare 광고 Listener
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
			mOfferList = arg2;
			if(mOfferList!=null && mOfferList.size()>0)
				show_offerdetail.setEnabled(true);
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
			show_offerwall.setEnabled(true);
		}
		else			// offers is not available at this moment for some reason. 
		{
			Toast.makeText(this, ""+retMsg, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	// This is called when Offer Detail Dialogue shows.
	public void onOpenOfferDetails() {
		
	}

	@Override
	// This is called when offerwall shows.
	public void onOpenOfferwall() {
		
	}
}
