package com.xiaoxiang.springboot.core.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Stephen
 */
@Component("pageKeyGenerator")
public class PageKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(target.getClass().getName()).append(".");
        sb.append(method.getName()).append(".");
        for (Object obj : params) {
            sb.append(obj.toString());
        }
        return sb.toString();
    }
}
