package com.fsn.cauly.example;

import android.content.Context;
import android.content.Intent;

public class Util {
	public static final int CODE_PublisherActivity = 0;
	public static final int CODE_CustomOfferwallPublisherActivity = 1;
	public static final int CODE_DisplayAdPublisherActivity = 2;
	public static final int CODE_AdvertiserActivity = 3;
	public static final int CODE_BannerActivity = 4;
	public static final int CODE_CloseAdPublisherActivity = 5;
	public static final int CODE_ToastAdPublisherActivity = 6;
	
	public static  String[] items = {"Go OfferWall Ad","Go Custom Offerwal Ad","Go DisplayAd","Go AdVertiser Report","Go Banner Ad","Go Close Ad","Go Toast Ad"};
	public static void goActivity(int pos, Context context) {
		Intent i;
		switch (pos) {
		case CODE_PublisherActivity:
			i = new Intent(context, PublisherActivity.class);
			context.startActivity(i);
			break;
		case CODE_CustomOfferwallPublisherActivity:
			i = new Intent(context, CustomOfferwallPublisherActivity.class);
			context.startActivity(i);
			break;
		case CODE_DisplayAdPublisherActivity:
			i = new Intent(context, DisplayAdPublisherActivity.class);
			context.startActivity(i);
			break;
		case CODE_AdvertiserActivity:
			i = new Intent(context, AdvertiserActivity.class);
			context.startActivity(i);
			break;
		case CODE_BannerActivity:
			i = new Intent(context,BannerActivity.class);
			context.startActivity(i);
			break;
		case CODE_CloseAdPublisherActivity:
			i = new Intent(context, CloseAdPublisherActivity.class);
			context.startActivity(i);
			break;
		case CODE_ToastAdPublisherActivity:
			i = new Intent(context, ToastAdPublisherActivity.class);
			context.startActivity(i);
			break;
		}
	}
}
