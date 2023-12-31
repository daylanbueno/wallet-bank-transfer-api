package com.ontop.WalletBankTransferAPI.adapter.repositories;


import com.ontop.WalletBankTransferAPI.adapter.entities.WalletTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransactionEntity, Integer> {
}
