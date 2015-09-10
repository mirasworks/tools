package com.mirasworks.tools.clia.impl;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mirasworks.tools.commandparser.api.IArgsCommandParser;

public abstract class CliCommandParser implements IArgsCommandParser {
    @SuppressWarnings("unused")
    private static Logger     l       = LoggerFactory.getLogger(CliCommandParser.class);

    protected Options         options = new Options();

    private CommandLineParser parser;
    private HelpFormatter     help;

    /**
     * here add options like that options.addOption("start", false,
     * "start the server");
     */
    protected abstract void initOptions();

    /**
     * here do things when receive commands if (cmd.hasOption("start")) {
     *
     * }
     *
     * @param cmd
     */
    protected abstract void internalParseCmd(CommandLine cmd);

    public CliCommandParser() {
        parser = new GnuParser();
        help = new HelpFormatter();
        initOptions();
    }

    /**
     * process command from the command line launch
     */
    @Override
    public String parseCmd(String[] args) {

        try {
            CommandLine cmd = parser.parse(options, args);
            internalParseCmd(cmd);
        } catch (ParseException e1) {
            help.printHelp("--NO HELP--", options);

        }

        return "";
    }

}
