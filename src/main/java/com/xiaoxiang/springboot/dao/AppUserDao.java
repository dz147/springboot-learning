package com.xiaoxiang.springboot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoxiang.springboot.entity.AppUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * (AppUser)表数据库访问层
 *
 * @author Stephen
 * @since 2021-05-19 14:25:20
 */
@Mapper
public interface AppUserDao extends BaseMapper<AppUser> {

}