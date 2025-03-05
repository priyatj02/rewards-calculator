package com.retailer.rewards.model;

import com.retailer.rewards.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Rewards {
    private long customerId;
    private long totalRewards;
    private List<Transaction> transactions;

}
