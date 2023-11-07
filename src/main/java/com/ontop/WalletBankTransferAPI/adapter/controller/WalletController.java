package com.ontop.WalletBankTransferAPI.adapter.controller;


import com.ontop.WalletBankTransferAPI.adapter.dto.DtoBalance;
import com.ontop.WalletBankTransferAPI.adapter.external.OntopExternalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wallets")
@AllArgsConstructor
public class WalletController {

    private final OntopExternalService ontopExternalService;

    @GetMapping("/balance")
    private DtoBalance findBalanceByUserId(@RequestParam Integer userId) {
        return ontopExternalService.findBananceUserId(userId);
    }
}
