package com.ontop.WalletBankTransferAPI.adapter.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DtoPaymentoRequest {
    private Source source;
    private Destination destination;
    private double amount;

    @Data
    @AllArgsConstructor
    public class Source {
        private String type;
        private SourceInformation sourceInformation;
        private Account account;
    }

    @Data
    @AllArgsConstructor
    public class Destination {
        private String name;
        private Account account;
    }

    @Data
    @AllArgsConstructor
    public class SourceInformation {
        private String name;
    }

    @Data
    @AllArgsConstructor
    public class Account {
        private String accountNumber;
        private String currency;
        private String routingNumber;
    }
}
