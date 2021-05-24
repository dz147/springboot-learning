package com.xiaoxiang.springboot.config.properties;

import com.xiaoxiang.springboot.config.constant.FastCodeConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author JohnnyLiu
 */
@Data
@Component
@ConfigurationProperties(prefix = "fast-code.jwt")
public class JwtProperties {

    /**
     * 使用JWT
     */
    private boolean enabled;

    /**
     * 密钥
     */
    private String secret = FastCodeConstant.JWT_DEFAULT_SECRET;

    /**
     * 到期时间
     */
    private Long expire = FastCodeConstant.JWT_DEFAULT_EXPIRE;

    /**
     * taken 头标记
     */
    private String tokenHeader = FastCodeConstant.API_ACCESS_KEY_HEADER_NAME;


    /**
     * taken 启动刷新
     */
    private boolean enableRefresh = false;


    private String uniqueId = FastCodeConstant.JWT_UNIQUE_ID;

    /**
     * 签发人
     */
    private String issuer;

    /**
     * 主题
     */
    private String subject;

    /**
     * 签发的目标
     */
    private String audience;

    /**
     * 刷新token倒计时，默认10分钟，10*60=600
     */
    private Integer refreshTokenCountdown = FastCodeConstant.JWT_TOKEN_REFRESH_COUNTDOWN;


}
