package com.ontop.WalletBankTransferAPI.adapter.external;

import com.ontop.WalletBankTransferAPI.adapter.dto.DtoBalance;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${EXTERNAL_API_BASE_URL}", name = "ontop-external-service")
public interface OntopExternalService {
    @GetMapping(value = "/wallets/balance?user_id=1000")
    DtoBalance findBananceUserId(@RequestParam("user_id") Integer userId);

}
