package com.Yang.modules.core.service;

import com.Yang.modules.core.bean.UserInfo;
import com.Yang.modules.core.entity.InitConfig;
import com.Yang.modules.core.entity.UserEntity;

public interface UserService {

	UserInfo getUserInfo(String openId , String appId, String secret);
	
	String getAccessToken(String appId, String secret);
	
	InitConfig getAppConfig(String access_id);
	
	UserEntity saveNewUser(String parentId,UserInfo user,InitConfig initConfig);
	
	UserEntity getUser(String openId);
	
	void updateUser(UserInfo user,UserEntity userEntity);
	
}
