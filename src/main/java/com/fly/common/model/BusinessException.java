package com.fly.common.model;

/**
 * Created by jinxiaofei on 16/9/20.
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -7332019616674959131L;
    private Long code;

    public BusinessException() {
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Long code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getMsg());
        this.code = Long.valueOf(responseCode.getCode());
    }
    public BusinessException(Long code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = Long.valueOf((long) code);
    }

    public BusinessException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = Long.valueOf((long) code);
    }

    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public Long getCode() {
        return this.code;
    }
}
