package com.fsn.cauly.example;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulySquare;
import com.fsn.cauly.CaulySquareAd;
import com.fsn.cauly.CaulySquareDisplayAd;
import com.fsn.cauly.CaulySquareDisplayAdListener;
import com.fsn.cauly.CaulySquareListener;
import com.fsn.cauly.cachemanager.ImageCacheManager;

public class CustomOfferwallPublisherActivity extends Activity implements CaulySquareListener, OnClickListener, CaulySquareDisplayAdListener {

	String APP_CODE="gatester";  // your app code which you are assigned.
    Button  request_offerlist, show_offerdetail;
    CaulySquare mCaulySquare;
    ArrayList<CaulySquareAd> mOfferList;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher3);
        setSpinner();
        request_offerlist = (Button) findViewById(R.id.request_ad_data);
        show_offerdetail = (Button) findViewById(R.id.show_ad_detail);
        request_offerlist.setOnClickListener(this);
        show_offerdetail.setOnClickListener(this);
        show_offerdetail.setEnabled(false);
        
        /////////////////////////////////////////////////////////////////////
        // 카울리 스퀘어 초기화 
        initCaulySquare();
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
	 					i = new Intent(CustomOfferwallPublisherActivity.this,PublisherActivity.class);
	 					startActivity(i);
	 					finish();
	 					break;
	 				case 2:
	 					i = new Intent(CustomOfferwallPublisherActivity.this,DisplayAdPublisherActivity.class);
	 					startActivity(i);
	 					finish();
	 					break;
	 				case 3:
	 					i = new Intent(CustomOfferwallPublisherActivity.this,AdvertiserActivity.class);
	 					startActivity(i);
	 					finish();
	 					break;
	 				case 4:
						i = new Intent(CustomOfferwallPublisherActivity.this,BannerActivity.class);
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
	         go_pub.setSelection(1);
	    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.request_ad_data: 				 
			requestOfferList(); 
			break;
		case R.id.show_ad_detail:				
			show_AdDetail();
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
			if(mOfferList!=null && mOfferList.size()>0){
				show_offerdetail.setEnabled(true);
				showCustomOfferwallDialog();
			}
		}
		else			// failed
		{
			Toast.makeText(this, ""+retMsg, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onOfferStatusReceived(int retCode, String retMsg) {
	}

	@Override
	// This is called when Offer Detail Dialog shows.
	public void onOpenOfferDetails() {
		
	}

	@Override
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
	
	// Custom Offerwall implementation 
	void showCustomOfferwallDialog()
	{
		final Dialog dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
		dialog.setContentView(R.layout.custom_offerwall);
		ListView listview =  (ListView) dialog.findViewById(R.id.list);
		ImageView close =  (ImageView) dialog.findViewById(R.id.close);
		listview.setDividerHeight(0);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,long arg3) {
				mCaulySquare.showOfferDetailDialog(CustomOfferwallPublisherActivity.this, mOfferList.get(pos));
			}
		});
		close.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		ListAdapter adapter = new ListAdapter(); 
		adapter.setList(mOfferList);
		listview.setAdapter(adapter);
		dialog.show();
	}
	
	class ListAdapter extends BaseAdapter 
	{

		ArrayList<CaulySquareAd> list;
		public void setList(ArrayList<CaulySquareAd> list)
		{
			this.list = list;
		}
		public int getCount() {
			return list.size();
		}

		public Object getItem(int position) {
			return list.get(position);
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if(position <0 || position >=list.size())
				return convertView;
			if(convertView!=null && position==(Integer)convertView.getTag())
				return convertView;
			
			CaulySquareAd info = list.get(position);
		    View view = View.inflate(CustomOfferwallPublisherActivity.this, R.layout.custom_offerwall_item, null);
		    View bg = view.findViewById(R.id.bg);
		    
		    TextView title = (TextView) view.findViewById(R.id.title);
		    TextView detail = (TextView) view.findViewById(R.id.detail);
		    TextView money = (TextView) view.findViewById(R.id.money);
		    TextView type = (TextView) view.findViewById(R.id.type);
		    ImageView  icon = (ImageView) view.findViewById(R.id.icon);
		    ImageView  money_icon = (ImageView) view.findViewById(R.id.money_icon);
		    title.setText(""+info.title);
		    detail.setText(""+info.popover_desc);
		    money.setText(""+info.reward);
		    type.setText(getTypeString(info.action_type));
		    ImageCacheManager.getInstance(CustomOfferwallPublisherActivity.this).setImageBitmap(info.img_url, icon);
		    ImageCacheManager.getInstance(CustomOfferwallPublisherActivity.this).setImageBitmap(info.point_url, money_icon);
	    	LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) bg.getLayoutParams();
	    	lp.bottomMargin = PixelFromDP(CustomOfferwallPublisherActivity.this , 8);
	    	bg.setLayoutParams(lp);
	    	bg.setBackgroundResource(R.drawable.block02);
		    view.setTag(Integer.valueOf(position));
			return view;
		}
		
	}
	private String getTypeString(int type)
	{
		switch(type)
		{
		case 1:
		case 2:
			return "기본형";
		case 3:
			return "설치형";
		case 4:
			return "실행형";
		case 5:
			return "액션형";
		}
		return "기본형";
	}
	public  int PixelFromDP(Context context, float dip) {
		if (context == null)
			return 0;
		
		return (int)(dip * context.getResources().getDisplayMetrics().density);
	}
}
