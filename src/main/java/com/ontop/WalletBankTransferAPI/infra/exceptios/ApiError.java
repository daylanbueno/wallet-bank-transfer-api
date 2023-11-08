package com.ontop.WalletBankTransferAPI.infra.exceptios;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
    private String message;
}

