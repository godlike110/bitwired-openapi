package com.yoyo.spot.openapi.client.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * author:zhiwei
 * <p>
 * email:597384114@qq.com
 * <p>
 * time:2020/2/12
 **/
@Data
public class AccountBalance {

    private String assetSymbol;
    private BigDecimal totalBalance;
    private BigDecimal freezeBalance;
    private BigDecimal btcValue;

    public String getAssetSymbol() {
        return assetSymbol;
    }

    public void setAssetSymbol(String assetSymbol) {
        this.assetSymbol = assetSymbol;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public BigDecimal getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(BigDecimal freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public BigDecimal getBtcValue() {
        return btcValue;
    }

    public void setBtcValue(BigDecimal btcValue) {
        this.btcValue = btcValue;
    }
}
