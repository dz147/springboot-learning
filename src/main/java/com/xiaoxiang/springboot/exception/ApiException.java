package com.xiaoxiang.springboot.exception;


public class ApiException extends FastCodeException {

    public ApiException(String msg) {
        super(msg);
    }
}
