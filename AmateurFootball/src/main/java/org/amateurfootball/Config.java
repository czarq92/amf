package org.amateurfootball;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
//@EnableWebMvc
//@ComponentScan("org.amateurfootball.controller")
public class Config extends WebSecurityConfigurerAdapter{
//public class Config extends WebMvcConfigurerAdapter {

	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
	}
	
//	@Bean    
//	@Description("Thymeleaf template resolver serving HTML 5")
//    public ServletContextTemplateResolver templateResolver() {
//        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
//        templateResolver.setPrefix("/*");
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode("HTML5");
//        templateResolver.setCharacterEncoding("UTF-8");     
//        templateResolver.setCacheable(false);
//        return templateResolver;
//    }
//
//	@Bean
//	@Description("Thymeleaf view resolver")
//	public ThymeleafViewResolver viewResolver() {
//	    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//	    viewResolver.setTemplateEngine(null);
//	    viewResolver.setContentType("text/html;charset=UTF-8");
//	    viewResolver.setCharacterEncoding("utf-8");
//	
//	    return viewResolver;
//	}
}
