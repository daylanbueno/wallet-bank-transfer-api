package com.ontop.WalletBankTransferAPI.domain.ports;

import com.ontop.WalletBankTransferAPI.domain.Payment;
import com.ontop.WalletBankTransferAPI.domain.Wallet;
import com.ontop.WalletBankTransferAPI.domain.WalletTrasaction;

import java.math.BigDecimal;

public interface OutbountWalletTransactionPor {
    WalletTrasaction createExternalTransaction(WalletTrasaction walletTrasaction);

    WalletTrasaction registerTransaction(WalletTrasaction walletTrasaction);

    Wallet findBalance(Integer userId);

    Payment registerPayment (Integer userId, BigDecimal amount);


}
