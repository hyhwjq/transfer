package com.yjcloud.transfer.core.adapter;

import com.yjcloud.transfer.core.AbstractDocker;
import com.yjcloud.transfer.core.Lifecycle;
import com.yjcloud.transfer.entity.MessageDTO;
import com.yjcloud.transfer.util.config.ConfigureEnum;
import com.yjcloud.transfer.util.config.PropertyConfigurer;
import com.yjcloud.transfer.util.mongodb.MongoDBClient;

/**
 * Created by hhc on 17/9/13.
 */
public abstract class MongoAdapter extends AbstractDocker<MessageDTO> implements Lifecycle, Runnable {
    protected MongoDBClient mongoClient = null;
    private Thread worker;
    private volatile boolean running = false;

    protected abstract void runTask();

    @Override
    public void run() {
        this.runTask();
    }

    @Override
    public void start() {
        if (worker == null){
            this.init();
            worker = new Thread(this);
        }
//        Preconditions.checkArgument(worker != null, "worker uninitialized");
        worker.start();
        running = true;
    }

    @Override
    public void init() {
        String url = PropertyConfigurer.getProperty(ConfigureEnum.SOURCE_MONGO_URL.getName());
        String username = PropertyConfigurer.getProperty(ConfigureEnum.SOURCE_MONGO_USERNAME.getName());
        String password = PropertyConfigurer.getProperty(ConfigureEnum.SOURCE_MONGO_PASSWORD.getName());
        String db = PropertyConfigurer.getProperty(ConfigureEnum.SOURCE_MONGO_DB.getName());

        mongoClient = new MongoDBClient(url, username, password, db);
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void destroy() {
        running = false;
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
