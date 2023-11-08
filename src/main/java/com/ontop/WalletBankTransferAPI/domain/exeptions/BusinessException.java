package com.ontop.WalletBankTransferAPI.domain.exeptions;

public class BusinessException extends  RuntimeException {
    public BusinessException(String msg)  {
        super(msg);
    }
}
