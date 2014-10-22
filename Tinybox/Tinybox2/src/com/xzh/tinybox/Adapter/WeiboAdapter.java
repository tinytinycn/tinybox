package com.xzh.tinybox.Adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xzh.tinybox.R;
import com.xzh.tinybox.Object.Status;
import com.xzh.tinybox.Tool.TinyTool;

public class WeiboAdapter extends BaseAdapter{
	protected Activity activity;
	protected List<Status> statuses;
	protected int faceType;
	protected LayoutInflater layoutInflater;
	
	public WeiboAdapter(Activity activity) {
		this.activity = activity;
	}

	public WeiboAdapter(Activity activity, List<Status> statuses, int faceType) {
		this.activity = activity;
		this.statuses = new ArrayList<Status>();
		this.layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.faceType = faceType;
		if(statuses != null){
			this.statuses.addAll(statuses);
			//save list to storage!!
			
		}
	}

	@Override
	public int getCount() {
		return statuses.size()+1;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	
	//从 list中取出 status微博对象
	public Status getStatus(int position){
		if(statuses.size()>0){
			return statuses.get(position);
		}else{
			return null;
		}
	}
	public long getMinId(){
		if(statuses.size()>0){
			return statuses.get(statuses.size()-1).id;
		}else{
			return Long.MAX_VALUE;
		}
	}
	public long getMaxId(){
		if(statuses.size()>0){
			return statuses.get(0).id;
		}else{
			return 0;
		}
	}
	//添加新的微博（refresh、more）
	public void putStatuses(List<Status> statuses){
		//...
	}
	
	private void loadBitmap(ImageView iv, String url, boolean hideView){
		Log.v("loadbitmap-->>", "loadbitmap");
		if(url != null){
			Log.v("url-->", url);
			String img_url = TinyTool.getImageUrl(activity,url);
			if(img_url != null){
				iv.setImageURI(Uri.fromFile(new File(img_url)));
				iv.setVisibility(View.VISIBLE);
				return;
			}
		}
		if(hideView){
			iv.setVisibility(View.GONE);
		}
	}
	
	@SuppressLint("InflateParams") @Override
	public View getView(int position, View view, ViewGroup arg2) {
		if(view == null){
			view = layoutInflater.inflate(R.layout.weibo_list_item, null);
		}
		
		if(position < statuses.size()){
			Status status = statuses.get(position);
			
			TextView text = (TextView)view.findViewById(R.id.item_body_content_txt);
			TextView name = (TextView)view.findViewById(R.id.item_user_name);
			TextView source_android_creatTime = (TextView)view.findViewById(R.id.item_source_And_creatTime);
			ImageView profileImage = (ImageView)view.findViewById(R.id.item_user_profile_image);
			ImageView statusImage = (ImageView)view.findViewById(R.id.item_body_content_img);
			
			if(status.user.name != null){
				name.setText(status.user.name);
				loadBitmap(profileImage, status.user.profile_image_url, false);
			}else{
				name.setText("TinyNULL");
				loadBitmap(profileImage, status.user.profile_image_url, false);
			}
//			if(status.thumbnail_pic != null){
//				loadBitmap(statusImage, status.thumbnail_pic, false);
//			}else{
//				statusImage.setVisibility(View.GONE);
//			}
			
			source_android_creatTime.setText(status.getTextSource() +" ~ "+TinyTool.getTimeStr(status.getCreatTime(), new Date()));
			text.setText(status.text);
			
		}else{
			//more
		}
		return view;
	}

}
