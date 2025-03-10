package com.retailer.rewards.model;

import com.retailer.rewards.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class TotalRewards {
    private long customerId;
    private Map<String,Long> totalRewards;
    private List<Transaction> transactions;

}
