package me.jinwenjie.interceptor;

import me.jinwenjie.errorhandle.CustomException;
import me.jinwenjie.errorhandle.ExceptionEnum;
import me.jinwenjie.util.JWTUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityUserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        // Bearer xxx
        String token = request.getHeader("Authorization");
        token = token == null ? "" : token.split(" ")[1];
        // URI: /users /users/total
        if (request.getRequestURI().equals("/users") || request.getRequestURI().equals("/users/total")) {
            // register for everyone
            if ("POST".equals(method)) {
                return true;
            }
            // check others
            if (JWTUtil.checkAdmin(token)) {
                return true;
            } else {
                throw new CustomException(ExceptionEnum.AUTH_NOT_ADMIN);
            }
        }
        // URI: /users/uid
        Integer uid = Integer.parseInt(request.getRequestURI().split("/")[2]);
        switch (method) {
            // user self or admin
            case "GET":
            case "PUT":
                if (JWTUtil.getUserId(token).equals(uid) || JWTUtil.checkAdmin(token)) {
                    return true;
                } else {
                    throw new CustomException(ExceptionEnum.AUTH_REQUIRED);
                }
                // admin
            case "DELETED":
                if (JWTUtil.checkAdmin(token)) {
                    return true;
                } else {
                    throw new CustomException(ExceptionEnum.AUTH_NOT_ADMIN);
                }
                // refuse others
            default:
                return false;
        }
    }
}
