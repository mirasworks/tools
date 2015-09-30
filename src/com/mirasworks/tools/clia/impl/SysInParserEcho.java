package com.mirasworks.tools.clia.impl;

import com.mirasworks.tools.commandparser.api.ISysInParser;

/**
 * 
 * @author Damien MIRAS
 * 
 *         <pre>
 * default system parser just return the entered command
 *         </pre>
 *
 */
public class SysInParserEcho implements ISysInParser {

	@Override
	public String parse(String line) {

		System.out.println("echo : " + line);
		return "";
	}

}
