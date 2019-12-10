package com.Yang.modules.core.service;

import java.util.List;

import com.Yang.modules.core.model.Articles;

public interface ICustomService {
	
	public void sendText(String openId, String content, String appId, String secret);
	  
	public void sendImage(String openId, String mediaId, String appId, String secret);
	
	public void sendImageByUrl(String openId, String imgUrl, String appId, String secret);
	
	public void sendNews(String openId, List<Articles> articles,String appId, String secret);
}
