package com.personalfinance.repository;

import com.personalfinance.entity.Transaction;
import com.personalfinance.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository {
    List<Transaction> findByUserAndIsDeletedFalse(User user);
    List<Transaction> findByUserAndIsDeletedFalseOrderByTransactionDateDesc(User user);
    Page<Transaction> findByUserAndIsDeletedFalse(User user, Pageable pageable);
    List<Transaction> findByUserAndTypeAndIsDeletedFalse(User user, Transaction.TransactionType type);
    List<Transaction> findByUserAndCategoryIdAndIsDeletedFalse(User user, Long categoryId);
    List<Transaction> findByUserAndTransactionDateBetweenAndIsDeletedFalse(User user, LocalDate startDate, LocalDate endDate);
}
