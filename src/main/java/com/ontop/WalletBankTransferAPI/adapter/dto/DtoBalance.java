package com.ontop.WalletBankTransferAPI.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoBalance {

    @JsonProperty("balance")
    private BigDecimal total;

    @JsonProperty("user_id")
    private Integer userId;

}
