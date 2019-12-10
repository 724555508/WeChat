package com.Yang.modules.book.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.Yang.modules.book.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

	@Resource BookService bookService;
	
	@RequestMapping("/statistics")
	public ModelAndView statistics(HttpServletResponse response,HttpServletRequest request , String code) {
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Pragma","No-cache"); 
		response.setHeader("Cache-Control","no-cache"); 
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setDateHeader("Expires", 0);
		System.out.println(request.getServerName());
		return bookService.statistics(request, code);
	}
	
}
