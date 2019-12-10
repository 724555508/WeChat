package com.Yang.modules.book.service;

import java.util.Date;

import com.Yang.modules.core.entity.InitConfig;

public interface TemplateMessageService {

	
	void sendMessageToSharer(String openId , String userName , int personNum, Date createTime , InitConfig initConfig);
	
}
