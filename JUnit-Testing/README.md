CMSC	355	 Assignment	3
jUnit	Testing
Note: When you turn in an assignment to be graded in this class you are making the claim that you
neither gave nor received assistance on the work you turned in (except, of course, assistance from the
instructor).
Program Names: FractionalNumber Test.java and FractionalNumber.java
Define a class using Test Driven Development that models a fractional number as a numerator divided
by a denominator. This fractional number can be represented as the quotient of two integers. For
example, ½, ¾, and so forth are all rational numbers (in this case, we mean the mathematical meaning of
the fraction, not the integer division this expression would produce in a Java program.) Represent
rational numbers as two values of type int, one for the numerator, and one for the denominator. Your
class should have two instance variables of type int. The name of the class is FractionalNumber. It will
have a default, parameterless constructor and an overloaded constructor with two arguments that are
used to set the instance variables of an object of the FractionalNumber class to any two integer values.
Note that the numerator, the denominator, or both may be negative. However the denominator cannot
be 0.
You should include a method to normalize the sign of the rational number so that the denominator is
always positive and the numerator is either positive or negative. For example, after normalization, 4/-8
would be the same as –4/8.
Define the following methods (using the names in parenthesis) for addition (add), subtraction (subtract),
multiplication (multiply), and division (divide) of objects of your class, FractionalNumber. Each of these
methods must accept a FractionalNumber object as the single parameter. Arithmetic operations on
fractions are defined by the following rules:
a/b + c/d = (a*d + b*c) / b*d
a/b – c/d = (a*d – b*c) / b*d
a/b * c/d = a*c / d*b
 (a/b) / (c/d) = a*d / b*c, where c/d ≠ 0
These are instance methods with a single argument that do not return a value. The result is a change in
the state of the value of the data members of the calling object. For example, the add method (for
addition) has a calling object and one argument. Therefore, if num1 has a numerator value of 1 and
a denominator value of 2 and num2 has a numerator value of 1 and a denominator value of 4, the
method call,
rationalNum1.add(rationalNum2);
changes the values of the instance variables of num1 so they represent the result of adding num2 to the
original version of num1 (numerator is 3 and denominator is 4). 
CMSC	355	 Assignment	3
Define getter and setter methods for the instance variables and override the equals and toString
methods. Hint: Two rational numbers a/b and c/d are equal if a*d equals c*b.
Your file names should be FractionalNumber.java and FractionalNumberTest.java for
the jUnit test class.
