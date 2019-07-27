package board.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import board.interceptor.LoggerInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new LoggerInterceptor());
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver(){
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setMaxUploadSize(5 * 1024 * 1024); //최대 용량:5MB
		return resolver;
	}
}