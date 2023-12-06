package com.exciting.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	private final long MAX_AGE_SECS = 3600;
	
	
	@Value("${file.upload.directory}")
	private String fileUploadDirectory;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String imagePathPattern = "/uploads/**";
		String imagePathLocation = fileUploadDirectory;
		registry.addResourceHandler(imagePathPattern).addResourceLocations(imagePathLocation).setCachePeriod(0);
		;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 모든 경로에 대해 CORS 설정 적용
				.allowedOrigins("http://localhost:3000") // 오타 수정
				.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS").allowedHeaders("*")
				.allowCredentials(true).maxAge(MAX_AGE_SECS);
	}

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/uploads/**").addResourceLocations("classpath:/static/uploads/");
//	}

	public void destroy() {
	}
}