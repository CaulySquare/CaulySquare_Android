package com.fsn.cauly.cachemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageCacheManager {
	public static final String FILE_NAME = "/data/data/com.fsn.cauly.example/cache";
	public interface BitmapDownloadListener
	{
		void onBitmapDownloaded(Bitmap bitmap);
	};
	static ImageCacheManager mManager=null;
	static Context mContext;
	static ImageCache mImageCache;
	static HashMap<String, String> mDownloadMap = new HashMap<String, String>();
	public static synchronized ImageCacheManager getInstance(Context context)
	{	
		mContext = context;
		if(mManager==null){
			mImageCache = new ImageCache();
			mManager = new ImageCacheManager();
			 File file = new File(FILE_NAME);
		        if(!file.exists())
		        	file.mkdir();
		}
		return mManager;
	}

	
	public void setImageBitmap(String url, ImageView imageView) {

		Bitmap bitmap = mImageCache.getBitmapFromCache(url);
		if (bitmap == null || bitmap.isRecycled()) {

			BitmapCacheDownloaderTask task = new BitmapCacheDownloaderTask(
					imageView);
			DownloaderBitmapDrawable drawable = new DownloaderBitmapDrawable(
					null, task);
			imageView.setImageDrawable(drawable);
			task.execute(url);

		} else {
			imageView.setImageBitmap(bitmap);
		}
	}

	class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
		private String downloaderAddress;
		BitmapDownloadListener mListener;
		ImageView mView;
		public BitmapDownloaderTask() {
		}
		public void setListener(BitmapDownloadListener listener) {
			mListener = listener;
		}
		public void setImageView(ImageView view)
		{
			mView = view;
		}
		@Override
		protected Bitmap doInBackground(String... params) {
			downloaderAddress = params[0];
			if(downloaderAddress!=null && downloaderAddress.startsWith("http") )
				return downloadBitmap(downloaderAddress) ;
			else
				return createImageFromFile(downloaderAddress);
				
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if(mListener!=null)
				mListener.onBitmapDownloaded(bitmap);
			if(mView!=null)
				mView.setImageBitmap(bitmap);
		}
	}
	Bitmap downloadBitmap(String url) {
		final HttpClient client = new DefaultHttpClient();
		final HttpGet getRequest = new HttpGet(url);

		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				final HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream inputStream = null;
					try {
						inputStream = entity.getContent();
						return BitmapFactory.decodeStream(inputStream);
					} finally {
						if (inputStream != null) {
							inputStream.close();
						}
						entity.consumeContent();
					}
				}
			} else
				throw new Exception("Error Code : " + statusCode);
		} catch (Exception e) {
			getRequest.abort();
		}
		catch(Error e)
		{
			getRequest.abort();
		}
		return null;
	}
	
	public static String getName(String strFilePath)
	{
		String name =null;
		if(strFilePath!=null && strFilePath.length()>0)
		{
			name = strFilePath.substring(strFilePath.lastIndexOf("/")+1);
		}
		return name;
	}
	

	public void init() {
		
	}
	 

	static class DownloaderBitmapDrawable extends BitmapDrawable {

		private final WeakReference<BitmapCacheDownloaderTask> bitmapDownloaderTaskReference;

		DownloaderBitmapDrawable(Bitmap bitmap,
				BitmapCacheDownloaderTask bitmapDownloaderTask) {
			super(bitmap);

			bitmapDownloaderTaskReference = new WeakReference<BitmapCacheDownloaderTask>(
					bitmapDownloaderTask);
		}

		public BitmapCacheDownloaderTask getBitmapDownloaderTask() {
			return bitmapDownloaderTaskReference.get();
		}
	}
	
	class BitmapCacheDownloaderTask extends AsyncTask<String, Void, Bitmap> {
		private String downloaderAddress;
		private final WeakReference<ImageView> imageViewReference;

		public BitmapCacheDownloaderTask(ImageView imageView) {
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			downloaderAddress = params[0];
			return downloadBitmap(downloaderAddress);
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (bitmap != null) {
				mImageCache.addBitmapToCache(downloaderAddress, bitmap);

				if (imageViewReference != null) {
					ImageView imageView = imageViewReference.get();
					if(imageView!=null)
						imageView.setImageBitmap(bitmap);
				}
			}
		}
	}
	public  Bitmap createImageFromFile(String filename) {
		Bitmap bitmap = null;
		try
		{
			File file = new File(filename);
			FileInputStream stream = new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(stream);
			stream.close();
		}
		catch(Exception e){}
		catch(Throwable e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
	
	public void clearCache()
	{
		mImageCache.clearCache();
		
	}
}
