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
}
