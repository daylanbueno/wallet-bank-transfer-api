package com.ontop.WalletBankTransferAPI.adapter.external;

import com.ontop.WalletBankTransferAPI.adapter.dto.DtoWallet;
import com.ontop.WalletBankTransferAPI.adapter.dto.DtoPaymentResponse;
import com.ontop.WalletBankTransferAPI.adapter.dto.DtoTransaction;
import com.ontop.WalletBankTransferAPI.adapter.dto.DtoPaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${EXTERNAL_API_BASE_URL}", name = "ontop-external-service")
public interface OntopExternalService {
    @GetMapping(value = "/wallets/balance")
    DtoWallet findBananceUserId(@RequestParam("user_id") Integer userId);

    @PostMapping(value = "/wallets/transactions")
    DtoTransaction createTransaction(@RequestBody DtoTransaction dtoTransaction);

    @PostMapping(value = "/api/v1/payments")
    DtoPaymentResponse createPayment(@RequestBody DtoPaymentRequest externalTransactionDTO);
}
