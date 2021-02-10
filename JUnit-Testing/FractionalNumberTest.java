package fractiontest;

/************************************************************************
 * Name: Rahul Madala
 * Course: CMSC 355
 * Assignment: Assignment 3 - JUnit Testing
 * 
 ************************************************************************/
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FractionalNumberTest {

	void fractionException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			FractionalNumber fraction = new FractionalNumber(1, 0);
			});
	}
	
	/**
	 * This method tests the addition of two positive fraction numbers
	 */
	@Test
	void addPositivesTest() {
		FractionalNumber fraction = new FractionalNumber(1, 3);
		fraction.add(new FractionalNumber(1, 2));
		
		FractionalNumber expectedResult = new FractionalNumber(5, 6);
		assertEquals(fraction, expectedResult);
	}
	
	/**
	 * This method tests the addition of one positive and one negative fraction numbers
	 */
	@Test
	void addPosAndNegTest() {
		FractionalNumber fraction = new FractionalNumber(1, 2);
		fraction.add(new FractionalNumber(-1, 3));
		
		FractionalNumber expectedResult = new FractionalNumber(1, 6);
		assertEquals(fraction, expectedResult);
	}
	
	/**
	 * This method tests the addition of one negative and one positive fraction numbers
	 */
	@Test
	void addNegAndPosTest() {
		FractionalNumber fraction = new FractionalNumber(-3, 4);
		fraction.add(new FractionalNumber(1, 2));
		
		FractionalNumber expectedResult = new FractionalNumber(-1, 4);
		assertEquals(fraction, expectedResult);
	}
	
	/**
	 * This method tests the addition of two negative fraction numbers
	 */
	@Test
	void addNegativesTest() {
		FractionalNumber fraction = new FractionalNumber(1, -4);
		fraction.add(new FractionalNumber(-1, 2));
		
		FractionalNumber expectedResult = new FractionalNumber(-3, 4);
		assertEquals(fraction, expectedResult);
	}
	
	/**
	 * This method tests the subtraction of two positive fraction numbers
	 */
	@Test
	void subPositivesTest() {
		FractionalNumber fraction = new FractionalNumber(3, 4);
		fraction.subtract(new FractionalNumber(1, 2));
		
		FractionalNumber expectedResult = new FractionalNumber(1, 4);
		assertEquals(fraction, expectedResult);
	}
	
	/**
	 * This method tests the subtraction of one positive and one negative fraction numbers
	 */
	@Test
	void subPosAndNegTest() {
		FractionalNumber fraction = new FractionalNumber(1, 4);
		fraction.subtract(new FractionalNumber(1, -2));
		
		FractionalNumber expectedResult = new FractionalNumber(3, 4);
		assertEquals(fraction, expectedResult);
	}

	/**
	 * This method tests the subtraction of one negative and one positive fraction numbers
	 */
	@Test
	void subNegAndPosTest() {
		FractionalNumber fraction = new FractionalNumber(-1, 4);
		fraction.subtract(new FractionalNumber(1, 2));
		
		FractionalNumber expectedResult = new FractionalNumber(-3, 4);
		assertEquals(fraction, expectedResult);
	}
	
	/**
	 * This method tests the subtraction of two negative fraction numbers
	 */
	@Test
	void subNegativesTest() {
		FractionalNumber fraction = new FractionalNumber(-1, 4);
		fraction.subtract(new FractionalNumber(-1, 2));
		
		FractionalNumber expectedResult = new FractionalNumber(1, 4);
		assertEquals(fraction, expectedResult);
	}
	
	/**
	 * This method tests the multiplication of two positive fraction numbers
	 */
	@Test
	void multPostivesTest() {
		FractionalNumber fraction = new FractionalNumber(1, 4);
		fraction.multiply(new FractionalNumber(1, 2));
			
		FractionalNumber expectedResult = new FractionalNumber(1, 8);
		assertEquals(fraction, expectedResult);
	}

	/**
	 * This method tests the multiplication of one positive and one negative fraction numbers
	 */
	@Test
	void multPosAndNegTest() {
		FractionalNumber fraction = new FractionalNumber(1, 4);
		fraction.multiply(new FractionalNumber(1, -2));
			
		FractionalNumber expectedResult = new FractionalNumber(-1, 8);
		assertEquals(fraction, expectedResult);
	}
	
	/**
	 * This method tests the multiplication of one negative and one positive fraction numbers
	 */
	@Test
	void multNegativesTest() {
		FractionalNumber fraction = new FractionalNumber(-1, 4);
		fraction.multiply(new FractionalNumber(1, -2));
			
		FractionalNumber expectedResult = new FractionalNumber(1, 8);
		assertEquals(fraction, expectedResult);
	}
	
	/**
	 * This method tests the division of two positive fraction numbers
	 */
	@Test
	void dividePositivesTest() {
		FractionalNumber fraction = new FractionalNumber(1, 3);
		fraction.divide(new FractionalNumber(1, 2));
			
		FractionalNumber expectedResult = new FractionalNumber(2, 3);
		assertEquals(fraction, expectedResult);
	}
	
	/**
	 * This method tests the division of two negative fraction numbers
	 */
	@Test
	void divideNegativesTest() {
		FractionalNumber fraction = new FractionalNumber(-1, 3);
		fraction.divide(new FractionalNumber(1, -2));
			
		FractionalNumber expectedResult = new FractionalNumber(2, 3);
		assertEquals(fraction, expectedResult);
	}
	
	/**
	 * This method tests the division of one positive and one negative fraction numbers
	 */
	@Test
	void dividePosAndNegTest() {
		FractionalNumber fraction = new FractionalNumber(1, 3);
		fraction.divide(new FractionalNumber(1, -2));
			
		FractionalNumber expectedResult = new FractionalNumber(-2, 3);
		assertEquals(fraction, expectedResult);
	}
	
	/**
	 * This method tests the divide by zero of fraction numbers
	 */
	@Test
	void divideZeroTest() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			FractionalNumber fraction = new FractionalNumber(1, 2);
			fraction.divide(new FractionalNumber(0, 2));
			});
	}
	
	/**
	 * This method tests the two fraction numbers equal test
	 */
	@Test
	void equalsTest() {
		FractionalNumber fraction = new FractionalNumber(-1, 2);
		FractionalNumber otherFraction = new FractionalNumber(1, -2);
		assertTrue(fraction.equals(otherFraction));
	}
	
	/**
	 * This method tests the two fraction numbers not equal test
	 */
	@Test
	void equalsFalseTest() {
		FractionalNumber fraction = new FractionalNumber(1, 3);
		FractionalNumber otherFraction = new FractionalNumber(1, 2);
		assertFalse(fraction.equals(otherFraction));
	}
	
	/**
	 * This method tests the toString representation of th Fraction number object
	 */
	@Test
	void toStringTest() {
		FractionalNumber fraction = new FractionalNumber(1, -3);
		assertEquals(fraction.toString(), "-1/3");
	}
}
