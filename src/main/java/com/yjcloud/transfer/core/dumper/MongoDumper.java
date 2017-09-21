package com.yjcloud.transfer.core.dumper;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.yjcloud.transfer.core.DataDocker;
import com.yjcloud.transfer.core.Dumper;
import com.yjcloud.transfer.core.adapter.MongoAdapter;
import com.yjcloud.transfer.entity.MessageDTO;
import com.yjcloud.transfer.util.config.ConfigureEnum;
import com.yjcloud.transfer.util.config.PropertyConfigurer;
import com.yjcloud.transfer.util.mongodb.MongoDBClient;
import org.bson.Document;

/**
 * Created by hhc on 17/9/13.
 */
public class MongoDumper extends MongoAdapter implements Dumper {
    private DataDocker<MessageDTO> docker;

    public MongoDumper(DataDocker<MessageDTO> docker) {
        this.docker = docker;
    }

    @Override
    protected void runTask() {
        MongoDatabase mdb = this.mongoClient.getDb();
        LOG.info("mongo dumper started.");

        while (isRunning()) {
            MessageDTO message = docker.pull();
            MongoCollection<Document> mc = mdb.getCollection(message.getName());
            Document document = message.getData();
            mc.insertOne(document);
            LOG.debug("insert doc: {}", document);
        }
    }

    @Override
    public void init() {
        String url = PropertyConfigurer.getProperty(ConfigureEnum.DESTINATION_MONGO_URL.getName());
        String username = PropertyConfigurer.getProperty(ConfigureEnum.DESTINATION_MONGO_USERNAME.getName());
        String password = PropertyConfigurer.getProperty(ConfigureEnum.DESTINATION_MONGO_PASSWORD.getName());
        String db = PropertyConfigurer.getProperty(ConfigureEnum.DESTINATION_MONGO_DB.getName());

        mongoClient = new MongoDBClient(url, username, password, db);
    }

    @Override
    public void dump() {
        super.start();
    }


}
