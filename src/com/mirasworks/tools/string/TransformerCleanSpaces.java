package com.mirasworks.tools.string;

/**
 * 
 * Damien MIRAS remove double spaces
 */
public class TransformerCleanSpaces  {

	/**
	 * replace double spaces by one
	 */
	public String process(String str) {
		return str.replaceAll("\\s+", " ");
	}

	/**
	 * replace double spaces by one
	 */
	public String processCast(String str) {
		return process(str);
	}

}
