package com.ontop.WalletBankTransferAPI.adapter.domain.service;

import com.ontop.WalletBankTransferAPI.domain.Payment;
import com.ontop.WalletBankTransferAPI.domain.Wallet;
import com.ontop.WalletBankTransferAPI.domain.WalletTransactionDomain;
import com.ontop.WalletBankTransferAPI.domain.enums.TransactionStatus;
import com.ontop.WalletBankTransferAPI.domain.exeptions.BusinessException;
import com.ontop.WalletBankTransferAPI.domain.ports.OutbountWalletTransactionPor;
import com.ontop.WalletBankTransferAPI.domain.services.WalletService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class WalletServiceTest {

    private WalletService walletService;

    @MockBean
    private OutbountWalletTransactionPor outbountWalletTransactionPor;
    @BeforeEach
    public void setUp() {
        this.walletService = new  WalletService(outbountWalletTransactionPor);
    }

    @Test
    @DisplayName("should fail when amount is null")
    public void shouldFailWhenAmountIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            walletService.execute(1000, null);
        });
    }

    @Test
    @DisplayName("should fail when userId is null")
    public void shouldFailWhenUserIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            walletService.execute(null, BigDecimal.valueOf(1000));
        });
    }

    @Test
    @DisplayName("should fail when balance is not enough")
    public void shouldFailWhenBalanceIsNotEnough() {

        Wallet wallet = new Wallet(BigDecimal.valueOf( 500), 1000);

        Mockito.when(outbountWalletTransactionPor.findBalance(Mockito.anyInt())).thenReturn(wallet);

        assertThrows(BusinessException.class, () -> {
            walletService.execute(1000, BigDecimal.valueOf(1000));
        });
    }

    @Test
    @DisplayName("should fail when payment fail")
    public void shouldFailWhenPaymentFail() {

        Wallet wallet = new Wallet(BigDecimal.valueOf( 2000), 1000);

        WalletTransactionDomain externalWallet =
                new WalletTransactionDomain( 2019,1000,BigDecimal.valueOf( 2000), null , null);


        WalletTransactionDomain internalwalletTransactionDomain =
                new WalletTransactionDomain( 2019,1000,BigDecimal.valueOf( 2000), LocalDateTime.now(), TransactionStatus.PENDING);

        Mockito.when(outbountWalletTransactionPor.findBalance(Mockito.anyInt())).thenReturn(wallet);

        Mockito.when(outbountWalletTransactionPor.createExternalTransaction(Mockito.any())).thenReturn(externalWallet);

        Mockito.when(outbountWalletTransactionPor.registerTransaction(Mockito.any())).thenReturn(internalwalletTransactionDomain);

        Mockito.when(outbountWalletTransactionPor.registerPayment(Mockito.any(), Mockito.any())).thenReturn(null);

        assertThrows(BusinessException.class, () -> {
            walletService.execute(1000, BigDecimal.valueOf(1000));
        });
    }

    @Test
    @DisplayName("should complete transaction succefully")
    public void shouldCompleTransactionSuccefully() {

        Wallet wallet = new Wallet(BigDecimal.valueOf( 2000), 1000);

        WalletTransactionDomain externalWallet =
                new WalletTransactionDomain( 2019,1000,BigDecimal.valueOf( 2000), null , null);


        WalletTransactionDomain internalwalletTransactionDomain =
                new WalletTransactionDomain( 2019,1000,BigDecimal.valueOf( 2000), LocalDateTime.now(), TransactionStatus.PENDING);

        Payment payment = new Payment("70cfe468-91b9-4e04-8910-5e8257dfadfa", "Processing",BigDecimal.valueOf(2000));

        Mockito.when(outbountWalletTransactionPor.findBalance(Mockito.anyInt())).thenReturn(wallet);

        Mockito.when(outbountWalletTransactionPor.createExternalTransaction(Mockito.any())).thenReturn(externalWallet);

        Mockito.when(outbountWalletTransactionPor.registerTransaction(Mockito.any())).thenReturn(internalwalletTransactionDomain);

        Mockito.when(outbountWalletTransactionPor.registerPayment(Mockito.any(), Mockito.any())).thenReturn(payment);

        WalletTransactionDomain transaction = walletService.execute(1000, BigDecimal.valueOf(2000));

        Assertions.assertThat(transaction.getAmount()).isEqualTo(BigDecimal.valueOf(1800));
        Assertions.assertThat(transaction.getFeeAmount()).isEqualTo(BigDecimal.valueOf(200));
        Assertions.assertThat(transaction.getStatus()).isEqualTo(TransactionStatus.COMPLETED);
        Assertions.assertThat(transaction.getPaymentId()).isNotNull();

    }

}
