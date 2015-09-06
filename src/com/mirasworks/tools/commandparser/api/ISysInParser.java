package com.mirasworks.tools.commandparser.api;



public interface ISysInParser {
    /**
     * Parse the command and return result as a string
     * @param line
     * @return String command result
     */
    String parse(String line);
}
