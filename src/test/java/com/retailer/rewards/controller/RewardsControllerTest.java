package com.retailer.rewards.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.retailer.rewards.model.RewardsPerMonth;
import com.retailer.rewards.model.TotalRewards;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.retailer.rewards.entity.Transaction;
import com.retailer.rewards.service.RewardsService;
import com.retailer.rewards.exception.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Collections;

@SpringBootTest
public class RewardsControllerTest {

    @Mock
    private RewardsService rewardsService;

    @InjectMocks
    private RewardsController rewardsController;

    @Test
    public void testCalculateRewardPerMonth() {
        int customerId = 1;
        int month = 1;
        int year = 2023;
        RewardsPerMonth rewards = new RewardsPerMonth(customerId, 100L, Arrays.asList(new Transaction()));
        when(rewardsService.calculateRewardsPerMonth(customerId, month, year)).thenReturn(rewards);

        RewardsPerMonth result = rewardsController.calculateRewardPerMonth(customerId, month, year);
        assertEquals(rewards, result);
    }

    @Test
    public void testCalculateRewardPerMonth_InvalidCustomer() {
        int customerId = -1;
        int month = 1;
        int year = 2023;
        when(rewardsService.calculateRewardsPerMonth(customerId, month, year)).thenThrow(new ResourceNotFoundException("Customer not found"));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            rewardsController.calculateRewardPerMonth(customerId, month, year);
        });

        assertEquals("Customer not found", exception.getMessage());
    }

    @Test
    public void testCalculateRewardPerMonth_InvalidMonth() {
        int customerId = 1;
        int month = 13; // Invalid month
        int year = 2023;
        when(rewardsService.calculateRewardsPerMonth(customerId, month, year)).thenThrow(new IllegalArgumentException("Invalid month"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardsController.calculateRewardPerMonth(customerId, month, year);
        });

        assertEquals("Invalid month", exception.getMessage());
    }

    @Test
    public void testGetCustomerTotalPoints() {
        int customerId = 1;
        TotalRewards rewards = new TotalRewards(customerId, Collections.singletonMap("total", 100L), Arrays.asList(new Transaction()));
        when(rewardsService.calculateTotalPoints(customerId)).thenReturn(rewards);

        TotalRewards result = rewardsController.getCustomerTotalPoints(customerId);
        assertEquals(rewards, result);
    }

    @Test
    public void testGetCustomerTotalPoints_InvalidCustomer() {
        int customerId = -1;
        when(rewardsService.calculateTotalPoints(customerId)).thenThrow(new ResourceNotFoundException("Customer not found"));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            rewardsController.getCustomerTotalPoints(customerId);
        });

        assertEquals("Customer not found", exception.getMessage());
    }
}
