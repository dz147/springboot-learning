package com.xiaoxiang.springboot.config;

import com.xiaoxiang.springboot.config.properties.SwaggerProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xiaoxiang
 */
@Slf4j
@Configuration
@EnableSwagger2
@AllArgsConstructor
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = "fast-code.swagger", value = {"enabled"}, matchIfMissing = true)
public class SwaggerAutoConfig implements InitializingBean {


    private final SwaggerProperties swaggerProperties;


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getScanPackage()))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .termsOfServiceUrl(swaggerProperties.getUrl())
                .contact(swaggerProperties.getContact())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .build();
    }


    @Override
    public void afterPropertiesSet() {
        createRestApi();
    }
}
