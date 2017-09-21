package com.yjcloud.transfer.test;

import com.yjcloud.transfer.core.DataDocker;
import com.yjcloud.transfer.core.dumper.MongoDumper;
import com.yjcloud.transfer.core.dumper.OSSDumper;
import com.yjcloud.transfer.core.loader.MongoFSLoader;
import com.yjcloud.transfer.core.loader.MongoLoader;
import com.yjcloud.transfer.entity.MessageDTO;

/**
 * Created by hhc on 17/9/13.
 */
public class Test {

    @org.junit.Test
    public void testMongo2Mongo() throws Exception {
        DataDocker<MessageDTO> docker = new DataDocker<>();

        MongoLoader mongoLoader = new MongoLoader(docker);
        mongoLoader.load();
        MongoDumper mongoDumper = new MongoDumper(docker);
        mongoDumper.dump();

        while (true) {
            Thread.sleep(100000L);
        }

    }

    @org.junit.Test
    public void testMongoGridFS2OSS() throws Exception {
        DataDocker<MessageDTO> docker = new DataDocker<>();

        MongoFSLoader mongoFSLoader = new MongoFSLoader(docker);
        mongoFSLoader.load();

        OSSDumper ossDumper = new OSSDumper(docker);
        ossDumper.dump();

        while (true) {
            Thread.sleep(100000L);
        }

    }

}
