package com.Yang.modules.core.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.Yang.common.conf.CoreConf;
import com.Yang.common.db.RedisUtil;
import com.Yang.common.utils.Is;
import com.Yang.modules.core.bean.UserInfo;
import com.Yang.modules.core.dao.InitConfigDao;
import com.Yang.modules.core.dao.UserDao;
import com.Yang.modules.core.entity.InitConfig;
import com.Yang.modules.core.entity.UserEntity;
import com.Yang.modules.core.service.UserService;
import com.Yang.util.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

	@Resource RedisUtil redisUtil;
	@Resource InitConfigDao initConfigDao;
	@Resource UserDao userDao;
	
	@Override
	public UserInfo getUserInfo(String openId , String appId, String secret) {
		String result = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + this.getAccessToken(appId, secret) + "&openid=" + openId);
		UserInfo user = new UserInfo();
		
		JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
		if ("0".equals(jsonObject.get("subscribe").toString().replaceAll("\"", ""))) {
			return null;
		}
		user.setOpenId(jsonObject.get("openid").toString().replaceAll("\"", ""));
		user.setNickname( jsonObject.get("nickname").toString().replaceAll("\"", ""));
		user.setSex(jsonObject.get("sex").toString().replaceAll("\"", ""));
		user.setCity( jsonObject.get("city").toString().replaceAll("\"", ""));
		user.setProvince(jsonObject.get("province").toString().replaceAll("\"", ""));
		user.setCountry( jsonObject.get("country").toString().replaceAll("\"", ""));
		user.setHeadimgurl( jsonObject.get("headimgurl").toString().replaceAll("\"", ""));
		return user;
	}

	@Override
	public String getAccessToken(String appId, String secret) {
		String token = redisUtil.get(CoreConf.weixin_accessToken_prefix+appId);
		if (!Is.Null(token)) {
			return token;
		}
		String result = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + secret);
		JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
		token = jsonObject.get("access_token").toString().replaceAll("\"", "");
		if (!Is.Null(token)) {
			redisUtil.setex(CoreConf.weixin_accessToken_prefix+appId,token,3600);
			return token;
		}
		throw new RuntimeException("获取accesstoken失败,appId=" + appId + ",secret=" + secret);
	}

	@Override
	public InitConfig getAppConfig(String access_id) {
		Object obj = redisUtil.getObject(CoreConf.weixin_initConfig_prefix+access_id);
		if (!Is.Null(obj)) {
			return (InitConfig) obj;
		}
		InitConfig initConfig = initConfigDao.selectById(access_id);
		redisUtil.setObjectex(CoreConf.weixin_initConfig_prefix + access_id, initConfig, 3600);
		return initConfig;
	}

	@Override
	public UserEntity saveNewUser(String parentId, UserInfo user, InitConfig initConfig) {
		
		log.info("新用户:" + user.getNickname());
		UserEntity userEntity = new UserEntity();
		userEntity.setOpenId(user.getOpenId());
		userEntity.setUsername(user.getNickname());
		userEntity.setHeadImg(user.getHeadimgurl());
		userEntity.setCreateTime(new Date());
		userEntity.setBelongto(initConfig.getAppid());
		userEntity.setSource(1);
		userEntity.setSubscribe(1);
		if (!Is.Null(parentId)) {
			userEntity.setParentId(parentId);
		}
		userDao.insert(userEntity);
		return userEntity;
		
	}

	@Override
	public UserEntity getUser(String openId) {
		return userDao.selectById(openId);
	}

	@Override
	public void updateUser(UserInfo user,UserEntity userEntity) {
		userEntity.setUsername(user.getNickname());
		userEntity.setHeadImg(user.getHeadimgurl());
		userEntity.setSubscribe(1);
		userDao.updateById(userEntity);
	}

	
	
}
