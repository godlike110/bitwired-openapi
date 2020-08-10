package com.yoyo.spot.openapi.client.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * author:zhiwei
 * <p>
 * email:597384114@qq.com
 * <p>
 * time:2020/2/12
 **/
@Data
public class AccountData {
    private AccountBalance[] details;
    private BigDecimal totalBtcValue;

    public AccountBalance[] getDetails() {
        return details;
    }

    public void setDetails(AccountBalance[] details) {
        this.details = details;
    }

    public BigDecimal getTotalBtcValue() {
        return totalBtcValue;
    }

    public void setTotalBtcValue(BigDecimal totalBtcValue) {
        this.totalBtcValue = totalBtcValue;
    }
}
