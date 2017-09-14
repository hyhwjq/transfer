package com.yjcloud.transfer.util.mongodb;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MongoDBClient {
	
	private String url;
	private String username;
	private String password;
	private String database;

	private MongoClient mongo;
	private MongoDatabase mdb;
	
	public MongoDBClient(String url, String username, String password, String db) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.database = db;
		
		this.init();
	}
	
	public void init() {
		String[] addrs = url.split(",");
		List<ServerAddress> serverList = new ArrayList<ServerAddress>(addrs.length);
		for (String addr: addrs) {
			String[] hostport = addr.split(":");
			serverList.add(new ServerAddress(hostport[0], Integer.parseInt(hostport[1])));
		}

		MongoCredential credential = MongoCredential.createCredential(username, 
				database, password.toCharArray());


		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			this.mongo = new MongoClient(serverList, getOptions());
		} else {
			this.mongo = new MongoClient(serverList, Arrays.asList(credential), getOptions());
		}
		this.mdb = mongo.getDatabase(database);
	}
	
	public MongoClientOptions getOptions() {
		return new MongoClientOptions.Builder().socketKeepAlive(false) // 是否保持长链接
		.connectTimeout(5000) // 链接超时时间
		.socketTimeout(20*1000) // read数据超时时间
		.readPreference(ReadPreference.primary()) // 最近优先策略
		.connectionsPerHost(30) // 每个地址最大请求数
		.maxWaitTime(1000 * 60 * 2) // 长链接的最大等待时间
		.threadsAllowedToBlockForConnectionMultiplier(50) // 一个socket最大的等待请求数
		.writeConcern(WriteConcern.UNACKNOWLEDGED).build();
	}
	
	public MongoClient getMongo() {
		return mongo;
	}

	public void setMongo(MongoClient mongo) {
		this.mongo = mongo;
	}

	public MongoDatabase getDb() {
		return mdb;
	}

	public void setDb(MongoDatabase db) {
		this.mdb = db;
	}

	public String getDatabase() {
		return database;
	}
	
	public void close() {
		if (mongo != null) {
			try {
				mongo.close();
			} catch (Exception e) {
			}
		}
		
	}
}
