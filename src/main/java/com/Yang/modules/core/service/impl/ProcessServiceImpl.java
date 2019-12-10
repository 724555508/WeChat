package com.Yang.modules.core.service.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.Yang.common.utils.Is;
import com.Yang.common.utils.MessageXmlUtil;
import com.Yang.modules.book.model.Click_Book;
import com.Yang.modules.book.model.Subscribe_Book;
import com.Yang.modules.book.model.Text_Book;
import com.Yang.modules.book.model.Unsubscribe_Book;
import com.Yang.modules.core.dao.TemplateDao;
import com.Yang.modules.core.entity.InitConfig;
import com.Yang.modules.core.service.ProcessService;
import com.Yang.modules.core.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProcessServiceImpl implements ProcessService{

	
	@Resource UserService userService;
	@Resource TemplateDao TemplateDao;
	
	/**
	 * 	统一处理公众号消息
	 * @param request
	 * @param access_id
	 * @return
	 */
	@Override
	public String execute(HttpServletRequest request, String access_id) {
		
		try {
			Map requestMap = MessageXmlUtil.parseXml(request);
			log.info("消息解析成功:内容为:{}" , requestMap);

			String fromUserName = (String) requestMap.get("FromUserName");

			String toUserName = (String) requestMap.get("ToUserName");

			String msgType = (String) requestMap.get("MsgType");
			
			String content = Is.Null((String) requestMap.get("Content")) ? "" : ((String) requestMap.get("Content")).trim();
			
			
			InitConfig appConfig = userService.getAppConfig(access_id);
			
			switch (msgType) {
			
				case MessageXmlUtil.REQ_MESSAGE_TYPE_TEXT:
					
					Text_Book text = new Text_Book(appConfig, fromUserName, toUserName, content);
					return text.doSomething();
					
				case MessageXmlUtil.REQ_MESSAGE_TYPE_EVENT:
					
					String eventType = (String) requestMap.get("Event");
					if (eventType.equals(MessageXmlUtil.EVENT_TYPE_SUBSCRIBE)) {
						//关注
						String eventKey = (String) requestMap.get("EventKey");
						if (!Is.Null(eventKey)) {
							log.info("{}扫码关注：key={}" , fromUserName , eventKey);
						}
						return new Subscribe_Book(appConfig,fromUserName, toUserName, content,eventKey).doSomething();
					}else if (eventType.equals(MessageXmlUtil.EVENT_TYPE_UNSUBSCRIBE)) {
						//取消关注
						return new Unsubscribe_Book(appConfig, fromUserName, toUserName, content).doSomething();
					}else if (eventType.equals(MessageXmlUtil.EVENT_TYPE_SCAN)){
						//扫码
					}else if (eventType.equals(MessageXmlUtil.EVENT_TYPE_CLICK)) {
						//点击菜单
						String eventKey = (String) requestMap.get("EventKey");
						if (!Is.Null(eventKey)) {
							log.info("{}点击菜单：key={}" , fromUserName , eventKey);
						}
						return new Click_Book(appConfig, fromUserName, toUserName, content,eventKey).doSomething();
					}
				default:
					break;
					
			}
			
			
		} catch (Exception e) {
			log.info("处理公众号消息错误， accessId:{}" , access_id);
			e.printStackTrace();
		}
		
		return "";
	}

	
	
}
