package com.retailer.rewards.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {
    @Id
    private Long transactionId;

    @NotNull
    private Long customerId;

    @NotNull
    private Timestamp transactionDate;

    @NotNull
    private double transactionAmount;
}
