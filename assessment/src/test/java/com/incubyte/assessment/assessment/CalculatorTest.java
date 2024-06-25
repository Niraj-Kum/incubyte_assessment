package com.incubyte.assessment.assessment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalculatorTest {
	@InjectMocks
	private Calculator calculator;

	@Test
	void addEmptyString() throws NegativNumbersNotAllowedException {
		Assertions.assertEquals(calculator.add(""), 0);
	}
	
	@Test
	void addNullString() throws NegativNumbersNotAllowedException {
		Assertions.assertEquals(calculator.add(null), 0);
	}
	
	@Test
	void addSingleDigit() throws NegativNumbersNotAllowedException {
		Assertions.assertEquals(calculator.add("100"), 100);
		Assertions.assertEquals(calculator.add("26500"), 0);
	}
	
	@Test
	void addTwoDigits() throws NegativNumbersNotAllowedException {
		Assertions.assertEquals(calculator.add("1,2"), 3);
		Assertions.assertEquals(calculator.add	("267,2433"), 267);
	}
	
	@Test
	void addTwoDigitsWithNewLineSeperation() throws NegativNumbersNotAllowedException {
		Assertions.assertEquals(calculator.add("1\n2"), 3);
		Assertions.assertEquals(calculator.add("267\n2433"), 267);
	}
	
	@Test
	void addTwoDigitsWithWrongDelimiter() {
		Assertions.assertThrows(NumberFormatException.class, () -> calculator.add("1;2"));
	}
	
	@Test
	void addMultipleDigits() throws NegativNumbersNotAllowedException {
		Assertions.assertEquals(calculator.add("1,2,3,4,5,6,7,8,9"), 45);
		Assertions.assertEquals(calculator.add("161,43\n31,5\n63,78,9"), 390);
	}
	
	@Test
	void shouldAddUsingNewDelimiter() throws NegativNumbersNotAllowedException {
		Assertions.assertEquals(calculator.add("//;\n1;2;3;4;5;6;7;8;9"), 45);
	}
	
	@Test
	void throwExceptionWhenAddingNegativeNumber() {
		Assertions.assertThrows(NegativNumbersNotAllowedException.class, () -> calculator.add("//;\n-1;-2;3;4;-5;6;-7;8;9"));
	}
	
	@Test
	void shouldIgnoreNumbersGreaterthatThousand() throws NegativNumbersNotAllowedException {
		Assertions.assertEquals(calculator.add("//;\n1000;2000;3000;4;5;6;715;8;9"), 1747);
	}
	
	@Test
	public void longLengthDelimiterTest() throws NegativNumbersNotAllowedException {
		Assertions.assertEquals(calculator.add("//;;;;;;;;;;\n1;;;;;;;;;;2;;;;;;;;;;3"), 6);
	}
	
	@Test
	public void singleBoxDelimiterTest() throws NegativNumbersNotAllowedException {
		int ans = calculator.add("//[;]\n1;2;3");
		Assertions.assertEquals(ans, 6);
	}

	@Test
	public void singleBoxLongDelimiterTest() throws NegativNumbersNotAllowedException {
		int ans = calculator.add("//[;;;;;;;;;;]\n1;;;;;;;;;;2;;;;;;;;;;3");
		Assertions.assertEquals(ans, 6);
	}
	
	@Test
	public void multiBoxMultipleDelimiterTest() throws NegativNumbersNotAllowedException {
		int ans = calculator.add("//[;][%]\n1;2%3");
		Assertions.assertEquals(ans, 6);
	}
	
	@Test
	public void multiBoxMultipleDelimiterWithLongLengthTest() throws NegativNumbersNotAllowedException {
		int ans = calculator.add("//[;;;;;;;;][%%%%%][&&&&&&&]\n1;;;;;;;;2%%%%%3&&&&&&&4");
		Assertions.assertEquals(ans, 10);
	}
}
