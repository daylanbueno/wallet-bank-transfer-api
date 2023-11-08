package com.ontop.WalletBankTransferAPI.infra.exceptios;

public class ExternalErrorException extends RuntimeException {
    public ExternalErrorException(String msg)  {
        super(msg);
    }
}
