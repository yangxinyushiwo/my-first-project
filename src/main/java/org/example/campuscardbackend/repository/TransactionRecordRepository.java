package org.example.campuscardbackend.repository;

import org.example.campuscardbackend.entity.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, String> {
    List<TransactionRecord> findByCardIdOrderByCreatedAtDesc(String cardId);
}