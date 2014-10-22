package com.xzh.tinybox;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;


public class StartActivity extends Activity{
	
	private WeiboAuth mWeiboAuth;
	private Oauth2AccessToken myAccessToken,mAccessToken;
	private Button login_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);
		//获取对象
		mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
		myAccessToken = AccessTokenKeeper.getAccessToken(this);
		login_btn = (Button)findViewById(R.id.login_btn);
		//刷新Login_btn
		updateLoginBtn(myAccessToken);
		
	}
	
	class MyAuthListener implements WeiboAuthListener{

		@Override
		public void onCancel() {
			Toast.makeText(StartActivity.this, "StartActivity-onCancel", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onComplete(Bundle arg0) {

			//获得 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(arg0);
			//判断 Token 有效性
			if(mAccessToken.isSessionValid()){
				//Token 详细内容
				String date = new SimpleDateFormat("yyyy/MM/dd HH-mm-ss").format(new java.util.Date(mAccessToken.getExpiresTime()));
				Toast.makeText(StartActivity.this, date, Toast.LENGTH_SHORT).show();
				//Token 保存
				AccessTokenKeeper.setAccessToken(StartActivity.this, mAccessToken);
				Toast.makeText(StartActivity.this, "save succeed!", Toast.LENGTH_SHORT).show();
				// 刷新login_btn
				updateLoginBtn(mAccessToken);
			}else{
				//错误代码提示
				String code = arg0.getString("code");
				Toast.makeText(StartActivity.this, code, Toast.LENGTH_LONG).show();
			}
			
			Toast.makeText(StartActivity.this, "StartActivity-onComplete", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onWeiboException(WeiboException arg0) {
			Toast.makeText(StartActivity.this, "StartActivity-onWeiboException", Toast.LENGTH_SHORT).show();
			System.out.println(arg0.toString());
		}
		
	}
	
	private void updateLoginBtn(Oauth2AccessToken token){
		
		if(token.isSessionValid()){
			login_btn.setBackgroundColor(getResources().getColor(R.color.lightGreen));
			login_btn.setText("welcome");
			login_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					//跳转主页面
					Intent i = new Intent();
					i.setClass(StartActivity.this, MainActivity.class);
					startActivity(i);
				}
			});
		}else{
			login_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					//跳转认证页面
					mWeiboAuth.anthorize(new MyAuthListener());
				}
			});
		}
		
	}
	
}
