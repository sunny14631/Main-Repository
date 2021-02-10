## Test Plan
Use this markdown document to record your Test Plan for Individual Assignment #3. Remember that you want to design tests that are FIRST - Fast, Independent, Repeatable, and Timely.

| Test ID|fractionalNum1    |          fractionalNum2      | Action Performed | Expected Change | Actual Change |
---------|----------------|----------------------------|------------------|-----------------|---------------|                                                              
| 1 | num = 1; den = 3; | num = 1; den = 2; | fractionalNum1.add(fractionalNum2);| fractionalNum1: num = 5; den = 6; | fractionalNum1: num = 5; den = 6;
| 2 | num = 1; den = 2; | num = -1; den = 3; | fractionalNum1.add(fractionalNum2);| fractionalNum1: num = 1; den = 6; | fractionalNum1: num = 1; den = 6;
| 3 | num = -3; den = 4; | num = 1; den = 2; | fractionalNum1.add(fractionalNum2);| fractionalNum1: num = -1; den = 4; | fractionalNum1: num = -1; den = 4;  
| 4 | num = 1; den = -4; | num = -1; den = 2; | fractionalNum1.add(fractionalNum2);| fractionalNum1: num = -3; den = 4; | fractionalNum1: num = -3; den = 4;
| 5 | num = 3; den = 4; | num = 1; den = 2; | fractionalNum1.subtract(fractionalNum2);| fractionalNum1: num = 1; den = 4; | fractionalNum1: num = 1; den = 4;
| 6 | num = 1; den = 4; | num = 1; den = -2; | fractionalNum1.subtract(fractionalNum2);| fractionalNum1: num = 3; den = 4; | fractionalNum1: num = 3; den = 4;  
| 7 | num = -1; den = 4; | num = 1; den = 2; | fractionalNum1.subtract(fractionalNum2);| fractionalNum1: num = -3; den = 4; | fractionalNum1: num = -3; den = 4;   
| 8 | num = -1; den = 4; | num = -1; den = 2; | fractionalNum1.subtract(fractionalNum2);| fractionalNum1: num = -3; den = 4; | fractionalNum1: num = -3; den = 4;
| 9 | num = 1; den = 4; | num = 1; den = 2; | fractionalNum1.multiply(fractionalNum2);| fractionalNum1: num = 1; den = 8; | fractionalNum1: num = 1; den = 8;
| 10 | num = 1; den = 4; | num = 1; den = -2; | fractionalNum1.multiply(fractionalNum2);| fractionalNum1: num = -1; den = 8; | fractionalNum1: num = -1; den = 8;  
| 11 | num = -1; den = 4; | num = 1; den = -2; | fractionalNum1.multiply(fractionalNum2);| fractionalNum1: num = 1; den = 8; | fractionalNum1: num = 1; den = 8;  
| 12 | num = 1; den = 3; | num = 1; den = 2; | fractionalNum1.divide(fractionalNum2);| fractionalNum1: num = 2; den = 3; | fractionalNum1: num = 2; den = 3;
| 13 | num = -1; den = 3; | num = 1; den = -2; | fractionalNum1.divide(fractionalNum2);| fractionalNum1: num = 2; den = 3; | fractionalNum1: num = 2; den = 3;
| 14 | num = 1; den = 3; | num = 1; den = -2; | fractionalNum1.divide(fractionalNum2);| fractionalNum1: num = -2; den = 3; | fractionalNum1: num = -2; den = 3;  
| 15 | num = 1; den = 2; | num = 0; den = 2; | fractionalNum1.divide(fractionalNum2);| IllegalArgumentException; | IllegalArgumentException; 
| 16 | num = 1; den = 3; | num = 1; den = 2; | fractionalNum1.equals(fractionalNum2);| False; | False;
| 17 | num = -1; den = 2; | num = 1; den = -2; | fractionalNum1.equals(fractionalNum2);| True; | True;
| 18 | num = 1; den = -3; | N/A; | fractionalNum1.toString();| "-1/3"; | "-1/3";  
| 19 | num = 1; den = 0; | N/A; | new FractionalNumber(1, 0);| IllegalArgumentException; | IllegalArgumentException;  
| 20 |
