USE bank;

DELIMITER !
#DROP TRIGGER Bank_Transaction_Trigger!
CREATE TRIGGER Bank_Transaction_Trigger AFTER INSERT
ON Bank_Transaction FOR EACH ROW
BEGIN
	IF NEW.transaction_type = 'withdraw' THEN
		CALL withdraw_procedure(NEW.amount, NEW.checking_acct_num, NEW.savings_acct_num);
	ELSEIF NEW.transaction_type = 'deposit' THEN
		CALL deposit_procedure(NEW.amount, NEW.checking_acct_num, NEW.savings_acct_num);
	END IF;
END!


#DROP TRIGGER Debit_Transaction_Trigger!
CREATE TRIGGER Debit_Transaction_Trigger AFTER INSERT
ON Debit_Transaction FOR EACH ROW
BEGIN
	SET @card_num = 0;
    SELECT card_num
    INTO @card_num
    FROM Credit_Card, Debit_Card, Checking_Account
	WHERE Debit_Card.card_num = NEW.card_num
	AND Debit_Card.acct_num = Checking_Account.acct_num
	AND Checking_Account.cust_id = Credit_Card.cust_id;
	IF NEW.vendor = 'bank' THEN
		CALL bank_debit_procedure(NEW.amount, NEW.card_num);
	ELSEIF NEW.vendor = 'credit' THEN
		UPDATE Credit_Card
        SET Credit_Card.balance = Credit_Card.balance - NEW.amount
        WHERE card_num = @card_num;
	ELSE
		UPDATE Debit_Card
		SET Debit_Card.debit_limit = Debit_Card.debit_limit - NEW.amount
        WHERE Debit_Card.card_num = NEW.card_num;
	END IF;
    
    UPDATE Checking_Account
    SET Checking_Account.balance = Checking_Account.balance - NEW.amount
    WHERE Debit_Card.card_num = NEW.card_num
    AND Debit_Card.acct_num = Checking_Account.acct_num;
END; !

#DROP TRIGGER Debit_Limit_Overdraft!
CREATE TRIGGER Debit_Limit_Overdraft AFTER UPDATE
ON Debit_Card FOR EACH ROW
BEGIN
	IF NEW.debit_limit < 0 THEN
		UPDATE Checking_Account
        SET balance = balance - 50
        WHERE NEW.acct_num = Checking_Account.acct_num;
	END IF;
END; !
#DROP TRIGGER Credit_Transaction_Trigger!
CREATE TRIGGER Credit_Transaction_Trigger AFTER INSERT
ON Credit_Transaction FOR EACH ROW
BEGIN
	UPDATE Credit_Card
	SET balance = NEW.amount + balance, credit_limit = credit_limit - NEW.amount
    WHERE NEW.card_num = Credit_Card.card_num;
    
    IF Credit_Card.credit_limit < 0 THEN
		UPDATE Credit_Card
		SET Credit_Card.late_fees = Credit_Card.late_fees + 50
		WHERE NEW.card_num = Credit_Card.card_num;
	END IF;
    
END; !

DELIMITER ;