package com.xiaoxiang.springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author JohnnyLiu
 */
@Slf4j
@ComponentScan("com.xiaoxiang.springboot")
public class SpringAutoScanConfig {

    public SpringAutoScanConfig() {
        log.debug("Web config initialization");
    }
}
