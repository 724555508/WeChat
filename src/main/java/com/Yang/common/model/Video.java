package com.Yang.common.model;

import com.Yang.modules.core.entity.InitConfig;

public abstract class Video extends Message implements MessageType{

	
	public Video(InitConfig initConfig, String fromUserName, String toUserName, String content) {
		super(initConfig, fromUserName, toUserName, content);
	}

	@Override
	public abstract String doSomething();

}
