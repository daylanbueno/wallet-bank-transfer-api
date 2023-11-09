package com.ontop.WalletBankTransferAPI.adapter.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DtoPaymentResponse {
    private RequestInfo requestInfo;
    private PaymentInfo paymentInfo;

    @Data
    public class RequestInfo {
        private String status;
    }

    @Data
    public class PaymentInfo {
        private BigDecimal amount;
        private String id;
    }
}
