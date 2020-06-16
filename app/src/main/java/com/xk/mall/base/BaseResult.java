package com.xk.mall.base;

/**
 * File descripition:   状态划分 基类
 */

public class BaseResult {
    public String message;
    public int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
