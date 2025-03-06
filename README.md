# Rewards Service API

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
  - [Calculate Rewards Per Month](#calculate-rewards-per-month)
  - [Get Customer Total Points](#get-customer-total-points)
  - [Add Transaction](#add-transaction)
- [Running Tests](#running-tests)
- [License](#license)
- [Contact](#contact)

## Overview
A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction. (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.

## Features
- Calculate rewards per month for a customer
- Calculate total points for a customer
- Add new transactions
- Global exception handling

## Technologies Used
- Java
- Spring Boot
- Maven
- Lombok
- JUnit
- Mockito

## Getting Started

### Prerequisites
- Java 8 or higher
- Maven 3.6.0 or higher

### Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/priyatj02/rewards-calculator.git
    ```
2. Navigate to the project directory:
    ```sh
    cd rewards-calculator
    ```
3. Build the project:
    ```sh
    mvn clean install
    ```

### Running the Application
1. Run the application:
    ```sh
    mvn spring-boot:run
    ```
2. The application will start on `http://localhost:8080`.

## API Endpoints

### Calculate Rewards Per Month
- **URL:** `/CalculateReward/perMonth`
- **Method:** `GET`
- **Parameters:**
  - `customerId` (required)
  - `month` (required)
  - `year` (required)
- **Response:** `Rewards` object

Example:
**Endpoint:** `GET /calculateRewardPerMonth`

**Request:**
```json
{
  "customerId": 1,
  "month": 1,
  "year": 2025
}
```
**Response:**
```json
{
  "customerId": 1,
  "totalRewards": 100,
  "transactions": [
    {
      "transactionId": 1,
      "customerId": 1,
      "transactionDate": "2025-02-15T00:00:00",
      "transactionAmount": 130
    },
    {
      "transactionId": 2,
      "customerId": 1,
      "transactionDate": "2025-01-15T00:00:00",
      "transactionAmount": 80
    }
  ]
}
```

### Get Customer Total Points
- **URL:** `/CalculateReward/totalPoints`
- **Method:** `GET`
- **Parameters:**
  - `customerId` (required)
- **Response:** `Rewards` object

Endpoint: GET /customerTotalPoints

**Request:**
```json
    {
      "customerId": 1
    }
```
**Response:**
```json
{
    "customerId": 1,
    "totalRewards": 140,
    "transactions": [
      {
        "transactionId": 1,
        "customerId": 1,
        "transactionDate": "2025-02-15T00:00:00",
        "transactionAmount": 130
      },
      {
        "transactionId": 2,
        "customerId": 1,
        "transactionDate": "2025-01-15T00:00:00",
        "transactionAmount": 80
      }
    ]
}
```

## Running Tests
To run the tests, use the following command:
```sh
mvn test
```
