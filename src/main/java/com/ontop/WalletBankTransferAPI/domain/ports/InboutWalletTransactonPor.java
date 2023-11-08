package com.ontop.WalletBankTransferAPI.domain.ports;

import com.ontop.WalletBankTransferAPI.domain.WalletTransaction;

import java.math.BigDecimal;

public interface InboutWalletTransactonPor {
     WalletTransaction execute(Integer userId, BigDecimal amount);

}
