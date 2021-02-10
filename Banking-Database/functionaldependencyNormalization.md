**Functional Dependencies**

Customer

**Customer id** → name, SSN, driver\_lisence\_id, credit\_score

Credit Card

**Card number** →  expiration\_date, balance, credit\_limit,late\_fees,min\_payment,interest\_rate,due\_date,credit\_used,security code

Credit Card Transaction

**Transaction id** →  vendor, date\_purchased, amount

Savings

**Account number** →  routing\_number, balance, interest\_rate, interest\_time, withdraw\_limit, num\_withdraw, withdraw\_money\_limit

Checking

**Account number →  ** routing\_number, balance, maintenance\_fee, minimum\_balance

Bank\_Transaction

**Transaction id →  ** transaction\_type, balance, transaction\_passes

Loan

**Loan id →   ** type, principal, interest\_rate, interest\_type, interest\_time, late\_fees, balance

Address

**Address id →   ** street\_name, zip\_code, state




##Customer


| customer\_id | name | SSN | drivers\_lisence\_id | credit\_score | account\_number | account\_number | card\_number | address\_id | loan\_id |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 124 | Rahul Madala | 304-58-3435 | 234567 | 705 | 12489 | 84205 | 1234-5678-9034-4567 | 123678 | 12346 |
| 125 | Mosammat Sarker | 456-89-2345 | C1234567890123 | 710 | 15678 | 23458 | 3463-4578-1235-2345 | 124520 | 58038 |




##Credit Card


| Card\_number | expiration\_date | balance | credit\_limit | late\_fees | min\_payment | interest\_rate | due\_date | credit\_used | Security code | Transaction\_id |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1234-5678-9034-4567 | 10-22 | 500 | 1000 | 45 | 20 | 5.5 | 8/19/20 | 400 | 567 | 356478 |
| 3463-4578-1235-2345 | 11-21 | 450 | 750 | 0 | 50 | 2.5 | 8/25/20 | 300 | 456 | 123446 |



##Saving


| Account\_Number | routing\_number | balance | interest\_rate | interest\_time | withdraw\_limit | num\_withdraw | withdraw\_money\_limit | Transaction\_id |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 12489 | 1245901 | 456780 | 2.5 | 24 | 7 | 2 | 100000 | 927592 |
| 15678 | 2405890 | 123900 | 1.5 | 15 | 5 | 1 | 75000 | 982745 |




##Checking


| Account\_number | routing\_number | balance | maintenance\_fee | minimum\_balance | Transaction\_id |
| --- | --- | --- | --- | --- | --- |
| 4535362865 | 1112223330 | 1200.00 | 5.00 | 25.00 | 927592 |
| 9756422344 | 0001113339 | 2100.00 | 10.00 | 30.00 | 982745 |


##Bank Transaction


| Transaction\_id | transaction\_type | balance | transaction\_passes | Loan\_id (FK) |
| --- | --- | --- | --- | --- |
| 927592 | deposit | 50.00 | true | 6294657394 |
| 982745 | withdraw | 100.00 | false | null |



##Loan

| loan\_id | type | principal | interest\_rate | interest\_type | interest\_time | late\_fees | balance |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 6294657394 | Auto | 100000 | 5% | simple | 72 | 60.00 | 1000.00
 |
| 9823579023 | Personal | 190000 | 4% | compound | 360 | 100.00 | 2000.00 |



##Address

| Address\_id | street\_name | zip\_code | State |
| --- | --- | --- | --- |
| 0987654321 | 1234 Rose St. | 23294 | VA |
| 0123456789 | 3453 Tulipe Rd. | 12444 | VA |



#4NF


| customer_id | Card_number |Account\_number Saving | Account\_number  Checking | Transaction_id | loan_id | Address_id |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 124 | 1234-5678-9034-4567 | 12489 | 4535362865| 927592 | 6294657394 | 0987654321 | 
 |
| 125 | 3463-4578-1235-2345 | 15678 | 9756422344 | 982745 | 9823579023 | 0123456789 | 



#Descriptions

Customer_id                               = A
Card_number                               = B
Account_Number  (Saving)                  = C
Account_Number  (Checking)                = D
Transaction_id                            = E
Loan_id                                   = F
Address_id                                = G



R = { A,B,C,D,E,F,G}
Functional key = { A → B, B → C, C → D, D → E, E → F, F → G}
So there Super Key will be :
SK = {ABCDEFG}+
      = {A}+


Prime Attribute = {ABCDEFG}
Candidate Key = {ABCDEFG}

Checking if this is BCNF:
In order to make it a BCNF, NPA → PA. All their left hand side is the  super key of the primary Attribute. So this is a BCNF. 




