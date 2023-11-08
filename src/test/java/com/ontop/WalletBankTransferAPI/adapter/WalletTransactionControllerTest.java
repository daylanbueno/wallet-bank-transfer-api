package com.ontop.WalletBankTransferAPI.adapter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontop.WalletBankTransferAPI.adapter.controller.WalletTransactionController;
import com.ontop.WalletBankTransferAPI.adapter.dto.DtoTransfer;
import com.ontop.WalletBankTransferAPI.domain.WalletTransactionDomain;
import com.ontop.WalletBankTransferAPI.domain.services.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WalletTransactionController.class)
public class WalletTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WalletService walletService;

    @Test
    public void shouldExecuteTransferenceCuccessfully() throws Exception {

        DtoTransfer dtoTransfer = DtoTransfer.builder().userId(1000).amount(BigDecimal.valueOf(2000)).build();

        Mockito.when(walletService.execute(dtoTransfer.getUserId(), dtoTransfer.getAmount()))
                .thenReturn(new WalletTransactionDomain());

        mockMvc.perform(MockMvcRequestBuilders.post("/wallets/wallet-to-bank/transfer")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dtoTransfer)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
