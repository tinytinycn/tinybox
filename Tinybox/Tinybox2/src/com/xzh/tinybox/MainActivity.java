package com.xzh.tinybox;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.xzh.tinybox.Adapter.WeiboAdapter;
import com.xzh.tinybox.Object.Status;
import com.xzh.tinybox.Tool.JsonTool;
import com.xzh.tinybox.Tool.StorageManager;


public class MainActivity extends Activity {
	
	private StatusesAPI mStatusesAPI;
	private Oauth2AccessToken mAccessToken;
	
	private List<Status> mStatuses;
	
	private ListView timeLine_listView;
	private ImageView main_null_iv;
	private WeiboAdapter weiboListAdapter;
	
	private String jsonStr;
	
	private RequestListener mListener = new RequestListener() {
		
		@Override
		public void onWeiboException(WeiboException arg0) {
			Toast.makeText(MainActivity.this, "MainActivity-onWeiboException", Toast.LENGTH_LONG).show();
		}
		
		@Override
		public void onComplete(String arg0) {
			//保存j son
			try {
				StorageManager.saveJsonStr(arg0, "/data/data/com.xzh.tinybox", 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//get j son over!
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        
        mAccessToken = AccessTokenKeeper.getAccessToken(this);
        mStatusesAPI = new StatusesAPI(mAccessToken);
        
        //初始化 List对象
        timeLine_listView= (ListView)findViewById(R.id.timeLine_list);
        main_null_iv = (ImageView)findViewById(R.id.main_null_img);
        //获取微博数据
    	mStatusesAPI.friendsTimeline(0L, 0L, 20, 1, false, 0, false, mListener);
    	//test
    	
    }

 
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_action_refresh) {
        	//刷新主页
        	//读取 保存的j son
			jsonStr = StorageManager.readJsonStr("/data/data/com.xzh.tinybox/home_timeline");
			//处理 j son--> list<Status>
        	mStatuses = JsonTool.Json2List(Status.class, jsonStr, "statuses");
        	//创建、设置适配器
			weiboListAdapter = new WeiboAdapter(MainActivity.this, mStatuses, 0);
			timeLine_listView.setAdapter(weiboListAdapter);
			main_null_iv.setVisibility(View.GONE);
        	
            return true;
        }else if(id == R.id.main_action_shareWeibo){
        	//跳转分享界面
        	Intent i = new Intent();
        	i.setClass(MainActivity.this, ShareActivity.class);
        	startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
