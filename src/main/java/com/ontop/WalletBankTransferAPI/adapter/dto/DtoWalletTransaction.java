package com.ontop.WalletBankTransferAPI.adapter.dto;

import com.ontop.WalletBankTransferAPI.domain.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoWalletTransaction {
    private Integer walletTransactionId;
    private Integer userId;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private TransactionStatus status;
    private BigDecimal feeAmount;
}
