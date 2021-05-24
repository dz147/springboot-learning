package com.xiaoxiang.springboot.config.properties;

import com.xiaoxiang.springboot.config.constant.FastCodeConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * fast-code.cors 是yml配置
 * 详情查看注解ConfigurationProperties使用
 * @author JohnnyLiu
 */

@Data
@ConfigurationProperties(prefix = "fast-code.cors")
public class CorsProperties {

    /**
     * 是否启用跨域，默认启用
     */
    private boolean enable = true;

    /**
     * 是否允许发送cookie
     */
    private boolean allowCredentials = true;

    /**
     * CORS过滤的路径，默认：/**
     */
    private String path = FastCodeConstant.CORS_PATH;

    /**
     * 允许访问的源
     */
    private List<String> allowedOrigins = FastCodeConstant.CORS_ALLOWED_ORIGINS;

    /**
     * 允许访问的请求头
     */
    private List<String> allowedHeaders = FastCodeConstant.CORS_ALLOWED_HEADERS;

    /**
     * 允许访问的请求方式
     */
    private List<String> allowedMethods = FastCodeConstant.CORS_ALLOWED_METHODS;

    /**
     * 允许响应的头
     */
    private List<String> exposedHeaders = FastCodeConstant.CORS_EXPOSED_HEADERS;

    /**
     * 该响应的有效时间默认为30分钟，在有效时间内，浏览器无须为同一请求再次发起预检请求
     */
    private Long maxAge = FastCodeConstant.CORS_MAX_AGE;

}
