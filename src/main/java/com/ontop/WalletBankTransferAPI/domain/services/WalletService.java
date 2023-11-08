package com.ontop.WalletBankTransferAPI.domain.services;

import com.ontop.WalletBankTransferAPI.domain.Payment;
import com.ontop.WalletBankTransferAPI.domain.WalletTransactionDomain;
import com.ontop.WalletBankTransferAPI.domain.enums.TransactionStatus;
import com.ontop.WalletBankTransferAPI.domain.exeptions.BusinessException;
import com.ontop.WalletBankTransferAPI.domain.ports.InboutWalletTransactonPor;
import com.ontop.WalletBankTransferAPI.domain.ports.OutbountWalletTransactionPor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class WalletService implements InboutWalletTransactonPor {

    private final OutbountWalletTransactionPor outbountWalletTransactionPor;

    public WalletService(OutbountWalletTransactionPor externalWalletPort) {
        this.outbountWalletTransactionPor = externalWalletPort;
    }

    @Override
    public WalletTransactionDomain execute(Integer userId, BigDecimal amount) {

        if (userId == null || amount == null) {
            throw new IllegalArgumentException("amount and user_id must not be null");
        }

        var walletBalance = outbountWalletTransactionPor.findBalance(userId);

        if (walletBalance.getBalance().compareTo(amount) < 0 ) {
            throw new BusinessException("Balance is not enough");
        }

        var externalWalletTransaction = outbountWalletTransactionPor
                .createExternalTransaction(new WalletTransactionDomain(userId, amount));

        WalletTransactionDomain registredWalletTransaction = registerPendingTransaction(externalWalletTransaction);

        Payment paymentInfo = outbountWalletTransactionPor.registerPayment(userId, amount);

        if (paymentInfo == null) {
            registredWalletTransaction.setStatus(TransactionStatus.FAILED);
            outbountWalletTransactionPor.registerTransaction(registredWalletTransaction);
            throw new BusinessException("Payment failed");
        }

        applyFeeAmount(registredWalletTransaction);

        completeTransaction(registredWalletTransaction, paymentInfo);

        return registredWalletTransaction;
    }

    private void completeTransaction(WalletTransactionDomain registredWalletTransaction, Payment paymentInfo) {
        registredWalletTransaction.setStatus(TransactionStatus.COMPLETED);
        registredWalletTransaction.setPaymentId(paymentInfo.getId());
        outbountWalletTransactionPor.registerTransaction(registredWalletTransaction);
    }

    private static void applyFeeAmount(WalletTransactionDomain walletTransaction) {
        int percentage = 10;
        BigDecimal amount = walletTransaction.getAmount();

        // Calculate the fee
        BigDecimal feeAmount = amount.multiply(BigDecimal.valueOf(percentage)).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        // Set the fee amount in the transaction
        walletTransaction.setFeeAmount(feeAmount);

        // Update the transaction amount by subtracting the fee
        BigDecimal transactionAmount = amount.subtract(feeAmount);
        walletTransaction.setAmount(transactionAmount);
    }

    private WalletTransactionDomain registerPendingTransaction(WalletTransactionDomain pedenteTransaction) {
        return outbountWalletTransactionPor
                .registerTransaction(
                        new WalletTransactionDomain(pedenteTransaction.getWalletTransactionId(), pedenteTransaction.getUserId(),pedenteTransaction.getAmount(), LocalDateTime.now(), TransactionStatus.PENDING));
    }
}
