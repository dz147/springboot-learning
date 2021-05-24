package com.xiaoxiang.springboot.exception;


import com.xiaoxiang.springboot.model.IError;

/**
 * @author Stephen
 */
public class TokenException extends FastCodeException {

    public TokenException(String message) {
        super(message);
    }

    public TokenException(Integer errorCode, String message) {
        super(errorCode, message);
    }

    public TokenException(IError iErrorEnum) {
        super(iErrorEnum);
    }
}
