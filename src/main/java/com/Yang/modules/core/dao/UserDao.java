package com.Yang.modules.core.dao;

import org.apache.ibatis.annotations.Mapper;

import com.Yang.modules.core.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface UserDao extends BaseMapper<UserEntity>{

	
	
}
