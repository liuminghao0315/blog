package com.lmh.blog_project.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmh.blog_project.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptor implements HandlerInterceptor {
//    @Autowired
    private TokenUtil tokenUtil = new TokenUtil();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // 忽略 CORS 预检请求 (OPTIONS 请求)
//        if ("OPTIONS".equals(request.getMethod())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//            return false;  // 不继续执行后续的拦截器或控制器逻辑
//        }
        String token = request.getHeader("token");
        if(tokenUtil.parseToken(token)!=null){
            try {
                System.out.println("请求成功，用户信息："+new ObjectMapper().writeValueAsString(tokenUtil.parseToken(token)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return true;
        }else{
            System.out.println("请求失败，token错误");
            return false;
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
