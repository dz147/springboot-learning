package com.xiaoxiang.springboot.model;

import com.xiaoxiang.springboot.exception.ApiException;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author Stephen
 */
@Data
public class ApiResult<T> implements Serializable {

    private long code;

    private String msg;

    private T data;


    public ApiResult() {
    }

    public ApiResult(IError errorCode) {
        errorCode = Optional.ofNullable(errorCode).orElse(ApiErrorCode.FAILED);
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public static <T> ApiResult<T> success(T data) {
        ApiErrorCode aec = ApiErrorCode.SUCCESS;
        return restResult(data, aec);
    }

    public static <T> ApiResult<T> failed(String msg, T data) {
        return restResult(data, ApiErrorCode.FAILED.getCode(), msg);
    }

    public static <T> ApiResult<T> failed(String msg) {
        return restResult(null, ApiErrorCode.FAILED.getCode(), msg);
    }

    public static <T> ApiResult<T> failed(IError errorCode, T data) {
        return restResult(data, errorCode);
    }

    public static <T> ApiResult<T> failed(IError errorCode) {
        return restResult(null, errorCode);
    }

    public static <T> ApiResult<T> restResult(T data, IError errorCode) {
        return restResult(data, errorCode.getCode(), errorCode.getMsg());
    }

    private static <T> ApiResult<T> restResult(T data, long code, String msg) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public boolean ok() {
        return ApiErrorCode.SUCCESS.getCode() == this.code;
    }

    public T serviceData() {
        if (!this.ok()) {
            throw new ApiException(this.msg);
        } else {
            return this.data;
        }
    }

    public long getCode() {
        return this.code;
    }

    public T getData() {
        return this.data;
    }

    public String getMsg() {
        return this.msg;
    }

    public ApiResult<T> setCode(final long code) {
        this.code = code;
        return this;
    }

    public ApiResult<T> setData(final T data) {
        this.data = data;
        return this;
    }

    public ApiResult<T> setMsg(final String msg) {
        this.msg = msg;
        return this;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ApiResult;
    }

}
