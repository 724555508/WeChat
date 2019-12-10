package com.Yang.modules.core.service;

import javax.servlet.http.HttpServletRequest;

public interface ProcessService {

	/**
	 * 	统一处理公众号消息
	 * @param request
	 * @param access_id
	 * @return
	 */
	
	String execute(HttpServletRequest request,String access_id);
	
}
