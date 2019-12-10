package com.Yang.modules.book.model;

import java.io.IOException;

import com.Yang.common.model.Click;
import com.Yang.common.utils.Is;
import com.Yang.common.utils.SpringContextUtil;
import com.Yang.modules.core.dao.UserDao;
import com.Yang.modules.core.entity.InitConfig;
import com.Yang.modules.core.entity.UserEntity;
import com.Yang.modules.core.service.BasicService;
import com.Yang.modules.core.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Click_Book extends Click{

	public Click_Book(InitConfig initConfig, String fromUserName, String toUserName, String content, String eventKey) {
		super(initConfig, fromUserName, toUserName, content, eventKey);
	}

	@Override
	public String doSomething() {
		
		UserService userService = SpringContextUtil.getBean(UserService.class);
		BasicService basicService = SpringContextUtil.getBean(BasicService.class);
		UserDao userDao = SpringContextUtil.getBean(UserDao.class);
		//获取推广二维码
		if ("getCode".equals(eventKey)) {
			UserEntity userEntity = userService.getUser(fromUserName);
			if (Is.Null(userEntity)) {
				log.info("数据库中没有该用户");
			}else {
				String imgUrl = "";
				if (Is.Null(userEntity.getPosterUrl())) {
					//生成推广二维码
					imgUrl = basicService.generatePosterUrl(fromUserName, initConfig);
					userEntity.setPosterUrl(imgUrl);
					userDao.updateById(userEntity);
				}else {
					imgUrl = userEntity.getPosterUrl();
				}
				
				//换取mediaId
				try {
					String mediaId = basicService.getMediaIdByImgUrlForEver(imgUrl, initConfig.getAppid(),initConfig.getSecret());
					System.out.println(mediaId);
					return replyImage(mediaId);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}

}
