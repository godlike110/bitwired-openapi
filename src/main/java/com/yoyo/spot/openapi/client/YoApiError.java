package com.yoyo.spot.openapi.client;


/**
 * YoYo API error object.
 */
public class YoApiError {

    /**
     * Error code.
     */
    private int code;

    /**
     * Error message.
     */
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
