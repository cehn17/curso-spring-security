package com.cehn17.spring.security.dto;

import java.io.Serializable;

public class LogoutResponse implements Serializable {

    String message;

    public LogoutResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
