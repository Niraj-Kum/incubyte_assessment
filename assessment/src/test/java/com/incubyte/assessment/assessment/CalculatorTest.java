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
}
