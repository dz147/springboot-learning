package com.xiaoxiang.springboot.core.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;

/**
 * @author Stephen
 */
public interface BaseEnum<T extends Serializable> extends IEnum<T> {

    String getTitle();
}

