package com.xiaoxiang.springboot.exception;

import com.xiaoxiang.springboot.model.IError;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.authc.AuthenticationException;

/**
 * @author Stephen
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class JwtTokenException extends AuthenticationException {

    private IError error;

    public JwtTokenException(IError error) {
        super(error.getMsg());
        this.error = error;
    }
}
