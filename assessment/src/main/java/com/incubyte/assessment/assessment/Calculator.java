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
		String skip = checkForEvenOddPattern(numbers);
		Map<String, String> formattedString = getFormattedNumbersAndDelimiter(numbers, skip);
		String delimiter = formattedString.get(Constants.DELIMITER);
		String formattedNumbers = formattedString.get(Constants.FORMATTED_NUMBER);
		return calculateSum(formattedNumbers, delimiter, skip);
	}
	
	String checkForEvenOddPattern(String numbers) {
		String skip = "";
		if(numbers.startsWith("//")) {
			String arr[] = numbers.split("\n");
			if(arr[0].contains("1")) {
				skip = "odd";
			}else if(arr[0].contains("0")) {
				skip = "even";
			}
		}
		return skip;
	}

	private int getSkipValue(String skip) {
		if(skip.equals("odd")) {
			return 0;
		} else if(skip.equals("even")) {
			return 1;
		} else {
			return -1;
		}
	}
	
	private int addBasedOnSkip(int index, int value, int skipValue) {
		if(skipValue == -1 || skipValue == index % 2){
			return value;
		} else {
			return 0;
		}
	}
	
	private int calculateSum(String numbers, String delimiter, String skip) throws NegativeNumbersNotAllowedException {
		boolean doMultiplication = (delimiter.contains("\\*"));
		int skipValue = getSkipValue(skip);
		String[] digits = numbers.split(delimiter);
		int result = doMultiplication ? 1 : 0;
		ArrayList<String> negativeNumbers = new ArrayList<>();
		for (int i = 0; i < digits.length; i++) {
			String x = digits[i];
			int value = Integer.parseInt(x);
			if (value < 0) {
				negativeNumbers.add(x);
			}
			if (doMultiplication) {
				result = result * value;
			} else if(value <= Constants.MAX_VALUE) {
				result += addBasedOnSkip(i, value, skipValue);
			}
		}
		if (!negativeNumbers.isEmpty()) {
			StringJoiner stringJoiner = new StringJoiner(Constants.COMMA);
			negativeNumbers.forEach(stringJoiner::add);
			throw new NegativeNumbersNotAllowedException(Constants.NEGATIVE_NUMBER_EXCEPTION_MESSAGE + " "
					+ Constants.OPEN_BRACKET + stringJoiner.toString() + Constants.CLOSE_BRACKET);
		}
		return result;
	}

	String removeSkipIfPresent(String numbers, String skip) {
		String arr[] = numbers.split("\n");
		String result = "";
		if(arr[0].contains(skip)) {
			result = arr[0].replace(skip, "");
		}
		for(int i = 1; i < arr.length; i++) {
			result += arr[i];
		}
		return result;
	}
	
	private Map<String, String> getFormattedNumbersAndDelimiter(String numbers, String skip) {
		numbers = removeSkipIfPresent(numbers, skip);
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
			String delimiters[] = delimiter.split(Constants.NEW_DELIMITER_BACKWARD_BACKSLASH + Constants.OPEN_BRACKET);
			String finalDelimiter = Constants.NEW_LINE;
			for (String x : delimiters) {
				if (!x.isEmpty()) {
					finalDelimiter += Constants.PIPE + x.substring(0, x.length() - 1);
				}
			}
			return finalDelimiter;
		}
		if(delimiter.contains("*")) {
			delimiter = Constants.NEW_LINE + Constants.PIPE + "\\*";
		} else {
			delimiter = Constants.NEW_LINE + Constants.PIPE + delimiter;			
		}
		return delimiter;
	}
}