package com.xiaoxiang.springboot.utils;

import com.xiaoxiang.springboot.config.properties.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Stephen
 */
@Slf4j
@Component
public class JwtHeaderUtil {

    private static String tokenHeader;

    @Autowired
    public JwtHeaderUtil(JwtProperties jwtProperties) {
        tokenHeader = jwtProperties.getTokenHeader();
        log.debug("tokenHead:{}", tokenHeader);
    }

    /**
     * 从请求头或者请求参数中
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String getAuthHeader(HttpServletRequest request) {
        return StringUtils.clean(request.getHeader(tokenHeader));
    }


    /**
     * 从请求头或者请求参数中
     *
     * @param response HttpServletRequest
     * @param token    String
     */
    public static void setAuthHeader(HttpServletResponse response, String token) {
        response.setHeader(tokenHeader, token);
    }


}
