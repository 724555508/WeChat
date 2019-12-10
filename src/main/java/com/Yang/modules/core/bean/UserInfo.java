package com.Yang.modules.core.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserInfo implements Serializable{

	String openId;
	String nickname;
	String sex;
	String city;
	String province;
	String country;
	String headimgurl;
	
}
