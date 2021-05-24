package com.xiaoxiang.springboot.core.utils;

import com.xiaoxiang.springboot.exception.FrameworkException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Stephen
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    public static ApplicationContext applicationContext;


    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     *
     * @param applicationContext spring上下文
     * @throws BeansException 上下文获取异常
     */
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取 ApplicationContext
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            throw new FrameworkException("applicationContext is not exits");
        }
        return applicationContext;
    }

    /**
     * 获取 ConfigurableApplicationContext
     *
     * @return ConfigurableApplicationContext
     */
    private static ConfigurableApplicationContext getConfigurableApplicationContext() {
        return (ConfigurableApplicationContext) getApplicationContext();
    }

    /**
     * 获取对象
     *
     * @param name bean注册名
     * @return 获取注册名字为 name 的 bean 实例
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     *
     * @param requiredType 对象类型
     * @param <T>          返回对象类型
     * @return 返回Bean实例
     */
    public static <T> T getBean(Class<T> requiredType) {
        return getApplicationContext().getBean(requiredType);
    }


    /**
     * 获取类型为requiredType的对象
     *
     * @param name         bean注册名
     * @param requiredType 对象类型
     * @param <T>          返回对象类型
     * @return 返回Bean实例
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return getApplicationContext().getBean(name, requiredType);
    }

    /**
     * 判断是否有一个与之注册名称匹配的bean
     *
     * @param name bean注册名
     * @return 匹配返回true，反之
     */
    public static boolean containsBean(String name) {
        return getApplicationContext().containsBean(name);
    }

    /**
     * 判断注册名相匹配的bean是否为单例模式
     *
     * @param name bean注册名
     * @return 单例返回true，反之
     */
    public static boolean isSingleton(String name) {
        return getApplicationContext().isSingleton(name);
    }

    /**
     * 获取注册名相匹配的bean的类型
     *
     * @param name bean注册名
     * @return 注册bean类型
     */
    public static Class<?> getType(String name) {
        return getApplicationContext().getType(name);
    }

    /**
     * 获取注册类型相匹配bean
     *
     * @param clazz bean类型
     * @return 注册bean类型
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return getApplicationContext().getBeansOfType(clazz);
    }


    /**
     * 注册bean
     *
     * @param name  bean注册名
     * @param clazz bean类型
     * @param args  构造函数参数
     * @param <T>   bean类型
     * @return 返回bean实例
     */
    public static <T> T registerBean(String name, Class<T> clazz, Object... args) {
        if (containsBean(name)) {
            throw new RuntimeException("Bean is exist for name : " + name);
        }
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        for (Object arg : args) {
            beanDefinitionBuilder.addConstructorArgValue(arg);
        }
        return registerBean(name, clazz, beanDefinitionBuilder);
    }

    public static <T> T registerBean(String name, Class<T> clazz, Supplier<T> supplier) {
        if (containsBean(name)) {
            throw new RuntimeException("Bean is exist for name : " + name);
        }
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz, supplier);
        return registerBean(name, clazz, beanDefinitionBuilder);
    }

    public static <T> T registerBean(String name, Class<T> clazz, BeanDefinitionBuilder beanDefinitionBuilder) {
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) getConfigurableApplicationContext().getBeanFactory();
        beanFactory.registerBeanDefinition(name, beanDefinition);
        return applicationContext.getBean(name, clazz);
    }


}
