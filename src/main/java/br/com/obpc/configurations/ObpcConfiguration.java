package br.com.obpc.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@EnableScheduling
@Configuration
public class ObpcConfiguration implements WebMvcConfigurer{
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry){		
		 registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");	
		  registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		  registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
		  registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
	}

}
