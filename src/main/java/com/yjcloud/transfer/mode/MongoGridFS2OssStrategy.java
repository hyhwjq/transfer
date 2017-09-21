package com.yjcloud.transfer.mode;

import com.yjcloud.transfer.core.DataDocker;
import com.yjcloud.transfer.core.dumper.OSSDumper;
import com.yjcloud.transfer.core.loader.MongoFSLoader;
import com.yjcloud.transfer.entity.MessageDTO;
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
        DataDocker<MessageDTO> docker = new DataDocker<>();

        MongoFSLoader mongoFSLoader = new MongoFSLoader(docker);
        mongoFSLoader.load();
        OSSDumper ossDumper = new OSSDumper(docker);
        ossDumper.dump();
    }

}
