package com.ontop.WalletBankTransferAPI.domain.ports;

import com.ontop.WalletBankTransferAPI.domain.Payment;
import com.ontop.WalletBankTransferAPI.domain.Wallet;
import com.ontop.WalletBankTransferAPI.domain.WalletTransactionDomain;

import java.math.BigDecimal;
import java.util.Optional;

public interface OutboundWalletTransactionPort {
    WalletTransactionDomain createExternalTransaction(WalletTransactionDomain walletTrasaction);

    WalletTransactionDomain registerTransaction(WalletTransactionDomain walletTrasaction);

    Wallet findBalance(Integer userId);

    Optional<Payment> registerPayment (Integer userId, BigDecimal amount);


}
