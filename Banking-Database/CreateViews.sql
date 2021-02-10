USE bank;
CREATE VIEW Customer_Address_View AS
SELECT Customer.cust_name, Address.street_name, 
Address.city, Address.zip_code
FROM Customer, Address
WHERE Customer.address_id = Address.address_id;

CREATE VIEW Customer_Checking_Balance_View AS
SELECT Customer.cust_name, Checking_Account.balance
FROM Customer, Checking_Account
WHERE Customer.cust_id = Checking_Account.cust_id;

CREATE VIEW Customer_Savings_Balance_View AS
SELECT Customer.cust_name, Savings_Account.balance
FROM Customer, Savings_Account
WHERE Customer.cust_id = Savings_Account.cust_id;

CREATE VIEW Customer_Credit_Statement_View AS
SELECT Customer.cust_name, Credit_Transaction.vendor,
Credit_Transaction.amount, Credit_Transaction.transaction_date
FROM Customer, Credit_Card, Credit_Transaction
WHERE Customer.cust_id = Credit_Card.cust_id
AND Credit_Card.card_num = Credit_Transaction.card_num;

CREATE VIEW Customer_Debit_Statement_View AS
SELECT Customer.cust_name, Debit_Transaction.vendor,
Debit_Transaction.amount, Debit_Transaction.transaction_date
FROM Customer, Checking_Account, Debit_Card, Debit_Transaction
WHERE Customer.cust_id = Checking_Account.cust_id
AND Checking_Account.acct_num = Debit_Card.acct_num
AND Debit_Card.card_num = Debit_Transaction.card_num;

CREATE VIEW Customer_Loan_View AS
SELECT Customer.cust_name, Loan.loan_type, Loan.principal,
Loan.monthly_payment, Loan.monthly_due_date
FROM Customer, Loan
WHERE Customer.cust_id = Loan.cust_id;

CREATE VIEW Customer_Checking_Transaction_View AS
SELECT Customer.cust_name, Bank_Transaction.checking_acct_num,
Bank_Transaction.transaction_type, Bank_Transaction.transfer_type, Bank_Transaction.amount,
Bank_Transaction.transaction_date
FROM Customer, Bank_Transaction, Checking_Account
WHERE Customer.cust_id = Checking_Account.cust_id
AND Checking_Account.acct_num = Bank_Transaction.checking_acct_num;

CREATE VIEW Customer_Savings_Transaction_View AS
SELECT Customer.cust_name, Bank_Transaction.savings_acct_num,
Bank_Transaction.transaction_type, Bank_Transaction.transfer_type, Bank_Transaction.amount,
Bank_Transaction.transaction_date
FROM Customer, Bank_Transaction, Savings_Account
WHERE Customer.cust_id = Savings_Account.cust_id
AND Savings_Account.acct_num = Bank_Transaction.savings_acct_num;