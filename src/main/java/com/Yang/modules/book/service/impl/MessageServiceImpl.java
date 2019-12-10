package com.Yang.modules.book.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.Yang.modules.book.service.MessageService;
import com.Yang.modules.core.entity.InitConfig;
import com.Yang.modules.core.service.ICustomService;

@Service
public class MessageServiceImpl implements MessageService{

	@Resource ICustomService customService;
	
	@Override
	public void sendNewUserResponse(String openId, InitConfig initConfig) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Hi，   欢迎参加【你阅读·我颂书】活动！").append("\n").append("\n");
		buffer.append("下方是您的专属海报，邀请好友扫码关注任务后，即可0元包邮领取！").append("\n").append("\n");
		customService.sendText(openId, buffer.toString(),
				initConfig.getAppid(),initConfig.getSecret());
	}
	
}
