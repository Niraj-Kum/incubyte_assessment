package com.incubyte.assessment.assessment;

import org.springframework.stereotype.Component;

@Component
public class Calculator {
	public int add(String numbers) {
		int result = 0;
		if(numbers == null || numbers.isEmpty()) {
			return result;
		}
		return Integer.parseInt(numbers);
	}
}