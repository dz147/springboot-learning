package com.xiaoxiang.springboot.service;


import com.xiaoxiang.springboot.exception.JwtTokenException;
import com.xiaoxiang.springboot.jwt.JwtToken;

import java.util.Map;

/**
 * @author Stephen
 */
public interface JwtTokenService {


    /**
     * 刷新token
     *
     * @param jwtToken JwtToken对象
     * @return 返回新的token
     */
    String refreshToken(JwtToken jwtToken);

    /**
     * 创建token
     *
     * @param sessionKey 微信登入sessionKey ,
     * @return 返回新的token
     */
    String generateToken(String sessionKey, String openId) throws JwtTokenException;

    /**
     * 创建token
     *
     * @param phone 手机号码 ,
     * @return 返回新的token
     * @throws JwtTokenException
     */
    String generateToken(String phone) throws JwtTokenException;

    /**
     * 构建build
     *
     * @param token 请求中获取的token
     * @return 返回 JwtToken对象
     */
    JwtToken buildJwtToken(String token);


    /**
     * token白名单
     *
     * @return 返回 JwtToken对象
     */
    Map<String, String> tokenFilterList();

}
