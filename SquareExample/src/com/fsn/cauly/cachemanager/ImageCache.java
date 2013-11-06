package com.fsn.cauly.cachemanager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

public class ImageCache  implements Serializable{
	
	private static final long serialVersionUID = 6671578131461367414L;
	
	private final Map<String , SynchronizedBitmap> synchronizedMap;
	
	public ImageCache() {
		synchronizedMap = new HashMap<String , SynchronizedBitmap>();
	}
	
	void addBitmapToCache(String url , Bitmap bitmap) {
		synchronizedMap.put(url, new SynchronizedBitmap(bitmap));	
	}
	
	Bitmap getBitmapFromCache(String url) {
		SynchronizedBitmap bitmap = synchronizedMap.get(url);
		if (bitmap != null)
			return bitmap.get();
		return null;
	}
	
	public void clearCache() {
		synchronizedMap.clear();
	}
		
	public static ImageCache toImageCache (String fileName) {
		ImageCache imageCache = null;
		try {
			imageCache = (ImageCache)ObjectRepository.readObject(fileName);
		} catch (Exception e) {
		}
		return imageCache;  
	}
	
	public static boolean fromImageCache (String fileName , ImageCache cache) {
		try {
			ObjectRepository.saveObject(cache, fileName);
			return true;
		} catch (Exception e) {
		}
		return false;
	}
		
	static final class SynchronizedBitmap implements Serializable {

		private static final long serialVersionUID = -1145951279811763306L;
		
		private final Bitmap bitmap;
		
		public SynchronizedBitmap(Bitmap bitmap) {
			this.bitmap = bitmap;
		}
		
		public Bitmap get() {
			return bitmap;
		}
	}
}
