package com.yoyo.spot.openapi.client.enums;

/**
 * @author wenzhiwei
 * @time 2019-05-04 13:50
 **/
public enum OrderSide {

    BUY("BUY"),
    SELL("SELL");

    private String type;

    OrderSide(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
