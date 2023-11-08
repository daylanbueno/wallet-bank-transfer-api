package com.ontop.WalletBankTransferAPI.adapter.entities;

import com.ontop.WalletBankTransferAPI.domain.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity(name = "tb_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransaction {
    @Id
    @Column(name = "id")
    private Integer walletTransactionId;
    private Integer userId;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private String paymentId;
    private BigDecimal totalFee;

}
