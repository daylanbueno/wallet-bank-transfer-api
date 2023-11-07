package com.ontop.WalletBankTransferAPI.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DtoPeymentResponse {
    private RequestInfo requestInfo;
    private PaymentInfo paymentInfo;

    @Data
    public class RequestInfo {
        private String status;
    }

    @Data
    public class PaymentInfo {
        private double amount;
        private String id;
    }
}
