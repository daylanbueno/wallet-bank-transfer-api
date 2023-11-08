package com.ontop.WalletBankTransferAPI.adapter.repositories;


import com.ontop.WalletBankTransferAPI.adapter.entities.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Integer> {
}
