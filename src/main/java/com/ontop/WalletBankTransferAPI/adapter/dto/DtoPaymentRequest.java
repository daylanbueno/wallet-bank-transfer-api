package com.ontop.WalletBankTransferAPI.adapter.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DtoPaymentRequest {
    private Source source;
    private Destination destination;
    private BigDecimal amount;

    @Builder
    @Data
    public static class Source {
        private String type;
        private SourceInformation sourceInformation;
        private Account account;

    }

    @Builder
    @Data
    public static class Destination {
        private String name;
        private Account account;
    }

    @Builder
    @Data
    public static class SourceInformation {
        private String name;
    }

    @Builder
    @Data
    public static class Account {
        private String accountNumber;
        private String currency;
        private String routingNumber;
    }
}
