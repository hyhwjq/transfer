package com.yjcloud.transfer.mode;

import com.yjcloud.transfer.core.dumper.OSSDumper;
import com.yjcloud.transfer.core.loader.MongoFSLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hhc on 17/9/15.
 */
public class MongoGridFS2OssStrategy implements Strategy {
    private static final Logger LOG = LoggerFactory.getLogger(MongoGridFS2OssStrategy.class);

    @Override
    public void execute() {
        LOG.info("execute mongoGridFS to oss strategy");
        MongoFSLoader mongoFSLoader = new MongoFSLoader();
        mongoFSLoader.load();
        OSSDumper ossDumper = new OSSDumper();
        ossDumper.dump();
    }

}
