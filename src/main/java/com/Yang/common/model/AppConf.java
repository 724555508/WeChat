package com.Yang.common.model;

import com.Yang.modules.core.entity.InitConfig;

import lombok.Data;

public class AppConf {

	public InitConfig initConfig;

	public AppConf(InitConfig initConfig) {
		super();
		this.initConfig = initConfig;
	}

	
}
