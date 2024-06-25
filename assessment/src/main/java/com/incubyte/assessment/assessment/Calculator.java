package com.incubyte.assessment.assessment;

import org.springframework.stereotype.Component;

@Component
public class Calculator {
	
	public static final String DEFAULT_DELIMITER = "\n|,";
	
	public int add(String numbers) {
		int result = 0;
		if(numbers == null || numbers.isEmpty()) {
			return result;
		}
		return calculateSum(numbers);
	}
	
	public int calculateSum(String numbers) {
		String[] digits = numbers.split(DEFAULT_DELIMITER);
		int sum = 0;
		for(String x: digits) {
			sum += Integer.parseInt(x);
		}
		return sum;
	}
	
	
}