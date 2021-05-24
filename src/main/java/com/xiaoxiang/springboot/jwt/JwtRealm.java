package com.xiaoxiang.springboot.jwt;

import cn.hutool.core.util.StrUtil;
import com.xiaoxiang.springboot.exception.JwtTokenException;
import com.xiaoxiang.springboot.model.ApiErrorCode;
import com.xiaoxiang.springboot.utils.JwtTokenUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephen
 */
@Slf4j
public class JwtRealm extends AuthorizingRealm {

    /**
     * 注意坑点 : 必须重写此方法，不然Shiro会报错
     * 因为创建了 JWTToken 用于替换Shiro原生 token,所以必须在此方法中显式的进行替换，否则在进行判断时会一直失败
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String phone = (String) principals.getPrimaryPrincipal();
        System.out.println("用户授权");
        Set<String> role = new HashSet<>();
        role.add("user");
        info.setRoles(role);
        info.addStringPermission("user:view");
        return info;
    }

    /**
     * 校验 验证token逻辑
     */
    @SneakyThrows
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        JwtToken jwtToken = (JwtToken) token;
        if (jwtToken == null) {
            throw new JwtTokenException(ApiErrorCode.TOKEN_DISABLED);
        }
        String uniqueId = JwtTokenUtil.getUniqueId(jwtToken.getToken());
        if (StrUtil.isBlank(uniqueId)) {
            throw new JwtTokenException(ApiErrorCode.TOKEN_DISABLED);
        }
        if (JwtTokenUtil.isExpired(jwtToken.getToken())) {
            throw new JwtTokenException(ApiErrorCode.TOKEN_DISABLED);
        }
        return new SimpleAuthenticationInfo(jwtToken.getToken(), jwtToken.getSecret(), getName());
    }

}
