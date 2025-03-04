The rest API to get customer rewards based on customer Id

Problem Statement:

A retailer offers a rewards program to its customers, awarding points based on each recorded purchase. 

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction.
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points). 

Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.

Endpoints:

    1) GET Endpoint:
        URL : http://localhost:8080/rewards/{customerId}
        Purpose : To calculate reward points of a customer based on previous transaction
        Response : Transaction in json format
            Example response: 
                {
                   "customerId": 101,
                   "lastMonthRewardPoints": 40,
                   "lastSecondMonthRewardPoints": 70,
                   "lastThirdMonthRewardPoints": 30,
                   "totalRewards": 140
               }
        Response : 200 OK (For Success)
                   400 BAD REQUEST (For missing/invalid customer id)
       
    2)POST Endpoint:
        URL : http://localhost:8080/rewards/transactions
        Purpose : To store data in database during each transaction
        Sample Request body : 
            {
                "transactionId" : "1004",
                "customerId": "101",
                "transactionAmount": 90",
                "transactionDate" : "2025-03-01"
            }
            Response : 200 OK (For Success)
        