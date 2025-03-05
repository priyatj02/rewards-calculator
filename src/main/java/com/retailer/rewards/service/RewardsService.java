package com.retailer.rewards.service;

import com.retailer.rewards.model.Rewards;

/**
 * Service interface for calculating rewards.
 */
public interface RewardsService {

    /**
     * Calculates rewards per month for a customer.
     *
     * @param customerId the customer ID
     * @param month the month
     * @param year the year
     * @return the rewards
     */
    Rewards calculateRewardsPerMonth(long customerId, int month, int year);

    /**
     * Calculates total points for a customer.
     *
     * @param customerId the customer ID
     * @return the rewards
     */
    Rewards calculateTotalPoints(long customerId);
}
