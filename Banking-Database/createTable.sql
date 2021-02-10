CREATE DATABASE bank;
USE bank;

CREATE TABLE Address (
	address_id NUMERIC(10) CHECK(address_id >= 0),
	street_name VARCHAR(100) NOT NULL,
    city VARCHAR(30) NOT NULL,
	state VARCHAR(2) NOT NULL CHECK(state IN ('-','AL','AK','AZ','AR','CA','CO','CT','DE','DC','FL','GA','HI','ID','IL','IN','IA','KS','KY','LA','ME','MD','MA','MI','MN','MS','MO','MT','NE','NV','NH','NJ','NM','NY','NC','ND','OH','OK','OR','PA','RI','SC','SD','TN','TX','UT','VT','VA','WA','WV','WI','WY')),
	zip_code NUMERIC(5) NOT NULL CHECK(zip_code = 0 OR (zip_code > 10000 AND zip_code < 99999)),
    PRIMARY KEY(address_id)
);

CREATE TABLE Customer (
	cust_id NUMERIC(10) CHECK(cust_id >= 0),
	cust_name VARCHAR(40) NOT NULL,
	ssn NUMERIC(9) UNIQUE CHECK((ssn BETWEEN 100000000 AND 999999999) OR ssn = 0),
	drivers_license_id VARCHAR(20) UNIQUE,
	credit_score NUMERIC(3) CHECK((credit_score BETWEEN 300 AND 850) OR credit_score = 0),
	address_id NUMERIC(10) CHECK (address_id >= 0),
    PRIMARY KEY(cust_id),
    FOREIGN KEY(address_id) REFERENCES Address(address_id)
);
CREATE TABLE Savings_Account (
	acct_num NUMERIC(10) CHECK(acct_num >= 0),
	cust_id NUMERIC(10) CHECK(cust_id >= 0),
	routing_num NUMERIC(10) CHECK(routing_num >= 0),
	balance NUMERIC(10, 2),
	int_rate NUMERIC(3, 1) CHECK(int_rate >= 0),
	int_term NUMERIC(3) CHECK(int_term >= 0),
	withdraw_limit NUMERIC(3),
	withdrawal_limit NUMERIC(10, 2),
    PRIMARY KEY(acct_num),
    FOREIGN KEY(cust_id) REFERENCES Customer(cust_id)
);

CREATE TABLE Checking_Account (
	acct_num NUMERIC(10) CHECK(acct_num >=0),
	cust_id NUMERIC(10) CHECK (cust_id >=0),
	routing_num NUMERIC(10) CHECK(routing_num >= 0),
	balance NUMERIC(10, 2),
	maintenance_fee NUMERIC(10, 2) CHECK(maintenance_fee >= 0),
	min_bal NUMERIC(10, 2) CHECK(min_bal >= 0),
    PRIMARY KEY(acct_num),
    FOREIGN KEY(cust_id) REFERENCES Customer(cust_id)
);

CREATE TABLE Bank_Transaction (
	transaction_id NUMERIC(10) CHECK(transaction_id >= 0),
	transaction_type VARCHAR(8) CHECK(transaction_type IN ('deposit', 'withdraw')),
	transfer_type VARCHAR(5) CHECK(transfer_type IN ('check', 'cash', 'ACH', 'EFT', 'Debit')),
	checking_acct_num NUMERIC(10) CHECK(checking_acct_num >= 0),
	savings_acct_num NUMERIC(10) CHECK(savings_acct_num >= 0),
	amount NUMERIC(10, 2) NOT NULL,
	transaction_date DATE NOT NULL,
    CONSTRAINT oneAcct CHECK(NOT (checking_acct_num > 0 AND savings_acct_num > 0)),
    PRIMARY KEY(transaction_id),
    FOREIGN KEY(checking_acct_num) REFERENCES Checking_Account(acct_num),
    FOREIGN KEY(savings_acct_num) REFERENCES Savings_Account(acct_num)
);

CREATE TABLE Credit_Card (
	card_num NUMERIC(16) CHECK(card_num > 1000000000000000 AND card_num < 9999999999999999),
	cust_id NUMERIC(10) CHECK(cust_id >= 0),
	expiration_date VARCHAR(5), #CHECK(expiration_date REGEXP ('1[0-2]/[0-9][0-9]' OR '0[1-9]/[0-9][0-9]')),
	security_code NUMERIC(3) CHECK(security_code BETWEEN 100 and 999),
	balance NUMERIC(10, 2),
	credit_limit NUMERIC(10, 2),
    int_rate NUMERIC(4,2) CHECK(int_rate >= 0),
	late_fees NUMERIC(6, 2) CHECK(late_fees >= 0),
	min_payment NUMERIC(6, 2) CHECK(min_payment >= 0),
	due_date DATE NOT NULL,
    PRIMARY KEY(card_num),
    FOREIGN KEY(cust_id) REFERENCES Customer(cust_id)
);

CREATE TABLE Debit_Card (
	card_num NUMERIC(16) CHECK(card_num > 1000000000000000 AND card_num < 9999999999999999),
	acct_num NUMERIC(10) CHECK(acct_num >= 0),
	expiration_date VARCHAR(5), #CHECK(expiration_date REGEXP ('1[0-2]/[0-9][0-9]' OR '0[1-9]/[0-9][0-9]' OR '-')),
	security_code NUMERIC(3) CHECK(security_code BETWEEN 100 AND 999),
	debit_limit NUMERIC(7,2),
	PRIMARY KEY(card_num),
	FOREIGN KEY(acct_num) REFERENCES Checking_Account(acct_num)
);
CREATE TABLE Debit_Transaction (
	transaction_id NUMERIC(10) CHECK(transaction_id >= 0),
	card_num NUMERIC(16) CHECK(card_num > 1000000000000000 AND card_num < 9999999999999999),
	vendor VARCHAR(30) NOT NULL,
	amount NUMERIC(10, 2) NOT NULL,
    transaction_date DATE NOT NULL,
    ref_num NUMERIC(10) CHECK(ref_num >= 0),
    PRIMARY KEY(transaction_id),
    FOREIGN KEY(card_num) REFERENCES Debit_Card(card_num)
);
CREATE TABLE Credit_Transaction (
	transaction_id NUMERIC(10) CHECK(transaction_id >= 0),
	card_num NUMERIC(16) CHECK(card_num > 1000000000000000 AND card_num < 9999999999999999),
	vendor VARCHAR(30) NOT NULL,
	amount NUMERIC(10) NOT NULL,
	transaction_date DATE NOT NULL,
	ref_num NUMERIC(10) CHECK(ref_num >= 0),
    PRIMARY KEY(transaction_id),
    FOREIGN KEY(card_num) REFERENCES Credit_Card(card_num)
);

CREATE TABLE Loan (
	loan_id NUMERIC(10) CHECK(loan_id >= 0),
	cust_id NUMERIC(10) CHECK(cust_id >= 0),
	loan_type VARCHAR(20) CHECK(loan_type IN ('auto', 'personal', 'mortgage', 'student', 'business')),
	principal NUMERIC(10) CHECK(principal >= 0),
	int_type VARCHAR(20) CHECK(int_type IN ('simple', 'compound')),
    int_rate NUMERIC(4,2) CHECK(int_rate >= 0),
	loan_term NUMERIC(4) CHECK(loan_term >= 0),
	monthly_payment NUMERIC(10, 2) CHECK(monthly_payment >=0),
    monthly_due_date DATE NOT NULL,
	remain_bal NUMERIC(10, 2) CHECK(remain_bal >= 0),
	late_fees NUMERIC(10, 2) CHECK (late_fees >= 0),
    PRIMARY KEY(loan_id),
    FOREIGN KEY(cust_id) REFERENCES Customer(cust_id)
);
