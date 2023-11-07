package com.ontop.WalletBankTransferAPI.domain;

import java.math.BigDecimal;

public class Balance {

    private BigDecimal total;

    private Integer userId;


    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
