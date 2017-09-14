package com.yjcloud.transfer;

import com.yjcloud.transfer.application.Application;
import com.yjcloud.transfer.core.dumper.MongoDumper;
import com.yjcloud.transfer.core.dumper.OSSDumper;
import com.yjcloud.transfer.core.loader.MongoFSLoader;
import com.yjcloud.transfer.core.loader.MongoLoader;
import com.yjcloud.transfer.util.config.PropertyConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceProvider {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceProvider.class);

    public static void main(String[] args) throws Exception {
        Application.getInstance().getContext().start();
        String mode = PropertyConfigurer.getProperty("run.mode");

        if (mode.equals("0")){
            MongoLoader mongoLoader = new MongoLoader();
            mongoLoader.load();
            MongoDumper mongoDumper = new MongoDumper();
            mongoDumper.dump();
        } else if (mode.equals("1")){
            MongoFSLoader mongoFSLoader = new MongoFSLoader();
            mongoFSLoader.load();
            OSSDumper ossDumper = new OSSDumper();
            ossDumper.dump();
        }
        LOG.info("服务实例启动完毕...");

        while (true) {
            Thread.sleep(10000);
        }
    }
}
