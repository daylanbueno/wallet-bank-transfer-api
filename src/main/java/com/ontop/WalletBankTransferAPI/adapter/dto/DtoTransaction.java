package com.ontop.WalletBankTransferAPI.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoTransaction {

    @JsonProperty("wallet_transaction_id")
    private Integer walletTransactionId;

    private BigDecimal amount;

    @JsonProperty("user_id")
    private Integer userId;

}
