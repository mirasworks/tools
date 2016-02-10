package com.mirasworks.tools.databases.mongo.connector;

import com.mirasworks.tools.databases.api.DataBaseException;
import com.mirasworks.tools.databases.api.IDataBaseConnector;
//import com.mongodb.DB;
//import com.mongodb.Mongo;

public class MongoDbConnector implements IDataBaseConnector {

   // private static Mongo mongo;

    private static MongoDbConnector instance = new MongoDbConnector();


    public static synchronized MongoDbConnector getInstance() {
        return instance;
    }
    @Override
    public void connect(String host, int port) throws DataBaseException {
//        try {
//            mongo = new Mongo(host, port);
//        } catch (UnknownHostException e) {
//            throw new DataBaseException(e);
//        }

    }

    @Override
    public void disconnect() throws DataBaseException {
      //  mongo.close();
    }

//    public synchronized DB getDb(String dbName) {
//        DB db = mongo.getDB(dbName);
//
//        return db;
//    }
}
