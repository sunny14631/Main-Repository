package fractiontest;

/************************************************************************
 * Name: Rahul Madala
 * Course: CMSC 355
 * Assignment: Assignment 3 - JUnit Testing
 * 
 ************************************************************************/

public class FractionalNumber {
	
	private int num;
	private int den;

	// Default constructor
	public FractionalNumber() {
		num = 1;
		den = 1;
	}
	
	// Constructor takes two arguments. If the denominator is zero an exception is thrown.
	public FractionalNumber(int newNum, int newDen) {
		num = newNum;
		if(newDen == 0) {
			throw new IllegalArgumentException("Denominator cannot be 0");
		}
		else {
			den = newDen;
		}
	}
	
	public int getNum() {
		return num;
	}
	
	public void setNum(int newNum) {
		num = newNum;
	}
	
	public int getDen() {
		return den;
	}
	
	public void setDen(int newDen) {
		den = newDen;
	}
	
	/**
	 * This method normalizes the sign of the fraction by assigning the numerator
	 * to a positive or negative number, and the denominator to a positive integer.
	 * @return
	 */
	public void normSign() {
		if(num < 0 && den < 0) {
			num = Math.abs(num);
			den = Math.abs(den);
		}
		else if(num > 0 && den < 0) {
			num = -(num);
			den = Math.abs(den);
		}
	}
	
	/**
	 * This method adds two fractional numbers. It takes FractionalNumber as an
	 * argument that contains both numerator and denominator values
	 * 
	 * @param frac
	 */
	public void add(FractionalNumber frac) {
		num = num * frac.getDen() + den * frac.getNum();
		den = den * frac.getDen();
		normSign();
	}
	
	/**
	 * This method subtracts two fractional numbers. It takes FractionalNumber as an
	 * argument that contains both numerator and denominator values
	 * 
	 * @param frac
	 */
	public void subtract(FractionalNumber frac) {
		frac.normSign();
		if(frac.getNum() < 0) {
			frac.setNum(Math.abs(frac.getNum()));
			add(frac);
		}
		else {
			num = num * frac.getDen() - den * frac.getNum();
			den = den * frac.getDen();
		}
		normSign();
	}
	
	/**
	 * This method multiplies two fractional numbers. It takes FractionalNumber as an
	 * argument that contains both numerator and denominator values
	 * 
	 * @param frac
	 */
	public void multiply(FractionalNumber frac) {
		num = num * frac.getNum();
		den = den * frac.getDen();
	}
	
	/**
	 * This method divides two fractional numbers. It takes FractionalNumber as an
	 * argument that contains both numerator and denominator values
	 * 
	 * @param frac
	 */
	public void divide(FractionalNumber frac) {
		if(frac.getNum() == 0) {
			throw new IllegalArgumentException("Fraction cannot equal 0.");
		}
		num = num * frac.getDen();
		den = den * frac.getNum();
	}
	
	/**
	 * Checks whether the given object is an instance of FrantionalNumber object
	 */
	public boolean equals(Object other) {
		if(!(other instanceof FractionalNumber)) {
			return false;
		}
		FractionalNumber frac = (FractionalNumber) other;
		if(num * frac.getDen() == den * frac.getNum()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * To string representation of the FractionalNumber object
	 */
	public String toString() {
		normSign();
		if(num == 0) {
			return "0";
		}
		return num + "/" + den;
	}
}