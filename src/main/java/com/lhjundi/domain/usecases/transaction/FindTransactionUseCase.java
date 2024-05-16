package com.lhjundi.domain.usecases.transaction;

import com.lhjundi.domain.entities.transaction.Transaction;

import java.util.List;
import java.util.Optional;

public class FindTransactionUseCase {
    private TransactionDAO transactionDAO;

    public FindTransactionUseCase(TransactionDAO transactionDAO){
        this.transactionDAO = transactionDAO;
    }

    public Optional<Transaction> findOne(Integer id){
        if(id == null) throw new IllegalArgumentException("ID can not be null");
        return transactionDAO.searchOne(id);
    }

    public List<Transaction> searchAll(){
        return transactionDAO.searchAll();
    }
}
