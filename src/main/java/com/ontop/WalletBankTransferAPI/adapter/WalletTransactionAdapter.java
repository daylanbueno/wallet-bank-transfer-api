package com.ontop.WalletBankTransferAPI.adapter;

import com.ontop.WalletBankTransferAPI.adapter.dto.DtoPaymentRequest;
import com.ontop.WalletBankTransferAPI.adapter.dto.DtoPeymentResponse;
import com.ontop.WalletBankTransferAPI.adapter.dto.DtoTransaction;
import com.ontop.WalletBankTransferAPI.adapter.entities.WalletTransaction;
import com.ontop.WalletBankTransferAPI.adapter.external.OntopExternalService;
import com.ontop.WalletBankTransferAPI.adapter.repositories.WalletTransactionRepository;
import com.ontop.WalletBankTransferAPI.domain.Payment;
import com.ontop.WalletBankTransferAPI.domain.Wallet;
import com.ontop.WalletBankTransferAPI.domain.ports.OutbountWalletTransactionPor;
import com.ontop.WalletBankTransferAPI.util.Mocks;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class WalletTransactionAdapter implements OutbountWalletTransactionPor {


    private final WalletTransactionRepository walletTransactionRepository;

    private final OntopExternalService ontopExternalService;

    private final ModelMapper  modelMapper;

    public com.ontop.WalletBankTransferAPI.domain.WalletTransaction createExternalTransaction(com.ontop.WalletBankTransferAPI.domain.WalletTransaction walletTrasaction) {
        var dtoTransaction = modelMapper.map(walletTrasaction, DtoTransaction.class);
        DtoTransaction transaction = ontopExternalService.createTransaction(dtoTransaction);
        return modelMapper.map(transaction, com.ontop.WalletBankTransferAPI.domain.WalletTransaction.class);
    }

    @Override
    public com.ontop.WalletBankTransferAPI.domain.WalletTransaction registerTransaction(com.ontop.WalletBankTransferAPI.domain.WalletTransaction walletTrasaction) {
        WalletTransaction entity = modelMapper.map(walletTrasaction, WalletTransaction.class);
        WalletTransaction saved = walletTransactionRepository.save(entity);
        return modelMapper.map(saved, com.ontop.WalletBankTransferAPI.domain.WalletTransaction.class);
    }

    @Override
    public Wallet findBalance(Integer userId) {
        return modelMapper
                .map(ontopExternalService.findBananceUserId(userId), Wallet.class) ;
    }

    @Override
    public Payment registerPayment(Integer userId, BigDecimal amount) {
        var paymentRequest = Mocks.createMock(userId,amount);
        try {
            DtoPeymentResponse dtoPeymentResponse = ontopExternalService.creastePayment(paymentRequest);
            return new Payment(dtoPeymentResponse.getPaymentInfo().getId(),
                    dtoPeymentResponse.getRequestInfo().getStatus(),
                    dtoPeymentResponse.getPaymentInfo().getAmount());

        } catch (Exception exception) {
            return null;
        }
    }
    private DtoPaymentRequest mockPaymentRequest(BigDecimal amount) {
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
                        .name("TONY STARK")
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
