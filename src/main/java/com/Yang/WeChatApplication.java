package com.Yang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.Yang.common.utils.SpringContextUtil;

@SpringBootApplication
public class WeChatApplication extends SpringBootServletInitializer implements ApplicationListener<ContextRefreshedEvent>{

	public static void main(String[] args) {
		SpringApplication.run(WeChatApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		return application.sources(WeChatApplication.class); 
	}
	
	public void onApplicationEvent(ContextRefreshedEvent event) {
		SpringContextUtil.setApplicationContext(event.getApplicationContext());
	}

}
