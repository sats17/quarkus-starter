//package com.github.sats17.configuration;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class LoggingInterceptor implements HandlerInterceptor {
//
//    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        logger.info("Incoming Request: method={}, uri={}, params={}", 
//                    request.getMethod(), 
//                    request.getRequestURI(), 
//                    request.getQueryString());
//        return true;
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        logger.info("Outgoing Response: status={}, uri={}", 
//                    response.getStatus(), 
//                    request.getRequestURI());
//    }
//}
