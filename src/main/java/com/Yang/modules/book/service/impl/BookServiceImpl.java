package com.Yang.modules.book.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.Yang.modules.book.dao.BookDao;
import com.Yang.modules.book.service.BookService;
import com.Yang.modules.core.dao.InitConfigDao;
import com.Yang.modules.core.dao.UserDao;
import com.Yang.modules.core.entity.InitConfig;
import com.Yang.modules.core.entity.UserEntity;
import com.Yang.modules.core.service.BasicService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Service
public class BookServiceImpl implements BookService{

	@Resource BasicService basicService;
	@Resource InitConfigDao initConfigDao;
	@Resource BookDao bookDao;
	@Resource UserDao userDao;
	
	@Override
	public ModelAndView statistics(HttpServletRequest request, String code) {
		ModelAndView mav = new ModelAndView("");
		String serverName = request.getServerName();
		InitConfig initConfig = initConfigDao.selectOne(new QueryWrapper<InitConfig>().eq("domainName", serverName));
		Map<String, String> map = basicService.getOpenIdAndTokenByCode(code, initConfig.getAppid(), initConfig.getSecret());
		String openId = map.get("openid");
//		String access_token = map.get("access_token");
		
		
		List<Integer> countPeople = bookDao.countPeople(openId);
		
		List<UserEntity> userEntities = userDao.selectList(new QueryWrapper<UserEntity>().eq("parent_id", openId));
		
		
		mav.getModel().put("countPeople",countPeople);
		mav.getModel().put("userList",userEntities);
		return mav;
	}

	
	
}
