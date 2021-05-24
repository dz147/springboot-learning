package com.xiaoxiang.springboot.jwt;

import com.xiaoxiang.springboot.config.constant.FastCodeConstant;
import com.xiaoxiang.springboot.exception.JwtTokenException;
import com.xiaoxiang.springboot.exception.TokenException;
import com.xiaoxiang.springboot.model.ApiErrorCode;
import com.xiaoxiang.springboot.service.JwtTokenService;
import com.xiaoxiang.springboot.utils.JwtHeaderUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Stephen
 */
@Slf4j
@AllArgsConstructor
public class JwtFilter extends AuthenticatingFilter {

    private final JwtTokenService tokenService;


    /**
     * 将 token 转成 JwtToken对象
     *
     * @param request  ServletRequest
     * @param response ServletResponse
     * @return AuthenticationToken
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String token = JwtHeaderUtil.getAuthHeader(httpRequest);
        return tokenService.buildJwtToken(token);
    }

    /**
     * 访问失败处理
     *
     * @param request  ServletRequest
     * @param response ServletResponse
     * @return boolean
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        String url = WebUtils.toHttp(request).getRequestURI();
        log.debug("onAccessDenied url：{}", url);
        request.setAttribute(FastCodeConstant.ERROR_KEY, ApiErrorCode.TOKEN_DISABLED);
        throw new TokenException(ApiErrorCode.TOKEN_DISABLED);
    }

    /**
     * 此方法调用登陆，验证逻辑,期间executeLogin 里面会调用 createToken方法并执行登入操作
     *
     * @param request     ServletRequest
     * @param response    ServletResponse
     * @param mappedValue Object
     * @return boolean
     */
    @SneakyThrows
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 如果是login 方法就放行不进行登入校验
        if (this.isLoginRequest(request, response)) {
            return true;
        }
        try {
            boolean allowed = executeLogin(request, response);
            return allowed || super.isPermissive(mappedValue);
        } catch (AuthenticationException e) {
            return false;
        }
    }

    /**
     * 登录成功处理
     *
     * @param token    AuthenticationToken
     * @param subject  Subject
     * @param request  ServletRequest
     * @param response ServletResponse
     * @return boolean
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) {
        //TODO
        if (false) {
            String url = WebUtils.toHttp(request).getRequestURI();
            log.debug("鉴权成功,token:{},url:{}", token, url);
            String newToken = tokenService.refreshToken((JwtToken) token);
            log.debug("刷新Token,newToken:{},url:{}", newToken, url);
            HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
            JwtHeaderUtil.setAuthHeader(httpServletResponse, newToken);
        }
        return true;
    }


    /**
     * 登录失败处理
     *
     * @param token    AuthenticationToken
     * @param e        AuthenticationException
     * @param request  ServletRequest
     * @param response ServletResponse
     * @return boolean
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.error("登录失败，token:" + token + ",error:" + e.getMessage(), e);
        JwtTokenException tokenException = (JwtTokenException) e;
        request.setAttribute(FastCodeConstant.ERROR_KEY, tokenException.getError());
        return false;
    }

}
