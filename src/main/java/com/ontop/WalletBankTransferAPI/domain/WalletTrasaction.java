package com.ontop.WalletBankTransferAPI.domain;

import com.ontop.WalletBankTransferAPI.domain.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WalletTrasaction {
    private Integer walletTransactionId;
    private Integer userId;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private TransactionStatus status;
    private String paymentId;

    public WalletTrasaction() {
    }

    public WalletTrasaction(Integer id, Integer userId, BigDecimal amount, LocalDateTime dateTime, TransactionStatus status) {
        this.walletTransactionId = id;
        this.userId =  userId;
        this.amount = amount;
        this.dateTime = dateTime;
        this.status = status;
    }

    public WalletTrasaction(Integer userId, BigDecimal amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public Integer getWalletTransactionId() {
        return walletTransactionId;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setWalletTransactionId(Integer walletTransactionId) {
        this.walletTransactionId = walletTransactionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
