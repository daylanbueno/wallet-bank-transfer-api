package com.ontop.WalletBankTransferAPI.domain.ports;

import com.ontop.WalletBankTransferAPI.domain.Payment;
import com.ontop.WalletBankTransferAPI.domain.Wallet;
import com.ontop.WalletBankTransferAPI.domain.WalletTransaction;

import java.math.BigDecimal;

public interface OutbountWalletTransactionPor {
    WalletTransaction createExternalTransaction(WalletTransaction walletTrasaction);

    WalletTransaction registerTransaction(WalletTransaction walletTrasaction);

    Wallet findBalance(Integer userId);

    Payment registerPayment (Integer userId, BigDecimal amount);


}
