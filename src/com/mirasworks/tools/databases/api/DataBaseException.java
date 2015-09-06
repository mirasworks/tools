package com.mirasworks.tools.databases.api;

import java.net.UnknownHostException;

public class DataBaseException extends Exception {

	private static final long serialVersionUID = -6175943453370059385L;

	public DataBaseException(UnknownHostException e) {
		super(e);
	}

}
