package com.retailer.rewards.service;

import com.retailer.rewards.model.Rewards;



public interface RewardsService {
    Rewards getRewardsByCustomerId(Long customerId);
}
