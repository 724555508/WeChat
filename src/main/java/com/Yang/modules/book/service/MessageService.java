package com.Yang.modules.book.service;

import com.Yang.modules.core.entity.InitConfig;

public interface MessageService {

	void sendNewUserResponse(String openId,InitConfig initConfig);
}
