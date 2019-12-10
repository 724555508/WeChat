package com.Yang.modules.book.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookDao {

	List<Integer> countPeople(String openId);
	
}
