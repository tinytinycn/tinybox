package com.xzh.tinybox.Tool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xzh.tinybox.Interfaces.WeiboObject;

public class JsonTool {
	
	public static Object Json2SingleObject(Object obj, String json){
		if(obj == null || json == null){
			return obj;
		}
		
		try {
			Field[] fields = obj.getClass().getFields();
			if(fields != null){
				
				JSONObject jsonObj = new JSONObject(json);
				for(Field field : fields){
					
					Object objValue = jsonObj.get(field.getName());
					//test
					try {
							if(objValue != null){
								if(field.getType() == String.class){
									Log.v("StringField-->>", String.valueOf(objValue));
									field.set(obj, String.valueOf(objValue));
								}else if(field.getType() == long.class){
									field.set(obj, Long.valueOf(String.valueOf(objValue)));
									Log.v("LongField-->>", ""+Long.valueOf(String.valueOf(objValue)));
								}else if(field.getType() == int.class){
									field.set(obj, Integer.valueOf(String.valueOf(objValue)));
								}else if(field.getType() == boolean.class){
									field.set(obj, Boolean.valueOf(String.valueOf(objValue)));
								}else{
									Object fieldObject = field.getType().newInstance();
									if(fieldObject instanceof WeiboObject){
										//递归处理OBject
										Log.v("ObjectField-->>", "ObjectField");
										Json2SingleObject(fieldObject,String.valueOf(objValue));
										field.set(obj, fieldObject);
									}
								}
								
							}else{
								Log.v("thisfield-->>", "null");
								field.set(obj, null);
							}	
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
		
	}
	
	public static List Json2List(Class c, String json, String propertyName){
		List mList = null;
		if(c == null || json == null){
			return mList;
		}
		
		try {
			Field[] fields = c.getFields();
			if(fields != null){
				
				if(propertyName != null){
					JSONObject jsonObj = new JSONObject(json);
					String jsonStr = jsonObj.get(propertyName).toString();
					JSONArray jsonArray = new JSONArray(jsonStr);
					mList = new ArrayList();
					for(int i=0;i < jsonArray.length();i++){
						Object obj = c.newInstance();
						mList.add(obj);
						Log.v("jsonArray-->>", "---->>>>"+i);
						Json2SingleObject(obj, jsonArray.getString(i));
						
					}
				}
			}
		}
		catch (Exception e) {
					e.printStackTrace();
				}
		return mList;
	}
	
	
}
