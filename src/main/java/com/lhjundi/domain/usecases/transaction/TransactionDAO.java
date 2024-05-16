package com.lhjundi.domain.usecases.transaction;

import com.lhjundi.domain.entities.transaction.Transaction;
import com.lhjundi.domain.usecases.utils.DAO;

import java.util.Optional;

public interface TransactionDAO extends DAO<Transaction, Integer> {
    Optional<Transaction> searchTransactionByBookId(Integer id);
}