package com.xzh.tinybox.Object;

import java.io.Serializable;

import com.xzh.tinybox.Interfaces.WeiboObject;

public class User implements WeiboObject,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//UID
//	public long id;
	//用户名
	public String name;
	//用户头像
	public String profile_image_url;
	//用户最近一条微博信息字段
//	public Status status;
	//case!!加入其他field可能导致其他field没有值。！！
}
