package com.mirasworks.tools.commandparser.api;

import com.mirasworks.configuration.api.ConfigurationManager;

public interface ICliApplication {
	/**
	 * process entering command line
	 * 
	 * @param line
	 */
	void parseInputStream(String line);

	/**
	 * 
	 * @return application name
	 */
	String getApplicationName();

	/**
	 * start listening the commands
	 */
	void startListening();

	/**
	 * set the configuration Manager
	 * @param config
	 */
	void setConfigurationManager(ConfigurationManager config);

	void setCommandParser(IArgsCommandParser parser);
}
