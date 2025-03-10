package com.retailer.rewards.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.retailer.rewards.entity.Transaction;
import com.retailer.rewards.exception.ResourceNotFoundException;
import com.retailer.rewards.model.RewardsPerMonth;
import com.retailer.rewards.model.TotalRewards;
import com.retailer.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class RewardsServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardsServiceImpl rewardsService;

    private List<Transaction> transactions;
    private List<Transaction> perMonthTransactions;

    @BeforeEach
    public void setUp() {
        transactions = Arrays.asList(
                new Transaction(1L, 1L, Timestamp.valueOf("2025-02-15 00:00:00"), 130),
                new Transaction(2L, 1L, Timestamp.valueOf("2025-01-15 00:00:00"), 80)
        );
        perMonthTransactions = Arrays.asList(
                new Transaction(1L, 1L, Timestamp.valueOf("2025-02-15 00:00:00"), 130)
        );
    }

    @Test
    public void testCalculateRewardsPerMonth_ValidCustomer() {
        long customerId = 1L;
        int month = 2;
        int year = 2025;
        when(transactionRepository.findAllByCustomerIdAndTransactionDateBetween(anyLong(), any(Timestamp.class), any(Timestamp.class)))
                .thenReturn(perMonthTransactions);

        RewardsPerMonth rewards = rewardsService.calculateRewardsPerMonth(customerId, month, year);
        assertNotNull(rewards);
        assertEquals(1L, rewards.getCustomerId());
        assertEquals(110L, rewards.getTotalRewards());
    }

    @Test
    public void testCalculateRewardsPerMonth_InvalidCustomer() {
        long customerId = -1L;
        int month = 1;
        int year = 2023;
        when(transactionRepository.findAllByCustomerIdAndTransactionDateBetween(anyLong(), any(Timestamp.class), any(Timestamp.class)))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            rewardsService.calculateRewardsPerMonth(customerId, month, year);
        });
    }

    @Test
    public void testCalculateRewardsPerMonth_InvalidMonth() {
        long customerId = 1L;
        int month = 13; // Invalid month
        int year = 2023;

        assertThrows(IllegalArgumentException.class, () -> {
            rewardsService.calculateRewardsPerMonth(customerId, month, year);
        });
    }

    @Test
    public void testCalculateTotalPoints_ValidCustomer() {
        long customerId = 1L;
        when(transactionRepository.findAllByCustomerId(anyLong())).thenReturn(transactions);

        TotalRewards rewards = rewardsService.calculateTotalPoints(customerId);
        assertNotNull(rewards);
        assertEquals(1L, rewards.getCustomerId());
        assertEquals(140L, rewards.getTotalRewards().get("Total_Rewards"));
        assertEquals(2, rewards.getTransactions().size());
        assertTrue(rewards.getTotalRewards().containsKey("JANUARY"));
        assertTrue(rewards.getTotalRewards().containsKey("FEBRUARY"));
        assertEquals(30L, rewards.getTotalRewards().get("JANUARY"));
        assertEquals(110L, rewards.getTotalRewards().get("FEBRUARY"));
    }

    @Test
    public void testCalculateTotalPoints_InvalidCustomer() {
        long customerId = -1L;
        when(transactionRepository.findAllByCustomerId(anyLong())).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            rewardsService.calculateTotalPoints(customerId);
        });
    }

    @Test
    public void testCalculateRewardsPerMonth_NoTransactions() {
        long customerId = 1L;
        int month = 1;
        int year = 2023;
        when(transactionRepository.findAllByCustomerIdAndTransactionDateBetween(anyLong(), any(Timestamp.class), any(Timestamp.class)))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            rewardsService.calculateRewardsPerMonth(customerId, month, year);
        });
    }

    @Test
    public void testCalculateTotalPoints_NoTransactions() {
        long customerId = 1L;
        when(transactionRepository.findAllByCustomerId(anyLong())).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            rewardsService.calculateTotalPoints(customerId);
        });
    }

    @Test
    public void testCalculateRewardsPerMonth_NullTransactions() {
        long customerId = 1L;
        int month = 1;
        int year = 2023;
        when(transactionRepository.findAllByCustomerIdAndTransactionDateBetween(anyLong(), any(Timestamp.class), any(Timestamp.class)))
                .thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            rewardsService.calculateRewardsPerMonth(customerId, month, year);
        });
    }

    @Test
    public void testCalculateTotalPoints_NullTransactions() {
        long customerId = 1L;
        when(transactionRepository.findAllByCustomerId(anyLong())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            rewardsService.calculateTotalPoints(customerId);
        });
    }
}