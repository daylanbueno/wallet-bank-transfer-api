package com.ontop.WalletBankTransferAPI.domain.services;

import com.ontop.WalletBankTransferAPI.domain.Payment;
import com.ontop.WalletBankTransferAPI.domain.WalletTrasaction;
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
    public WalletTrasaction execute(Integer userId, BigDecimal amount) {

        var walletBalance = outbountWalletTransactionPor.findBalance(userId);

        if (walletBalance.getBalance().compareTo(amount) < 0 ) {
            throw new BusinessException("Balance is not enough");
        }

        var walletTransaction = outbountWalletTransactionPor
                .createExternalTransaction(new WalletTrasaction(userId, amount));

        WalletTrasaction walletTrasaction = registerNewTransaction(walletTransaction);

        Payment paymentInfo = outbountWalletTransactionPor.registerPayment(userId, amount);

        if (paymentInfo == null) {
            walletTrasaction.setStatus(TransactionStatus.FAILED);
            walletTransaction.setPaymentId(paymentInfo.getId());
            outbountWalletTransactionPor.registerTransaction(walletTrasaction);
            throw new BusinessException("Payment failed");
        }

        walletTrasaction.setStatus(TransactionStatus.COMPLETED);
        outbountWalletTransactionPor.registerTransaction(walletTrasaction);

        return walletTrasaction;
    }

    private WalletTrasaction registerNewTransaction(WalletTrasaction pedenteTransaction) {
        return outbountWalletTransactionPor
                .registerTransaction(
                        new WalletTrasaction(pedenteTransaction.getWalletTransactionId(), pedenteTransaction.getUserId(),pedenteTransaction.getAmount(), LocalDateTime.now(), TransactionStatus.PENDING));
    }
}
