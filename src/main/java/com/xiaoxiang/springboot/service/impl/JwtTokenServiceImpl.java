package com.xiaoxiang.springboot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Maps;
import com.xiaoxiang.springboot.entity.AppUser;
import com.xiaoxiang.springboot.jwt.JwtToken;
import com.xiaoxiang.springboot.service.AppUserService;
import com.xiaoxiang.springboot.service.JwtTokenService;
import com.xiaoxiang.springboot.utils.JwtTokenUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

/**
 * @author Stephen
 */
@Component
public class JwtTokenServiceImpl implements JwtTokenService {

    @Resource
    private AppUserService appUserService;

    @Override
    public String refreshToken(JwtToken jwtToken) {
        AppUser user = getUserById(jwtToken.getUniqueId());
        user.setSecret(RandomUtil.randomString(16));
        String newToken = JwtTokenUtil.refreshToken(jwtToken.getToken(), user.getSecret());
        if (!ObjectUtil.equal(newToken, jwtToken.getToken())) {
            appUserService.updateById(user);
        }
        return newToken;
    }

    @Override
    public synchronized String generateToken(String sessionKey, String openId) {
        AppUser user = Optional.ofNullable(appUserService.lambdaQuery().eq(AppUser::getPhone, openId).one())
                .orElse(new AppUser(openId));
        user.setSecret(RandomUtil.randomString(16));
        return appUserService.saveOrUpdate(user) ? JwtTokenUtil.generateToken(user.getId(), user.getSecret()) : null;
    }

    @Override
    public synchronized String generateToken(String phone) {
        AppUser user = Optional.ofNullable(appUserService.lambdaQuery().eq(AppUser::getPhone, phone).one())
                .orElse(new AppUser(phone));
        user.setSecret(RandomUtil.randomString(16));
        return appUserService.saveOrUpdate(user) ? JwtTokenUtil.generateToken(user.getId(), user.getSecret()) : null;
    }

    @Override
    public JwtToken buildJwtToken(String token) {
        String uniqueId = JwtTokenUtil.getUniqueId(token);
        String secret = getUserById(uniqueId).getSecret();
        return JwtToken.build(token, uniqueId, secret);
    }

    @Override
    public Map<String, String> tokenFilterList() {
        Map<String, String> filterList = Maps.newHashMap();
        filterList.put("/app/token", "anon");
        filterList.put("/app/upload", "anon");
        filterList.put("/mall/prod/order/wxNotify", "anon");
        filterList.put("/app/userById", "anon");
        filterList.put("/notify/**", "anon");
        filterList.put("/ws/**", "anon");
        filterList.put("/mall/prod/page", "anon");
        //商品白名单
        filterList.put("/mall/prod/del/cacheInfo", "anon");
        //商品分类白名单
        filterList.put("/mall/prod/category/**", "anon");
        //商品标签白名单
        filterList.put("/mall/prod/tag/**", "anon");
        filterList.put("/app/pack/**", "anon");
        filterList.put("/mall/es/**", "anon");
        return filterList;
    }


    public AppUser getUserById(String userId) {
        return Optional.ofNullable(appUserService.getById(userId)).orElseThrow(() ->
                new AuthenticationException("用户不存在"));
    }

}
