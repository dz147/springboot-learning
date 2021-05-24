package com.xiaoxiang.springboot.config;

import cn.hutool.core.collection.CollUtil;
import com.xiaoxiang.springboot.config.properties.CorsProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 *
 * @author Stephen
 */
@Slf4j
@Configuration
@AllArgsConstructor
@ConditionalOnProperty(value = {"cors.enable"}, matchIfMissing = true)
@EnableConfigurationProperties(CorsProperties.class)
public class CorsAutoConfig {


    private final CorsProperties properties;

    /**
     * CORS跨域设置
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        log.debug("corsProperties:{}", properties);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 跨域配置
        corsConfiguration.setAllowCredentials(properties.isAllowCredentials());

        if (CollUtil.isNotEmpty(properties.getAllowedOrigins())) {
            corsConfiguration.setAllowedOrigins(properties.getAllowedOrigins());
        }
        if (CollUtil.isNotEmpty(properties.getAllowedHeaders())) {
            corsConfiguration.setAllowedHeaders(properties.getAllowedHeaders());
        }
        if (CollUtil.isNotEmpty(properties.getAllowedMethods())) {
            corsConfiguration.setAllowedMethods(properties.getAllowedMethods());
        }
        if (CollUtil.isNotEmpty(properties.getExposedHeaders())) {
            corsConfiguration.setExposedHeaders(properties.getExposedHeaders());
        }
        if (properties.getMaxAge() != null) {
            corsConfiguration.setMaxAge(properties.getMaxAge());
        }
        source.registerCorsConfiguration(properties.getPath(), corsConfiguration);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.setEnabled(properties.isEnable());
        return bean;
    }

}