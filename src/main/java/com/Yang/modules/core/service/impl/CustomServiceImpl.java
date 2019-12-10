package com.Yang.modules.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.Yang.common.conf.CoreConf;
import com.Yang.common.utils.nullUtil.IsNull;
import com.Yang.modules.core.model.Articles;
import com.Yang.modules.core.service.ICustomService;
import com.Yang.modules.core.service.UserService;
import com.Yang.util.HttpUtils;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;


@Service("customService")
@Slf4j
public class CustomServiceImpl implements ICustomService {
	
	@Resource UserService userService;
	
	void send(String appId, String secret,String param){
		if (IsNull.isNullOrEmpty(appId)) {
			return;
		}
		String res = HttpUtils.sendPost(CoreConf.CUSTOM_SEND_MSG + userService.getAccessToken(appId, secret), param);
		log.info(res);
	}
	
	@Override
	public void sendText(String openId, String content, String appId, String secret) {
		String param = "{\"touser\":\"" + openId + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + content
				+ "\"}}";
		send(appId,secret,param);
	}
	@Override
	public void sendImage(String openId, String mediaId,String appId, String secret) {
		String param = "{\"touser\":\"" + openId + "\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"" + mediaId
				+ "\"}}";
		send(appId,secret,param);
	}
	
	@Override
	public void sendNews(String openId, List<Articles> articlesParams,String appId, String secret) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append(String.format("\"touser\":\"%s\"", new Object[] { openId })).append(",");
		buffer.append(String.format("\"msgtype\":\"%s\"", new Object[] { "news" })).append(",");
		buffer.append("\"news\":{\"articles\":[");
		for (int i = 0; i < articlesParams.size(); i++) {
			Articles articlesParam = articlesParams.get(i);
			if (i < articlesParams.size() - 1) {
				buffer.append("{").append(String.format("\"title\":\"%s\"", new Object[] { articlesParam.getTitle() })).append(",")
				.append(String.format("\"description\":\"%s\"", new Object[] { articlesParam.getDescription() })).append(",")
				.append(String.format("\"url\":\"%s\"", new Object[] { articlesParam.getUrl() })).append(",")
				.append(String.format("\"picurl\":\"%s\"", new Object[] { articlesParam.getPicurl() })).append("},");
			}else {
				buffer.append("{").append(String.format("\"title\":\"%s\"", new Object[] { articlesParam.getTitle() })).append(",")
				.append(String.format("\"description\":\"%s\"", new Object[] { articlesParam.getDescription() })).append(",")
				.append(String.format("\"url\":\"%s\"", new Object[] { articlesParam.getUrl() })).append(",")
				.append(String.format("\"picurl\":\"%s\"", new Object[] { articlesParam.getPicurl() })).append("}");
			}
		}
		buffer.append("]");
		buffer.append("}");
		buffer.append("}");
		log.info(buffer.toString());
		send(appId,secret,buffer.toString());
	}

	@Override
	public void sendImageByUrl(String openId, String imgUrl, String appId, String secret) {
		
		
		
	}
	
	String getTicket(String param,String appId,String secret) {
		String url = CoreConf.GET_TIKET_URL + userService.getAccessToken(appId, secret);
		String res = HttpUtils.sendPost(url, param);
		if (IsNull.isNullOrEmpty(res)) {
			return null;
		}
		JSONObject jsonObject = (JSONObject) JSONObject.parse(res);
		String ticket = jsonObject.get("ticket").toString().replaceAll("\"", "");
		log.info("######成功获取ticket:" + ticket);
		return ticket;
	}
}
