package com.Yang.modules.core.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("a_message")
public class MessageEntity {

	@TableId(value = "id",type = IdType.INPUT)
	String id;
	String openId;
	String info;
	Date sendTime;
	String templateId;
	String errcode;
	
}
