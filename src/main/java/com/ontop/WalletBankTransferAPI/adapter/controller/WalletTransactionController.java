package com.ontop.WalletBankTransferAPI.adapter.controller;


import com.ontop.WalletBankTransferAPI.adapter.dto.DtoTransfer;
import com.ontop.WalletBankTransferAPI.adapter.dto.DtoWalletTransaction;
import com.ontop.WalletBankTransferAPI.domain.ports.InboundWalletTransactonPort;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wallets")
@AllArgsConstructor
public class WalletTransactionController {

    private final InboundWalletTransactonPort inboundWalletTransactonPort;

    private final ModelMapper modelMapper;
    @PostMapping("/wallet-to-bank/transfer")
    private DtoWalletTransaction executeTransfer(@RequestBody DtoTransfer dtoTransfer) {
        return  modelMapper
                .map(inboundWalletTransactonPort
                        .execute(dtoTransfer.getUserId(), dtoTransfer.getAmount()), DtoWalletTransaction.class);
    }
}
