package com.xzh.tinybox.workTask;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.xzh.tinybox.Tool.TinyTool;

import android.os.AsyncTask;
import android.util.Log;

public class PullFileTask extends AsyncTask<String, Integer, String>{

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		Log.v("-->>", "task");
		String url = arg0[0];
		try {
			Log.v("-->>", "connection");
			HttpURLConnection connection = (HttpURLConnection)(new URL(url).openConnection());
			connection.setDoInput(true);
			connection.setUseCaches(false);
			//读数据
			InputStream is = connection.getInputStream();
			//写数据
			FileOutputStream fos = new FileOutputStream("/data/data/com.xzh.tinybox/imgs"+"/"+url.hashCode());
			//is-->f o s 数据转存
			TinyTool.dataStreamTrans(is,fos);
			connection.disconnect();
			fos.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return url;
	}

	

}
