package com.xzh.tinybox.Tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.util.Log;

public class StorageManager {
	
	public static void saveJsonStr(String json, String path,int type) throws Exception{
		if(json == null || json.length() == 0){
			return;
		}
		
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		FileOutputStream fos = null;
		OutputStreamWriter osw= null;
		
		fos = new FileOutputStream(path+"/home_timeline");
		osw = new OutputStreamWriter(fos,"UTF-8");
		osw.write(json);
		Log.v("saveJsonStr-->>", "saved.");
		osw.close();
		
//		switch(type){
//			case Time_line:
//		}
		
	}
	
	public static String readJsonStr(String fileName){
		StringBuilder sb = new StringBuilder();
		if(!new File(fileName).exists()){
			return null;
		}
		
		try {
			FileInputStream fis = new FileInputStream(fileName);
			InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String tmp = "";
			while((tmp=br.readLine())!=null){
				sb.append(tmp);
			}
			Log.v("readJsonStr-->>", "read.");
			isr.close();
			
		} catch (Exception e) {
		}
		
		return sb.toString();
	}
	
	
}
