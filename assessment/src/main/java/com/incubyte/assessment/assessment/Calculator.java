package com.incubyte.assessment.assessment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Calculator {

	public static final String DEFAULT_DELIMITER = "\n|,";

	public int add(String numbers) {
		int result = 0;
		if (numbers == null || numbers.isEmpty()) {
			return result;
		}
		Map<String, String> formattedString = getFormattedNumbersAndDelimiter(numbers);
		String delimiter = formattedString.get("delimiter");
		String formattedNumbers = formattedString.get("formattedNumber");
		return calculateSum(formattedNumbers, delimiter);
	}

	private int calculateSum(String numbers, String delimiter) {
		String[] digits = numbers.split(delimiter);
		int sum = 0;
		for (String x : digits) {
			sum += Integer.parseInt(x);
		}
		return sum;
	}

	private Map<String, String> getFormattedNumbersAndDelimiter(String numbers) {
		Map<String, String> formattedStrings = new HashMap<>();
		String delimiter = "\n|";
		if (numbers.startsWith("//")) {
			int startIndex = 2;
			int endIndex = numbers.indexOf("\n");
			formattedStrings.put("delimiter", delimiter + numbers.substring(startIndex, endIndex));
			formattedStrings.put("formattedNumber", numbers.substring(endIndex + 1));
		} else {
			formattedStrings.put("delimiter", delimiter + ",");
			formattedStrings.put("formattedNumber", numbers);
		}
		return formattedStrings;
	}

}