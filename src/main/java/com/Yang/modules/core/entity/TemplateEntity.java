package com.Yang.modules.core.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("a_template")
public class TemplateEntity {

	@TableId
	String appId;
	
	@TableId
	String method;
	
	String templateId;
	
	Date gmtCreate;
}
