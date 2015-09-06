package com.mirasworks.tools.databases.api;


public interface IDataBaseConnector {

	void connect(String host, int port) throws DataBaseException;

	void disconnect() throws DataBaseException;
}
