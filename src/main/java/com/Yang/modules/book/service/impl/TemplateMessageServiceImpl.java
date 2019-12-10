package com.Yang.modules.book.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import com.Yang.common.utils.Is;
import com.Yang.common.utils.id.SnowflakeIdWorker;
import com.Yang.modules.book.service.TemplateMessageService;
import com.Yang.modules.core.dao.MessageDao;
import com.Yang.modules.core.entity.InitConfig;
import com.Yang.modules.core.entity.MessageEntity;
import com.Yang.modules.core.model.TemplateParam;
import com.Yang.modules.core.service.UserService;
import com.Yang.util.DateUtil;
import com.Yang.util.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TemplateMessageServiceImpl implements TemplateMessageService{

	@Resource UserService userService;
	@Resource MessageDao messageDao;
	
	public void sendTemplateMessage(String openId, String templateId, String url, String topcplor,
			List<TemplateParam> params, String appId, String secret) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append(String.format("\"touser\":\"%s\"", new Object[] { openId })).append(",");
		buffer.append(String.format("\"template_id\":\"%s\"", new Object[] { templateId })).append(",");
		buffer.append(String.format("\"url\":\"%s\"", new Object[] { url })).append(",");
		buffer.append(String.format("\"topcolor\":\"%s\"", new Object[] { topcplor })).append(",");
		buffer.append("\"data\":{");
		TemplateParam param = null;
		for (int i = 0; i < params.size(); i++) {
			param = (TemplateParam) params.get(i);
			if (i < params.size() - 1) {
				buffer.append(String.format("\"%s\": {\"value\":\"%s\",\"color\":\"%s\"},",
						new Object[] { param.getName(), param.getValue(), param.getColor() }));
			} else {
				buffer.append(String.format("\"%s\": {\"value\":\"%s\",\"color\":\"%s\"}",
						new Object[] { param.getName(), param.getValue(), param.getColor() }));
			}
		}
		buffer.append("}");
		buffer.append("}");
		
		MessageEntity message = new MessageEntity();
		message.setId(SnowflakeIdWorker.generateIdString());
		message.setInfo(buffer.toString());
		message.setOpenId(openId);
		message.setSendTime(new Date());
		message.setTemplateId(templateId);
		
		String sendUrl = null;
		sendUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
				+ this.userService.getAccessToken(appId, secret);
		String res = HttpUtils.sendPost(sendUrl, buffer.toString());
		if (!Is.Null(res)) {
			log.info(res);
			JSONObject jsonRes = (JSONObject) JSON.parse(res);
			String errcode = jsonRes.getString("errcode");
			message.setErrcode(errcode);
		}
		messageDao.insert(message);
		
	}

	@Override
	public void sendMessageToSharer(String openId, String userName,int personNum, Date createTime, InitConfig initConfig) {
		String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
		log.info("method:{}" , method);
		
		List<TemplateParam> params = new ArrayList();
	    TemplateParam templateParam = new TemplateParam();
	    templateParam.setName("first");
	    templateParam.setValue("您有一位新的朋友加入啦，当前累计已关注"+personNum+"人（不包括取消关注）" + "\\n");
	    templateParam.setColor("#EE2C2C");
	    params.add(templateParam);
	    TemplateParam templateParam2 = new TemplateParam();
	    templateParam2.setName("keyword1");
	    templateParam2.setValue(userName + "\\n");
	    templateParam2.setColor("#4169E1");
	    params.add(templateParam2);
	    TemplateParam templateParam3 = new TemplateParam();
	    templateParam3.setName("keyword2");
	    templateParam3.setValue(DateUtil.formatToString(createTime, "yyyy-MM-dd HH:mm:ss") + "\\n");
	    templateParam3.setColor("#4169E1");
	    params.add(templateParam3);
//	    TemplateParam templateParam4 = new TemplateParam();
//	    templateParam4.setName("remark");
//	    templateParam4.setValue("感谢您的支持!");
//	    templateParam4.setColor("#EE2C2C");
//	    params.add(templateParam4);
	    sendTemplateMessage(openId, "TvDCZpuQv_LRiobPGfTpTDpzC34NNBE_GNwuuwBn5nY", "", "#FF0000", params ,initConfig.getAppid(), initConfig.getSecret());
		
	}
}
