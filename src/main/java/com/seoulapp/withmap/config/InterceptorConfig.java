package com.seoulapp.withmap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.seoulapp.withmap.Interceptor.AuthInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private static final String[] EXCLUDE_PATHS = {
    		"/withmap/signin/**",
    		"/withmap/users/**/check",
            "/v2/api-docs",
            "/configuration/security", 
            "/configuration/ui",
            "/swagger-resources/**",
            "/swagger-ui.html**",
            "/webjars/**",
            "favicon.ico",
            "/withmap/users/log/**"
    };
 
    @Autowired
    private AuthInterceptor authInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                        .excludePathPatterns(EXCLUDE_PATHS);
    }
}