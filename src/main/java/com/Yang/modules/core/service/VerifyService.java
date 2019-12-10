package com.Yang.modules.core.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface VerifyService {

	/**
	 * 	响应微信发送的Token验证
	 * @param request
	 */
	void auto(HttpServletRequest request , String access_id , HttpServletResponse response);
	
}
