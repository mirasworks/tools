package com.mirasworks.tools.clia.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mirasworks.tools.commandparser.api.ISysInParser;


public class SysInParserEcho implements ISysInParser {
    @SuppressWarnings("unused")
    private static Logger l = LoggerFactory.getLogger(SysInParserEcho.class);

    @Override
    public String parse(String line) {

        System.out.println("echo : " + line);
        return "";
    }


}
