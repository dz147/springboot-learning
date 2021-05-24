package com.xiaoxiang.springboot.core.utils;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxiang.springboot.core.service.impl.BaseServiceImpl;
import com.xiaoxiang.springboot.model.IEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Stephen
 */
@Component
public class MybatisUtil {

    public static <T extends IEntity> BaseMapper<T> getOneMappersByModelClass(Class<T> typeClass) throws IllegalStateException {
        List<BaseMapper<T>> baseMappers = getMappersByModelClass(typeClass);
        return baseMappers.stream().findFirst().orElse(null);
    }

    public static <M extends BaseMapper<T>, T extends IEntity> List<BaseMapper<T>> getMappersByModelClass(Class<T> typeClass) throws IllegalStateException {
        List<BaseServiceImpl<M, T>> baseMappers = getServicesByModelClass(typeClass);
        return baseMappers.stream().map((Function<BaseServiceImpl<M, T>, BaseMapper<T>>) ServiceImpl::getBaseMapper).collect(Collectors.toList());
    }

    public static <M extends BaseMapper<T>, T extends IEntity> BaseServiceImpl<M, T> getOneServiceByModelClass(Class<T> typeClass) throws IllegalStateException {
        List<BaseServiceImpl<M, T>> baseServices = getServicesByModelClass(typeClass);
        return baseServices.stream().findFirst().orElse(null);
    }

    public static <M extends BaseMapper<T>, T extends IEntity> List getServicesByModelClass(Class<T> typeClass) throws IllegalStateException {
        if (typeClass == null) {
            throw new IllegalStateException("typeClass is not null");
        }
        Map<String, BaseServiceImpl> beanMap = SpringContextUtil.getBeansOfType(BaseServiceImpl.class);
        if (CollUtil.isEmpty(beanMap)) {
            return Collections.emptyList();
        }
        return beanMap.values().stream().filter(baseService -> baseService.isCurrentModelClass(typeClass)).collect(Collectors.toList());
    }


}
