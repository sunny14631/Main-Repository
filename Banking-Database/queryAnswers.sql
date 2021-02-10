USE bank;
#1. How many customers have a checking account balance over $10,000?
SELECT DISTINCT COUNT(cust_id) 
FROM Checking_Account
WHERE balance > 10000;

#2. How many customers have both a checking account and a savings account?
SELECT DISTINCT COUNT(Customer.cust_id)
FROM Checking_Account, Savings_Account, Customer
WHERE Checking_Account.cust_id = Customer.cust_id
AND Savings_Account.cust_id = Customer.cust_id
AND Checking_Account.acct_num IS NOT NULL
AND Savings_Account.acct_num IS NOT NULL;

#3. Which customers have a loan whose principal amount exceeds their checking account balance?
SELECT Customer.cust_name
FROM Customer, Loan, Checking_Account
WHERE Customer.cust_id = Loan.cust_id
AND Checking_Account.cust_id = Customer.cust_id
AND Loan.principal > Checking_Account.balance;

#4. Which customers have an interest rate that exceeds 3% in any type of account or loan?
SELECT Customer.cust_name
FROM Customer, Savings_Account, Loan, Credit_Card
WHERE Customer.cust_id = Savings_Account.cust_id
AND Customer.cust_id = Loan.cust_id
AND Customer.cust_id = Credit_Card.cust_id
AND (Loan.int_rate > 3 OR Credit_Card.int_rate > 3 OR Savings_Account.int_rate > 3);

#5. Which customers have credit cards whose balance is overdue
SELECT Customer.cust_name
FROM Customer, Credit_Card
WHERE Customer.cust_id = Credit_Card.cust_id
AND late_fees > 0;

#6. Which customers have either a car loan or a mortgage but not both?
SELECT Customer.cust_name
FROM Customer, Loan
WHERE Customer.cust_id = Loan.cust_id
GROUP BY Customer.cust_id, Loan.loan_type
HAVING Loan.loan_type = 'auto' XOR Loan.loan_type = 'mortgage';

#7. Which transactions involved withdrawing more money than the current balance on their checking accounts?
SELECT Bank_Transaction.transaction_id, transaction_date, Checking_Account.balance, Bank_Transaction.amount 
FROM Bank_Transaction, Checking_Account
WHERE Bank_Transaction.checking_acct_num = Checking_Account.acct_num
AND Bank_Transaction.amount > Checking_Account.balance
AND Bank_Transaction.transaction_type = 'withdraw';

#8. How many savings accounts have a balance greater than that of savings account ID 3457?
SELECT COUNT(acct_num)
FROM Savings_Account
WHERE balance > (SELECT balance FROM Savings_Account WHERE acct_num = 3457);

#9. How many customers applying for a loan have a good credit score (700 or greater)?
SELECT COUNT(Customer.cust_id)
FROM Customer, Loan
WHERE Customer.cust_id = Loan.cust_id
AND Customer.credit_score >= 700;

#10. Which transactions deal with savings accounts?
SELECT * FROM Bank_Transaction
WHERE savings_acct_num > 0;

#11. Which credit cards have statements due in the second week of June?
SELECT card_num 
FROM Credit_Card
WHERE due_date BETWEEN '2020-06-07' AND '2020-06-13';

#12. Which savings accounts have their withdrawal limit exceeded?
SELECT acct_num
FROM Savings_Account
WHERE withdrawal_limit < 0;

#13. What are the 100 most recent transactions in the bank?
SELECT * FROM Bank_Transaction
ORDER BY transaction_date
LIMIT 100;

#14. Which loans have a principal less than the amount present in the customer’s savings account?
SELECT Loan.loan_id
FROM Loan, Customer, Savings_Account
WHERE Loan.cust_id = Customer.cust_id
AND Savings_Account.cust_id = Customer.cust_id
AND Loan.principal < Savings_Account.balance;

#15 Which customers have late fees applied on both their credit cards and loans?
SELECT DISTINCT Customer.cust_name
FROM Customer, Credit_Card, Loan
WHERE Credit_Card.cust_id = Customer.cust_id
AND Loan.cust_id = Loan.cust_id
AND Loan.late_fees > 0
AND Credit_Card.late_fees > 0;

#16. What checks has customer 110 deposited to the bank that is used to pay off an existing loan?
SELECT Bank_Transaction.transaction_id
FROM Bank_Transaction, Checking_Account, Debit_Card, Customer, Debit_Transaction
WHERE Bank_Transaction.checking_acct_num = Checking_Account.acct_num
AND Debit_Card.acct_num = Checking_Account.acct_num
AND Debit_Transaction.card_num = Debit_Card.card_num
AND Checking_Account.cust_id = Customer.cust_id
AND Bank_Transaction.transfer_type = 'check'
AND Bank_Transaction.transaction_type = 'deposit'
AND Checking_Account.cust_id = 110
AND Debit_Transaction.vendor = 'bank'
AND Bank_Transaction.transaction_date = Debit_Transaction.transaction_date
AND Bank_Transaction.amount = Debit_Transaction.amount;

#17. When is the credit card statement due for each customer?
SELECT Customer.cust_name, Credit_Card.card_num, Credit_Card.due_date
FROM Customer, Credit_Card
WHERE Customer.cust_id = Credit_Card.cust_id;

#18. What is the minimum payment due for the current month for customers who have late fees?
SELECT Customer.cust_name, min_payment
FROM Credit_Card, Customer
WHERE Credit_Card.cust_id = Customer.cust_id
AND Credit_Card.late_fees > 0;

#19. What customers in the bank have credit scores with less than 700 and have an existing loan or credit card?
SELECT DISTINCT Customer.cust_name
FROM Customer, Loan, Credit_Card
WHERE (Loan.cust_id = Customer.cust_id OR Credit_Card.cust_id = Customer.cust_id)
AND Customer.credit_score < 700;

#20. What is the average monthly maintenance fee for checking accounts
SELECT AVG(maintenance_fee)
FROM Checking_Account;


