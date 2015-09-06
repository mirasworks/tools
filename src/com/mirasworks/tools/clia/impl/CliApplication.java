package com.mirasworks.tools.clia.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mirasworks.configuration.api.ConfigurationManager;
import com.mirasworks.tools.commandparser.api.IArgsCommandParser;
import com.mirasworks.tools.commandparser.api.ICliApplication;
import com.mirasworks.tools.commandparser.api.ISysInParser;

public abstract class CliApplication implements ICliApplication {


    private IArgsCommandParser   commandParser;
    private ISysInParser         sysInParser;

    private ConfigurationManager config = null;

    @Override
    public abstract String getApplicationName();

    public void setSysInParser(ISysInParser parser) {
        sysInParser = parser;
    }

    public void setCommandParser(IArgsCommandParser parser) {
        commandParser = parser;
    }


    /**
     * blocking while waiting for command... implement a thread instead
     */
    @Override
    public void startListening() {
        System.err.println(getApplicationName() + " starting ");

        BufferedReader buff = null;
        InputStreamReader inputStream = null;
        try {

            inputStream = new InputStreamReader(System.in, "UTF-8");
            buff = new BufferedReader(inputStream);
            String line = "";

            System.err.println(getApplicationName() + " waiting for commands");


            while (line.equalsIgnoreCase("q") == false) {
                Thread.sleep(100);

                try {
                    parseInputStream(line);

                } catch (Exception e) {
                    System.err.println("Command end with errors " + e.getMessage());
                    e.printStackTrace();
                }
                line = buff.readLine();

            }
            System.out.println(getApplicationName() + " quit bye bye");
        } catch (InterruptedException e) {
        	System.err.println("core application starting has a problem ; application cannot start");
            e.printStackTrace();
        } catch (Exception e) {
        	System.err.println("core application starting has a problem ; application cannot start");
            e.printStackTrace();
        } finally {
            if (buff != null) {
                try {
                    buff.close();
                } catch (IOException e) {
                    System.err.println("buffer stream won't close " + e.getMessage());
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                	e.printStackTrace();
                    System.err.println("input stream won't close " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void parseInputStream(String line) {
        sysInParser.parse(line);
    }

    public void processCommand(String[] args) {
        commandParser.parseCmd(args);

    }

    protected boolean initApp(ConfigurationManager config) {
        setConfigurationManager(config);
        return (this.config.initConfiguration());
    }

    @Override
    public void setConfigurationManager(ConfigurationManager config) {
        this.config = config;

    }


}
