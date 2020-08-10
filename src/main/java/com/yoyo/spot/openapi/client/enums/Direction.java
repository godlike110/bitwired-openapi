package com.yoyo.spot.openapi.client.enums;

/**
 * @author wenzhiwei
 * @time 2019-05-04 13:55
 **/
public enum Direction {

    OPENLON("openLong"),
    OPENSHORT("openShort"),
    CLOSELONG("closeLong"),
    CLOSESHORT("closeShort");

    private String direction;


    Direction(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
