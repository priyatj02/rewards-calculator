package com.retailer.rewards.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.retailer.rewards.exception.ResourceNotFoundException;
import com.retailer.rewards.service.RewardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.rewards.constants.Constants;
import com.retailer.rewards.entity.Transaction;
import com.retailer.rewards.model.Rewards;
import com.retailer.rewards.repository.TransactionRepository;

/**
 * Implementation of the RewardsService interface.
 */
@Service
@Slf4j
public class RewardsServiceImpl implements RewardsService {

	@Autowired
	TransactionRepository transactionRepository;

	/**
	 * Calculates rewards per month for a customer.
	 *
	 * @param customerId the customer ID
	 * @param month the month
	 * @param year the year
	 * @return the rewards
	 */
	@Override
	public Rewards calculateRewardsPerMonth(long customerId, int month, int year) {
		Timestamp startTimestamp = Timestamp.valueOf(LocalDateTime.of(year, month, 1, 0, 0));
		Timestamp endTimestamp = Timestamp.valueOf(LocalDateTime.of(year, month + 1, 1, 0, 0));
		List<Transaction> transactions = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId, startTimestamp, endTimestamp);
		if (transactions.isEmpty()) {
			log.warn("Customer ID or Transactions not found for customer ID: {}", customerId);
			throw new ResourceNotFoundException("Transactions not found for customer ID: " + customerId);
		}
		log.info("Customer ID or Transactions made by customer {} are : {}", customerId, transactions);
		long reward = transactions.stream().map(transaction -> calculateRewards(transaction)).mapToLong(r -> r).sum();
		Rewards rewards = new Rewards(customerId, reward, transactions);
		return rewards;
	}

	/**
	 * Calculates total points for a customer.
	 *
	 * @param customerId the customer ID
	 * @return the rewards
	 */
	@Override
	public Rewards calculateTotalPoints(long customerId) {
		List<Transaction> transactions = transactionRepository.findAllByCustomerId(customerId);
		if (transactions.isEmpty()) {
			log.warn("Customer ID or Transactions not found for customer ID: {}", customerId);
			throw new ResourceNotFoundException("Customer ID or Transactions not found for customer ID: " + customerId);
		}
		long reward = transactions.stream().map(transaction -> calculateRewards(transaction)).mapToLong(r -> r).sum();
		Rewards rewards = new Rewards(customerId, reward, transactions);
		return rewards;
	}

	/**
	 * Calculates rewards for a transaction.
	 *
	 * @param t the transaction
	 * @return the rewards
	 */
	private Long calculateRewards(Transaction t) {
		if (t.getTransactionAmount() > Constants.firstRewardLimit && t.getTransactionAmount() <= Constants.secondRewardLimit) {
			return Math.round(t.getTransactionAmount() - Constants.firstRewardLimit);
		} else if (t.getTransactionAmount() > Constants.secondRewardLimit) {
			return Math.round(t.getTransactionAmount() - Constants.secondRewardLimit) * 2
					+ (Constants.secondRewardLimit - Constants.firstRewardLimit);
		} else
			return 0L;
	}
}