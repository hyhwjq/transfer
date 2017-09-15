package com.yjcloud.transfer;

import com.yjcloud.transfer.application.Application;
import com.yjcloud.transfer.mode.ActionMan;
import com.yjcloud.transfer.mode.Mongo2MongoStrategy;
import com.yjcloud.transfer.mode.MongoGridFS2OssStrategy;
import com.yjcloud.transfer.mode.Strategy;
import com.yjcloud.transfer.util.config.ConfigureEnum;
import com.yjcloud.transfer.util.config.PropertyConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceProvider {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceProvider.class);

    public static void main(String[] args) throws Exception {
        Application.getInstance().getContext().start();
        String mode = PropertyConfigurer.getProperty(ConfigureEnum.RUN_MODE.getName());

        Strategy strategy = null;
        if (ConfigureEnum.RUN_MODE_MONGO_TO_MONGO.getName().equals(mode)) {
            strategy = new Mongo2MongoStrategy();
        } else if (ConfigureEnum.RUN_MODE_MONGO_GRID_FS_TO_OSS.getName().equals(mode)) {
            strategy = new MongoGridFS2OssStrategy();
        }

        if (strategy == null) {
            LOG.info("does not support the mode.");
            return;
        }

        ActionMan actionMan = new ActionMan(strategy);
        actionMan.goToBattle();
        LOG.info("服务实例启动完毕...");

        while (true) {
            Thread.sleep(10000L);
        }
    }
}
