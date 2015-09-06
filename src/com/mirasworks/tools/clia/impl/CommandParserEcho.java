package com.mirasworks.tools.clia.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mirasworks.tools.commandparser.api.IArgsCommandParser;

/**
 *
 * @author damien Simple command parser it echo and doe nothing it is a default
 *         implementation as sample
 */
public class CommandParserEcho implements IArgsCommandParser {
    @SuppressWarnings("unused")
    private static Logger l = LoggerFactory.getLogger(CommandParserEcho.class);


    @Override
    public String parseCmd(String[] args) {
        System.out.println("args received");
        return "";
    }


}
