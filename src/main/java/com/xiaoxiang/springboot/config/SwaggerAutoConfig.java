package com.xiaoxiang.springboot.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaoxiang.springboot.config.constant.FastCodeConstant;
import com.xiaoxiang.springboot.config.properties.SwaggerProperties;
import com.xiaoxiang.springboot.core.utils.SpringContextUtil;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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


    private final List<ResponseMessage> globalResponses = Arrays.asList(
            new ResponseMessageBuilder().code(200).message("Success").build(),
            new ResponseMessageBuilder().code(400).message("Bad request").build(),
            new ResponseMessageBuilder().code(401).message("Unauthorized").build(),
            new ResponseMessageBuilder().code(403).message("Forbidden").build(),
            new ResponseMessageBuilder().code(404).message("Not found").build(),
            new ResponseMessageBuilder().code(500).message("Internal server error").build());

    private final Class<?>[] ignoredParameterTypes = new Class[] {
            ServletRequest.class,
            ServletResponse.class,
            HttpServletRequest.class,
            HttpServletResponse.class,
            HttpSession.class,
            ApiIgnore.class
    };

    @Override
    public void afterPropertiesSet() throws Exception {
        List<SwaggerProperties.ApiScan> apiScans = swaggerProperties.getApiScans();
        log.debug("Swagger Docket registry start ~~~~");
        if (CollUtil.isNotEmpty(apiScans)) {
            apiScans.forEach(apiScan -> {
                SpringContextUtil.registerBean(apiScan.getGroupName(), Docket.class, () -> buildApiDocket(
                        apiScan.getGroupName(),
                        apiScan.getBasePackage(),
                        apiScan.getAntPattern())
                        .securitySchemes(contentApiKeys())
                        .securityContexts(contentSecurityContext(apiScan.getPathRegex()))
                        .enable(swaggerProperties.isEnabled()));
                log.debug("registry bean name :" + apiScan.getGroupName());
            });
        }
    }

    private List<ApiKey> contentApiKeys() {
        return Arrays.asList(
                new ApiKey("Access key from header", FastCodeConstant.API_ACCESS_KEY_HEADER_NAME, In.HEADER.name()),
                new ApiKey("Access key from query", FastCodeConstant.API_ACCESS_KEY_QUERY_NAME, In.QUERY.name())
        );
    }


    private List<SecurityContext> contentSecurityContext(String pathRegex) {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(contentApiAuth())
                        .forPaths(PathSelectors.regex(pathRegex))
                        .build()
        );
    }

    private List<SecurityReference> contentApiAuth() {
        AuthorizationScope[] authorizationScopes = {new AuthorizationScope("content api", "Access content api")};
        return Arrays.asList(new SecurityReference("Access key from header", authorizationScopes),
                new SecurityReference("Access key from query", authorizationScopes));
    }


    private Docket buildApiDocket(String groupName, String basePackage, String antPattern) {
        Assert.hasText(groupName, "Group name must not be blank");

        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        if (StrUtil.isNotEmpty(basePackage)) {
            docket.groupName(groupName);
        }
        ApiSelectorBuilder apiSelectorBuilder = docket.select();
        if (StrUtil.isEmpty(basePackage)) {
            apiSelectorBuilder.apis(RequestHandlerSelectors.withClassAnnotation(Api.class));
        } else {
            apiSelectorBuilder.apis(RequestHandlerSelectors.basePackage(basePackage));
        }
        if (StrUtil.isEmpty(antPattern)) {
            apiSelectorBuilder.paths(PathSelectors.any());
        } else {
            apiSelectorBuilder.paths(PathSelectors.ant(antPattern));
        }

        return apiSelectorBuilder.build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .ignoredParameterTypes(ignoredParameterTypes)
                .globalResponseMessage(RequestMethod.GET, globalResponses)
                .globalResponseMessage(RequestMethod.POST, globalResponses)
                .globalResponseMessage(RequestMethod.DELETE, globalResponses)
                .globalResponseMessage(RequestMethod.PUT, globalResponses)
                .directModelSubstitute(Temporal.class, String.class);

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


}
