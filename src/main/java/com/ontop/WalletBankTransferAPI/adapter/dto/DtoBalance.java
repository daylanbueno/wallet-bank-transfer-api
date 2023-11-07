package com.ontop.WalletBankTransferAPI.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoBalance {

    @JsonProperty("balance")
    private BigDecimal total;

    private Integer user_id;

}
