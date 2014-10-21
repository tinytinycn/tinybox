package com.xzh.tinybox;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.xzh.tinybox.Adapter.WeiboAdapter;
import com.xzh.tinybox.Object.Status;
import com.xzh.tinybox.Tool.JsonTool;


public class MainActivity extends Activity {
	
	private StatusesAPI mStatusesAPI;
	private Oauth2AccessToken mAccessToken;
	
	private List<Status> mStatuses;
	
	private ListView timeLine_listView;
	private WeiboAdapter weiboListAdapter;
	
	private RequestListener mListener = new RequestListener() {
		
		@Override
		public void onWeiboException(WeiboException arg0) {
			Toast.makeText(MainActivity.this, "MainActivity-onWeiboException", Toast.LENGTH_LONG).show();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void onComplete(String arg0) {

			// 返回微博数据 J son--> list<Status>
			mStatuses = JsonTool.Json2List(Status.class, arg0, "statuses");
			System.out.println(mStatuses.size());
			//创建、设置适配器
			weiboListAdapter = new WeiboAdapter(MainActivity.this, mStatuses, 0);
			timeLine_listView.setAdapter(weiboListAdapter);
			//保存数据
			
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        
        mAccessToken = AccessTokenKeeper.getAccessToken(this);
        mStatusesAPI = new StatusesAPI(mAccessToken);
        
        //初始化list
        timeLine_listView= (ListView)findViewById(R.id.timeLine_list);
        
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
        	//刷新主界面
        	//test
        	mStatusesAPI.friendsTimeline(0L, 0L, 20, 1, false, 0, false, mListener);
        	
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
