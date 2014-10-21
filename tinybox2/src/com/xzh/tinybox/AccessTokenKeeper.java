package com.xzh.tinybox;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

public class AccessTokenKeeper {
	private static final String PREFERENCES_NAME = "com_weibo_sdk_android";

    private static final String KEY_UID           = "uid";
    private static final String KEY_ACCESS_TOKEN  = "access_token";
    private static final String KEY_EXPIRES_IN    = "expires_in";
    
    public static void setAccessToken(Context c,Oauth2AccessToken o){
    	if(null == c || null == o){
    		return;
    	}
    	
    	SharedPreferences pref = c.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
    	Editor e = pref.edit();
    	e.putString(KEY_UID, o.getUid());
    	e.putString(KEY_ACCESS_TOKEN, o.getToken());
    	e.putLong(KEY_EXPIRES_IN, o.getExpiresTime());
    	e.commit();
    }
    public static Oauth2AccessToken getAccessToken(Context c){
    	if(null == c){
    		return null;
    	}
    	Oauth2AccessToken mToken = new Oauth2AccessToken();
    	SharedPreferences pref = c.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
    	mToken.setUid(pref.getString(KEY_UID, ""));
    	mToken.setToken(pref.getString(KEY_ACCESS_TOKEN, ""));
    	mToken.setExpiresTime(pref.getLong(KEY_EXPIRES_IN, 0));
    	return mToken;
    }
    public static void clear(Context c){
    	if(null == c){
    		return;
    	}
    	SharedPreferences pref = c.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
    	Editor e = pref.edit();
    	e.clear();
    	e.commit();
    }
}
