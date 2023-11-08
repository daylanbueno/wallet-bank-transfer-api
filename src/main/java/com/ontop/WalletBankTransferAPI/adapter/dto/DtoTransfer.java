package com.ontop.WalletBankTransferAPI.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DtoTransfer {
    private Integer userId;
    private BigDecimal amount;
}
