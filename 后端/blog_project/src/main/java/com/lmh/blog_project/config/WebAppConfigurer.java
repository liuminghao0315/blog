package com.lmh.blog_project.config;

import com.lmh.blog_project.interceptor.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new Interceptor());
        interceptorRegistration.excludePathPatterns("/**")
                .excludePathPatterns("/bwun/findall")
                .excludePathPatterns("/loginToken")
                .excludePathPatterns("/login")
                .excludePathPatterns("/db/user/add");
    }
}
