package com.mirasworks.tools.commandparser.api;

import com.mirasworks.configuration.api.ConfigurationManager;

public interface ICliApplication {
    /**
    * process entering command line
    * @param line
    */
    void parseInputStream(String line);
    String getApplicationName();
    void startListening();
    void setConfigurationManager(ConfigurationManager config);
    void setCommandParser(IArgsCommandParser parser) ;
}
