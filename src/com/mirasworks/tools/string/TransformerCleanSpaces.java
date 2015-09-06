package com.mirasworks.tools.string;

import com.mirasworks.tools.pattern.pipeline.IPipe;

/**
 * 
 * @author Koda
 * remove double spaces
 */
public class TransformerCleanSpaces implements  IPipe<String, String> {

	
	/**
	 * replace double spaces by one
	 */
	@Override
	public String process(String str) {
		return  str.replaceAll("\\s+", " ");
	}

	/**
	 * replace double spaces by one
	 */
	@Override
	public String processTransform(String str) {
		return process(str);
	}

	
	





}
