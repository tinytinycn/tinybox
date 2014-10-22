package com.xzh.tinybox.workTask;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SendWeiboTask extends AsyncTask<Context, Integer, Boolean>{
	
	private StatusesAPI mStatusesAPI;
	private String mContent_txt;
	private RequestListener mListener = new RequestListener() {
		
		@Override
		public void onWeiboException(WeiboException arg0) {
			// TODO Auto-generated method stub
			Log.v("shareActivity", "onWeiboException");
		}
		
		@Override
		public void onComplete(String arg0) {
			// TODO Auto-generated method stub
			Log.v("shareAcivity", "onComplete");
		}
	};
	
	public SendWeiboTask(StatusesAPI mStatusesAPI, String mContent_txt) {
		super();
		this.mStatusesAPI = mStatusesAPI;
		this.mContent_txt = mContent_txt;
	}

	@Override
	protected Boolean doInBackground(Context... context) {
		Log.v("share-content", mContent_txt);
		mStatusesAPI.update(mContent_txt, "0.0", "0.0", mListener);
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

		

	
}
