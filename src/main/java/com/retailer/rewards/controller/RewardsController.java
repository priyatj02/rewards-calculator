package com.retailer.rewards.controller;

import com.retailer.rewards.entity.Transaction;
import com.retailer.rewards.exception.ServiceException;
import com.retailer.rewards.repository.TransactionRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.retailer.rewards.model.Rewards;
import com.retailer.rewards.service.RewardsService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/rewards")
public class RewardsController {

    @Autowired
    RewardsService rewardsService;

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping(value = "/{customerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rewards> getRewardsByCustomerId(@PathVariable("customerId") Long customerId) {
        List<Transaction> customer = transactionRepository.findAllByCustomerId(customerId);
        log.info("Transactions made by customer {} are : {}", customerId, customer);
        try {
            if (customer.isEmpty()) {
                throw new ServiceException("Invalid / Missing customer Id ");
            } else {
                Rewards customerRewards = rewardsService.getRewardsByCustomerId(customerId);
                return new ResponseEntity<>(customerRewards, HttpStatus.OK);
            }
        } catch (ServiceException e) {
            log.error("Exception occurred : {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/transactions")
    public ResponseEntity<Object> addTransaction(@Valid @RequestBody Transaction transaction) {

        this.transactionRepository.save(new Transaction(transaction.getTransactionId(), transaction.getCustomerId(), transaction.getTransactionDate(), transaction.getTransactionAmount()));

        return ResponseEntity.ok().build();
    }

}
