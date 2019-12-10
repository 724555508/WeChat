package com.Yang.modules.book.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public interface BookService {

	ModelAndView statistics(HttpServletRequest request , String code);
	
}
