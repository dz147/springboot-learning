package com.xiaoxiang.springboot.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author Stephen
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Facet {
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";
}
