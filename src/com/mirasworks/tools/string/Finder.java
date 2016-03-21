package com.mirasworks.tools.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Finder {

    /**
     * extract text between two pattern
     *
     * @param strStart
     * @param strEnd
     * @param inputText
     * @return
     */
    public static String extractFirst(String strStart, String strEnd, String inputText) {

        String regexString = Pattern.quote(strStart) + "(.*?)" + Pattern.quote(strEnd);
        Pattern pattern = Pattern.compile(regexString);

        Matcher matcher = pattern.matcher(inputText);

        while (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public static String replaceBetween(String inputText, String strStart, String replacement, String strEnd) {

        String regexString = Pattern.quote(strStart) + "(.*?)" + Pattern.quote(strEnd);
        return inputText.replaceAll(regexString, replacement);

    }

    public static String replaceNumbersBy(String inputText, String replacement) {
        return inputText.replaceAll("-?\\d+", replacement);
    }

}
