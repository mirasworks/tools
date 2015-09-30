package com.mirasworks.tools.clia.impl;

import com.mirasworks.tools.commandparser.api.IArgsCommandParser;

/**
 *
 * @author damien
 * 
 *         <pre>
 *         Simple command parser it echo and doe nothing it is a default
 *         implementation as sample
 * 
 *         <pre>
 */
public class CommandParserEcho implements IArgsCommandParser {

	@Override
	public String parseCmd(String[] args) {
		System.out.println("args received");
		return "";
	}

}
