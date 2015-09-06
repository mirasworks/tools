package com.mirasworks.tools.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Finder {

	/**
	 *  extract text between two pattern , it is  thread Safe
	 * @param pattern1
	 * @param pattern2
	 * @param inputText
	 * @return
	 */
	public String extractFirst(String pattern1, String pattern2, String inputText) {

		String regexString = Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2);
		Pattern pattern = Pattern.compile(regexString);
	
		Matcher matcher = pattern.matcher(inputText);

		while (matcher.find()) {
			return  matcher.group(1);
		}
		return "";
	}

}
