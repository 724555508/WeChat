package com.Yang.modules.core.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Yang.modules.core.service.ProcessService;
import com.Yang.modules.core.service.VerifyService;

@RestController
public class BaseController {

	@Resource VerifyService verifyService;
	@Resource ProcessService processService;
	
	
	@RequestMapping("/v/{access_id}")
	public void auto(HttpServletRequest request , @PathVariable String access_id, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		if (isGet) {
			verifyService.auto(request, access_id, response);
		}else {
			PrintWriter print = response.getWriter();
			try {
				print.write(processService.execute(request, access_id));
			} catch (Exception e) {
				print.write("");
				e.printStackTrace();
			}finally {
				print.flush();
				print.close();
			}
		}
	}
	
}
