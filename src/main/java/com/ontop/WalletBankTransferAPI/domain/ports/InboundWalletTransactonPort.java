package com.ontop.WalletBankTransferAPI.domain.ports;

import com.ontop.WalletBankTransferAPI.domain.WalletTransactionDomain;

import java.math.BigDecimal;

public interface InboundWalletTransactonPort {
     WalletTransactionDomain execute(Integer userId, BigDecimal amount);

}
