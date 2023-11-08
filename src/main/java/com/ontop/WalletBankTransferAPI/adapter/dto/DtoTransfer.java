package com.ontop.WalletBankTransferAPI.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class DtoTransfer {
    private Integer userId;
    private BigDecimal amount;
}
