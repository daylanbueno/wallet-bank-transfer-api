package com.ontop.WalletBankTransferAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WalletBankTransferApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletBankTransferApiApplication.class, args);
	}

}
