package com.Yang.common.model;

import com.Yang.modules.core.entity.InitConfig;

public abstract class Click extends Message implements MessageType{

	protected String eventKey;
	
	public Click(InitConfig initConfig, String fromUserName, String toUserName, String content , String eventKey) {
		super(initConfig, fromUserName, toUserName, content);
		this.eventKey = eventKey;
	}

	@Override
	public abstract String doSomething();

}
