package com.yjcloud.transfer.core.loader;

import com.google.common.base.Splitter;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.yjcloud.transfer.core.DataDocker;
import com.yjcloud.transfer.core.Loader;
import com.yjcloud.transfer.core.adapter.MongoAdapter;
import com.yjcloud.transfer.entity.MessageDTO;
import com.yjcloud.transfer.util.config.ConfigureEnum;
import com.yjcloud.transfer.util.config.PropertyConfigurer;
import org.bson.Document;

/**
 * Created by hhc on 17/9/13.
 */
public class MongoLoader extends MongoAdapter implements Loader {
    private Long count = 0L;
    private DataDocker<MessageDTO> docker;

    public MongoLoader(DataDocker<MessageDTO> docker) {
        this.docker = docker;
    }

    @Override
    protected void runTask() {
        LOG.info("mongo loader started.");
        MongoDatabase mdb = mongoClient.getDb();
        String collectionNames = PropertyConfigurer.getProperty(ConfigureEnum.SOURCE_MONGO_COLLECTIONS.getName());
        Iterable<String> collections = Splitter.on(",").split(collectionNames);
        for (String collection : collections) {
            LOG.info("collection {} loading...", collection);
            MongoCollection<Document> mc = mdb.getCollection(collection);
            FindIterable<Document> documents = mc.find();

            MongoCursor<Document> cursor = documents.iterator();
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                LOG.trace("push doc {}", doc);
                docker.pushData(new MessageDTO(collection, doc));
                count++;
            }
        }

        LOG.info("mongo loading completed. Article {} the load data ", count);
    }

    @Override
    public void load() {
        super.start();
    }

}
