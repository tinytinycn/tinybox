package com.xzh.tinybox;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;

public class ShareActivity extends Activity{
	private EditText et;
	private ImageButton at_btn,album_btn,pic_btn,huati_btn;
	private ImageView pre_iv;
	
	private Oauth2AccessToken mToken;
	private StatusesAPI mStatusesAPI;
	
	private String share_content_txt = "";
	
	private final int PIC_FROM_CAMERA = 10001;
	private final int PIC_FROM_GARELLY = 10002;
	
	private Bitmap mBitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_activity);
		
		//弹出输入软盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		
		//获取组件
		et = (EditText)findViewById(R.id.et_140_content);
		at_btn = (ImageButton)findViewById(R.id.at_btn);
		album_btn = (ImageButton)findViewById(R.id.album_btn);
		pic_btn = (ImageButton)findViewById(R.id.pic_btn);
		huati_btn = (ImageButton)findViewById(R.id.huati_btn);
		pre_iv = (ImageView)findViewById(R.id.iv_pic_preview_content);
		
		//绑定监听器
		at_btn.setOnClickListener(new ToolBtnListener());
		album_btn.setOnClickListener(new ToolBtnListener());
		pic_btn.setOnClickListener(new ToolBtnListener());
		huati_btn.setOnClickListener(new ToolBtnListener());
		
		//获取Token，获取微博接口实例
		mToken = AccessTokenKeeper.getAccessToken(ShareActivity.this);
		mStatusesAPI = new StatusesAPI(mToken);
		
		//test
		
	
	}
	
	
	
	public class ToolBtnListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.at_btn:
				Log.v("onclickListener-->", "at");
				
				break;
			case R.id.album_btn:
				Log.v("onclickListener-->", "album");
				
				Intent pickIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(pickIntent, PIC_FROM_GARELLY);
				break;
			case R.id.pic_btn:
				Log.v("onclickListener-->", "pic");
				
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				Uri captureImageUri = Uri.fromFile(new File("/sdcard", "captureImage.jpg"));
				intent.putExtra(MediaStore.EXTRA_OUTPUT, captureImageUri);
				startActivityForResult(intent, PIC_FROM_CAMERA);
				break;
			case R.id.huati_btn:
				Log.v("onclickListener-->", "huati");
				
				String topicText = "输入话题名";
				et.getText().insert(et.getSelectionStart(), "#"+topicText+"#");
				et.setSelection(et.getSelectionStart()-topicText.length()-1, et.getSelectionStart()-1);
				InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
				imm.showSoftInput(et, InputMethodManager.SHOW_FORCED);
				break;
			default:
				break;
			}
		}
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		
		
		if(resultCode == RESULT_OK){
			switch (requestCode) {
			case PIC_FROM_CAMERA:
				Log.v("onActivityResult-->", "fromCamera");
				//获取图片
				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inJustDecodeBounds = true;
				Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/captureImage.jpg",option);
				//压缩图片
				int widthRatio = (int) Math.ceil(option.outWidth / (float) 1080);
				if (widthRatio > 1)  
                {  
                    option.inSampleSize = widthRatio;  
                }
				option.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeFile("/sdcard/captureImage.jpg", option);
				mBitmap = bitmap;
				//显示压缩后图片
				pre_iv.setVisibility(View.VISIBLE);
				pre_iv.setImageBitmap(bitmap);
				break;
			case PIC_FROM_GARELLY:
				Log.v("onActivityResult-->", "fromGarelly");
				Uri imageFileUri = data.getData();
				
				try {
					BitmapFactory.Options option2 = new BitmapFactory.Options();
					option2.inJustDecodeBounds = true;
					Bitmap bitmap2 = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri), null, option2);
					int widthRatio2 = (int) Math.ceil(option2.outWidth / (float) 1080);
					if (widthRatio2 > 1)  
	                {  
	                    option2.inSampleSize = widthRatio2;  
	                }
					option2.inJustDecodeBounds = false;
					bitmap2 = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri), null, option2);
					mBitmap = bitmap2;
					pre_iv.setVisibility(View.VISIBLE);
					pre_iv.setImageBitmap(bitmap2);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.share, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == R.id.share_action_sendWeibo){
			Log.v("shareActivity", "send-start");
			//获取文本
			share_content_txt = et.getText().toString();
			//获取图片


			//判断图片
			if(pre_iv.isShown()){
				if(isNotTxt(share_content_txt)){
					mStatusesAPI.upload("分享图片", mBitmap, "0.0", "0.0", new SendWeiboListener());
				}else{
				//发一条带图微博
				mStatusesAPI.upload(share_content_txt, mBitmap, "0.0", "0.0", new SendWeiboListener());
				}
			}else if(!isMore140(share_content_txt)){
				//发一条文字微博
				mStatusesAPI.update(share_content_txt, "0.0", "0.0", new SendWeiboListener());
			}else {
				Toast.makeText(ShareActivity.this, "Over140!", Toast.LENGTH_SHORT).show();
			}
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public class SendWeiboListener implements RequestListener{
		@Override
		public void onWeiboException(WeiboException arg0) {
			Log.v("shareActivity", "onWeiboException2");
		}
		
		@Override
		public void onComplete(String arg0) {
			Log.v("shareAcivity", "onComplete2");
			//弹出ShareActivity
			ShareActivity.this.finish();
		}
	}
	
	private boolean isMore140(String txt){
		int len = txt.length();
		Log.v("limite140-->", ""+len);
		if(len >=140 || len <= 0){
			return true;
		}else{
			return false;
		}
	}
	private boolean isNotTxt(String txt){
		int len = txt.length();
		if(len == 0){
			return true;
		}
		return false;
	}
	
	
	
}
