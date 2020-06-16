package com.xk.mall.utils;

public class EventBusMessage {
    private String message;

    public EventBusMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
