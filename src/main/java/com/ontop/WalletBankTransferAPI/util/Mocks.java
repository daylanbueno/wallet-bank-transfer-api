package com.ontop.WalletBankTransferAPI.util;

import com.ontop.WalletBankTransferAPI.adapter.dto.DtoPaymentRequest;

import java.math.BigDecimal;

public class Mocks {
    public static DtoPaymentRequest createMock(Integer userId, BigDecimal amount) {
        if (userId == 5001) {
            return mockPayment(amount, "JAMES FAILED");
        }

        if (userId == 5002) {
            return mockPayment(amount, "JAMES TIMEOUT");
        }
        return mockPayment(amount,"TONY STARK");
    }

    private static DtoPaymentRequest mockPayment(BigDecimal amount, String name) {
        return  DtoPaymentRequest.builder()
                .source(DtoPaymentRequest.Source.builder()
                        .type("COMPANY")
                        .sourceInformation(DtoPaymentRequest.SourceInformation.builder()
                                .name("ONTOP INC")
                                .build())
                        .account(DtoPaymentRequest.Account.builder()
                                .accountNumber("0245253419")
                                .currency("USD")
                                .routingNumber("028444018")
                                .build())
                        .build())
                .destination(DtoPaymentRequest.Destination.builder()
                        .name(name)
                        .account(DtoPaymentRequest.Account.builder()
                                .accountNumber("1885226711")
                                .currency("USD")
                                .routingNumber("211927207")
                                .build())
                        .build())
                .amount(amount)
                .build();
    }

}
