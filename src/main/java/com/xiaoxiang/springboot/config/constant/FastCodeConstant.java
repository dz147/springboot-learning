package com.xiaoxiang.springboot.config.constant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

/**
 * @author Stephen
 */
@Slf4j
public class FastCodeConstant {


    public FastCodeConstant() {
        log.debug("FastCodeConstant");
    }

    /**
     * 自定义异常key
     */
    public final static String ERROR_KEY = "apiFailure";



    /**
     * api 请求头名字
     */
    public final static String API_ACCESS_KEY_HEADER_NAME = "API-" + HttpHeaders.AUTHORIZATION;

    /**
     * api token 参数名
     */
    public final static String API_ACCESS_KEY_QUERY_NAME = "api_access_key";

    /**
     * admin 请求头名字
     */
    public final static String ADMIN_TOKEN_HEADER_NAME = "ADMIN-" + HttpHeaders.AUTHORIZATION;

    /**
     * 用户家目录
     */
    public final static String USER_HOME = System.getProperties().getProperty("user.home");


    /**
     * 未知版本号
     */
    public static final String UNKNOWN_VERSION = "unknown";

    /**
     *
     */
    public static final String CORS_PATH = "/**";


    public static final List<String> CORS_ALLOWED_ORIGINS = Collections.singletonList(CorsConfiguration.ALL);

    /**
     * 允许访问的请求头
     */
    public static final List<String> CORS_ALLOWED_HEADERS = Collections.singletonList(CorsConfiguration.ALL);


    /**
     * 允许访问的请求方式
     */
    public static final List<String> CORS_ALLOWED_METHODS = Collections.singletonList(CorsConfiguration.ALL);

    /**
     * 允许响应的头
     */
    public static final List<String> CORS_EXPOSED_HEADERS = Collections.singletonList("token");

    /**
     * 该响应的有效时间默认为30分钟，在有效时间内，浏览器无须为同一请求再次发起预检请求
     */
    public static final Long CORS_MAX_AGE = 1800L;

    /**
     * 指定页
     */
    public static final Integer PAGE_DEFAULT_INDEX = 1;

    /**
     * 分页大小
     */
    public static final Integer PAGE_DEFAULT_SIZE = 10;


    /**
     * 消息公告
     */
    public static final String MESSAGE_DEFAULT_TITLE = "messageTitle";


    /**
     * 默认密钥
     */
    public static final String JWT_DEFAULT_SECRET = "O@k%m#PL<a";

    /**
     * JWT唯一键
     */
    public static final String JWT_UNIQUE_ID = "userId";

    /**
     * JWT 默认过期时间，3600L，单位秒
     */
    public static final Long JWT_DEFAULT_EXPIRE = 3600L;

    /**
     * JWT刷新新token响应状态码
     */
    public static final int JWT_REFRESH_TOKEN_CODE = 460;

    /**
     * JWT 刷新倒计时
     */
    public static final int JWT_TOKEN_REFRESH_COUNTDOWN = 10 * 60;

}
