package com.retailer.rewards.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.retailer.rewards.entity.Transaction;
import com.retailer.rewards.repository.TransactionRepository;

@SpringBootTest
public class TransactionRepositoryTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void testFindAllByCustomerId() {
        Long customerId = 1L;
        List<Transaction> transactions = Arrays.asList(new Transaction());
        when(transactionRepository.findAllByCustomerId(customerId)).thenReturn(transactions);

        List<Transaction> result = transactionRepository.findAllByCustomerId(customerId);
        assertEquals(transactions, result);
    }

    @Test
    public void testFindAllByCustomerIdAndTransactionDateBetween() {
        Long customerId = 1L;
        Timestamp startTimestamp = new Timestamp(System.currentTimeMillis());
        Timestamp endTimestamp = new Timestamp(System.currentTimeMillis());
        List<Transaction> transactions = Arrays.asList(new Transaction());
        when(transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId, startTimestamp, endTimestamp)).thenReturn(transactions);

        List<Transaction> result = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId, startTimestamp, endTimestamp);
        assertEquals(transactions, result);
    }
}