package com.ontop.WalletBankTransferAPI.domain.services;

import com.ontop.WalletBankTransferAPI.domain.Payment;
import com.ontop.WalletBankTransferAPI.domain.WalletTransactionDomain;
import com.ontop.WalletBankTransferAPI.domain.enums.TransactionStatus;
import com.ontop.WalletBankTransferAPI.domain.exeptions.BusinessException;
import com.ontop.WalletBankTransferAPI.domain.ports.InboundWalletTransactonPort;
import com.ontop.WalletBankTransferAPI.domain.ports.OutboundWalletTransactionPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class WalletService implements InboundWalletTransactonPort {

    private final OutboundWalletTransactionPort outbountWalletTransactionPort;

    public WalletService(OutboundWalletTransactionPort externalWalletPort) {
        this.outbountWalletTransactionPort = externalWalletPort;
    }

    @Override
    public WalletTransactionDomain execute(Integer userId, BigDecimal amount) {

        if (userId == null || amount == null) {
            throw new IllegalArgumentException("amount and user_id must not be null");
        }

        var walletBalance = outbountWalletTransactionPort.findBalance(userId);

        if (walletBalance.getBalance().compareTo(amount) < 0 ) {
            throw new BusinessException("Balance is not enough");
        }

        var externalWalletTransaction = outbountWalletTransactionPort
                .createExternalTransaction(new WalletTransactionDomain(userId, amount));

        WalletTransactionDomain registredWalletTransaction = registerPendingTransaction(externalWalletTransaction);

        Optional<Payment> paymentInfo = outbountWalletTransactionPort.registerPayment(userId, amount);

        if (paymentInfo.isEmpty()) {
            registredWalletTransaction.setStatus(TransactionStatus.FAILED);
            outbountWalletTransactionPort.registerTransaction(registredWalletTransaction);
            throw new BusinessException("Payment failed");
        }

        applyFeeAmount(registredWalletTransaction);

        completeTransaction(registredWalletTransaction, paymentInfo.get());

        return registredWalletTransaction;
    }

    private void completeTransaction(WalletTransactionDomain registredWalletTransaction, Payment paymentInfo) {
        registredWalletTransaction.setStatus(TransactionStatus.COMPLETED);
        registredWalletTransaction.setPaymentId(paymentInfo.getId());
        outbountWalletTransactionPort.registerTransaction(registredWalletTransaction);
    }

    private static void applyFeeAmount(WalletTransactionDomain walletTransaction) {
        int percentage = 10;
        BigDecimal amount = walletTransaction.getAmount();

        // Calculate the fee
        BigDecimal feeAmount = amount.multiply(BigDecimal.valueOf(percentage)).divide(BigDecimal.valueOf(100));

        // Set the fee amount in the transaction
        walletTransaction.setFeeAmount(feeAmount);

        // Update the transaction amount by subtracting the fee
        BigDecimal transactionAmount = amount.subtract(feeAmount);
        walletTransaction.setAmount(transactionAmount);
    }

    private WalletTransactionDomain registerPendingTransaction(WalletTransactionDomain pedenteTransaction) {
        return outbountWalletTransactionPort
                .registerTransaction(
                        new WalletTransactionDomain(pedenteTransaction.getWalletTransactionId(), pedenteTransaction.getUserId(),pedenteTransaction.getAmount(), LocalDateTime.now(), TransactionStatus.PENDING));
    }
}
