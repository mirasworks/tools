package com.mirasworks.tools.databases.mongo.connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import com.mirasworks.tools.databases.api.DataBaseException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;

public class MongoDbConnector {

	private MongoClient mongoClient = null;

	private static MongoDbConnector instance = null;

	private Map<String, CodecRegistry> codecs = new HashMap<String, CodecRegistry>();

	public static synchronized MongoDbConnector getInstance() {
		if (null == instance) {
			instance = new MongoDbConnector();

		}
		return instance;
	}

	public MongoDbConnector() {

		codecs.put("BSON", MongoClient.getDefaultCodecRegistry());
	}

	public void registerCodec(CollectibleCodec<?> codec) {
		String entityClassName = codec.getEncoderClass().getName();
		if (codecs.containsKey(entityClassName) == false) {
			codecs.put(entityClassName, CodecRegistries.fromCodecs(codec));
		}
	}


	public void connect(String host, int port)  {
		
		List<CodecRegistry> registries = new ArrayList<CodecRegistry>(codecs.values());
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(registries);
		MongoClientOptions options = MongoClientOptions.builder().codecRegistry(codecRegistry).build();
		mongoClient = new MongoClient(host + ":" + port, options);

	}


	public void disconnect() throws DataBaseException {
		mongoClient.close();
	}

	public synchronized MongoDatabase getDataBase(String dbName) {
		MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);

		return mongoDatabase;
	}
}
