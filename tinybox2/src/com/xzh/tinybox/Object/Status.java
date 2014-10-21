package com.xzh.tinybox.Object;

import java.io.Serializable;
import java.util.Date;

import android.text.Html;

import com.xzh.tinybox.Interfaces.WeiboObject;
import com.xzh.tinybox.Tool.TinyTool;


public class Status implements WeiboObject,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 微博 创建时间**/
	public String created_at;
	/** 微博 id**/
	public long id;
	/** 微博 mid**/
	public long mid;
	/** 微博 字符串id**/
	public String idstr;
	/** 微博 内容**/
	public String text;
	/** 微博 HTML来源**/
	public String source;
	
	
	/** 微博 缩略图**/
//	public String thumbnail_pic;
	/** 微博 中尺寸图**/
//	public String bmiddle_pic;
	/** 微博 原图**/
//	public String original_pic;
	/** 转发内容 **/
	//注意！retweeted_status field假如可能导致text 参数获取不到！！
//	public Status retweeted_status;
	/** 微博作者用户信息 **/
	public User user;
	
	private String text_source;
	public String getTextSource(){
		if(text_source == null){
			try{
				text_source = Html.fromHtml(source).toString();
			}catch(Exception e){
				text_source = "Tinybox";
			}
		}
		return text_source;
	}
	public Date getCreatTime(){
		return TinyTool.string2Date(created_at);
	}
}
