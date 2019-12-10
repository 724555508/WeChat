package com.Yang.common.model;

import java.util.Date;


import com.Yang.common.utils.MessageXmlUtil;
import com.Yang.modules.core.entity.InitConfig;

public class Message extends AppConf{

	
	protected String fromUserName;
	
	protected String toUserName;
	
	protected String content;

	public Message(InitConfig initConfig, String fromUserName, String toUserName, String content) {
		super(initConfig);
		this.fromUserName = fromUserName;
		this.toUserName = toUserName;
		this.content = content;
	}
	
	protected String replyText(String content) {
		StringBuffer str = new StringBuffer();
		str.append("<xml>");
		str.append("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");
		str.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");
		str.append("<CreateTime>" + new Date().getTime() + "</CreateTime>");
		str.append("<MsgType><![CDATA[" + MessageXmlUtil.REQ_MESSAGE_TYPE_TEXT + "]]></MsgType>");
		str.append("<Content><![CDATA[" + content + "]]></Content>");
		str.append("</xml>");
		return str.toString();
	}
	
	protected String replyImage(String mediaId) {
		StringBuffer str = new StringBuffer();
		str.append("<xml>");
		str.append("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");
		str.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");
		str.append("<CreateTime>" + new Date().getTime() + "</CreateTime>");
		str.append("<MsgType><![CDATA[" + MessageXmlUtil.REQ_MESSAGE_TYPE_IMAGE + "]]></MsgType>");
		str.append("<Image><MediaId><![CDATA[" + mediaId + "]]></MediaId></Image>");
		str.append("</xml>");
		return str.toString();
	}
	
	
}
