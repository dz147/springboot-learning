package com.xiaoxiang.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxiang.springboot.dao.AppUserDao;
import com.xiaoxiang.springboot.entity.AppUser;
import com.xiaoxiang.springboot.service.AppUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * (AppUser)表服务实现类
 *
 * @author Stephen
 * @since 2021-05-19 11:12:49
 */
@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserDao, AppUser> implements AppUserService {

}