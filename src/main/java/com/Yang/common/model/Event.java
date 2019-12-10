package com.Yang.common.model;

import com.Yang.modules.core.entity.InitConfig;

public abstract class Event extends Message implements MessageType{


	public Event(InitConfig initConfig, String fromUserName, String toUserName, String content) {
		super(initConfig, fromUserName, toUserName, content);
	}

	@Override
	public abstract String doSomething();

}
