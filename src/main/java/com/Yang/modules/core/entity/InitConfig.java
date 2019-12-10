package com.Yang.modules.core.entity;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("a_init_config")
public class InitConfig implements Serializable {
	
	
	@TableId
    private String checkcode;

    /**
     * 公众号校验token
     */
    private String token;

    /**
     * appId
     */
    private String appid;

    /**
     * secret
     */
    private String secret;

    /**
     * 域名
     */
    private String domainname;

    /**
     * 微信号
     */
    private String wechatid;

    private static final long serialVersionUID = 1L;
}