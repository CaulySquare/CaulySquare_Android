package com.fsn.cauly.example;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulySquare;
import com.fsn.cauly.CaulySquareAd;
import com.fsn.cauly.CaulySquareListener;
import com.fsn.cauly.Logger;
import com.fsn.cauly.Logger.LogLevel;

public class ExampleActivity extends Activity implements CaulySquareListener {

	
	// 광고 요청을 위한 App Code
	private static final String APP_CODE = "CAULY";
    Button send_offerwall, request_offerlist, show_offerdetail;
    Button actioncompleted, execcompleted;
    CaulySquare mCaulySquare;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Cauly 로그 수준 지정 : 로그의 상세함 순서는 다음과 같다.
        //	LogLevel.Info > LogLevel.Warn > LogLevel.Error > LogLevel.None
        Logger.setLogLevel(LogLevel.Debug);
     
        // CaulyAdInfo 생성 : CaulyAdInfoBuilder 사용. APP_CODE 비롯한 전체 설정.
        // 설정 메소드
        //    	appCode(String appCode)				: APP_CODE 지정. 생성자 "CaulyAdInfoBuilder(APP_CODE)"를 사용해도 됨.

		CaulyAdInfo adInfo = new CaulyAdInfoBuilder(APP_CODE).build();
		mCaulySquare = CaulySquare.initWithAdInfo(this, adInfo);
		
		
    }

	
	//////////////////////////////
	//
	// CaulySquareState Function 호출
	//
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
	//
	// CaulySquare 광고 Listener
	//
	//////////////////////////////
	

	@Override
	public void onCloseOfferDetails(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCloseOfferwall(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOfferListReceived(int arg0, String arg1,
			ArrayList<CaulySquareAd> arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOfferStatusReceived(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpenOfferDetails() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpenOfferwall() {
		// TODO Auto-generated method stub
		
	}
}
