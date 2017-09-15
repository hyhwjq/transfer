package com.yjcloud.transfer.mode;

import com.yjcloud.transfer.core.dumper.MongoDumper;
import com.yjcloud.transfer.core.loader.MongoLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hhc on 17/9/15.
 */
public class Mongo2MongoStrategy implements Strategy {
    private static final Logger LOG = LoggerFactory.getLogger(Mongo2MongoStrategy.class);

    @Override
    public void execute() {
        LOG.info("execute mongo to mongo strategy");
        MongoLoader mongoLoader = new MongoLoader();
        mongoLoader.load();
        MongoDumper mongoDumper = new MongoDumper();
        mongoDumper.dump();
    }

}
