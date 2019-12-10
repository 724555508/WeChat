package com.Yang.modules.core.service.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.Yang.common.utils.CheckCodeUtil;
import com.Yang.modules.core.service.VerifyService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VerifyServiceImpl implements VerifyService{

	/**
	 * 	响应微信发送的Token验证
	 */
	@Override
	public void auto(HttpServletRequest request , String access_id, HttpServletResponse response) {
		
		String signature = request.getParameter("signature");

		String timestamp = request.getParameter("timestamp");

		String nonce = request.getParameter("nonce");

		String echostr = request.getParameter("echostr");
		
		
		if ((signature != null) && (CheckCodeUtil.checkSignature("60AD0E397F51344192943FA1F16ECF36",signature, timestamp, nonce))) {
			log.info("############access_id:{},校验成功" , access_id);
			try {
				PrintWriter print = response.getWriter();
				print.write(echostr);
				print.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			log.info("############access_id:{},校验失败" , access_id);
		}
		
	}

	
	
}
