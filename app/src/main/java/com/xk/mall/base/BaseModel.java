package com.xk.mall.base;

import java.io.Serializable;

/**
 * File descripition:   mode基类
 */
public class BaseModel<T> implements Serializable {
    private String msg;
    private int code;
    private T data;

    public BaseModel(String message, int code) {
        this.msg = message;
        this.code = code;
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

    public void setMsg(String message) {
        this.msg = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T result) {
        this.data = result;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + data +
                '}';
    }
}
