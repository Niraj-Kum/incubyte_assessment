package com.incubyte.assessment.assessment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import org.springframework.stereotype.Component;

@Component
public class Calculator {

	public int add(String numbers) throws NegativeNumbersNotAllowedException {
		if (numbers == null || numbers.isEmpty()) {
			return 0;
		}
		Map<String, String> formattedString = getFormattedNumbersAndDelimiter(numbers);
		String delimiter = formattedString.get(Constants.DELIMITER);
		String formattedNumbers = formattedString.get(Constants.FORMATTED_NUMBER);
		return calculateSum(formattedNumbers, delimiter);
	}

	private int calculateSum(String numbers, String delimiter) throws NegativeNumbersNotAllowedException {
		String[] digits = numbers.split(delimiter);
		int sum = 0;
		ArrayList<String> negativeNumbers = new ArrayList<>();
		for (String x : digits) {
			int value = Integer.parseInt(x);
			if (value < 0) {
				negativeNumbers.add(x);
			}
			if (value <= Constants.MAX_VALUE) {
				sum += value;
			}
		}
		if (!negativeNumbers.isEmpty()) {
			StringJoiner stringJoiner = new StringJoiner(Constants.COMMA);
			negativeNumbers.forEach(stringJoiner::add);
			throw new NegativeNumbersNotAllowedException(Constants.NEGATIVE_NUMBER_EXCEPTION_MESSAGE + " "
					+ Constants.OPEN_BRACKET + stringJoiner.toString() + Constants.CLOSE_BRACKET);
		}
		return sum;
	}

	private Map<String, String> getFormattedNumbersAndDelimiter(String numbers) {
		Map<String, String> formattedStrings = new HashMap<>();
		String delimiter = "";
		if (numbers.startsWith(Constants.NEW_DELIMITER_FORWARD_BACKSLASH)) {
			int startIndex = Constants.NEW_DELIMITER_FORWARD_BACKSLASH.length();
			int endIndex = numbers.indexOf(Constants.NEW_LINE);
			formattedStrings.put(Constants.DELIMITER,
					validateAndFormat(delimiter + numbers.substring(startIndex, endIndex)));
			formattedStrings.put(Constants.FORMATTED_NUMBER, numbers.substring(endIndex + 1));
		} else {
			formattedStrings.put(Constants.DELIMITER, validateAndFormat(Constants.COMMA));
			formattedStrings.put(Constants.FORMATTED_NUMBER, numbers);
		}
		return formattedStrings;
	}

	private String validateAndFormat(String delimiter) {
		if (delimiter.startsWith(Constants.OPEN_BRACKET) && delimiter.endsWith(Constants.CLOSE_BRACKET)) {
			String delimiters[] = delimiter.split(Constants.NEW_DELIMITER_BACKWARD_BACKSLASH + Constants.CLOSE_BRACKET);
			String finalDelimiter = Constants.NEW_LINE;
			for (String x : delimiters) {
				if (!x.isEmpty()) {
					finalDelimiter += Constants.PIPE + x.substring(0, x.length() - 1);
				}
			}
			return finalDelimiter;
		}
		return Constants.NEW_LINE + Constants.PIPE + delimiter;
	}

}