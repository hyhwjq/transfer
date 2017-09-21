package com.yjcloud.transfer.mode;

import com.yjcloud.transfer.core.DataDocker;
import com.yjcloud.transfer.core.dumper.MongoDumper;
import com.yjcloud.transfer.core.loader.MongoLoader;
import com.yjcloud.transfer.entity.MessageDTO;
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
        DataDocker<MessageDTO> docker = new DataDocker<>();

        MongoLoader mongoLoader = new MongoLoader(docker);
        mongoLoader.load();
        MongoDumper mongoDumper = new MongoDumper(docker);
        mongoDumper.dump();
    }

}
