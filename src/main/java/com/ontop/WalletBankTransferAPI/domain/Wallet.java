package com.ontop.WalletBankTransferAPI.domain;

import java.math.BigDecimal;

public class Wallet {
    private BigDecimal balance;

    private Integer userId;

    public Wallet() {
    }

    public Wallet(BigDecimal balance, Integer userId) {
        this.balance = balance;
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
