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
	void addEmptyString() {
		Assertions.assertEquals(calculator.add(""), 0);
	}
	
	@Test
	void addNullString() {
		Assertions.assertEquals(calculator.add(null), 0);
	}
	
	@Test
	void addSingleDigit() {
		Assertions.assertEquals(calculator.add("100"), 100);
		Assertions.assertEquals(calculator.add("26500"), 26500);
	}
	
	@Test
	void addTwoDigits() {
		Assertions.assertEquals(calculator.add("1,2"), 3);
		Assertions.assertEquals(calculator.add("267,2433"), 2700);
	}
	
	@Test
	void addTwoDigitsWithNewLineSeperation() {
		Assertions.assertEquals(calculator.add("1\n2"), 3);
		Assertions.assertEquals(calculator.add("267\n2433"), 2700);
	}
	
	@Test
	void addTwoDigitsWithWrongDelimiter() {
		Assertions.assertThrows(NumberFormatException.class, () -> calculator.add("1;2"));
	}
	
	@Test
	void addMultipleDigits() {
		Assertions.assertEquals(calculator.add("1,2,3,4,5,6,7,8,9"), 45);
		Assertions.assertEquals(calculator.add("161,43\n31,5\n63,78,9"), 390);
	}
	
	@Test
	void testToAddUsingNewDelimiter() {
		Assertions.assertEquals(calculator.add("//;\n1;2;3;4;5;6;7;8;9"), 45);
	}
}
