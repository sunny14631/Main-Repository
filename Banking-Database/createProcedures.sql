
USE bank;
# change the delimiter to !
DELIMITER !
DROP PROCEDURE IF EXISTS sp_insert_bank_transaction!

CREATE PROCEDURE sp_insert_bank_transaction(
  IN transaction_id NUMERIC(10)
, IN transaction_type VARCHAR(8)
, IN transfer_type VARCHAR(5)
, IN checking_acct_num NUMERIC(10)
, IN savings_acct_num NUMERIC(10)
, IN amount	NUMERIC(10,2)
, IN transaction_date DATE
)
BEGIN

INSERT INTO bank_transaction (
	transaction_id, 
	transaction_type, 
	transfer_type, 
	checking_acct_num, 
	savings_acct_num, 
	amount, 
	transaction_date
) VALUES (
	transaction_id, 
	transaction_type, 
	transfer_type, 
	checking_acct_num, 
	savings_acct_num, 
	amount, 
	transaction_date);
END; !

DROP PROCEDURE IF EXISTS sp_insert_debit_transaction!

CREATE PROCEDURE sp_insert_debit_transaction(
  IN transaction_id NUMERIC(10)
, IN card_num VARCHAR(19)
, IN vendor VARCHAR(30)
, IN amount NUMERIC(10)
, IN transaction_date DATE
, IN ref_num	NUMERIC(10)
)
BEGIN

INSERT INTO debit_transaction (
	transaction_id, 
	card_num, 
	vendor, 
	amount, 
	transaction_date, 
	ref_num
)
VALUES (
	transaction_id, 
	card_num, 
	vendor, 
	amount, 
	transaction_date, 
	ref_num);
END!

DROP PROCEDURE IF EXISTS sp_insert_credit_transaction!

CREATE PROCEDURE sp_insert_credit_transaction(
  IN transaction_id NUMERIC(10)
, IN card_num VARCHAR(19)
, IN vendor VARCHAR(30)
, IN amount NUMERIC(10)
, IN transaction_date DATE
, IN ref_num	NUMERIC(10)
)
BEGIN

INSERT INTO credit_transaction (
	transaction_id, 
	card_num, 
	vendor, 
	amount, 
	transaction_date, 
	ref_num
)
VALUES (
	transaction_id, 
	card_num, 
	vendor, 
	amount, 
	transaction_date, 
	ref_num);
END!

DROP PROCEDURE IF EXISTS bank_debit_procedure!

CREATE PROCEDURE bank_debit_procedure (IN amount NUMERIC(10, 2), 
									   IN debit_card_num NUMERIC(16))
BEGIN
	SET @loan_late_fees = 0;
    SET @loan_id = 0;
    SELECT late_fees, loan_id 
    INTO @loan_late_fees, @loan_id
    FROM Loan, Debit_Card, Checking_
	WHERE Debit_Card.card_num = debit_card_num
	AND Debit_Card.acct_num = Checking_Account.acct_num
	AND Checking_Account.cust_id = Loan.cust_id;
	IF @loan_late_fees > 0 AND amount > @loan_late_fees THEN
			SET @loan_cost = 0;
			SELECT SUM(Loan.remain_bal) + SUM(Loan.late_fees)
			INTO @loan_cost
			FROM Loan, Debit_Card, Checking_Account
			WHERE loan_id = @loan_id;
            
            UPDATE Loan
            SET Loan.remain_bal = @loan_cost - amount
			WHERE loan_id = @loan_id;
	ELSEIF @loan_late_fees > 0 AND amount < @loan_late_fees THEN
			UPDATE Loan
			SET Loan.late_fees = Loan.late_fees - amount
            WHERE loan_id = @loan_id;
	ELSE
			UPDATE Loan
            SET Loan.remain_bal = Loan.remain_bal - amount
            WHERE loan_id = @loan_id;
	END IF;
END!

DROP PROCEDURE IF EXISTS withdraw_procedure!
CREATE PROCEDURE withdraw_procedure(IN amount NUMERIC(10, 2), IN checking_num NUMERIC(10),
									IN savings_num NUMERIC(10))
BEGIN
	IF checking_num > 0 THEN
			UPDATE Checking_Account
            SET balance = balance - amount
            WHERE checking_num = acct_num;
	ELSE
			UPDATE Savings_Account
            SET balance = balance - amount,
            withdraw_limit = withdraw_limit - 1,
            withdrawal_limit = withdrawal_limit - amount
            WHERE savings_num = acct_num;
            
		SET @with_lim = 0;
        SET @withdraw_lim = 0;
        SELECT withdraw_limit, withdrawal_limit
        INTO @with_lim, @withdraw_lim 
        FROM Savings_Account
        WHERE acct_num = savings_num;
        IF @with_lim < 0 THEN
			UPDATE Savings_Account
			SET Savings_Account.balance = Savings_Account.balance - 50
            WHERE acct_num = savings_num;
		END IF;
		IF @withdraw_lim < 0 THEN
			UPDATE Savings_Account
			SET Savings_Account.balance = Savings_Account.balance - 50
            WHERE acct_num = savings_num;
		END IF;
	END IF;
END!

DROP PROCEDURE IF EXISTS deposit_procedure!
CREATE PROCEDURE deposit_procedure(IN amount NUMERIC(10, 2), IN checking_num NUMERIC(10),
									IN savings_num NUMERIC(10))
BEGIN
	IF checking_num > 0 THEN
			UPDATE Checking_Account
            SET balance = balance + amount
            WHERE checking_num = acct_num;
	ELSE
			UPDATE Savings_Account
            SET balance = balance + amount
            WHERE savings_num = acct_num;
	END IF;
END!
# change the delimiter back to semicolon
DELIMITER ;
