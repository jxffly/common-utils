package com.fly.common.model;

import java.util.stream.Stream;

/**
 * Created by jinxiaofei on 16/9/20.
 */
public enum ResponseCode {
    PARAM_ERROR(10010, "参数错误"),
    ERROR(99999, "错误");
    ;
    private int code;
    private String msg;


    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static ResponseCode valueOf(int code) {
        ResponseCode[] responseCodes = ResponseCode.values();
        return Stream.of(responseCodes)
                .filter(responseCode -> responseCode.getCode() == code)
                .findFirst().orElse(null);
    }

    public static String getMsgByCode(int code) {
        return valueOf(code).getMsg();
    }

    public static BusinessException getException(ResponseCode responseCode){
        return  new BusinessException(responseCode.getCode(),responseCode.getMsg());
    }
    public static BusinessException getException(ResponseCode responseCode,Throwable t){
        return  new BusinessException(responseCode.getCode(),responseCode.getMsg(),t);
    }
}