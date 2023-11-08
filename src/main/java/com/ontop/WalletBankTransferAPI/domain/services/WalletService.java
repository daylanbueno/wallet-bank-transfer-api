package com.ontop.WalletBankTransferAPI.domain.services;

import com.ontop.WalletBankTransferAPI.domain.Payment;
import com.ontop.WalletBankTransferAPI.domain.WalletTransaction;
import com.ontop.WalletBankTransferAPI.domain.enums.TransactionStatus;
import com.ontop.WalletBankTransferAPI.domain.exeptions.BusinessException;
import com.ontop.WalletBankTransferAPI.domain.ports.InboutWalletTransactonPor;
import com.ontop.WalletBankTransferAPI.domain.ports.OutbountWalletTransactionPor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WalletService implements InboutWalletTransactonPor {

    private final OutbountWalletTransactionPor outbountWalletTransactionPor;

    public WalletService(OutbountWalletTransactionPor externalWalletPort) {
        this.outbountWalletTransactionPor = externalWalletPort;
    }

    @Override
    public WalletTransaction execute(Integer userId, BigDecimal amount) {

        if (userId == null || amount == null) {
            throw new IllegalArgumentException("amount and user_id must not be null");
        }

        var walletBalance = outbountWalletTransactionPor.findBalance(userId);

        if (walletBalance.getBalance().compareTo(amount) < 0 ) {
            throw new BusinessException("Balance is not enough");
        }

        var externalWalletTransaction = outbountWalletTransactionPor
                .createExternalTransaction(new WalletTransaction(userId, amount));

        WalletTransaction registredWalletTransaction = registerPendingTransaction(externalWalletTransaction);

        Payment paymentInfo = outbountWalletTransactionPor.registerPayment(userId, amount);

        if (paymentInfo == null) {
            registredWalletTransaction.setStatus(TransactionStatus.FAILED);
            outbountWalletTransactionPor.registerTransaction(registredWalletTransaction);
            throw new BusinessException("Payment failed");
        }

        applyTaxe(registredWalletTransaction);

        completeTransaction(registredWalletTransaction, paymentInfo);

        return registredWalletTransaction;
    }

    private void completeTransaction(WalletTransaction registredWalletTransaction, Payment paymentInfo) {
        registredWalletTransaction.setStatus(TransactionStatus.COMPLETED);
        registredWalletTransaction.setPaymentId(paymentInfo.getId());
        outbountWalletTransactionPor.registerTransaction(registredWalletTransaction);
    }

    private static void applyTaxe(WalletTransaction walletTrasaction) {
        int percetage = 10;
        var amount  = walletTrasaction.getAmount();
        BigDecimal amountFree  = amount.multiply(BigDecimal.valueOf(percetage)).divide(BigDecimal.valueOf(100));

        walletTrasaction.setTotalFee(amountFree);

        walletTrasaction.setAmount(amount.subtract(amountFree));
    }

    private WalletTransaction registerPendingTransaction(WalletTransaction pedenteTransaction) {
        return outbountWalletTransactionPor
                .registerTransaction(
                        new WalletTransaction(pedenteTransaction.getWalletTransactionId(), pedenteTransaction.getUserId(),pedenteTransaction.getAmount(), LocalDateTime.now(), TransactionStatus.PENDING));
    }
}
