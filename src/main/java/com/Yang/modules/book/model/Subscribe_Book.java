package com.Yang.modules.book.model;

import java.io.IOException;

import javax.annotation.Resource;

import com.Yang.common.model.Subscribe;
import com.Yang.common.utils.Is;
import com.Yang.common.utils.SpringContextUtil;
import com.Yang.modules.book.service.MessageService;
import com.Yang.modules.book.service.TemplateMessageService;
import com.Yang.modules.core.bean.UserInfo;
import com.Yang.modules.core.dao.UserDao;
import com.Yang.modules.core.entity.InitConfig;
import com.Yang.modules.core.entity.UserEntity;
import com.Yang.modules.core.service.BasicService;
import com.Yang.modules.core.service.ICustomService;
import com.Yang.modules.core.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class Subscribe_Book extends Subscribe{

	public Subscribe_Book(InitConfig initConfig, String fromUserName, String toUserName, String content,
			String eventKey) {
		super(initConfig, fromUserName, toUserName, content, eventKey);
	}

	
	@Override
	public String doSomething() {
		
		UserService userService = SpringContextUtil.getBean(UserService.class);
		ICustomService customService = SpringContextUtil.getBean(ICustomService.class);
		BasicService basicService = SpringContextUtil.getBean(BasicService.class);
		UserDao userDao = SpringContextUtil.getBean(UserDao.class);
		TemplateMessageService templateMessageService = SpringContextUtil.getBean(TemplateMessageService.class);
		
		//获取用户信息
		UserInfo userInfo = userService.getUserInfo(fromUserName, initConfig.getAppid(), initConfig.getSecret());
		//判断是否之前关注过
		UserEntity user = userService.getUser(fromUserName);
		if (Is.Null(user)) {
			//保存新用户
			String parentId = "";
			if (!Is.Null(eventKey)) {
				//为扫码关注
				parentId = eventKey.substring(8);
			}
			UserEntity newUser = userService.saveNewUser(parentId, userInfo, initConfig);
			
			if (!Is.Null(parentId)) {
				//查询分享者当前累计关注用户数量
				Integer count = userDao.selectCount(new QueryWrapper<UserEntity>().eq("parent_id", parentId).eq("subscribe", 1));
				
				
				//1.向分享者发送通知
				templateMessageService.sendMessageToSharer(parentId, newUser.getUsername(), count, newUser.getCreateTime(), initConfig);
				
				//2.向关注者发送通知
				UserEntity userEntity = userService.getUser(parentId);
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("您通过  ["+userEntity.getUsername()+"]  的二维码成为我们的好朋友！").append("\n").append("\n");
				stringBuffer.append("您的专属图片已生成，邀请25位好朋友关注即可领取").append("\n").append("\n");
				stringBuffer.append("活动规则：分享专属海报，好友扫码，免费领取").append("\n");
				

				//换取mediaId
				try {
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								//3.向关注者发送海报图片
								//生成推广二维码
								String imgUrl = basicService.generatePosterUrl(fromUserName, initConfig);
								newUser.setPosterUrl(imgUrl);
								userDao.updateById(newUser);
								String mediaId = basicService.getMediaIdByImgUrlForEver(imgUrl, initConfig.getAppid(),initConfig.getSecret());
								customService.sendText(fromUserName, stringBuffer.toString(), initConfig.getAppid(), initConfig.getSecret());
								customService.sendImage(fromUserName, mediaId, initConfig.getAppid(), initConfig.getSecret());
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
					}).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}else {
			//修改用户资料
			userService.updateUser(userInfo, user);
		}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("Hi，   欢迎参加【你阅读·我颂书】活动！").append("\n").append("\n");
		buffer.append("将为您生成专属海报，邀请好友扫码关注任务后，即可0元包邮领取！").append("\n");
		return replyText(buffer.toString());
	}

}
