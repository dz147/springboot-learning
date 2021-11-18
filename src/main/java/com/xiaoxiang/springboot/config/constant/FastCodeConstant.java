package com.xiaoxiang.springboot.config.constant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
     * swagger文档标题
     */
    public final static String SWAGGER_TITLE = "Fast Code API Documentation";

    /**
     * swagger项目地址
     */
    public final static String SWAGGER_URL = "https://github.com/dandandog";

    /**
     * swagger项目描述
     */
    public final static String SWAGGER_DESCRIPTION = "Documentation for Fast Code API";

    /**
     * swagger联系人
     */
    public final static String SWAGGER_CONTACT_USER = "Johnny";

    /**
     * swagger联系url
     */
    public final static String SWAGGER_CONTACT_URL = "https://github.com/dandandog/fastcode/issues";

    /**
     * swagger联系邮箱
     */
    public final static String SWAGGER_CONTACT_EMAIL = "704365036@qq.com";

    /**
     * swagger项目证书
     */
    public final static String PROJECT_LICENSE = "GNU General Public License v3.0";

    /**
     * swagger项目证书
     */
    public final static String PROJECT_LICENSE_URL = "https://github.com/dandandog/fastcode/master/LICENSE";


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
     * 项目版本号
     */
    public static final String FAST_CODE_VERSION;

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
     * 登入页
     */
    public static final String LOGIN_PAGE = "/login";

    /**
     * 登入失败页
     */
    public static final String LOGIN_FAILED_PAGE = "/login?error=true";

    /**
     * 首页
     */
    public static final String INDEX_PAGE = "/index";

    /**
     * 500页
     */
    public static final String ERROR_PAGE = "/error";

    /**
     * 404页
     */
    public static final String NOT_FOUND_PAGE = "/404";

    /**
     * access页
     */
    public static final String ACCESS_PAGE = "/access";

    /**
     * 注册页
     */
    public static final String FORGET_PAGE = "/forget";

    /**
     * 页面后缀
     */
    public static final String PAGE_SUFFIX = "";

    /**
     * 消息标识前缀
     */
    public static final String MESSAGE_CODE_PREFIX = "framework.";

    /**
     * 消息弹窗
     */
    public static final String MESSAGE_DIALOG_WIDGET_VAR = "globalMessageDialog";

    /**
     * 消息公告
     */
    public static final String MESSAGE_GROWL_WIDGET_VAR = "globalMessageGrowl";

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


    static {
        FAST_CODE_VERSION = Optional.ofNullable(FastCodeConstant.class.getPackage().getImplementationVersion()).orElse(UNKNOWN_VERSION);
    }
}
