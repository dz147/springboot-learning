package com.xiaoxiang.springboot.config.properties;

import com.xiaoxiang.springboot.config.constant.FastCodeConstant;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import springfox.documentation.service.Contact;

import java.util.List;

/**
 * @author JohnnyLiu
 */
@Data
@ConfigurationProperties(prefix = "fast-code.swagger")
public class SwaggerProperties {


    private boolean enabled = false;

    /**
     * 标题
     */
    private String title = FastCodeConstant.SWAGGER_TITLE;

    /**
     * 网址
     */
    private String url = FastCodeConstant.SWAGGER_URL;

    /**
     * 描述
     */
    private String description = FastCodeConstant.SWAGGER_DESCRIPTION;

    /**
     * 版本
     */
    private String version = FastCodeConstant.FAST_CODE_VERSION;

    /**
     * 扫描包
     * */
    private String scanPackage;

    /**
     * 联系人参数配置
     */
    @NestedConfigurationProperty
    private Contact contact = new Contact(
            FastCodeConstant.SWAGGER_CONTACT_USER,
            FastCodeConstant.SWAGGER_CONTACT_URL,
            FastCodeConstant.SWAGGER_CONTACT_EMAIL
    );

    @NestedConfigurationProperty
    private List<ApiScan> apiScans;

    /**
     * 项目开源证书
     */
    private String license = FastCodeConstant.PROJECT_LICENSE;

    /**
     * 项目开源证书url
     */
    private String licenseUrl = FastCodeConstant.PROJECT_LICENSE_URL;


    @Getter
    @Setter
    public static class ApiScan {
        private String groupName = "default";
        private String basePackage = "";
        private String basePath = "";
        private String antPattern = "/**";
        private String pathRegex = "/.*";

        public String getAntPattern() {
            return basePath + antPattern;
        }

        public String getPathRegex() {
            return basePath + pathRegex;
        }
    }
}
