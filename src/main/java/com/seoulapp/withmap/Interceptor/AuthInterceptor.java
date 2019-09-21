package com.seoulapp.withmap.Interceptor;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.google.common.collect.ImmutableMap;
import com.seoulapp.withmap.exception.UnAuthorizedException;
import com.seoulapp.withmap.model.error.ErrorType;
import com.seoulapp.withmap.service.UserService;

@Component
public class AuthInterceptor implements HandlerInterceptor{
    private static final String HEADER_AUTH = "Authorization";
    static final Map<String, String> excludeRequest = ImmutableMap.of("/withmap/users", "POST");
 
    @Autowired
    private UserService userService;
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        final String token = request.getHeader(HEADER_AUTH);
        
        if(isExcludeRequest(request) || userService.isValidateToken(token)) {
        	return true;
        }
        
        throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "유효하지 않은 토큰 인증 요청입니다.");
    }
    
    private Boolean isExcludeRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        Optional<String> method = Optional.ofNullable(excludeRequest.get(requestURI));
        return method.orElse("").equals(httpMethod);
    }
}