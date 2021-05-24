package com.xiaoxiang.springboot.jwt;

import com.xiaoxiang.springboot.exception.JwtTokenException;
import com.xiaoxiang.springboot.model.ApiErrorCode;
import com.xiaoxiang.springboot.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * @author Stephen
 */
@Slf4j
public class JwtCredentialsMatcher implements CredentialsMatcher {


    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        String token = authenticationToken.getCredentials().toString();
        String salt = authenticationInfo.getCredentials().toString();
        try {
            return JwtTokenUtil.verifyToken(token, salt);
        } catch (Exception e) {
            throw new JwtTokenException(ApiErrorCode.TOKEN_DISABLED);
        }
    }
}
