package com.retailer.rewards.controller;

import com.retailer.rewards.entity.Transaction;
import com.retailer.rewards.exception.ResourceNotFoundException;
import com.retailer.rewards.repository.TransactionRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.retailer.rewards.model.Rewards;
import com.retailer.rewards.service.RewardsService;

/**
 * Controller for handling reward calculations.
 */
@RestController
@Slf4j
@RequestMapping("/CalculateReward")
public class RewardsController {

    @Autowired
    RewardsService rewardsService;

    @Autowired
    TransactionRepository transactionRepository;

    /**
     * Calculates reward per month for a customer.
     *
     * @param customerId the customer ID
     * @param month the month
     * @param year the year
     * @return the rewards
     */
    @GetMapping(value = "/perMonth")
    public Rewards calculateRewardPerMonth(@RequestParam int customerId, @RequestParam int month, @RequestParam int year) {
        log.info("Calculating rewards for customer ID: {}, month: {}, year: {}", customerId, month, year);
        Rewards rewards = rewardsService.calculateRewardsPerMonth(customerId, month, year);
        log.info("Rewards calculated successfully for customer ID: {}", customerId);
        return rewards;
    }

    /**
     * Gets total points for a customer.
     *
     * @param customerId the customer ID
     * @return the rewards
     */
    @GetMapping(value = "/customerTotalPoints")
    public Rewards getCustomerTotalPoints(@RequestParam int customerId) {
        log.info("Fetching total points for customer ID: {}", customerId);
        Rewards rewards = rewardsService.calculateTotalPoints(customerId);
        log.info("Total points fetched successfully for customer ID: {}", customerId);
        return rewards;
    }

    /**
     * Adds a new transaction.
     *
     * @param transaction the transaction
     * @return the response entity
     */
    @PostMapping(value = "/transactions")
    public ResponseEntity<Object> addTransaction(@Valid @RequestBody Transaction transaction) {
        log.info("Adding new transaction for customer ID: {}", transaction.getCustomerId());
        Transaction savedTransaction = transactionRepository.save(new Transaction(transaction.getTransactionId(), transaction.getCustomerId(), transaction.getTransactionDate(), transaction.getTransactionAmount()));
        if (savedTransaction == null) {
            log.error("Transaction could not be saved for customer ID: {}", transaction.getCustomerId());
            throw new ResourceNotFoundException("Transaction could not be saved");
        }
        log.info("Transaction added successfully for customer ID: {}", transaction.getCustomerId());
        return ResponseEntity.ok().build();
    }
}