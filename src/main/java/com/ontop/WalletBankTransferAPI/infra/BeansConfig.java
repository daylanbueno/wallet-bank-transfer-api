package com.ontop.WalletBankTransferAPI.infra;

import com.ontop.WalletBankTransferAPI.domain.ports.OutboundWalletTransactionPort;
import com.ontop.WalletBankTransferAPI.domain.services.WalletService;
import com.ontop.WalletBankTransferAPI.infra.exceptios.RetrieveMessageErrorDecoder;
import feign.codec.ErrorDecoder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public WalletService walletService(OutboundWalletTransactionPort outbountWalletTransactionPor) {
        return new WalletService(outbountWalletTransactionPor);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new RetrieveMessageErrorDecoder();
    }

}
