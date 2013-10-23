package com.fsn.cauly.example;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulySquare;
import com.fsn.cauly.CaulySquareAd;
import com.fsn.cauly.CaulySquareDisplayAd;
import com.fsn.cauly.CaulySquareDisplayAdListener;
import com.fsn.cauly.CaulySquareListener;

public class PublisherActivity extends Activity implements CaulySquareListener, OnClickListener, CaulySquareDisplayAdListener {

	String APP_CODE="gatester";  // your app code which you are assigned.
    Button show_offerwall, request_offerlist, show_offerdetail,request_adwall_status,show_display;
    CaulySquare mCaulySquare;
    ArrayList<CaulySquareAd> mOfferList;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);
        Button go_adv = (Button) findViewById(R.id.go_adv);
        go_adv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(PublisherActivity.this, AdvertiserActivity.class));
			}
		});
        request_adwall_status = (Button) findViewById(R.id.request_adwall_status);
        show_offerwall = (Button) findViewById(R.id.show_adwall);
        request_offerlist = (Button) findViewById(R.id.request_ad_data);
        show_offerdetail = (Button) findViewById(R.id.show_ad_detail);
        show_display = (Button) findViewById(R.id.show_display);
        request_adwall_status.setOnClickListener(this);
        show_offerwall.setOnClickListener(this);
        request_offerlist.setOnClickListener(this);
        show_offerdetail.setOnClickListener(this);
        show_display.setOnClickListener(this);
        show_offerwall.setEnabled(false);
        show_offerdetail.setEnabled(false);
        
        /////////////////////////////////////////////////////////////////////
        // 카울리 스퀘어 초기화 
        initCaulySquare();
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
		case R.id.request_ad_data: 				 
			requestOfferList(); 
			break;
		case R.id.show_ad_detail:				
			show_AdDetail();
			break;
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
	
	
	// show adwall page. it calls back "onOpenOfferwall","onCloseOfferwall"  on CaulySquareListener
	void showOfferWallScreen()
	{
		mCaulySquare.showOfferwall(this, "Your own title");
	}
	
	
	 // In case that you want to make your own design offer wall, you can get offer list .
	// It calls back "onOfferListReceived" on CaulySquareListener
	void requestOfferList()
	{
		mCaulySquare.requestOfferList(this); 
	}
	
	
	// This must call after requestOfferList().
	 // Once you succeed to get offer list, you can call this for showing detail info dialog 
    //which leads to specific ad action
	void show_AdDetail()
	{
		if(mOfferList.size()>0)
			mCaulySquare.showOfferDetailDialog(this, mOfferList.get(0));
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
	// This is called when Offer Detail Dialogue closed.
	public void onCloseOfferDetails(int arg0, String arg1) {
		
	}

	@Override
	// This is called when offerWall closed.
	public void onCloseOfferwall(int retCode, String retMsg) {
		
	}

	@Override
	// This is called when offer list received. 
	// CaulySquareAd contains offer Ad information as following.
	/*
	 *  public class CaulySquareAd {
			public String title;  		 // 광고 타이틀
			public String desc;   		 // 광고 상세 설명
			public String link;  		 // 랜딩페이지 URL
			public int ads_cd;			 //광고 고유번호
			public String img_url; 		 // 광고 Thumbnail URL
			public int reward;  		 //reward 가격
			public String point_url;  	 //reward 단위 Thumbnail URL
			
			...
		}
	 * */
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
		Log.i("CaulySample","onFailedToReceiveDisplayAd "+arg1+"  "+arg2 );
	}

	
	// DisplayAd is ready for showing 
	// 
	@Override
	public void onReceiveDisplayAd(CaulySquareDisplayAd ad, boolean arg1) {
		ad.show();
	}
}
