package com.ontop.WalletBankTransferAPI.adapter;

import com.ontop.WalletBankTransferAPI.adapter.dto.DtoPaymentRequest;
import com.ontop.WalletBankTransferAPI.adapter.dto.DtoPeymentResponse;
import com.ontop.WalletBankTransferAPI.adapter.dto.DtoTransaction;
import com.ontop.WalletBankTransferAPI.adapter.entities.WalletTrasactionEntity;
import com.ontop.WalletBankTransferAPI.adapter.external.OntopExternalService;
import com.ontop.WalletBankTransferAPI.adapter.repositories.WalletTransactionRepository;
import com.ontop.WalletBankTransferAPI.domain.Payment;
import com.ontop.WalletBankTransferAPI.domain.Wallet;
import com.ontop.WalletBankTransferAPI.domain.WalletTrasaction;
import com.ontop.WalletBankTransferAPI.domain.ports.OutbountWalletTransactionPor;
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

    public WalletTrasaction createExternalTransaction(WalletTrasaction walletTrasaction) {
        var dtoTransaction = modelMapper.map(walletTrasaction, DtoTransaction.class);
        DtoTransaction transaction = ontopExternalService.createTransaction(dtoTransaction);
        return modelMapper.map(transaction, WalletTrasaction.class);
    }

    @Override
    public WalletTrasaction registerTransaction(WalletTrasaction walletTrasaction) {
        WalletTrasactionEntity entity = modelMapper.map(walletTrasaction, WalletTrasactionEntity.class);
        WalletTrasactionEntity saved = walletTransactionRepository.save(entity);
        return modelMapper.map(saved, WalletTrasaction.class);
    }

    @Override
    public Wallet findBalance(Integer userId) {
        return modelMapper
                .map(ontopExternalService.findBananceUserId(userId), Wallet.class) ;
    }

    @Override
    public Payment registerPayment(Integer userId, BigDecimal amount) {
        var paymentRequest = mockPaymentRequest(amount);
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
                .amount(BigDecimal.valueOf(1000.0))
                .build();
    }

}
