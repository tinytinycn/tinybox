package com.xzh.tinybox.Tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.xzh.tinybox.workTask.PullFileTask;

import android.app.Activity;
import android.util.Log;

public class TinyTool {
	public static Date string2Date(String str){
		SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy",Locale.US);
		Date result = null;
		if(str != null){
			try {
				result = sdf.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static String getTimeStr(Date oldTime, Date currentDate)
	{
		long time1 = currentDate.getTime();

		long time2 = oldTime.getTime();

		long time = (time1 - time2) / 1000;
		
		if (time >= 0 && time < 60)
		{
			return "刚才";
		}
		else if (time >= 60 && time < 3600)
		{
			return time / 60 + "分钟前";
		}
		else if (time >= 3600 && time < 3600 * 24)
		{
			return time / 3600 + "小时前";
		}
		else
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd HH:mm",Locale.US);
			return sdf.format(oldTime);
		}
	}
	
	//从本地获取imageURL,如果不能则网络获取图片，保存本地
	public static String getImageUrl(Activity activity, String url){
		String result = null;
		if(url == null || "".equals(url)){
			return null;
		}
		result = "/data/data/com.xzh.tinybox/imgs"+"/"+url.hashCode();
		File file = new File(result);
		if(file.exists()){
			Log.v("-->>", "exists!");
			return result;
		}else{
			Log.v("-->>", "no exists!");
			(new File("/data/data/com.xzh.tinybox/imgs")).mkdirs();
			PullFileTask task = new PullFileTask();
			task.execute(url);
			return null;
		}
		
	}
	
	public static void dataStreamTrans(InputStream is, FileOutputStream fos){
		Log.v("-->>", "trans..");
		byte[] buffer = new byte[8192];
		int count = 0;
		try {
			while((count= is.read(buffer))>-1){
				fos.write(buffer,0,count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
