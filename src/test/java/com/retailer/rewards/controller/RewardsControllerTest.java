package com.retailer.rewards.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.retailer.rewards.entity.Transaction;
import com.retailer.rewards.model.Rewards;
import com.retailer.rewards.repository.TransactionRepository;
import com.retailer.rewards.service.RewardsService;

import java.util.Arrays;

@SpringBootTest
public class RewardsControllerTest {

    @Mock
    private RewardsService rewardsService;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardsController rewardsController;

    @Test
    public void testCalculateRewardPerMonth() {
        int customerId = 1;
        int month = 1;
        int year = 2023;
        Rewards rewards = new Rewards(customerId, 100L, Arrays.asList(new Transaction()));
        when(rewardsService.calculateRewardsPerMonth(customerId, month, year)).thenReturn(rewards);

        Rewards result = rewardsController.calculateRewardPerMonth(customerId, month, year);
        assertEquals(rewards, result);
    }

    @Test
    public void testGetCustomerTotalPoints() {
        int customerId = 1;
        Rewards rewards = new Rewards(customerId, 100L, Arrays.asList(new Transaction()));
        when(rewardsService.calculateTotalPoints(customerId)).thenReturn(rewards);

        Rewards result = rewardsController.getCustomerTotalPoints(customerId);
        assertEquals(rewards, result);
    }

    @Test
    public void testAddTransaction() {
        Transaction transaction = new Transaction();
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        ResponseEntity<Object> result = rewardsController.addTransaction(transaction);
        assertNotNull(result);
        assertEquals(ResponseEntity.ok().build(), result);
    }
}