package com.ontop.WalletBankTransferAPI.adapter.controller;


import com.ontop.WalletBankTransferAPI.adapter.dto.DtoTransfer;
import com.ontop.WalletBankTransferAPI.adapter.dto.DtoWallet;
import com.ontop.WalletBankTransferAPI.adapter.external.OntopExternalService;
import com.ontop.WalletBankTransferAPI.domain.WalletTrasaction;
import com.ontop.WalletBankTransferAPI.domain.services.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("wallets")
@AllArgsConstructor
public class WalletTransactionController {

    private final WalletService walletService;
    @PostMapping("/wallet-to-bank/transfer")
    private WalletTrasaction executeTransfer(@RequestBody DtoTransfer dtoTransfer) {
        return  walletService.execute(dtoTransfer.getUserId(), dtoTransfer.getAmount());
    }
}