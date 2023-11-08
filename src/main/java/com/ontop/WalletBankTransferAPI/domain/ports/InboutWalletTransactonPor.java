package com.ontop.WalletBankTransferAPI.domain.ports;

import com.ontop.WalletBankTransferAPI.domain.WalletTrasaction;

import java.math.BigDecimal;

public interface InboutWalletTransactonPor {
     WalletTrasaction execute(Integer userId, BigDecimal amount);

}
