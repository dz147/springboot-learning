package com.xiaoxiang.springboot.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xiaoxiang.springboot.config.properties.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Stephen
 */
@Slf4j
@Component
public class JwtTokenUtil {

    private static JwtProperties jwtProperties;

    @Autowired
    public JwtTokenUtil(JwtProperties jwtProperties) {
        JwtTokenUtil.jwtProperties = jwtProperties;
        log.debug(JSONUtil.toJsonPrettyStr(jwtProperties));
    }


    /**
     * 根据负载生成JWT的token
     *
     * @param uniqueId 系统用户的中唯一标识
     * @param secret   密钥
     * @return String
     */
    public static String generateToken(String uniqueId, String secret) {
        if (StrUtil.isBlank(uniqueId)) {
            log.error("uniqueId不能为空");
            throw new TokenExpiredException("uniqueId can not be null");
        }
        return JWT.create()
                .withClaim(jwtProperties.getUniqueId(), uniqueId)
                .withIssuer(jwtProperties.getIssuer())
                .withSubject(jwtProperties.getSubject())
                .withAudience(jwtProperties.getAudience())
                .withJWTId(IdUtil.randomUUID())
                .withExpiresAt(generateExpirationDate())
                .withIssuedAt(new Date())
                .sign(getEncryptSecret(secret));
    }

    public static String generateToken(String uniqueId) {
        return generateToken(uniqueId, jwtProperties.getSecret());
    }

    /**
     * 验证token
     *
     * @param token  token
     * @param secret 密钥
     * @return boolean
     */
    public static boolean verifyToken(String token, String secret) {
        Algorithm algorithm = getEncryptSecret(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(jwtProperties.getIssuer())
                .withSubject(jwtProperties.getSubject())
                .withAudience(jwtProperties.getAudience())
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt != null;
    }

    /**
     * 验证token
     *
     * @param token token
     * @return boolean
     */
    public static boolean verifyToken(String token) {
        return verifyToken(token, null);
    }


    /**
     * 解析 token
     *
     * @param token tokenrequire
     * @return DecodedJWT
     */
    public static DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }

    /**
     * 获取 token 负载中系统用户的中唯一标识
     *
     * @param token token
     * @return String
     */
    public static String getUniqueId(String token) {
        if (StrUtil.isBlank(token)) {
            return null;
        }
        DecodedJWT decodedJwt = decodeToken(token);
        return decodedJwt.getClaim(jwtProperties.getUniqueId()).asString();
    }

    /**
     * 获取发签时间
     *
     * @param token token
     * @return Date
     */
    public Date getIssuedAt(String token) {
        DecodedJWT decodedJwt = decodeToken(token);
        return decodedJwt.getIssuedAt();
    }


    /**
     * token 到期时间
     *
     * @param token token
     * @return Date
     */
    public static Date getExpiredAt(String token) {
        DecodedJWT decodedJwt = decodeToken(token);
        return decodedJwt.getExpiresAt();
    }

    /**
     * token 在有效期内
     *
     * @param token token
     * @return Date
     */
    public static boolean isExpired(String token) {
        Date expiredDate = getExpiredAt(token);
        return expiredDate.before(new Date());
    }


    public static String refreshToken(String oldToken, String secret) {
        if (StrUtil.isEmpty(oldToken)) {
            return null;
        }
        if (!jwtProperties.isEnableRefresh()) {
            return null;
        }
        if (tokenCanRefresh(oldToken)) {
            return generateToken(getUniqueId(oldToken), secret);
        } else {
            return oldToken;
        }
    }

    /**
     * 生成token的过期时间
     *
     * @return Date
     */
    private static Date generateExpirationDate() {
        Long expireSecond = jwtProperties.getExpire();
        log.debug("expireSecond:{}", expireSecond);
        LocalDateTime expireDate = LocalDateTime.now().plusSeconds(expireSecond.intValue());
        log.debug("expireDate:{}", expireDate);
        return DateTimeUtil.toDate(expireDate);
    }

    /**
     * 获取密钥
     *
     * @param secret 密钥
     * @return Algorithm
     */
    private static Algorithm getEncryptSecret(String secret) {
        secret = StrUtil.isBlank(secret) ? jwtProperties.getSecret() : secret;
        return Algorithm.HMAC256(secret);
    }

    private static boolean tokenCanRefresh(String token) {
        Integer countdown = jwtProperties.getRefreshTokenCountdown();
        Date expires = getExpiredAt(token);
        return DateUtil.offsetSecond(new Date(), countdown).after(expires);
    }

}
