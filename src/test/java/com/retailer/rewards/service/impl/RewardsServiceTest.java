package com.retailer.rewards.service.impl;

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
import com.retailer.rewards.model.Rewards;
import com.retailer.rewards.repository.TransactionRepository;
import com.retailer.rewards.service.impl.RewardsServiceImpl;

@SpringBootTest
public class RewardsServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardsServiceImpl rewardsService;

    @Test
    public void testCalculateRewardsPerMonth() {
        Long customerId = 1L;
        int month = 1;
        int year = 2023;
        Timestamp startTimestamp = Timestamp.valueOf("2023-01-01 00:00:00");
        Timestamp endTimestamp = Timestamp.valueOf("2023-02-01 00:00:00");
        List<Transaction> transactions = Arrays.asList(new Transaction());
        when(transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId, startTimestamp, endTimestamp)).thenReturn(transactions);

        Rewards rewards = rewardsService.calculateRewardsPerMonth(customerId, month, year);
        assertNotNull(rewards);
    }

    @Test
    public void testCalculateTotalPoints() {
        Long customerId = 1L;
        List<Transaction> transactions = Arrays.asList(new Transaction());
        when(transactionRepository.findAllByCustomerId(customerId)).thenReturn(transactions);

        Rewards rewards = rewardsService.calculateTotalPoints(customerId);
        assertNotNull(rewards);
    }
}