package com.Yang.modules.core.dao;

import org.apache.ibatis.annotations.Mapper;

import com.Yang.modules.core.entity.MessageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface MessageDao extends BaseMapper<MessageEntity>{

}
