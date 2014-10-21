package com.xzh.tinybox.Tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
}
