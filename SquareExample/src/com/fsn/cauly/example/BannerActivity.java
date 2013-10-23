package com.fsn.cauly.example;

import com.fsn.cauly.CaulyAdView;
import com.fsn.cauly.CaulyAdViewListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;

public class BannerActivity extends Activity implements CaulyAdViewListener {

	// Java Activity 전환 버튼
	public void switchToJavaActivity(View button) {
		finish();
	}

	//////////////////////////////////////////////////////////////////
	//
	// XML 기반 배너 광고
	//
	// - 이하 Java 코드는 CaulyAdView 상태에 따라 제어하기 위해 필요한 것으로,
	//    제어 필요가 없다면, 추가할 필요가 없으며,
	//    XML 파일 설정만으로 광고를 노출할 수 있다.
	//
	//////////////////////////////////////////////////////////////////

	private CaulyAdView xmlAdView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
        setSpinner();
        // 선택사항: XML의 AdView 항목을 찾아 Listener 설정
        xmlAdView = (CaulyAdView) findViewById(R.id.xmladview);
        xmlAdView.setAdViewListener(this);
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
 					i = new Intent(BannerActivity.this,PublisherActivity.class);
 					startActivity(i);
 					finish();
 					break;
 				case 1:
 					i = new Intent(BannerActivity.this,CustomOfferwallPublisherActivity.class);
 					startActivity(i);
 					finish();
 					break;
 				case 2:
 					i = new Intent(BannerActivity.this,DisplayAdPublisherActivity.class);
 					startActivity(i);
 					finish();
 					break;
 				case 3:
 					i = new Intent(BannerActivity.this,AdvertiserActivity.class);
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
         go_pub.setSelection(4);
    }
    // Activity 버튼 처리
	// - XML 배너 갱신
	public void onReloadJavaAdView(View button) {
		xmlAdView.reload();
	}
	
    // 선택사항: CaulyAdViewListener
    //	광고 동작에 대해 별도 처리가 필요 없는 경우,
    //	Activity의 "implements CaulyAdViewListener" 부분 제거하고 생략 가능.

	@Override
	public void onReceiveAd(CaulyAdView adView, boolean isChargeableAd) {
		// 광고 수신 성공 & 노출된 경우 호출됨.
		// 수신된 광고가 무료 광고인 경우 isChargeableAd 값이 false 임.
		if (isChargeableAd == false) {
			Log.d("Banner", "free banner AD received.");
		}
		else {
			Log.d("Banner", "normal banner AD received.");
		}
	}

	@Override
	public void onFailedToReceiveAd(CaulyAdView adView, int errorCode, String errorMsg) {
		// 배너 광고 수신 실패할 경우 호출됨.
		Log.d("Banner", "failed to receive banner AD.");
	}

	@Override
	public void onShowLandingScreen(CaulyAdView adView) {
		// 광고 배너를 클릭하여 랜딩 페이지가 열린 경우 호출됨.
		Log.d("Banner", "banner AD landing screen opened.");
	}    

	@Override
	public void onCloseLandingScreen(CaulyAdView adView) {
		// 광고 배너를 클릭하여 열린 랜딩 페이지가 닫힌 경우 호출됨.
		Log.d("Banner", "banner AD landing screen closed.");
	}
}
