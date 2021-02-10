Rahul Madala and Mosammat Sarker

Bank Database Proposal

Many banks have developed online applications for customers to manage their accounts. Account transactions include withdrawing money, depositing money, or transferring money between accounts. Due to this development, there are countless transactions that the bank has to process each day; therefore, it is impractical to keep physical records of each customer&#39;s banking information. The objective of this project is to develop a database management system that keeps track of customers&#39; banking information. This includes any checking, savings accounts, existing loans, or credit cards.

The database will store each customer&#39;s personal information such as full name, social security number, credit score, driver&#39;s license number, and address. The credit score is used to determine whether a customer qualifies for a loan or credit card. The driver&#39;s license number and social security number are security measures to ensure that unauthorized transactions are not made by an outside party. Each customer will have a checking account. The checking account will contain the account number, routing number, maintenance fee if applicable, and balance. Most transactions will occur in the checking accounts.

A savings account can only be created if a customer already has an existing checking account. Each savings account has an account number, routing number, and interest rate.. The interest time attribute keeps track of how long the account has been active. Each savings account also has its own withdrawal limit, which restricts the number of times a customer can withdraw money from the savings account per month.

Customers can also apply for either a loan or a credit card. Prospective applicants must have a good credit score to qualify for either account. Once they are qualified, customers can take out multiple loans and credit cards. Every loan will be categorized by a type (e.g. car loan, mortgage, etc) to easily distinguish the loans belonging to a customer. Each loan will have a principal, which is the amount of money that the bank loaned to the customer and an applicable interest rate. At the start of each loan, the bank will calculate the total balance that the customer will have paid once the customer has repaid the loan. The bank will then charge a monthly payment. If the customer defaults on the loan, late fees will be added onto the balance amount.

Each credit card issued to a customer has its own unique credit card number, specified expiration date, and a security code. Every credit card will have its own credit limit, which restricts the amount of money a customer can spend each month. At the end of the month, a statement will be issued to the customer detailing the credit card activity, the amount owed, and a due date. If the customer fails to pay the entire balance by the due date, the remaining balance will be carried over to the next statement, with interest applied to the amount that wasn&#39;t paid. In addition, if the customer doesn&#39;t meet the minimum payment requirement on the statement, late fees will be added onto the balance in the next statement. Delayed payment on either a loan or a credit card will also negatively affect the customer&#39;s credit score.

Transactions that occur between accounts within the bank will be stored in a separate table. Each transaction will either be a withdrawal or deposit. If the transaction cannot occur, due to either over-withdrawal or the number of transactions has been exceeded, the transaction will be marked as a failure on the customer&#39;s end. The customer associated with the transaction would then suffer monetary penalties.

There are two types of user groups: the bank employees and the customers. Bank employees will handle walk-in transactions while customers will interface with the online banking application. They will access the database through a banking application daily to process pertinent information, such as checks or new customer accounts, and update the database accordingly.

The major entities that are included in the database design are:

1. Customer
2. Address
3. Loan
4. Checking Account
5. Savings Account
6. Credit Card
7. Bank Transaction
8. Credit Card Transactions

The operations that will be used to maintain the database are:

1. Deposit check: this operation will obtain the relevant account id and either withdraw or deposit money into that account. The check will be archived for recordkeeping.
2. Update account information: This operation will be used to change a customer&#39;s personal information or change details about the accounts that they have opened (e.g. new credit card, primary or secondary account holder etc.)
3. Add new account: this operation will add an account (savings, checking, or credit card) to an existing customer.
4. Take out loan: this operation will add a loan to an existing customer if the customer has a sufficient credit score. The principal, balance, interest, and type of loan will be added to the Loan table.
5. Add late fees: This operation will be used to penalize customers who fail to pay required loans or statements on time. This will be applied on the late fees attribute.
6. Generate monthly statement: This operation will generate a monthly statement for all accounts.
7. Transfer money: This operation will allow customers to transfer money between their accounts.

These are some examples of user queries in this database:

1. How many customers have a checking account balance over $10,000
2. How many customers have both a savings account and a checking account?
3. Which customers have a loan whose principal amount exceeds their checking account balance?
4. Which customers have an interest rate that exceeds 3% in any type of account or loan?
5. How many customers have credit cards whose balance is overdue?
6. Which customers have either a car loan or a mortgage but not both?
7. Which transactions have done overdraft on their checking accounts?
8. How many savings accounts have a balance greater than that of savings account ID 3457?
9. How many customers applying for a loan have a good credit score?
10. Which transactions deal with savings accounts?
11. Which credit cards have statements due in the second week of June?
12. Which savings accounts have their withdrawal limit exceeded?
13. What are the 100 most recent transactions in the bank ?
14. Which loans have a balance less than the amount present in the customer&#39;s savings account?
15. Which customers have late fees applied on both their credit cards and loans?
16. How many checks has a customer deposited to the bank that is used to pay off an existing loan?
17. When is the credit card statement due for an individual customer?
18. What is the minimum payment due for the current month?
19. How many customers in the bank have credit scores with less than 700 and have an existing loan or credit card?
20. What is the monthly maintenance fee for checking accounts?

![](RackMultipart20200611-4-bzbwk2_html_f3a39459ec23cc0f.png)

**Functional Dependencies**

Customer

**Customer id** → name, SSN, driver\_lisence\_id, credit\_score

Credit Card

**Card number** -\&gt; expiration\_date, balance, credit\_limit,late\_fees,min\_payment,interest\_rate,due\_date,credit\_used,security code

Credit Card Transaction

**Transaction id** -\&gt; vendor, date\_purchased, amount

Savings

**Account number** -\&gt; routing\_number, balance, interest\_rate, interest\_time, withdraw\_limit, num\_withdraw, withdraw\_money\_limit

Checking

**Account number -\&gt;** routing\_number, balance, maintenance\_fee, minimum\_balance

Bank\_Transaction

**Transaction id -\&gt;** transaction\_type, balance, transaction\_passes

Loan

**Loan id -\&gt;** type, principal, interest\_rate, interest\_type, interest\_time, late\_fees, balance

Address

**Address id -\&gt;** street\_name, zip\_code, state

**Sample Data (FK = Foreign Key)**

Customer

| Column Name | Data Type | Length | Domain/Constraints |
| --- | --- | --- | --- |
| Customer\_id | Numeric | 10 | Primary Key |
| name | Character | 16 | FK to Checking\_Account |
| SSN | Numeric | 11 | ###-##-### |
| drivers\_lisence\_id | Alphanumeric | 20 | Parameters vary by state |
| credit\_score | Numeric | 3 | Any integer between 300 and 850 |
| account\_number | Numeric | 10 | FK to Savings |
| account\_number | Numeric | 10 | FK to Checking |
| account\_number | Numeric | 10 | FK to Savings |
| card\_number | Numeric | 19 | FK to Credit Card####-####-####-#### |
| address\_id | Numeric | 10 | FK to Address Table |
| loan\_id | Numeric | 10 | FK to Loan Table |

| customer\_id | name | SSN | drivers\_lisence\_id | credit\_score | account\_number | account\_number | card\_number | address\_id | loan\_id |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 124 | Rahul Madala | 304-58-3435 | 234567 | 705 | 12489 | 84205 | 1234-5678-9034-4567 | 123678 | 12346 |
| 125 | Mosammat Sarker | 456-89-2345 | C1234567890123 | 710 | 15678 | 23458 | 3463-4578-1235-2345 | 124520 | 58038 |

Credit Card

| Column Name | Data Type | Length | Domain/Constraint |
| --- | --- | --- | --- |
| card\_number | Numeric | 19 | Primary Key####-####-####-#### |
| expiration\_date | Numeric | 5 | ##-## |
| balance | Numeric | 10 | N/A |
| credit\_limit | Numeric | 10 | N/A |
| late\_fees | Numeric | 6 | N/A |
| min\_payment | Numeric | 6 | N/A |
| interest\_rate | Numeric | 3 | ##.# |
| due\_date | DATE | 8 | ##-##-## |
| credit\_used | Numeric | 10 | N/A |
| security\_code | Numeric | 3 | N/A |
| Transaction\_id | Numeric | 10 | FK to Credit Card Transaction |

| Card\_number | expiration\_date | balance | credit\_limit | late\_fees | min\_payment | interest\_rate | due\_date | credit\_used | Security code | Transaction\_id |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1234-5678-9034-4567 | 10-22 | 500 | 1000 | 45 | 20 | 5.5 | 8/19/20 | 400 | 567 | 356478 |
| 3463-4578-1235-2345 | 11-21 | 450 | 750 | 0 | 50 | 2.5 | 8/25/20 | 300 | 456 | 123446 |

Saving

| Column Name | Data Type | Length | Domain/Constraints |
| --- | --- | --- | --- |
| Account\_Number | Numeric | 10 | Primary Key |
| routing\_number | Numeric | 10 | N/A |
| balance | Numeric | 10 | N/A |
| interest \_rate | Numeric | 3 | ##.# |
| interest\_time | Numeric | 3 | Unit of measurement is months |
| withdraw\_limit | Numeric | 1 | N/A |
| num\_withdraw | Numeric | 1 | N/A |
| withdraw\_money\_limit | Numeric | 10 | N/A |
| transaction\_id | Numeric | 10 | FK to Bank\_Transaction |

| Account\_Number | routing\_number | balance | interest\_rate | interest\_time | withdraw\_limit | num\_withdraw | withdraw\_money\_limit | Transaction\_id |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 12489 | 1245901 | 456780 | 2.5 | 24 | 7 | 2 | 100000 | 927592 |
| 15678 | 2405890 | 123900 | 1.5 | 15 | 5 | 1 | 75000 | 982745 |

Checking

| Column Name | Data Type | Length | Domain/Constraints |
| --- | --- | --- | --- |
| Account\_number | Numeric | 10 | Primary Key |
| routing\_number | Numeric | 10 | N/A |
| balance | Numeric | 10 | N/A |
| maintenance\_fee | Numeric | 10 | N/A |
| minimum\_balance | Numeric | 10 | N/A |
| transaction\_id | Numeric | 10 | FK to Bank\_Transaction |

| Account\_number | routing\_number | balance | maintenance\_fee | minimum\_balance | Transaction\_id |
| --- | --- | --- | --- | --- | --- |
| 4535362865 | 1112223330 | 1200.00 | 5.00 | 25.00 | 927592 |
| 9756422344 | 0001113339 | 2100.00 | 10.00 | 30.00 | 982745 |

Bank Transaction

| Column Name | Data Type | Length | Domain/Constraints |
| --- | --- | --- | --- |
| Transaction\_id | Numeric | 10 | Primary Key |
| Transaction\_type | Character | 10 | {deposit, withdraw} |
| Balance | Numeric | 10 | N/A |
| transaction\_passes | Boolean | N/A | {true, false} |
| loan\_id | Numeric | 10 | FK to Loan (nullable) |

| Transaction\_id | transaction\_type | balance | transaction\_passes | Loan\_id (FK) |
| --- | --- | --- | --- | --- |
| 927592 | deposit | 50.00 | true | 6294657394 |
| 982745 | withdraw | 100.00 | false | null |

Loan

| Column Name | Data Type | Length | Domain/Constraints |
| --- | --- | --- | --- |
| loan\_id | Numeric | 10 | Primary Key |
| type | Character | 20 | {auto, personal, mortgage, college, business} |
| principal | Numeric | 10 | N/A |
| interest\_type | Character | 20 | {simple, compound} |
| interest\_time | Numeric | 6 | Unit of measurement is months |
| late\_fees | Numeric | 10 | N/A |
| balance | Numeric | 10 | N/A |

| loan\_id | type | principal | interest\_rate | interest\_type | interest\_time | late\_fees | balance |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 6294657394 | Auto | 100000 | 5% | simple | 72 | 60.00 | 1000.00
 |
| 9823579023 | Personal | 190000 | 4% | compound | 360 | 100.00 | 2000.00 |

Address

| Column Name | Data Type | Length | Domain/Constraints |
| --- | --- | --- | --- |
| Address\_id | Numeric | 10 | Primary key |
| street\_name | Alphanumeric | 100 | N/A |
| zip\_code | Numeric | 5 | ##### |
| State | Character | 2 | Domain is all two-letter state abbreviations |

| Address\_id | street\_name | zip\_code | State |
| --- | --- | --- | --- |
| 0987654321 | 1234 Rose St. | 23294 | VA |
| 0123456789 | 3453 Tulipe Rd. | 12444 | VA |
