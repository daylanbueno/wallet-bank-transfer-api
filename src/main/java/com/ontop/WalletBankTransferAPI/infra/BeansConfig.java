package com.ontop.WalletBankTransferAPI.infra;

import com.ontop.WalletBankTransferAPI.domain.ports.OutbountWalletTransactionPor;
import com.ontop.WalletBankTransferAPI.domain.services.WalletService;
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
    public WalletService walletService(OutbountWalletTransactionPor outbountWalletTransactionPor) {
        return new WalletService(outbountWalletTransactionPor);
    }

}
