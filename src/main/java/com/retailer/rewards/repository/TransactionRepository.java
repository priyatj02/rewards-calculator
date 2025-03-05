package com.retailer.rewards.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.retailer.rewards.entity.Transaction;

/**
 * Repository interface for managing transactions.
 */
@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Finds all transactions by customer ID.
     *
     * @param customerId the customer ID
     * @return the list of transactions
     */
    List<Transaction> findAllByCustomerId(Long customerId);

    /**
     * Finds all transactions by customer ID and transaction date between the specified timestamps.
     *
     * @param customerId the customer ID
     * @param startTimestamp the start timestamp
     * @param endTimestamp the end timestamp
     * @return the list of transactions
     */
    List<Transaction> findAllByCustomerIdAndTransactionDateBetween(long customerId, Timestamp startTimestamp, Timestamp endTimestamp);
}
