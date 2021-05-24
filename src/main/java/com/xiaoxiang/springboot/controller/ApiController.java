package com.xiaoxiang.springboot.controller;

import com.xiaoxiang.springboot.model.ApiResult;
import com.xiaoxiang.springboot.model.IError;

/**
 * @author Stephen
 */
public class ApiController {

    protected <T> ApiResult<T> success(T data) {
        return ApiResult.success(data);
    }

    protected <T> ApiResult<T> failed(String msg) {
        return ApiResult.failed(msg);
    }

    protected <T> ApiResult<T> failed(IError errorCode) {
        return ApiResult.failed(errorCode);
    }
}
