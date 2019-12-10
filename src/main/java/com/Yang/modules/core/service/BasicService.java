package com.Yang.modules.core.service;

import java.io.IOException;
import java.util.Map;

import com.Yang.modules.core.entity.InitConfig;

public interface BasicService{

	String generatePosterUrl(String openId,InitConfig initConfig);
	
	String getTicket(String param,String appId,String secret);
	
	String getMediaIdByImgUrlForEver(String imgUrl,String appId,String secret) throws IOException;
	
	Map<String, String> getOpenIdAndTokenByCode(String code,String appId,String secret);
	
}
