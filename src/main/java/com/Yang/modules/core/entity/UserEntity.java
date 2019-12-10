package com.Yang.modules.core.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("a_user")
public class UserEntity {

	@TableId(value = "open_id",type = IdType.INPUT)
	String openId;
	String username;
	String headImg;
	String parentId;
	Date createTime;
	String posterUrl;
	int source;
	String belongto;
	String vip;
	Date vipDueTime;
	int subscribe;//是否关注（0.不是   1.是）
	
}
