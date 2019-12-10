package com.Yang.modules.book.model;

import java.util.Date;

import com.Yang.common.model.Text;
import com.Yang.common.utils.MessageXmlUtil;
import com.Yang.modules.core.entity.InitConfig;

public class Text_Book extends Text{

	
	public Text_Book(InitConfig initConfig, String fromUserName, String toUserName, String content) {
		super(initConfig, fromUserName, toUserName, content);
	}

	@Override
	public String doSomething() {
		StringBuffer str = new StringBuffer();
		str.append("<xml>");
		str.append("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");
		str.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");
		str.append("<CreateTime>" + new Date().getTime() + "</CreateTime>");
		str.append("<MsgType><![CDATA[" + MessageXmlUtil.REQ_MESSAGE_TYPE_TEXT + "]]></MsgType>");
		str.append("<Content><![CDATA[" + "厉害哦:" + content + "]]></Content>");
		str.append("</xml>");
		return str.toString();
	}

}
