package com.Yang.modules.book.model;

import com.Yang.common.model.Unsubscribe;
import com.Yang.common.utils.SpringContextUtil;
import com.Yang.modules.core.dao.UserDao;
import com.Yang.modules.core.entity.InitConfig;
import com.Yang.modules.core.entity.UserEntity;
import com.Yang.modules.core.service.UserService;

public class Unsubscribe_Book extends Unsubscribe{

	public Unsubscribe_Book(InitConfig initConfig, String fromUserName, String toUserName, String content) {
		super(initConfig, fromUserName, toUserName, content);
	}

	@Override
	public String doSomething() {
		
		UserService userService = SpringContextUtil.getBean(UserService.class);
		UserDao userDao = SpringContextUtil.getBean(UserDao.class);
		UserEntity userEntity = userService.getUser(fromUserName);
		userEntity.setSubscribe(0);
		userDao.updateById(userEntity);
		return "";
	}

}
